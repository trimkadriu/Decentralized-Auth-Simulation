package com.tk.service.factory;

import com.tk.domain.enums.TransactionType;
import com.tk.domain.transaction.BlockchainTransaction;
import com.tk.domain.transaction.SPTransaction;
import com.tk.domain.transaction.SRTransaction;
import com.tk.domain.transaction.Transaction;

/**
 * TransactionFactory
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class TransactionFactory {

    @SuppressWarnings("unchecked")
    public <T extends Transaction> T getTransaction(TransactionType transactionType) {
        if (transactionType.equals(TransactionType.SR_TRANSACTION)) {
            SRTransaction transaction = new SRTransaction();
            transaction.setType(TransactionType.SR_TRANSACTION);
            return (T) transaction;
        } else if (transactionType.equals(TransactionType.SP_TRANSACTION)) {
            SPTransaction transaction = new SPTransaction();
            transaction.setType(TransactionType.SP_TRANSACTION);
            return (T) transaction;
        } else if (transactionType.equals(TransactionType.BLOCKCHAIN_TRANSACTION)) {
            BlockchainTransaction transaction = new BlockchainTransaction();
            transaction.setType(TransactionType.BLOCKCHAIN_TRANSACTION);
            return (T) transaction;
        }
        return null;
    }
}
