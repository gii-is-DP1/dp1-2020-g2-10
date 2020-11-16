package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contributions")
public class Contribution extends BaseEntity {

// Entidades

@NotNull
private ContributionType contributionType;

// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "story_id")
private Historia story;

@ManyToOne(optional=false)
//@JoinColumn(name = "author_id")
private Autor author;

//Getters and setters

public ContributionType getContributionType() {
	return contributionType;
}

public void setContributionType(ContributionType contributionType) {
	this.contributionType = contributionType;
}

public Historia getStory() {
	return story;
}

public void setStory(Historia story) {
	this.story = story;
}

public Autor getAuthor() {
	return author;
}

// Método ToString

@Override
public String toString() {
	return "Contribution [contributionType=" + contributionType + ", story=" + story + ", author=" + author + "]";
}



}
