package com.tk.dao.node;

import com.tk.dao.transaction.BlockchainTransactionDao;
import com.tk.dao.transaction.SRTransactionDao;
import com.tk.domain.Node;
import com.tk.domain.transaction.BlockchainTransaction;
import com.tk.domain.transaction.SRTransaction;
import com.tk.domain.transaction.Transaction;

import java.util.List;

/**
 * SRNode
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SRNodeDao extends NodeDao {
    protected List<SRTransaction> getAllTransactions(Node node) {
        SRTransactionDao srTransactionDao = new SRTransactionDao();
        List<SRTransaction> transactions = srTransactionDao.getAllTransactionsByRole(node.getPublicKey());
        return transactions;
    }
}
