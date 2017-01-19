package com.tk.dao;

import com.tk.database.DBConnection;

/**
 * GenericDao
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public interface GenericDao<T> {
    T getById(int id);
    void save(T object);
    void update(T object);
}
