package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

// Atributos
	
@NotEmpty
private String title;

@NotNull
@Range(min=0, max=5)
private Integer rating;

@NotEmpty
private String text;

@NotNull
@PastOrPresent
@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
private Date publicationDate;

// Relaciones

@ManyToOne(optional=false)
//@JoinColumn(name = "author_id")
private Author author;

@ManyToOne(optional=false)
//@JoinColumn(name = "story_id")
private Story story;

//Getters ands setters

public Integer getRating() {
	return rating;
}

public void setPunctuation(Integer rating) {
	this.rating = rating;
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public String getTitle() {
	return title;
}

public Date getPublicationDate() {
	return publicationDate;
}

public Author getAuthor() {
	return author;
}

public Story getStory() {
	return story;
}

// Método ToString
@Override
public String toString() {
	return "Review [title=" + title + ", punctuation=" + rating + ", text=" + text + ", publicationDate="
			+ publicationDate + ", author=" + author + ", story=" + story + "]";
}


}
