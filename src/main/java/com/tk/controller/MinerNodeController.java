package com.tk.controller;

import com.tk.domain.Node;
import com.tk.domain.enums.ConfigKeys;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.exception.DecAuthSimException;
import com.tk.domain.transaction.BlockchainTransaction;
import com.tk.domain.transaction.SPTransaction;
import com.tk.domain.transaction.SRTransaction;
import com.tk.service.CryptoService;
import com.tk.service.NodeService;
import com.tk.service.TransactionService;
import com.tk.service.util.CommonUtils;
import com.tk.service.util.Config;
import com.tk.view.MinerNodeView;

import java.util.Date;

/**
 * MinerNodeController
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class MinerNodeController implements NodeController {
    TransactionService transactionService;
    MinerNodeView minerNodeView;

    public MinerNodeController() {
        transactionService = new TransactionService();
        minerNodeView = new MinerNodeView();
    }

    public void start(Node node) throws DecAuthSimException, InterruptedException {
        //TODO: Handle method restart
        NodeService nodeService = new NodeService(NodeRole.MINER_NODE);

        // Wait for authentication request
        minerNodeView.showBanner(node);
        SRTransaction srTransaction = transactionService.getLatestReqAuthTransaction();
        while (srTransaction == null) {
            CommonUtils.sync();
            srTransaction = transactionService.getLatestReqAuthTransaction();
        }

        // Authentication is requested
        minerNodeView.printAuthenticationRequested();
        if(trxVerifyStep1(srTransaction) && trxVerifyStep2(srTransaction) && trxVerifyStep3(srTransaction)) {
            minerNodeView.printTrxAuthenticated();
            srTransaction.setStatus(TransactionStatus.AUTHENTICATED);
            transactionService.saveOrUpdate(srTransaction);
        } else {
            minerNodeView.printTrxNotAuthenticated();
            srTransaction.setStatus(TransactionStatus.UNAUTHENTICATED);
            transactionService.saveOrUpdate(srTransaction);
            return;
        }

        // Wait for the service to be provided
        minerNodeView.printWaitServiceProvided();
        int timeWaited = 0;
        while (!transactionService.hasStatus(srTransaction, TransactionStatus.SERVICE_PROVIDED)) {
            CommonUtils.sync();
            timeWaited += Config.readInt(ConfigKeys.SYNC_TIMEOUT);
            if(timeWaited >= Config.readInt(ConfigKeys.WAIT_TIMEOUT)) {
                break;
            }
        }

        // Update miner reputation
        BlockchainTransaction bcTransaction = transactionService
                .convertToOtherTransaction(srTransaction, TransactionType.BLOCKCHAIN_TRANSACTION);
        bcTransaction.setMinerReputation(node.getReputation());
        transactionService.saveOrUpdate(bcTransaction);

        // Wait for Service provided & received confirmation
        minerNodeView.printServiceConfirmation();
        timeWaited = 0;
        while (!transactionService.isServiceConfirmationSet(bcTransaction)) {
            CommonUtils.sync();
            timeWaited += Config.readInt(ConfigKeys.SYNC_TIMEOUT);
            if(timeWaited >= Config.readInt(ConfigKeys.WAIT_TIMEOUT)) {
                break;
            }
        }

        // Reward & Punish
        minerNodeView.printRewardAndPunish();
        bcTransaction = transactionService.setRewardOrPunishment(bcTransaction);

        // Update transaction data
        SPTransaction spTransaction = transactionService.convertToOtherTransaction(bcTransaction, TransactionType.SP_TRANSACTION);
        String signatureData = node.getPublicKey() + srTransaction.getRequestSignedData() + spTransaction.getResultSignedData();
        bcTransaction.setMinerPublicKey(node.getPublicKey());
        bcTransaction.setBlockchainSignedData(CryptoService.digitallySign(signatureData, node.getPrivateKey()));
        bcTransaction.setBlockchainTimeStamp(new Date());
        bcTransaction.setProofOfWork(CryptoService.getProofOfWork(bcTransaction.getBlockchainSignedData()));
        bcTransaction.setStatus(TransactionStatus.BLOCKCHAINED);

        // Proof-of-Work & Blockchain
        minerNodeView.printProofOfWork(bcTransaction.getProofOfWork());
        transactionService.saveOrUpdate(bcTransaction);

        // Update reputation
        double latestRepuation = bcTransaction.getMinerReputationOnBlockchain();
        node.setReputation(latestRepuation);
        nodeService.saveOrUpdate(node);
        minerNodeView.printUpdatedReputation(latestRepuation);
    }

    private boolean trxVerifyStep1(SRTransaction srTransaction) {
        boolean transactionIntegrity = transactionService.verifyTransactionIntegrity(srTransaction);
        minerNodeView.printVerifyIntegrity(transactionIntegrity);
        return transactionIntegrity;
    }

    private boolean trxVerifyStep2(SRTransaction srTransaction) {
        boolean transactionReputationRequired = transactionService.verifyTransactionReqReputation(srTransaction);
        minerNodeView.printVerifyRequestReputation(transactionReputationRequired, srTransaction.getSrReputation());
        return transactionReputationRequired;
    }

    private boolean trxVerifyStep3(SRTransaction srTransaction) {
        boolean reputationOnBlockchain = transactionService.verifyTransactionReputationOnBlockchain(srTransaction);
        minerNodeView.printVerifyReputationBlockchain(reputationOnBlockchain);
        return reputationOnBlockchain;
    }
}
