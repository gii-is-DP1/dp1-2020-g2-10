package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Stories {
	private List<Story> stories;

	@XmlElement
	public List<Story> getStoryList() {
		if (stories == null) {
			stories = new ArrayList<>();
		}
		return stories;
	}
}
