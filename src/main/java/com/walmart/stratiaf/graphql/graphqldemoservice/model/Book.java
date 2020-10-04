package com.walmart.stratiaf.graphql.graphqldemoservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Book {

	@Id
	private String isn;
	String title;
	String publisher;
	String[] authors;
	String publishedDate;

}
