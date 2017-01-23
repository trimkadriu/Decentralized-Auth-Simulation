package com.tk.dao.node;

import com.tk.dao.transaction.BlockchainTransactionDao;
import com.tk.database.DBConnection;
import com.tk.domain.Node;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.transaction.BlockchainTransaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MinerNode
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class MinerNodeDao extends NodeDao {
    public List<BlockchainTransaction> getAllTransactions(Node node) {
        BlockchainTransactionDao blockchainTransactionDao = new BlockchainTransactionDao();
        List<BlockchainTransaction> transactions = blockchainTransactionDao.getAllTransactionsByRole(node.getPublicKey());
        return transactions;
    }

    public List<Node> getAll() {
        List<Node> nodeList = null;
        try {
            String SQL = String.format("SELECT * FROM %s WHERE `role` = ?;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, NodeRole.MINER_NODE.toString());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return nodeList;
            }

            nodeList = new ArrayList<Node>();
            while (resultSet.next()) {
                Node node = new Node();
                node.setId(resultSet.getInt("id"));
                node.setRole(NodeRole.valueOf(resultSet.getString("role")));
                node.setPublicKey(resultSet.getString("public_key"));
                node.setPrivateKey(resultSet.getString("private_key"));
                node.setReputation(resultSet.getDouble("reputation"));
                nodeList.add(node);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return nodeList;
    }
}
