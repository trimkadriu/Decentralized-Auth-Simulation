package com.tk.dao.node

import com.tk.dao.transaction.BlockchainTransactionDao
import com.tk.domain.Node
import com.tk.domain.transaction.BlockchainTransaction
import com.tk.domain.transaction.Transaction
import jdk.nashorn.internal.ir.Block

/**
 * MinerNode

 * @author: Trim Kadriu <trim.kadriu></trim.kadriu>@gmail.com>
 */
class MinerNodeDao : NodeDao() {
    override fun getAllTransactions(node: Node): List<BlockchainTransaction> {
        val blockchainTransactionDao = BlockchainTransactionDao()
        val transactions = blockchainTransactionDao.getAllTransactionsByRole(node.publicKey)
        return transactions
    }
}
