package com.graphql_java_generator.client.domain.forum;

import java.util.UUID;

import com.graphql_java_generator.annotation.GraphQLInputType;
import com.graphql_java_generator.annotation.GraphQLNonScalar;
import com.graphql_java_generator.annotation.GraphQLScalar;

/**
 * @author generated by graphql-java-generator
 * @see <a href=
 *      "https://github.com/graphql-java-generator/graphql-java-generator">https://github.com/graphql-java-generator/graphql-java-generator</a>
 */
@GraphQLInputType
public class TopicInput {

	@GraphQLScalar(graphqlType = UUID.class)
	UUID boardId;

	@GraphQLNonScalar(graphqlType = TopicPostInput.class)
	TopicPostInput input;

	public void setBoardId(UUID boardId) {
		this.boardId = boardId;
	}

	public UUID getBoardId() {
		return boardId;
	}

	public void setInput(TopicPostInput input) {
		this.input = input;
	}

	public TopicPostInput getInput() {
		return input;
	}

	@Override
	public String toString() {
		return "TopicInput {" + "boardId: " + boardId + ", " + "input: " + input + "}";
	}
}
