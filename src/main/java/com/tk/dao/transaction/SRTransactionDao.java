package com.tk.dao.transaction;

import com.tk.dao.GenericDao;
import com.tk.database.DBConnection;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.transaction.SRTransaction;
import com.tk.service.factory.TransactionFactory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            String SQL = "SELECT * FROM ? WHERE id=? LIMIT 1;";
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, TABLE_NAME);
            statement.setInt(2, id);

            ResultSet resultSet = statement.executeQuery();
            transaction = fillData(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return transaction;
    }

    public void save(SRTransaction transaction) {
        try {
            String SQL = "INSERT INTO ? ('status', 'type', 'requested_service_name', " +
                    "'sr_public_key', 'request_signed_data', 'request_time_stamp', 'sr_reputation') " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, TABLE_NAME);
            statement.setString(2, transaction.getStatus().toString());
            statement.setString(3, transaction.getType().toString());
            statement.setString(4, transaction.getRequestedServiceName());
            statement.setString(5, transaction.getSrPublicKey());
            statement.setString(6, transaction.getRequestSignedData());
            statement.setDate(7, (Date) transaction.getRequestTimeStamp());
            statement.setDouble(8, transaction.getSrReputation());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(SRTransaction transaction) {
        try {
            String SQL = "UPDATE ? SET 'status' = ?, 'type' = ?, 'requested_service_name' = ?, " +
                    "'sr_public_key' = ?, 'request_signed_data' = ?, 'request_time_stamp' = ?, 'sr_reputation' = ? " +
                    "WHERE 'id' = ?;";
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, TABLE_NAME);
            statement.setString(2, transaction.getStatus().toString());
            statement.setString(3, transaction.getType().toString());
            statement.setString(4, transaction.getRequestedServiceName());
            statement.setString(5, transaction.getSrPublicKey());
            statement.setString(6, transaction.getRequestSignedData());
            statement.setDate(7, (Date) transaction.getRequestTimeStamp());
            statement.setDouble(8, transaction.getSrReputation());
            statement.setInt(9, transaction.getId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void saveOrUpdate(SRTransaction transaction) {
        SRTransaction transactionInBC = getById(transaction.getId());
        if (transactionInBC != null) {
            update(transaction);
        } else {
            save(transaction);
        }
    }

    public List<SRTransaction> getAllTransactionsByRole(String publicKey) {
        List<SRTransaction> transactions = null;
        // TODO: Still unimplemented since no need was shown
        return transactions;
    }


    private SRTransaction fillData(ResultSet resultSet) throws SQLException {
        SRTransaction transaction = null;
        if (!resultSet.isBeforeFirst()) {
            return transaction;
        }

        TransactionFactory transactionFactory = new TransactionFactory();
        transaction = (SRTransaction) transactionFactory.getTransaction(TransactionType.SR_TRANSACTION);
        while (resultSet.next()) {
            transaction.setId(resultSet.getInt("id"));
            transaction.setStatus(TransactionStatus.valueOf(resultSet.getString("status")));
            transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
            transaction.setRequestedServiceName(resultSet.getString("requested_service_name"));
            transaction.setSrPublicKey(resultSet.getString("sr_public_key"));
            transaction.setRequestSignedData(resultSet.getString("request_signed_data"));
            transaction.setRequestTimeStamp(resultSet.getDate("request_time_stamp"));
            transaction.setSrReputation(resultSet.getDouble("sr_reputation"));
        }
        return transaction;
    }
}
