package com.graphql_java_generator.client.domain.starwars;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author generated by graphql-java-generator
 * @See https://github.com/graphql-java-generator/graphql-java-generator
 */
public class MutationTypeCreateHuman {

	Human createHuman;

	public void setCreateHuman(Human createHuman) {
		this.createHuman = createHuman;
	}

	public Human getCreateHuman() {
		return createHuman;
	}
	
    public String toString() {
        return "MutationTypeCreateHuman {createHuman: " + createHuman + "}";
    }
}
