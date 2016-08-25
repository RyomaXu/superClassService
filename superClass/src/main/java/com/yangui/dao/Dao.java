package com.yangui.dao;

import java.util.List;

public interface Dao<T> {
    public void insert(T t);
    public void delete(int id);
    public T update(T t);
    public  T getOne(int id);
    public List<T> findAllList();
}
