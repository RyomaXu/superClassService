package com.yangui.service;

import java.util.List;

public interface BasService<T> {
        public void insert(T t);
        public void delete(int id);
        public void update(T t);
        public T getOne(int id);
        public List<T> findAll();
}
