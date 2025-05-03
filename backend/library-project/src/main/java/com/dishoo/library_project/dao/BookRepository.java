package com.dishoo.library_project.dao;

import com.dishoo.library_project.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
//TODO: rename package