package com.heatherwenzel.springdatademo.dao;

import com.heatherwenzel.springdatademo.domain.Author;

public interface AuthorDao {

    Author getById(Long id);

    Author findByName(String firstName, String lastName);

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

}
