package com.tk.dao.transaction;

import com.tk.dao.GenericDao;
import com.tk.database.DBConnection;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.transaction.BlockchainTransaction;
import com.tk.service.factory.TransactionFactory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SRTransactionDao
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class BlockchainTransactionDao implements GenericDao<BlockchainTransaction> {
    private static final String TABLE_NAME = "blockchain";

    public BlockchainTransaction getById(int id) {
        BlockchainTransaction transaction = null;
        try {
            String SQL = String.format("SELECT * FROM %s WHERE id = ? LIMIT 1;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            transaction = fillData(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return transaction;
    }

    public void save(BlockchainTransaction transaction) {
        try {
            String SQL = String.format("INSERT INTO %s ('status', 'type', 'miner_public_key', 'blockchain_signed_data', " +
                    "'blockchain_time_stamp', 'sr_reputation_on_blockchain', 'sp_reputation_on_blockchain', " +
                    "'miner_reputation_on_blockchain', 'proof_of_work') " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);", TABLE_NAME);

            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, transaction.getStatus().toString());
            statement.setString(2, transaction.getType().toString());
            statement.setString(3, transaction.getMinerPublicKey());
            statement.setString(4, transaction.getBlockchainSignedData());
            statement.setDate(5, (Date) transaction.getBlockchainTimeStamp());
            statement.setDouble(6, transaction.getSrReputationOnBlockchain());
            statement.setDouble(7, transaction.getSpReputationOnBlockchain());
            statement.setDouble(8, transaction.getMinerReputationOnBlockchain());
            statement.setString(9, transaction.getProofOfWork());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(BlockchainTransaction transaction) {
        try {
            String SQL = String.format("UPDATE %s SET 'status' = ?, 'type' = ?, 'miner_public_key' = ?, 'blockchain_signed_data' = ?, " +
                    "'blockchain_time_stamp' = ?, 'sr_reputation_on_blockchain' = ?, 'sp_reputation_on_blockchain' = ?, " +
                    "'miner_reputation_on_blockchain' = ?, 'proof_of_work' = ? " +
                    "WHERE 'id' = ?;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, transaction.getStatus().toString());
            statement.setString(2, transaction.getType().toString());
            statement.setString(3, transaction.getMinerPublicKey());
            statement.setString(4, transaction.getBlockchainSignedData());
            statement.setDate(5, (Date) transaction.getBlockchainTimeStamp());
            statement.setDouble(6, transaction.getSrReputationOnBlockchain());
            statement.setDouble(7, transaction.getSpReputationOnBlockchain());
            statement.setDouble(8, transaction.getMinerReputationOnBlockchain());
            statement.setString(9, transaction.getProofOfWork());
            statement.setInt(10, transaction.getId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void saveOrUpdate(BlockchainTransaction transaction) {
        BlockchainTransaction blockchainTransaction = getById(transaction.getId());
        if (blockchainTransaction != null) {
            update(transaction);
        } else {
            save(transaction);
        }
    }

    public List<BlockchainTransaction> getAllTransactionsByRole(String publicKey) {
        List<BlockchainTransaction> transactions = null;
        // TODO: Still unimplemented since no need was shown
        return transactions;
    }

    public BlockchainTransaction getLatestTransactionByPublicKey(String publicKey) {
        BlockchainTransaction transaction = null;
        try {
            String SQL = String.format("SELECT * FROM %s WHERE ? IN('sr_public_key', 'sp_public_key', 'miner_public_key') " +
                    "ORDER BY blockchain_time_stamp DESC LIMIT 1;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, publicKey);

            ResultSet resultSet = statement.executeQuery();
            transaction = fillData(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return transaction;
    }

    private BlockchainTransaction fillData(ResultSet resultSet) throws SQLException {
        BlockchainTransaction transaction = null;
        if (!resultSet.isBeforeFirst()) {
            return transaction;
        }

        TransactionFactory transactionFactory = new TransactionFactory();
        transaction = (BlockchainTransaction) transactionFactory.getTransaction(TransactionType.BLOCKCHAIN_TRANSACTION);
        while (resultSet.next()) {
            transaction.setId(resultSet.getInt("id"));
            transaction.setStatus(TransactionStatus.valueOf(resultSet.getString("status")));
            transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
            transaction.setMinerPublicKey(resultSet.getString("miner_public_key"));
            transaction.setBlockchainSignedData(resultSet.getString("blockchain_signed_data"));
            transaction.setBlockchainTimeStamp(resultSet.getDate("blockchain_time_stamp"));
            transaction.setSrReputationOnBlockchain(resultSet.getDouble("sr_reputation_on_blockchain"));
            transaction.setSpReputationOnBlockchain(resultSet.getDouble("sp_reputation_on_blockchain"));
            transaction.setMinerReputationOnBlockchain(resultSet.getDouble("miner_reputation_on_blockchain"));
            transaction.setProofOfWork(resultSet.getString("proof_of_work"));
        }
        return transaction;
    }
}
