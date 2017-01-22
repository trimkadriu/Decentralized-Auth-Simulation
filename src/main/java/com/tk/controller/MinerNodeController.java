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

import java.util.Date;

/**
 * MinerNodeController
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class MinerNodeController implements NodeController {
    TransactionService transactionService;

    public MinerNodeController() {
        transactionService = new TransactionService();
    }

    public void start(Node node) throws DecAuthSimException, InterruptedException {
        NodeService nodeService = new NodeService(NodeRole.MINER_NODE);

        //TODO: Handle method restart
        System.out.println("╔════════════════════════╗");
        System.out.println("║          Miner NODE Started          ║");
        System.out.println("╚════════════════════════╝");

        System.out.println("--> Waiting for an Authentication Request");
        SRTransaction srTransaction = transactionService.getLatestReqAuthTransaction();
        while (srTransaction == null) {
            CommonUtils.sync();
            srTransaction = transactionService.getLatestReqAuthTransaction();
        }
        System.out.println("--> Authentication is requested.");

        System.out.println("--> Verifying the transaction.");
        if(trxVerifyStep1(srTransaction) && trxVerifyStep2(srTransaction) && trxVerifyStep3(srTransaction)) {
            srTransaction.setStatus(TransactionStatus.AUTHENTICATED);
            System.out.println("--> Transaction is Authenticated");
        } else {
            srTransaction.setStatus(TransactionStatus.UNAUTHENTICATED);
            System.out.println("--> Transaction is not Authenticated");
            //TODO: Handle the process when transaction unathenticated
        }
        transactionService.saveOrUpdate(srTransaction);

        System.out.println("--> Waiting for the Transaction to be provided");
        int timeWaited = 0;
        while (!transactionService.hasStatus(srTransaction, TransactionStatus.SERVICE_PROVIDED)) {
            CommonUtils.sync();
            timeWaited += Config.readInt(ConfigKeys.SYNC_TIMEOUT);
            if(timeWaited >= Config.readInt(ConfigKeys.WAIT_TIMEOUT)) {
                break; //TODO: Skip waiting for Confirmations -> Proceed on Rewards & Punishment
            }
        }

        BlockchainTransaction bcTransaction = transactionService
                .convertToOtherTransaction(srTransaction, TransactionType.BLOCKCHAIN_TRANSACTION);
        System.out.println("--> Waiting for Service Provided & Service Received Confirmations");
        timeWaited = 0;
        while (!transactionService.isServiceConfirmationSet(bcTransaction)) {
            CommonUtils.sync();
            timeWaited += Config.readInt(ConfigKeys.SYNC_TIMEOUT);
            if(timeWaited >= Config.readInt(ConfigKeys.WAIT_TIMEOUT)) {
                break;
            }
        }

        System.out.println("--> Deciding for Rewards or Punishments");
        bcTransaction = transactionService.setRewardOrPunishment(bcTransaction);

        System.out.println("--> Solving the Proof-of-Work (PoW)");
        SPTransaction spTransaction = transactionService.convertToOtherTransaction(bcTransaction, TransactionType.SP_TRANSACTION);
        String signatureData = node.getPublicKey() + srTransaction.getRequestSignedData() + spTransaction.getResultSignedData();

        bcTransaction.setMinerPublicKey(node.getPublicKey());
        bcTransaction.setBlockchainSignedData(CryptoService.digitallySign(signatureData, node.getPrivateKey()));
        bcTransaction.setBlockchainTimeStamp(new Date());
        bcTransaction.setProofOfWork(CryptoService.getProofOfWork(bcTransaction.getBlockchainSignedData()));
        bcTransaction.setStatus(TransactionStatus.BLOCKCHAINED);
        System.out.println(String.format("  --> PoW: %s", bcTransaction.getProofOfWork()));

        System.out.println("--> Transaction Added on Blockchain");
        transactionService.saveOrUpdate(bcTransaction);

        double latestRepuation = bcTransaction.getMinerReputationOnBlockchain();
        System.out.println("--> My reputation after transaction in Blockchain");
        System.out.println("--> REPUTATION: " + latestRepuation);

        node.setReputation(latestRepuation);
        nodeService.saveOrUpdate(node);
        System.out.println("--> Reputation is updated");

        System.out.println("\n\n\n");
    }

    private boolean trxVerifyStep1(SRTransaction srTransaction) {
        boolean transactionIntegrity = transactionService.verifyTransactionIntegrity(srTransaction);
        System.out.println(String.format("  --> Verifying Transaction => Integrity: %s",
                Boolean.toString(transactionIntegrity).toUpperCase()));
        return transactionIntegrity;
    }

    private boolean trxVerifyStep2(SRTransaction srTransaction) {
        boolean transactionReputationRequired = transactionService.verifyTransactionReqReputation(srTransaction);
        System.out.println(String.format("  --> Verifying Transaction => Required Reputation: %s",
                Boolean.toString(transactionReputationRequired).toUpperCase()));
        System.out.println(String.format("  --> Has [%.0f] reputation out of [%s] required",
                srTransaction.getSrReputation(),
                Config.readValue(ConfigKeys.MIN_REPUTATION)));
        return transactionReputationRequired;
    }

    private boolean trxVerifyStep3(SRTransaction srTransaction) {
        boolean reputationOnBlockchain = transactionService.verifyTransactionReputationOnBlockchain(srTransaction);
        System.out.println(String.format("  --> Verifying Transaction => Reputation on Blockchain: %s",
                Boolean.toString(reputationOnBlockchain).toUpperCase()));
        return reputationOnBlockchain;
    }
}
