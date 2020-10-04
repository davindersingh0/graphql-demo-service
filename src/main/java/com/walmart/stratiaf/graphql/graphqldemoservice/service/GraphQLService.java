package com.walmart.stratiaf.graphql.graphqldemoservice.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.walmart.stratiaf.graphql.graphqldemoservice.model.Book;
import com.walmart.stratiaf.graphql.graphqldemoservice.repository.BookRepository;
import com.walmart.stratiaf.graphql.graphqldemoservice.repository.CustomResourceLoader;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class GraphQLService {

	@Value("classpath:books.graphql")
	Resource resource;

	private GraphQL graphQL;
	@Autowired
	private AllBooksDataFetcher allBooksDataFetcher;
	@Autowired
	private BookDataFetcher bookDataFetcher;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	CustomResourceLoader customResourceLoader;

	@PostConstruct
	private void loadSchema() throws IOException {

		loadDataIntoHSQL();

		File schemaFile = resource.getFile();

		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildRuntimeWiring();

		GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);

		graphQL = GraphQL.newGraphQL(graphQLSchema).build();

	}

	private RuntimeWiring buildRuntimeWiring() {

		return RuntimeWiring.newRuntimeWiring().type("Query", typeWiring -> typeWiring
				.dataFetcher("allBooks", allBooksDataFetcher).dataFetcher("book", bookDataFetcher)).build();
	}

	public GraphQL getGraphQL() {

		return graphQL;
	}

	private void loadDataIntoHSQL() throws IOException {

		loadBooksFromFile().forEach(book -> {
			bookRepository.save(book);
		});

	}

	private List<Book> loadBooksFromFile() throws IOException {

		String booksAsjsonfiedStr = customResourceLoader.getResourceData("classpath:books.json");

		Type empMapType = new TypeToken<ArrayList<Book>>() {
		}.getType();
	
		List<Book> books = new Gson().fromJson(booksAsjsonfiedStr, empMapType);
		
		return books;
	}

}
