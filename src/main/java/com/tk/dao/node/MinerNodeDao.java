package com.tk.dao.node;

import com.tk.dao.transaction.BlockchainTransactionDao;
import com.tk.domain.Node;
import com.tk.domain.transaction.BlockchainTransaction;
import com.tk.domain.transaction.Transaction;
import jdk.nashorn.internal.ir.Block;

import java.util.List;

/**
 * MinerNode
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class MinerNodeDao extends NodeDao {
    protected List<BlockchainTransaction> getAllTransactions(Node node) {
        BlockchainTransactionDao blockchainTransactionDao = new BlockchainTransactionDao();
        List<BlockchainTransaction> transactions = blockchainTransactionDao.getAllTransactionsByRole(node.getPublicKey());
        return transactions;
    }
}
