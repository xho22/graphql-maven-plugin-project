package com.graphql_java_generator.client.domain.forum;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.graphql_java_generator.annotation.GraphQLNonScalar;
import com.graphql_java_generator.annotation.GraphQLScalar;

/**
 * @author generated by graphql-java-generator
 * @See https://github.com/graphql-java-generator/graphql-java-generator
 */

public class Topic  {

	@GraphQLScalar(graphqlType = String.class)
	String id;


	@GraphQLScalar(graphqlType = String.class)
	String date;


	@GraphQLNonScalar(graphqlType = Member.class)
	Member author;


	@GraphQLScalar(graphqlType = Boolean.class)
	Boolean publiclyAvailable;


	@GraphQLScalar(graphqlType = Integer.class)
	Integer nbPosts;


	@GraphQLScalar(graphqlType = String.class)
	String title;


	@GraphQLScalar(graphqlType = String.class)
	String content;


	@GraphQLNonScalar(graphqlType = Post.class)
	@JsonDeserialize(contentAs = Post.class)
	List<Post> posts;



	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setAuthor(Member author) {
		this.author = author;
	}

	public Member getAuthor() {
		return author;
	}

	public void setPubliclyAvailable(Boolean publiclyAvailable) {
		this.publiclyAvailable = publiclyAvailable;
	}

	public Boolean getPubliclyAvailable() {
		return publiclyAvailable;
	}

	public void setNbPosts(Integer nbPosts) {
		this.nbPosts = nbPosts;
	}

	public Integer getNbPosts() {
		return nbPosts;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Post> getPosts() {
		return posts;
	}

    public String toString() {
        return "Topic {"
				+ "id: " + id
				+ ", "
				+ "date: " + date
				+ ", "
				+ "author: " + author
				+ ", "
				+ "publiclyAvailable: " + publiclyAvailable
				+ ", "
				+ "nbPosts: " + nbPosts
				+ ", "
				+ "title: " + title
				+ ", "
				+ "content: " + content
				+ ", "
				+ "posts: " + posts
        		+ "}";
    }
}
