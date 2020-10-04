package com.walmart.stratiaf.graphql.graphqldemoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walmart.stratiaf.graphql.graphqldemoservice.model.Book;

public interface BookRepository extends JpaRepository<Book, String>{

	
}
