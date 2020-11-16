package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.samples.petclinic.model.Capitulo;
import org.springframework.samples.petclinic.repository.CapituloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CapituloService {

	private CapituloRepository capituloRepository;	
	
	@Autowired
	public CapituloService(CapituloRepository capituloRepository) {
		this.capituloRepository = capituloRepository;
	}	

	

	@Transactional
	public void saveCapitulo(Capitulo capitulo) throws DataAccessException {
		
		// Creamos el capítulo
		capituloRepository.save(capitulo);		
		
	}		

}

