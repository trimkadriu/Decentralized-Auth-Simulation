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
    public Transaction getTransaction(TransactionType transactionType) {
        Transaction transaction = null;
        if (transactionType.equals(TransactionType.SR_TRANSACTION)) {
            transaction = new SRTransaction();
        } else if (transactionType.equals(TransactionType.SP_TRANSACTION)) {
            transaction = new SPTransaction();
        } else if (transactionType.equals(TransactionType.BLOCKCHAIN_TRANSACTION)) {
            transaction = new BlockchainTransaction();
        }
        return transaction;
    }
}
