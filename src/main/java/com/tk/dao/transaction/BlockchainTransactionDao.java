package com.tk.dao.transaction;

import com.tk.dao.GenericDao;
import com.tk.database.DBConnection;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.transaction.BlockchainTransaction;
import com.tk.service.factory.TransactionFactory;

import java.sql.*;
import java.util.List;
import java.util.StringTokenizer;

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
            String SQL = String.format("SELECT * FROM %s WHERE `id` = ? LIMIT 1;", TABLE_NAME);
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
            String SQL = String.format("INSERT INTO %s (`id`, `status`, `type`, `miner_public_key`, `blockchain_signed_data`, " +
                    "`blockchain_time_stamp`, `sr_reputation_on_blockchain`, `sp_reputation_on_blockchain`, " +
                    "`miner_reputation_on_blockchain`, `proof_of_work`, `confirmation_service_received`, `confirmation_service_sent`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", TABLE_NAME);

            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, transaction.getId());
            statement.setString(2, transaction.getStatus().toString());
            statement.setString(3, transaction.getType().toString());
            statement.setString(4, transaction.getMinerPublicKey());
            statement.setString(5, transaction.getBlockchainSignedData());
            statement.setTimestamp(6, new Timestamp(transaction.getBlockchainTimeStamp().getTime()));
            statement.setDouble(7, transaction.getSrReputationOnBlockchain());
            statement.setDouble(8, transaction.getSpReputationOnBlockchain());
            statement.setDouble(9, transaction.getMinerReputationOnBlockchain());
            statement.setString(10, transaction.getProofOfWork());
            statement.setBoolean(11, transaction.isConfirmationServiceReceived());
            statement.setBoolean(12, transaction.isConfirmationServiceSent());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(BlockchainTransaction transaction) {
        try {
            String SQL = String.format("UPDATE %s SET `status` = ?, `type` = ?, `miner_public_key` = ?, `blockchain_signed_data` = ?, " +
                    "`blockchain_time_stamp` = ?, `sr_reputation_on_blockchain` = ?, `sp_reputation_on_blockchain` = ?, " +
                    "`miner_reputation_on_blockchain` = ?, `proof_of_work` = ?, `confirmation_service_received` = ?, " +
                    "`confirmation_service_sent` = ? WHERE `id` = ?;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, transaction.getStatus().toString());
            statement.setString(2, transaction.getType().toString());
            statement.setString(3, transaction.getMinerPublicKey());
            statement.setString(4, transaction.getBlockchainSignedData());
            statement.setTimestamp(5, new Timestamp(transaction.getBlockchainTimeStamp().getTime()));
            statement.setDouble(6, transaction.getSrReputationOnBlockchain());
            statement.setDouble(7, transaction.getSpReputationOnBlockchain());
            statement.setDouble(8, transaction.getMinerReputationOnBlockchain());
            statement.setString(9, transaction.getProofOfWork());
            statement.setBoolean(10, transaction.isConfirmationServiceReceived());
            statement.setBoolean(11, transaction.isConfirmationServiceSent());
            statement.setInt(12, transaction.getId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
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
            String SQL = String.format("SELECT * FROM %s WHERE ? IN(`sr_public_key`, `sp_public_key`, `miner_public_key`) " +
                    "AND `status` = ? ORDER BY blockchain_time_stamp DESC LIMIT 1;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, publicKey);
            statement.setString(2, TransactionStatus.BLOCKCHAINED.toString());

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
        transaction = transactionFactory.getTransaction(TransactionType.BLOCKCHAIN_TRANSACTION);
        while (resultSet.next()) {
            transaction.setId(resultSet.getInt("id"));
            transaction.setStatus(TransactionStatus.valueOf(resultSet.getString("status")));
            transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
            transaction.setMinerPublicKey(resultSet.getString("miner_public_key"));
            transaction.setBlockchainSignedData(resultSet.getString("blockchain_signed_data"));
            Timestamp bcTimestamp = resultSet.getTimestamp("blockchain_time_stamp");
            transaction.setBlockchainTimeStamp(bcTimestamp == null ? null : new Date(bcTimestamp.getTime()));
            transaction.setSrReputationOnBlockchain(resultSet.getDouble("sr_reputation_on_blockchain"));
            transaction.setSpReputationOnBlockchain(resultSet.getDouble("sp_reputation_on_blockchain"));
            transaction.setMinerReputationOnBlockchain(resultSet.getDouble("miner_reputation_on_blockchain"));
            transaction.setProofOfWork(resultSet.getString("proof_of_work"));
            transaction.setConfirmationServiceSent(resultSet.getBoolean("confirmation_service_sent"));
            transaction.setConfirmationServiceReceived(resultSet.getBoolean("confirmation_service_received"));
        }
        return transaction;
    }
}
