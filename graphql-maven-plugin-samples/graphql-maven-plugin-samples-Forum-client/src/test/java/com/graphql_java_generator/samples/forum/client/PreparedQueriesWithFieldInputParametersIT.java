package com.graphql_java_generator.samples.forum.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.graphql_java_generator.client.response.GraphQLRequestExecutionException;
import com.graphql_java_generator.client.response.GraphQLRequestPreparationException;
import com.graphql_java_generator.samples.forum.client.graphql.forum.client.Topic;

/**
 * Check that the server correctly works with the combination for the arguments for the post field: as there are
 * optional argument, multiple queries must be implemented. In order to the sample to be properly coded, all must be
 * tested.
 * 
 * @author EtienneSF
 *
 */
public class PreparedQueriesWithFieldInputParametersIT {

	PreparedQueriesWithFieldInputParameters preparedQueriesWithFieldInputParameters;

	String boardName;
	Date since;

	PreparedQueriesWithFieldInputParametersIT() throws GraphQLRequestPreparationException {
		preparedQueriesWithFieldInputParameters = new PreparedQueriesWithFieldInputParameters();
	}

	@BeforeEach
	void beforeEach() {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(2018, 9, 20);// Month is 0-based, so this date is 2018, October the 20th
		since = cal.getTime();

		boardName = "Board name 2";
	}

	/**
	 * Only the since parameter
	 * 
	 * @throws GraphQLRequestExecutionException
	 */
	@Test
	void test_boardsWithPostSince() throws GraphQLRequestExecutionException {
		// Go, go, go
		List<Topic> topics = preparedQueriesWithFieldInputParameters.boardsWithPostSince(boardName, null, null, since);

		// Verification
		assertTrue(topics.size() >= 5);
		assertEquals(2, topics.get(0).getPosts().size(), "First topic has two posts since 2018-10-20");
		assertEquals(2, topics.get(1).getPosts().size(), "Second topic has two posts since 2018-10-20");
	}

	/**
	 * With the memberId and since parameters
	 * 
	 * @throws GraphQLRequestExecutionException
	 */
	@Test
	void test_memberId_since() throws GraphQLRequestExecutionException {

		// Go, go, go
		List<Topic> topics = preparedQueriesWithFieldInputParameters.boardsWithPostSince(boardName,
				UUID.fromString("00000000-0000-0000-0000-00000000002"), null, since);

		// Verification
		assertTrue(topics.size() >= 5);
		assertEquals(2, topics.get(0).getPosts().size(),
				"First topic has two posts since 2018-10-20 for member '00000000-0000-0000-0000-00000000002'");
		assertEquals(0, topics.get(1).getPosts().size(),
				"Second topic has no posts since 2018-10-20 for member '00000000-0000-0000-0000-00000000002'");
	}

	/**
	 * With the memberName and since parameters
	 * 
	 * @throws GraphQLRequestExecutionException
	 */
	@Test
	void test_memberName_since() throws GraphQLRequestExecutionException {

		// Go, go, go
		List<Topic> topics = preparedQueriesWithFieldInputParameters.boardsWithPostSince(boardName, null, "Name 12",
				since);

		// Verification
		assertTrue(topics.size() >= 5);
		assertEquals(0, topics.get(0).getPosts().size(),
				"First topic has no posts since 2018-10-20 for member 'Name 12'");
		assertEquals(2, topics.get(1).getPosts().size(),
				"Second topic has two posts since 2018-10-20 for member 'Name 12'");
	}

	/**
	 * With the memberId, memberName and since
	 * 
	 * @throws GraphQLRequestExecutionException
	 */
	@Test
	void test_memberId_memberName_since_OK() throws GraphQLRequestExecutionException {

		// Go, go, go
		List<Topic> topics = preparedQueriesWithFieldInputParameters.boardsWithPostSince(boardName,
				UUID.fromString("00000000-0000-0000-0000-00000000002"), "Name 2", since);

		// Verification
		assertTrue(topics.size() >= 5);
		assertEquals(2, topics.get(0).getPosts().size(),
				"First topic has two posts since 2018-10-20 for member '00000000-0000-0000-0000-00000000002'");
		assertEquals(0, topics.get(1).getPosts().size(),
				"Second topic has no posts since 2018-10-20 for member '00000000-0000-0000-0000-00000000002'");
	}

	/**
	 * With the memberId, memberName and since
	 * 
	 * @throws GraphQLRequestExecutionException
	 */
	@Test
	void test_memberId_memberName_since_KO() throws GraphQLRequestExecutionException {

		List<Topic> topics = preparedQueriesWithFieldInputParameters.boardsWithPostSince(boardName,
				UUID.fromString("00000000-0000-0000-0000-00000000002"), "Bad Name", since);

		// Verification
		assertTrue(topics.size() >= 5);
		assertEquals(0, topics.get(0).getPosts().size(),
				"First topic has two posts since 2018-10-20 for member '00000000-0000-0000-0000-00000000002'");
		assertEquals(0, topics.get(1).getPosts().size(),
				"Second topic has no posts since 2018-10-20 for member '00000000-0000-0000-0000-00000000002'");
	}
}
