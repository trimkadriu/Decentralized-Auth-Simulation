package com.tk.dao.node;

import com.tk.dao.transaction.SPTransactionDao;
import com.tk.domain.Node;
import com.tk.domain.transaction.SPTransaction;
import com.tk.domain.transaction.Transaction;

import java.util.List;

/**
 * SPNode
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SPNodeDao extends NodeDao {
    protected List<SPTransaction> getAllTransactions(Node node) {
        SPTransactionDao spTransactionDao = new SPTransactionDao();
        List<SPTransaction> transactions = spTransactionDao.getAllTransactionsByRole(node.getPublicKey());
        return transactions;
    }
}
