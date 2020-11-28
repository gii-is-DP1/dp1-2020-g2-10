/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

import lombok.Data;



@Entity
@Table(name = "authors")
public @Data class Author extends Person {

	private String biography;

	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
	private Set<Story> stories;
	
	
	protected Set<Story> getStoriesInternal() {
		if (this.stories == null) {
			this.stories = new HashSet<>();
		}
		return this.stories;
	}

	protected void setStoriesInternal(Set<Story> stories) {
		this.stories = stories;
	}

	public List<Story> getStories() {
		List<Story> sortedStories = new ArrayList<>(getStoriesInternal());
		PropertyComparator.sort(sortedStories, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedStories);
	}


	public void addStory(Story story) {
		getStoriesInternal().add(story);
		story.setAuthor(this);
	}
	
	public boolean removeStory(Story story) {
		return getStoriesInternal().remove(story);
	}
	
	
	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("id", this.getId()).append("new", this.isNew()).append("lastName", this.getLastName())
				.append("firstName", this.getFirstName()).append("biografia", this.biography).toString();
	}

}
