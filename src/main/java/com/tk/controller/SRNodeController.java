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
import com.tk.view.SRNodeView;

import java.util.Date;

/**
 * SRNodeController
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SRNodeController implements NodeController {
    private TransactionFactory transactionFactory;
    private TransactionService transactionService;
    private NodeService nodeService;
    private SRNodeView srNodeView;

    public SRNodeController() {
        transactionFactory = new TransactionFactory();
        transactionService = new TransactionService();
        srNodeView = new SRNodeView();
    }

    public void start(Node node) throws DecAuthSimException, InterruptedException {
        //TODO: Handle method restart
        nodeService = new NodeService(NodeRole.SR_NODE);

        // Initialize a transaction
        srNodeView.showBanner();
        String spPublicKey = srNodeView.askForSpNodePubKey(nodeService.getNodesByRole(NodeRole.SP_NODE));
        srNodeView.printSendingServiceRequest();

        SRTransaction srTransaction = transactionFactory.getTransaction(TransactionType.SR_TRANSACTION);
        srTransaction.setId(CommonUtils.generateId());
        srTransaction.setStatus(TransactionStatus.SERVICE_REQUESTED);
        srTransaction.setType(TransactionType.SR_TRANSACTION);
        srTransaction.setRequestedServiceName(DummyDataGenerator.getServiceName());
        srTransaction.setSrPublicKey(node.getPublicKey());
        String signatureData = node.getPublicKey() + node.getReputation() + srTransaction.getRequestedServiceName();
        srTransaction.setRequestSignedData(CryptoService.digitallySign(signatureData, node.getPrivateKey()));
        srTransaction.setRequestTimeStamp(new Date());
        srTransaction.setSrReputation(node.getReputation());
        srTransaction.setSpPublicKey(spPublicKey);
        transactionService.saveOrUpdate(srTransaction);

        // Wait to receive the service result
        srNodeView.printWaitServiceReceive();
        while(!transactionService.hasStatus(srTransaction, TransactionStatus.SERVICE_PROVIDED) &&
                !transactionService.hasStatus(srTransaction, TransactionStatus.UNAUTHENTICATED)) {
            CommonUtils.sync();
        }
        srTransaction = transactionService.getSRTransaction(srTransaction.getId());
        if(srTransaction.getStatus().equals(TransactionStatus.UNAUTHENTICATED)) {
            srNodeView.printTrxNotAuthenticated();
            return;
        }

        // Service is received & set confirm
        srNodeView.printServiceIsReceived(transactionService.getSPTransaction(srTransaction.getId()).getProvidedServiceResults());
        srNodeView.printServiceReceivedConfirm();
        transactionService.setConfirmationServiceReceived(srTransaction, true);

        // Waiting for transaction to be added on blockchain
        srNodeView.printWaitTrxOnBlockchain();
        while(!transactionService.hasStatus(srTransaction, TransactionStatus.BLOCKCHAINED)) {
            CommonUtils.sync();
        }

        // Update reputation
        double latestRepuation = transactionService.getBlockchainTransaction(srTransaction.getId()).getSrReputationOnBlockchain();
        node.setReputation(latestRepuation);
        nodeService.saveOrUpdate(node);
        srNodeView.printUpdatedReputation(latestRepuation);
    }

}
