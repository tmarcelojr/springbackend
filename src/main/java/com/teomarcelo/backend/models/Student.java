package com.teomarcelo.backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


// Entities in JPA are nothing but POJOs (Plain Old Java Objects) representing data that can be persisted to the database.  An entity represents a table store in a database. Every instance of an entity represents a row in the table.

// Nice article: https://www.baeldung.com/jpa-entities
// If you see @Bean instead of @Entity
@Entity
@Table(name="students")
public class Student {
	
//	GenerationType.IDENTITY indicates that the persistence provider must assign primary keys for the entity using a dataase identity column.
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private String firstname;
	@Column
	private String lastname;
	
	public int getId() {
		return id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}
