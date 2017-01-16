package com.tk.domain.transaction;

import com.tk.domain.enums.TransactionStatus;
import com.tk.domain.enums.TransactionType;

/**
 * Transaction
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class Transaction {
    private int id;
    private TransactionStatus status;
    private TransactionType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
