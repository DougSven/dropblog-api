package com.dougsvendsen.dropblog.core

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*
import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.NotEmpty
import org.joda.time.format.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty



class User {

	String name
	
}
