package org.springframework.samples.petclinic.web;

import java.nio.file.AccessDeniedException;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Carlos Cabello (https://github.com/carcabcol) 
 * 
 * The controller advice allow us to add global methods for @ExceptionHandler, @ModelAttribute, and @InitBinder**/

@ControllerAdvice
// @ControllerAdvice(assignableTypes = { requestUserMapper.class })
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class AlexandriaControllerAdvice {
	
	
	private UserService userService;
	
	@Autowired
	public AlexandriaControllerAdvice(UserService userService) {
		this.userService = userService;
	}
	/* Devuelve el usuario loguado, o null si no lo está*/
	@ModelAttribute("principal")
	public User populatePrincipal() {
		return userService.getPrincipal();
	}

}
