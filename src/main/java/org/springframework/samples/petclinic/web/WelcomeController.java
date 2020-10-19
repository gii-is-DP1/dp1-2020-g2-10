package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	
		  
		  List<Person> persons = new ArrayList<Person>();
		  
		  Person person1 = new Person();
		  Person person2 = new Person();
		  Person person3 = new Person();
		  Person person4 = new Person();
		  Person person5 = new Person();
		  Person person6 = new Person();
		  
		  person1.setFirstName("Óscar");
		  person1.setLastName("Dorado");
		  
		  person2.setFirstName("David");
		  person2.setLastName("García");
		  
		  person3.setFirstName("Pablo");
		  person3.setLastName("Calle");
		  
		  person4.setFirstName("Félix");
		  person4.setLastName("Conde");
		  
		  person5.setFirstName("Mariano");
		  person5.setLastName("Martín");
		  
		  person6.setFirstName("Carlos Manuel");
		  person6.setLastName("Cabellos");
		  
		  persons.add(person1);
		  persons.add(person2);
		  persons.add(person3);
		  persons.add(person4);
		  persons.add(person5);
		  persons.add(person6);
		  
		  model.put("persons", persons);
		  model.put("title", "Untitled" );
		  model.put("group", "Grupo 10");

	    return "welcome";
	  }
}
