package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "reviews")
public @Data class Review extends BaseEntity {

// Atributos
	
@NotEmpty
private String title;

@NotNull
@Range(min=0, max=5)
@Column(columnDefinition = "NUMBER")
private Integer rating;

@NotEmpty
@Column(columnDefinition = "TEXT")
private String text;

@NotNull
@PastOrPresent
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Column(name = "publication_date")
private Date publicationDate;

// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "author_id")
private Author author;

@ManyToOne(optional=false)
@JoinColumn(name = "story_id")
private Story story;


// Método ToString
@Override
public String toString() {
	return "Review [title=" + title + ", rating=" + rating + ", text=" + text + ", publicationDate="
			+ publicationDate + ", author=" + author + ", story=" + story + "]";
}


}
