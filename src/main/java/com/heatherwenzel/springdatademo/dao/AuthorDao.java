package com.heatherwenzel.springdatademo.dao;

import com.heatherwenzel.springdatademo.domain.Author;

public interface AuthorDao {

    Author getById(Long id);

    Author getByName(String firstName, String lastName);

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

}
