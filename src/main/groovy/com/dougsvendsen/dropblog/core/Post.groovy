package com.dougsvendsen.dropblog.core

import javax.persistence.*

import com.google.common.base.Splitter.Strategy;

class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id
	
}
