package org.graphql.maven.plugin.samples.server.generated;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author generated by graphql-maven-plugin
 */
@Entity(name = "Character")
@Table(name = "character")
public class CharacterImpl implements Character {

	@Id
	// @GeneratedValue
	String id;

	String name;

	@JsonDeserialize(contentAs = CharacterImpl.class)
	@ManyToMany(targetEntity = CharacterImpl.class)
	@CollectionTable(name = "character_friends", joinColumns = @JoinColumn(name = "character_id"))
	@Column(name = "friend_id")
	List<Character> friends;

	@JsonDeserialize(contentAs = Episode.class)
	// @Enumerated(EnumType.STRING)
	// @ElementCollection(fetch = FetchType.EAGER)
	// @JoinTable(name = "CharacterImpl_appears_in", joinColumns = @JoinColumn(name = "CharacterImpl_id"),
	// inverseJoinColumns =
	// @JoinColumn(name = "episode_id"), uniqueConstraints = {})
	@ElementCollection(targetClass = Episode.class)
	@CollectionTable(name = "character_appears_in", joinColumns = @JoinColumn(name = "character_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "episode")
	List<Episode> appearsIn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Character> getFriends() {
		return friends;
	}

	public void setFriends(List<Character> friends) {
		this.friends = friends;
	}

	public List<Episode> getAppearsIn() {
		return appearsIn;
	}

	public void setAppearsIn(List<Episode> appearsIn) {
		this.appearsIn = appearsIn;
	}

}
