package com.dougsvendsen.dropblog.core

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*
import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.NotEmpty
import org.joda.time.format.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty


@Entity
@Table(name = 'posts')
@ToString
@EqualsAndHashCode
class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	Long id
	
	@Column(name = 'title')
	@NotEmpty
	@JsonProperty
	String title
	
	@Column(name = 'content')
	@NotEmpty
	@JsonProperty
	String content
	
	@Column(name = 'create_date')
	@NotNull
	@JsonProperty
	Date createDate
	
	@Column(name = 'update_date')
	@NotNull
	@JsonProperty
	Date updateDate
	
}
