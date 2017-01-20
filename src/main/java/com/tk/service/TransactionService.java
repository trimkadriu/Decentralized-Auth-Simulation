package com.tk.service;

import com.tk.domain.transaction.Transaction;

/**
 * TransactionService
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class TransactionService {


    public TransactionService() {

    }

   /* public Transaction getNode(int id) {
        return nodeDao.getById(id);
    }

    public boolean exists(int id) {
        if(getNode(id) == null) {
            return false;
        }
        return true;
    }

    public boolean exists(Node node) {
        return exists(node.getId());
    }

    public void saveOrUpdate(Node node) {
        Node nodeInDB = getNode(node.getId());
        if (nodeInDB != null) {
            nodeDao.update(node);
        } else {
            nodeDao.save(node);
        }
    }
*/
}
