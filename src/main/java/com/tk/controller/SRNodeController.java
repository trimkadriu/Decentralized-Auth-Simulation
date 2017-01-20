package com.tk.controller;

import com.tk.dao.node.SRNodeDao;
import com.tk.domain.Node;
import com.tk.domain.enums.ConfigKeys;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.exception.DecAuthSimException;
import com.tk.domain.transaction.SRTransaction;
import com.tk.service.NodeService;
import com.tk.service.TransactionService;
import com.tk.service.factory.TransactionFactory;
import com.tk.service.util.CommonUtils;
import com.tk.service.util.Config;
import com.tk.service.util.DummyDataGenerator;

import java.util.Date;

/**
 * SRNodeController
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SRNodeController implements NodeController {
    NodeService nodeService;
    TransactionFactory transactionFactory;
    TransactionService transactionService;

    public SRNodeController() {
        transactionFactory = new TransactionFactory();
        transactionFactory = new TransactionFactory();
    }


    public void start(Node node) throws DecAuthSimException, InterruptedException {
        System.out.println("=====================================");
        System.out.println("Service Requester NODE Started");
        System.out.println("=====================================");

        nodeService = new NodeService(node.getRole());

        SRTransaction transaction = transactionFactory.getTransaction(TransactionType.SR_TRANSACTION);
        transaction.setId(CommonUtils.generateId());
        transaction.setStatus(TransactionStatus.SERVICE_REQUESTED);
        transaction.setType(TransactionType.SR_TRANSACTION);
        transaction.setRequestedServiceName(DummyDataGenerator.getServiceName());
        transaction.setSrPublicKey(node.getPublicKey());
        transaction.setRequestSignedData(nodeService.generateSRSignature(node, transaction.getRequestedServiceName()));
        transaction.setRequestTimeStamp(new Date());
        transaction.setSrReputation(node.getReputation());
        //transactionService.saveOrUpdate(transaction);

        while(true) {
            CommonUtils.clearCMD();
            Thread.sleep(Integer.parseInt(Config.readValue(ConfigKeys.SYNC_TIMEOUT)));
        }
    }
}
