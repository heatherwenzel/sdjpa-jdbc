package com.heatherwenzel.springdatademo.dao;

import com.heatherwenzel.springdatademo.domain.Author;

public interface AuthorDao {

    Author getById(Long id);
}
