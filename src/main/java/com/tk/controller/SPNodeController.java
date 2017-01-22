package com.tk.controller;

import com.tk.domain.Node;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.exception.DecAuthSimException;
import com.tk.domain.transaction.SPTransaction;
import com.tk.domain.transaction.SRTransaction;
import com.tk.service.CryptoService;
import com.tk.service.NodeService;
import com.tk.service.TransactionService;
import com.tk.service.factory.TransactionFactory;
import com.tk.service.util.CommonUtils;
import com.tk.service.util.DummyDataGenerator;

import java.util.Date;

/**
 * SPNodeController
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SPNodeController implements NodeController {
    private TransactionService transactionService;

    public SPNodeController() {
        transactionService = new TransactionService();
    }

    public void start(Node node) throws DecAuthSimException, InterruptedException {
        NodeService nodeService = new NodeService(NodeRole.SP_NODE);

        //TODO: Handle method restart
        System.out.println("╔════════════════════════╗");
        System.out.println("║    Service Provider NODE Started     ║");
        System.out.println("╚════════════════════════╝");

        System.out.println("--> Waiting for the Service to be Requested");
        SRTransaction srTransaction = transactionService.getRequestedTransaction(node.getPublicKey());
        while (srTransaction == null) {
            CommonUtils.sync();
            srTransaction = transactionService.getRequestedTransaction(node.getPublicKey());
        }

        //TODO: Handle un-authenticated transaction
        System.out.println("--> Waiting for the transaction to be Authenticated");
        while (!transactionService.hasStatus(srTransaction, TransactionStatus.AUTHENTICATED)) {
            CommonUtils.sync();
        }

        System.out.println("--> Transaction Authenticated");
        System.out.println("--> Providing the requested Service");

        SPTransaction spTransaction = transactionService.convertToOtherTransaction(srTransaction, TransactionType.SP_TRANSACTION);
        spTransaction.setProvidedServiceResults(DummyDataGenerator.getServiceData());
        spTransaction.setStatus(TransactionStatus.SERVICE_PROVIDED);
        spTransaction.setResultTimeStamp(new Date());
        String signatureData = node.getPublicKey() + node.getReputation() + spTransaction.getProvidedServiceResults();
        spTransaction.setResultSignedData(CryptoService.digitallySign(signatureData, node.getPrivateKey()));
        transactionService.saveOrUpdate(spTransaction);

        System.out.println("--> Service is provided");
        System.out.println("  --> SERVICE RESULTS:");
        System.out.println("     -----------------------");
        System.out.println("      " + spTransaction.getProvidedServiceResults());

        System.out.println("\n--> Sending Service Provided Confirmation");
        transactionService.setConfirmationServiceSent(spTransaction, true);

        System.out.println("--> Waiting for the Transaction to be added on Blockchain");
        while (!transactionService.hasStatus(spTransaction, TransactionStatus.BLOCKCHAINED)) {
            CommonUtils.sync();
        }

        double latestRepuation = transactionService.getBlockchainTransaction(spTransaction.getId()).getSrReputationOnBlockchain();
        System.out.println("--> My reputation after transaction in Blockchain");
        System.out.println("--> REPUTATION: " + latestRepuation);

        node.setReputation(latestRepuation);
        nodeService.saveOrUpdate(node);
        System.out.println("--> Reputation is updated");

        System.out.println("\n\n\n");
    }
}
