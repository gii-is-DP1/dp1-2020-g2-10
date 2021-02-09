package org.springframework.samples.petclinic.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User{
	@Id
	@Getter @Setter
	String username;
	
	@Getter @Setter
	String password;
	
	@Getter @Setter
	boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@Getter @Setter
	private Set<Authorities> authorities;
}
