package com.tk.controller;

import com.tk.domain.Node;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.exception.DecAuthSimException;
import com.tk.domain.transaction.SRTransaction;
import com.tk.service.CryptoService;
import com.tk.service.NodeService;
import com.tk.service.TransactionService;
import com.tk.service.factory.TransactionFactory;
import com.tk.service.util.CommonUtils;
import com.tk.service.util.DummyDataGenerator;

import java.util.Date;

/**
 * SRNodeController
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SRNodeController implements NodeController {
    private TransactionFactory transactionFactory;
    private TransactionService transactionService;

    public SRNodeController() {
        transactionFactory = new TransactionFactory();
        transactionService = new TransactionService();
    }

    public void start(Node node) throws DecAuthSimException, InterruptedException {
        NodeService nodeService = new NodeService(NodeRole.SR_NODE);
        //TODO: Handle method restart
        //TODO: Implement asking to whom do you want to send?
        String spPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMj/mJri58jpWcOh9RfqzdM1kRQAoDz5SgXyi0qcpubQ8w0KUikP6oK6YLXU0hNyYRRSaoxwpOI2g4ZjC2s8S7kCAwEAAQ==";

        System.out.println("╔════════════════════════╗");
        System.out.println("║    Service Requester NODE Started    ║");
        System.out.println("╚════════════════════════╝");

        System.out.println("--> Sending a service request transaction");

        SRTransaction transaction = transactionFactory.getTransaction(TransactionType.SR_TRANSACTION);
        transaction.setId(CommonUtils.generateId());
        transaction.setStatus(TransactionStatus.SERVICE_REQUESTED);
        transaction.setType(TransactionType.SR_TRANSACTION);
        transaction.setRequestedServiceName(DummyDataGenerator.getServiceName());
        transaction.setSrPublicKey(node.getPublicKey());
        String signatureData = node.getPublicKey() + node.getReputation() + transaction.getRequestedServiceName();
        transaction.setRequestSignedData(CryptoService.digitallySign(signatureData, node.getPrivateKey()));
        transaction.setRequestTimeStamp(new Date());
        transaction.setSrReputation(node.getReputation());
        transaction.setSpPublicKey(spPublicKey);
        transactionService.saveOrUpdate(transaction);

        System.out.println("--> Service Request transaction sent");

        //TODO: Handle un-authenticated transaction
        System.out.println("--> Waiting for the Service to be Provided");
        while(!transactionService.hasStatus(transaction, TransactionStatus.SERVICE_PROVIDED)) {
            CommonUtils.sync();
        }

        System.out.println("--> Service is provided");
        System.out.println("  --> SERVICE RESULTS:");
        System.out.println("     -----------------------");
        System.out.println("      " + transactionService.getSPTransaction(transaction.getId()).getProvidedServiceResults());

        System.out.println("\n--> Sending Service Received Confirmation");
        transactionService.setConfirmationServiceReceived(transaction, true);

        System.out.println("--> Waiting for the Transaction to be added on Blockchain");
        while(!transactionService.hasStatus(transaction, TransactionStatus.BLOCKCHAINED)) {
            CommonUtils.sync();
        }

        double latestRepuation = transactionService.getBlockchainTransaction(transaction.getId()).getSrReputationOnBlockchain();
        System.out.println("--> My reputation after transaction in Blockchain");
        System.out.println("--> REPUTATION: " + latestRepuation);

        node.setReputation(latestRepuation);
        nodeService.saveOrUpdate(node);
        System.out.println("--> Reputation is updated");

        System.out.println("\n\n\n");
    }
}
