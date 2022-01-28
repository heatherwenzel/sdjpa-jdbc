package com.heatherwenzel.springdatademo.repositories;


import com.heatherwenzel.springdatademo.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
