package com.tk.dao.node;

import com.tk.dao.transaction.SPTransactionDao;
import com.tk.database.DBConnection;
import com.tk.domain.Node;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.transaction.SPTransaction;
import com.tk.domain.transaction.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SPNode
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SPNodeDao extends NodeDao {
    public List<SPTransaction> getAllTransactions(Node node) {
        SPTransactionDao spTransactionDao = new SPTransactionDao();
        List<SPTransaction> transactions = spTransactionDao.getAllTransactionsByRole(node.getPublicKey());
        return transactions;
    }

    public List<Node> getAll() {
        List<Node> nodeList = null;
        try {
            String SQL = String.format("SELECT * FROM %s WHERE `role` = ?;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, NodeRole.SP_NODE.toString());
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
