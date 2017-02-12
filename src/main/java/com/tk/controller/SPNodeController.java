package com.tk.controller;

import com.tk.domain.Node;
import com.tk.domain.enums.ConfigKeys;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.exception.DecAuthSimException;
import com.tk.domain.transaction.SPTransaction;
import com.tk.domain.transaction.SRTransaction;
import com.tk.service.CryptoService;
import com.tk.service.NodeService;
import com.tk.service.TransactionService;
import com.tk.service.util.CommonUtils;
import com.tk.service.util.Config;
import com.tk.service.util.DummyDataGenerator;
import com.tk.view.SPNodeView;

import java.util.Date;

/**
 * SPNodeController
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SPNodeController implements NodeController {
    private TransactionService transactionService;
    private SPNodeView spNodeView;

    public SPNodeController() {
        transactionService = new TransactionService();
        spNodeView = new SPNodeView();
    }

    public void start(Node node) throws DecAuthSimException, InterruptedException {
        //TODO: Handle method restart
        NodeService nodeService = new NodeService(NodeRole.SP_NODE);

        // Wait for service request
        spNodeView.showBanner();
        SRTransaction srTransaction = transactionService.getRequestedTransaction(node.getPublicKey());
        while (srTransaction == null) {
            CommonUtils.sync();
            srTransaction = transactionService.getRequestedTransaction(node.getPublicKey());
        }

        // Service is requested
        srTransaction.setStatus(TransactionStatus.REQ_AUTHENTICATION);
        transactionService.saveOrUpdate(srTransaction);
        spNodeView.printServiceIsRequested();

        // Wait to Authenticate
        while (!transactionService.hasStatus(srTransaction, TransactionStatus.AUTHENTICATED) &&
                !transactionService.hasStatus(srTransaction, TransactionStatus.UNAUTHENTICATED))  {
            CommonUtils.sync();
        }
        srTransaction = transactionService.getSRTransaction(srTransaction.getId());
        if(srTransaction.getStatus().equals(TransactionStatus.UNAUTHENTICATED)) {
            spNodeView.printTrxNotAuthenticated();
            return;
        }

        SPTransaction spTransaction = transactionService.convertToOtherTransaction(srTransaction, TransactionType.SP_TRANSACTION);
        spTransaction.setResultTimeStamp(new Date());
        spTransaction.setSpReputation(node.getReputation());
        transactionService.saveOrUpdate(spTransaction);

        if (!Config.readBoolean(ConfigKeys.SIM_SP_PUNNISH)) {
            // Providing service results & confirm
            spTransaction.setProvidedServiceResults(DummyDataGenerator.getServiceData());
            spTransaction.setStatus(TransactionStatus.SERVICE_PROVIDED);
            spTransaction.setResultTimeStamp(new Date());
            String signatureData = node.getPublicKey() + node.getReputation() + spTransaction.getProvidedServiceResults();
            spTransaction.setSpReputation(node.getReputation());
            spTransaction.setResultSignedData(CryptoService.digitallySign(signatureData, node.getPrivateKey()));
            transactionService.saveOrUpdate(spTransaction);
            transactionService.setConfirmationServiceSent(spTransaction, true);
            spNodeView.printProvidingResults(spTransaction.getProvidedServiceResults());
        }

        // Waiting for transaction to be added on blockchain
        spNodeView.printWaitTrxOnBlockchain();
        while (!transactionService.hasStatus(spTransaction, TransactionStatus.BLOCKCHAINED)) {
            CommonUtils.sync();
        }

        // Update reputation
        double latestRepuation = transactionService.getBlockchainTransaction(spTransaction.getId()).getSpReputationOnBlockchain();
        node.setReputation(latestRepuation);
        nodeService.saveOrUpdate(node);
        spNodeView.printUpdatedReputation(latestRepuation);
    }
}
