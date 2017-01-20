package com.tk.dao.transaction;

import com.tk.dao.GenericDao;
import com.tk.database.DBConnection;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.transaction.SPTransaction;
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
public class SPTransactionDao implements GenericDao<SPTransaction> {
    private static final String TABLE_NAME = "blockchain";

    public SPTransaction getById(int id) {
        SPTransaction transaction = null;
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

    public void save(SPTransaction transaction) {
        try {
            String SQL = String.format("INSERT INTO %s (`id`, `status`, `type`, `provided_service_results`, " +
                    "`sp_public_key`, `result_signed_data`, `result_time_stamp`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);", TABLE_NAME);

            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, transaction.getId());
            statement.setString(2, transaction.getStatus().toString());
            statement.setString(3, transaction.getType().toString());
            statement.setString(4, transaction.getProvidedServiceResults());
            statement.setString(5, transaction.getSpPublicKey());
            statement.setString(6, transaction.getResultSignedData());
            statement.setDate(7, (Date) transaction.getResultTimeStamp());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(SPTransaction transaction) {
        try {
            String SQL = String.format("UPDATE %s SET `status` = ?, `type` = ?, `provided_service_results` = ?, " +
                    "`sp_public_key` = ?, `result_signed_data` = ?, `result_time_stamp` = ? " +
                    "WHERE `id` = ?;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setString(1, transaction.getStatus().toString());
            statement.setString(2, transaction.getType().toString());
            statement.setString(3, transaction.getProvidedServiceResults());
            statement.setString(4, transaction.getSpPublicKey());
            statement.setString(5, transaction.getResultSignedData());
            statement.setDate(6, (Date) transaction.getResultTimeStamp());
            statement.setInt(7, transaction.getId());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void saveOrUpdate(SPTransaction transaction) {
        SPTransaction transactionInBC = getById(transaction.getId());
        if (transactionInBC != null) {
            update(transaction);
        } else {
            save(transaction);
        }
    }

    public List<SPTransaction> getAllTransactionsByRole(String publicKey) {
        List<SPTransaction> transactions = null;
        // TODO: Still unimplemented since no need was shown
        return transactions;
    }

    private SPTransaction fillData(ResultSet resultSet) throws SQLException {
        SPTransaction transaction = null;
        if (!resultSet.isBeforeFirst()) {
            return transaction;
        }

        TransactionFactory transactionFactory = new TransactionFactory();
        transaction = transactionFactory.getTransaction(TransactionType.SP_TRANSACTION);
        while (resultSet.next()) {
            transaction.setId(resultSet.getInt("id"));
            transaction.setStatus(TransactionStatus.valueOf(resultSet.getString("status")));
            transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
            transaction.setProvidedServiceResults(resultSet.getString("provided_service_results"));
            transaction.setSpPublicKey(resultSet.getString("sp_public_key"));
            transaction.setResultSignedData(resultSet.getString("result_signed_data"));
            transaction.setResultTimeStamp(resultSet.getDate("result_time_stamp"));
        }
        return transaction;
    }
}
