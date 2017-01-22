package com.tk.service;

import com.tk.dao.transaction.BlockchainTransactionDao;
import com.tk.dao.transaction.SPTransactionDao;
import com.tk.dao.transaction.SRTransactionDao;
import com.tk.dao.transaction.TransactionDao;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.transaction.BlockchainTransaction;
import com.tk.domain.transaction.SPTransaction;
import com.tk.domain.transaction.SRTransaction;
import com.tk.domain.transaction.Transaction;

/**
 * TransactionService
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class TransactionService {

    public SRTransaction getSRTransaction(int id) {
        SRTransactionDao transactionDao = new SRTransactionDao();
        return transactionDao.getById(id);
    }

    public SPTransaction getSPTransaction(int id) {
        SPTransactionDao transactionDao = new SPTransactionDao();
        return transactionDao.getById(id);
    }

    public BlockchainTransaction getBlockchainTransaction(int id) {
        BlockchainTransactionDao transactionDao = new BlockchainTransactionDao();
        return transactionDao.getById(id);
    }

    public boolean exists(int id) {
        if(getSRTransaction(id) == null) {
            return false;
        }
        return true;
    }

    public boolean exists(Transaction transaction) {
        return exists(transaction.getId());
    }

    public void saveOrUpdate(SRTransaction transaction) {
        SRTransactionDao transactionDao = new SRTransactionDao();
        if (exists(transaction)) {
            transactionDao.update(transaction);
        } else {
            transactionDao.save(transaction);
        }
    }

    public void saveOrUpdate(SPTransaction transaction) {
        SPTransactionDao transactionDao = new SPTransactionDao();
        if (exists(transaction)) {
            transactionDao.update(transaction);
        } else {
            transactionDao.save(transaction);
        }
    }

    public void saveOrUpdate(BlockchainTransaction transaction) {
        BlockchainTransactionDao transactionDao = new BlockchainTransactionDao();
        if (exists(transaction)) {
            transactionDao.update(transaction);
        } else {
            transactionDao.save(transaction);
        }
    }

    public boolean hasStatus(Transaction transaction, TransactionStatus transactionStatus) {
        TransactionDao transactionDao = new TransactionDao();
        if(transactionDao.getTransactionStatus(transaction.getId()).equals(transactionStatus)) {
            return true;
        }
        return false;
    }

    public void setConfirmationServiceReceived(Transaction transaction, boolean confirmation) {
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.updateConfirmationServiceReceived(transaction.getId(), confirmation);
    }

    public void setConfirmationServiceSent(Transaction transaction, boolean confirmation) {
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.updateConfirmationServiceSent(transaction.getId(), confirmation);
    }

    public SRTransaction getRequestedTransaction(String spPublicKey) {
        SRTransactionDao transactionDao = new SRTransactionDao();
        return transactionDao.getSRBySPPublicKey(spPublicKey);
    }

    public <T extends Transaction> T convertToOtherTransaction(Transaction transaction, TransactionType transactionType){
        if(transaction == null) {
            return null;
        } else if(transactionType.equals(TransactionType.SR_TRANSACTION)) {
            SRTransactionDao transactionDao = new SRTransactionDao();
            return (T) transactionDao.getById(transaction.getId());
        } else if(transactionType.equals(TransactionType.SP_TRANSACTION)) {
            SPTransactionDao transactionDao = new SPTransactionDao();
            return (T) transactionDao.getById(transaction.getId());
        } else if(transactionType.equals(TransactionType.BLOCKCHAIN_TRANSACTION)) {
            BlockchainTransactionDao transactionDao = new BlockchainTransactionDao();
            return (T) transactionDao.getById(transaction.getId());
        }
        return null;
    }

}
