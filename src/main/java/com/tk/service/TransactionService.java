package com.tk.service;

import com.tk.dao.GenericDao;
import com.tk.dao.transaction.BlockchainTransactionDao;
import com.tk.dao.transaction.SPTransactionDao;
import com.tk.dao.transaction.SRTransactionDao;
import com.tk.dao.transaction.TransactionDao;
import com.tk.domain.enums.ConfigKeys;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.transaction.BlockchainTransaction;
import com.tk.domain.transaction.SPTransaction;
import com.tk.domain.transaction.SRTransaction;
import com.tk.domain.transaction.Transaction;
import com.tk.service.util.Config;

import java.security.PublicKey;
import java.util.Map;

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
        if (getSRTransaction(id) == null) {
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
        if (transactionDao.getTransactionStatus(transaction.getId()).equals(transactionStatus)) {
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

    @SuppressWarnings("unchecked")
    public <T extends Transaction> T convertToOtherTransaction(Transaction transaction, TransactionType transactionType) {
        GenericDao transactionDao = null;
        if (transaction == null) {
            return null;
        } else if (transactionType.equals(TransactionType.SR_TRANSACTION)) {
            transactionDao = new SRTransactionDao();
        } else if (transactionType.equals(TransactionType.SP_TRANSACTION)) {
            transactionDao = new SPTransactionDao();
        } else if (transactionType.equals(TransactionType.BLOCKCHAIN_TRANSACTION)) {
            transactionDao = new BlockchainTransactionDao();
        }
        return (T) transactionDao.getById(transaction.getId());
    }

    public boolean verifyTransactionIntegrity(SRTransaction transaction) {
        PublicKey publicKey = CryptoService.getPublicKey(transaction.getSrPublicKey());
        String decryptedDataHash = CryptoService.decrypt(transaction.getRequestSignedData(), publicKey);
        String rawData = transaction.getSrPublicKey() + transaction.getSrReputation() + transaction.getRequestedServiceName();
        String rawDataHash = CryptoService.getHash(rawData);
        return decryptedDataHash.equals(rawDataHash);
    }

    public boolean verifyTransactionReqReputation(SRTransaction transaction) {
        return transaction.getSrReputation() > Double.parseDouble(Config.readValue(ConfigKeys.MIN_REPUTATION));
    }

    public boolean verifyTransactionReputationOnBlockchain(SRTransaction srTransaction) {
        BlockchainTransactionDao bcTransactionDao = new BlockchainTransactionDao();
        TransactionDao transactionDao = new TransactionDao();
        BlockchainTransaction bcTransaction = bcTransactionDao.getLatestTransactionByPublicKey(srTransaction.getSrPublicKey());
        if (bcTransaction == null)
            return false;
        Map<TransactionType, String> transactionPublicKeys = transactionDao.getPublicKeysById(bcTransaction.getId());
        double reputationToCompare = 0;
        if (transactionPublicKeys.get(TransactionType.SR_TRANSACTION).equals(srTransaction.getSrPublicKey())) {
            return srTransaction.getSrReputation() == bcTransaction.getSrReputationOnBlockchain();
        } else if (transactionPublicKeys.get(TransactionType.SP_TRANSACTION).equals(srTransaction.getSrPublicKey())) {
            return srTransaction.getSrReputation() == bcTransaction.getSpReputationOnBlockchain();
        } else if (transactionPublicKeys.get(TransactionType.BLOCKCHAIN_TRANSACTION).equals(srTransaction.getSrPublicKey())) {
            return srTransaction.getSrReputation() == bcTransaction.getMinerReputationOnBlockchain();
        }
        return false;
    }
}
