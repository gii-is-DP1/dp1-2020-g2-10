package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "contributions")
public class Contribution extends BaseEntity {

// Entidades

@NotNull
@Enumerated(EnumType.STRING)
private ContributionType contributionType;

// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "author_id")
private Author author;

@ManyToOne(optional=false)
@JoinColumn(name = "story_id")
private Story story;

//Getters and setters

public ContributionType getContributionType() {
	return contributionType;
}

public void setContributionType(ContributionType contributionType) {
	this.contributionType = contributionType;
}

public Story getStory() {
	return story;
}

public void setStory(Story story) {
	this.story = story;
}

public Author getAuthor() {
	return author;
}

public void setAuthor(Author author) {
	this.author = author;
}
// Método ToString

@Override
public String toString() {
	return "Contribution [contributionType=" + contributionType + ", story=" + story + ", author=" + author + "]";
}



}
