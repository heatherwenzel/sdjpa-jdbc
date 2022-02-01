package com.heatherwenzel.springdatademo.dao;

import com.heatherwenzel.springdatademo.domain.Book;

public interface BookDao {

    Book getById(Long id);

    Book findByTitle(String title);

    Book saveBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

}
