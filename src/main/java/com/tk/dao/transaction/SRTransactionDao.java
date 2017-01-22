package com.tk.dao.transaction;

import com.tk.dao.GenericDao;
import com.tk.database.DBConnection;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.transaction.SRTransaction;
import com.tk.service.factory.TransactionFactory;

import java.sql.*;
import java.util.List;

/**
 * SRTransactionDao
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class SRTransactionDao implements GenericDao<SRTransaction> {
    private static final String TABLE_NAME = "blockchain";

    public SRTransaction getById(int id) {
        SRTransaction transaction = null;
        try {
            String SQL = String.format("SELECT * FROM %s WHERE `id`=? LIMIT 1;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            transaction = fillData(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return transaction;
    }

    public void save(SRTransaction transaction) {
        try {
            String SQL = String.format("INSERT INTO %s (`id`, `status`, `type`, `requested_service_name`, " +
                    "`sr_public_key`, `request_signed_data`, `request_time_stamp`, `sr_reputation`, `sp_public_key`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);", TABLE_NAME);

            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, transaction.getId());
            statement.setString(2, transaction.getStatus().toString());
            statement.setString(3, transaction.getType().toString());
            statement.setString(4, transaction.getRequestedServiceName());
            statement.setString(5, transaction.getSrPublicKey());
            statement.setString(6, transaction.getRequestSignedData());
            statement.setTimestamp(7, new Timestamp(transaction.getRequestTimeStamp().getTime()));
            statement.setDouble(8, transaction.getSrReputation());
            statement.setString(9, transaction.getSpPublicKey());

            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(SRTransaction transaction) {
        try {
            String SQL = String.format("UPDATE %s SET `status` = ?, `type` = ?, `requested_service_name` = ?, " +
                    "`sr_public_key` = ?, `request_signed_data` = ?, `request_time_stamp` = ?, `sr_reputation` = ?, " +
                    "`sp_public_key` = ? WHERE `id` = ?;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, transaction.getStatus().toString());
            statement.setString(2, transaction.getType().toString());
            statement.setString(3, transaction.getRequestedServiceName());
            statement.setString(4, transaction.getSrPublicKey());
            statement.setString(5, transaction.getRequestSignedData());
            statement.setTimestamp(6, new Timestamp(transaction.getRequestTimeStamp().getTime()));
            statement.setDouble(7, transaction.getSrReputation());
            statement.setString(8, transaction.getSpPublicKey());
            statement.setInt(9, transaction.getId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<SRTransaction> getAllTransactionsByRole(String publicKey) {
        List<SRTransaction> transactions = null;
        // TODO: Still unimplemented since no need was shown
        return transactions;
    }

    public SRTransaction getSRBySPPublicKey(String spPublicKey) {
        SRTransaction transaction = null;
        try {
            String SQL = String.format("SELECT * FROM %s WHERE `sp_public_key`=? AND `status` = '%s' " +
                    "ORDER BY `request_time_stamp` DESC LIMIT 1;", TABLE_NAME, TransactionStatus.SERVICE_REQUESTED.toString());
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, spPublicKey);

            ResultSet resultSet = statement.executeQuery();
            transaction = fillData(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return transaction;
    }

    public SRTransaction getLatestServiceRequest() {
        SRTransaction transaction = null;
        try {
            String SQL = String.format("SELECT * FROM %s WHERE `status` = ? ORDER BY `request_time_stamp` " +
                    "DESC LIMIT 1;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, TransactionStatus.REQ_AUTHENTICATION.toString());

            ResultSet resultSet = statement.executeQuery();
            transaction = fillData(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return transaction;
    }

    private SRTransaction fillData(ResultSet resultSet) throws SQLException {
        SRTransaction transaction = null;
        if (!resultSet.isBeforeFirst()) {
            return transaction;
        }

        TransactionFactory transactionFactory = new TransactionFactory();
        transaction = transactionFactory.getTransaction(TransactionType.SR_TRANSACTION);
        while (resultSet.next()) {
            transaction.setId(resultSet.getInt("id"));
            transaction.setStatus(TransactionStatus.valueOf(resultSet.getString("status")));
            transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
            transaction.setRequestedServiceName(resultSet.getString("requested_service_name"));
            transaction.setSrPublicKey(resultSet.getString("sr_public_key"));
            transaction.setRequestSignedData(resultSet.getString("request_signed_data"));
            transaction.setRequestTimeStamp(new Date(resultSet.getTimestamp("request_time_stamp").getTime()));
            transaction.setSrReputation(resultSet.getDouble("sr_reputation"));
            transaction.setSpPublicKey(resultSet.getString("sp_public_key"));
        }
        return transaction;
    }
}
