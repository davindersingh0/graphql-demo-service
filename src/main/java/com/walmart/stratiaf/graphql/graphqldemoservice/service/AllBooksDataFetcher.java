package com.walmart.stratiaf.graphql.graphqldemoservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walmart.stratiaf.graphql.graphqldemoservice.model.Book;
import com.walmart.stratiaf.graphql.graphqldemoservice.repository.BookRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllBooksDataFetcher  implements DataFetcher<List<Book>>{


	@Autowired
	BookRepository bookRepository;

	@Override
	public List<Book> get(DataFetchingEnvironment environment) throws Exception {

		return bookRepository.findAll();
	}

}
