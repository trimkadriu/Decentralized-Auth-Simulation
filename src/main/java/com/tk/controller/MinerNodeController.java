package com.tk.controller;

import com.tk.domain.Node;
import com.tk.domain.enums.ConfigKeys;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.exception.DecAuthSimException;
import com.tk.domain.transaction.BlockchainTransaction;
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

        System.out.println("--> Waiting for the Service to be Requested");
        SRTransaction srTransaction = transactionService.getRequestedTransaction(node.getPublicKey());
        while (srTransaction == null) {
            CommonUtils.sync();
            srTransaction = transactionService.getRequestedTransaction(node.getPublicKey());
        }

        System.out.println("--> Service is requested.");

        //TODO: Handle un-authenticated transaction
        if(trxVerifyStep1(srTransaction) && trxVerifyStep2(srTransaction) && trxVerifyStep3(srTransaction)) {
            srTransaction.setStatus(TransactionStatus.AUTHENTICATED);
            System.out.println("--> Transaction is Authenticated");
        } else {
            srTransaction.setStatus(TransactionStatus.UNAUTHENTICATED);
            System.out.println("--> Transaction is not Authenticated");
        }
        transactionService.saveOrUpdate(srTransaction);

        //TODO: Handle case when service is not provided
        System.out.println("--> Waiting for the Transaction to be provided");
        while (!transactionService.hasStatus(srTransaction, TransactionStatus.SERVICE_PROVIDED)) {
            CommonUtils.timeOut();
        }

        //TODO: Handle unreceived confirmations
        System.out.println("--> Waiting for Service Provided & Service Received Confirmations");
        while (!transactionService.hasStatus(srTransaction, TransactionStatus.SERVICE_PROVIDED)) {
            CommonUtils.timeOut();
        }

        //TODO: Assign rewards or punishments
        System.out.println("--> Deciding for Rewards or Punishments");
        double srReputationOnBc = 0;
        double spReputationOnBc = 0;
        double minerReputationOnBc = 0;

        //TODO: Solve PoW
        System.out.println("--> Solving the Proof-of-Work (PoW)");
        System.out.println("  --> PoW: ");
        String pow = "";

        String signatureData = node.getPublicKey() + node.getReputation() + pow;

        System.out.println("--> Transaction Added on Blockchain");
        BlockchainTransaction bcTransaction = transactionService
                .convertToOtherTransaction(srTransaction, TransactionType.BLOCKCHAIN_TRANSACTION);
        bcTransaction.setMinerPublicKey(node.getPublicKey());
        bcTransaction.setBlockchainSignedData(CryptoService.digitallySign(signatureData, node.getPrivateKey()));
        bcTransaction.setBlockchainTimeStamp(new Date());
        bcTransaction.setProofOfWork(pow);
        bcTransaction.setSrReputationOnBlockchain(srReputationOnBc);
        bcTransaction.setSpReputationOnBlockchain(spReputationOnBc);
        bcTransaction.setMinerReputationOnBlockchain(minerReputationOnBc);
        transactionService.saveOrUpdate(bcTransaction);

        double latestRepuation = bcTransaction.getSrReputationOnBlockchain();
        System.out.println("--> My reputation after transaction in Blockchain");
        System.out.println("--> REPUTATION: " + latestRepuation);

        node.setReputation(latestRepuation);
        nodeService.saveOrUpdate(node);
        System.out.println("--> Reputation is updated");

        System.out.println("\n\n\n");
    }

    private boolean trxVerifyStep1(SRTransaction srTransaction) {
        boolean transactionIntegrity = transactionService.verifyTransactionIntegrity(srTransaction);
        System.out.println("--> Verifying Transaction");
        System.out.println("  --> Verifying Transaction => Integrity: ");
        System.out.print(Boolean.toString(transactionIntegrity).toUpperCase());
        return transactionIntegrity;
    }

    private boolean trxVerifyStep2(SRTransaction srTransaction) {
        boolean transactionReputationRequired = transactionService.verifyTransactionReqReputation(srTransaction);
        System.out.println("  --> Verifying Transaction => Required Reputation: ");
        System.out.print(Boolean.toString(transactionReputationRequired).toUpperCase());
        System.out.println(String.format("     --> Has [%f] reputation out of [%s] required",
                srTransaction.getSrReputation(),
                Config.readValue(ConfigKeys.MIN_REPUTATION)));
        return transactionReputationRequired;
    }

    private boolean trxVerifyStep3(SRTransaction srTransaction) {
        boolean reputationOnBlockchain = transactionService.verifyTransactionReputationOnBlockchain(srTransaction);
        System.out.println("  --> Verifying Transaction => Reputation on Blockchain: ");
        System.out.print(Boolean.toString(reputationOnBlockchain).toUpperCase());
        return reputationOnBlockchain;
    }
}
