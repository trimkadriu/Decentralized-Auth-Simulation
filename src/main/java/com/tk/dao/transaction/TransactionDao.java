package com.tk.dao.transaction;

import com.tk.database.DBConnection;
import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;
import com.tk.domain.transaction.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * TransactionDao
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class TransactionDao {
    private static final String TABLE_NAME = "blockchain";

    public TransactionStatus getTransactionStatus(int id) {
        TransactionStatus status = null;
        try {
            String SQL = String.format("SELECT `status` FROM %s WHERE `id`=? LIMIT 1;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return status;
            }
            while (resultSet.next()) {
                status = TransactionStatus.valueOf(resultSet.getString("status"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public void updateConfirmationServiceReceived(int id, boolean confirmation) {
        try {
            String SQL = String.format("UPDATE %s SET `confirmation_service_received` = ? WHERE `id` = ?;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, confirmation ? 1 : 0);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateConfirmationServiceSent(int id, boolean confirmation) {
        try {
            String SQL = String.format("UPDATE %s SET `confirmation_service_sent` = ? WHERE `id` = ?;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, confirmation ? 1 : 0);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Map<TransactionType, String> getPublicKeysById(int id) {
        Map<TransactionType, String> result = null;
        try {
            String SQL = String.format("SELECT `sr_public_key`, `sp_public_key`, `miner_public_key` " +
                    "FROM %s WHERE `id`=? LIMIT 1;", TABLE_NAME);
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(SQL);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return result;
            }
            result = new HashMap<TransactionType, String>();
            while (resultSet.next()) {
                result.put(TransactionType.SR_TRANSACTION, resultSet.getString("sr_public_key"));
                result.put(TransactionType.SP_TRANSACTION, resultSet.getString("sp_public_key"));
                result.put(TransactionType.BLOCKCHAIN_TRANSACTION, resultSet.getString("miner_public_key"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return result;
    }
}
