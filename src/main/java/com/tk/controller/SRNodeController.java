package com.tk.controller;

import com.tk.domain.Node;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.transaction.SRTransaction;
import com.tk.service.CryptoService;
import com.tk.service.NodeService;
import com.tk.service.TransactionService;
import com.tk.service.factory.TransactionFactory;
import com.tk.service.util.DummyDataGenerator;

import java.util.Date;

/**
 * SRNodeController
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SRNodeController implements NodeController {

    public void start(Node node) {
        System.out.println("Service Requester NODE Started");
        System.out.println("=====================================");

        NodeService nodeService = new NodeService();
        TransactionFactory transactionFactory = new TransactionFactory();

        SRTransaction transaction = transactionFactory.getTransaction(TransactionType.SR_TRANSACTION);
        transaction.setStatus(TransactionStatus.SERVICE_REQUESTED);
        transaction.setType(TransactionType.SR_TRANSACTION);
        transaction.setRequestedServiceName(DummyDataGenerator.getServiceName());
        transaction.setSrPublicKey(node.getPublicKey());
        transaction.setRequestSignedData(nodeService.generateSRSignature(node, transaction.getRequestedServiceName()));
        transaction.setRequestTimeStamp(new Date());
        transaction.setSrReputation(node.getReputation());


    }
}
