package com.tk.dao.node;

import com.tk.dao.GenericDao;
import com.tk.dao.transaction.BlockchainTransactionDao;
import com.tk.database.DBConnection;
import com.tk.domain.Node;
import com.tk.domain.enums.NodeRole;
import com.tk.domain.transaction.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * NodeDao
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public abstract class NodeDao implements GenericDao<Node> {
    private static final String TABLE_NAME = "nodes";

    public Node getById(int id) {
        Node node = null;
        try {
            String SQL = String.format("SELECT * FROM %s WHERE `id` = ? LIMIT 1;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return node;
            }

            node = new Node();
            while (resultSet.next()) {
                node.setId(resultSet.getInt("id"));
                node.setRole(NodeRole.valueOf(resultSet.getString("role")));
                node.setPublicKey(resultSet.getString("public_key"));
                node.setPrivateKey(resultSet.getString("private_key"));
                node.setReputation(resultSet.getDouble("reputation"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return node;
    }

    public void save(Node node) {
        try {
            String SQL = String.format("INSERT INTO %s (`id`, `role`, `public_key`, `private_key`, `reputation`) " +
                    "VALUES (?, ?, ?, ?, ?);", TABLE_NAME);

            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, node.getId());
            statement.setString(2, node.getRole().toString());
            statement.setString(3, node.getPublicKey());
            statement.setString(4, node.getPrivateKey());
            statement.setDouble(5, node.getReputation());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(Node node) {
        try {
            String SQL = String.format("UPDATE %s SET `role` = ?, `public_key` = ?, `private_key` = ?, `reputation` = ? " +
                    "WHERE `id` = ?;", TABLE_NAME);

            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, node.getRole().toString());
            statement.setString(2, node.getPublicKey());
            statement.setString(3, node.getPrivateKey());
            statement.setDouble(4, node.getReputation());
            statement.setInt(5, node.getId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected Transaction getLatestReputationChangeTrx(Node node) {
        BlockchainTransactionDao transactionDao = new BlockchainTransactionDao();
        Transaction transaction = transactionDao.getLatestTransactionByPublicKey(node.getPublicKey());
        return transaction;
    }

    protected abstract List<? extends Transaction> getAllTransactions(Node node);
}
