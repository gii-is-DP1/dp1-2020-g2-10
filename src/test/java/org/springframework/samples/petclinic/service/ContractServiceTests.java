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
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractStatus;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ContractServiceTests {        
    
	@Autowired
	protected ContractService contractService;
	
	@Autowired
    protected CompanyService companyService;
	
	@Autowired
	protected AuthorService authorService;

	//Tests HU11
		@Test
		@Transactional
		@WithMockUser(value = "author1", authorities = {
		        "author"
		    })
		void shouldFindContractsAuthor() {
			
			Collection<Contract> contracts = this.contractService.findContractsByAuthorId();
			assertThat(contracts.size()).isEqualTo(4);
			//FECHAS
			//OFERTA
			//Usando el tipo Date fallaba puesto que las fechas se inicializan en la BD como TimeStamp
			//Date offerDate = new Date(120, 7, 20, 16, 30, 0);
			Timestamp offerDate = new Timestamp(120, 7, 20, 16, 30, 0, 0); 
			//RESPUESTA
			Timestamp answerDate = new Timestamp(120, 7, 25, 18, 27, 0, 0); 
			//INICIO
			Timestamp startDate = new Timestamp(120, 8, 1, 0, 0, 0, 0); 
			//FIN
			Timestamp endDate = new Timestamp(120, 8, 30, 23, 59, 0, 0); 

			Contract contract = EntityUtils.getById(contracts, Contract.class, 1);
			assertThat(contract.getHeader()).isEqualTo("Mecenazgo EXCLUSIVO SEPTIEMBRE 2020");
			assertThat(contract.getBody()).isEqualTo("Por el presente contrato se estipula que Bookista ofrece"
					+ " un mecenazgo a Marco Medina Sandoval. Marco Medina se compromete a"
					+ " NO ACEPTAR PATROCINIOS de otras compañías durante el mes de SEPTIEMBRE 2020.");
			assertThat(contract.getOfferDate()).isEqualTo(offerDate);
			assertThat(contract.getStartDate()).isEqualTo(startDate);
			assertThat(contract.getEndDate()).isEqualTo(endDate);
			assertThat(contract.getAnswerDate()).isEqualTo(answerDate);
			assertThat(contract.getRemuneration()).isEqualTo(12000.5);
			assertThat(contract.getIsExclusive()).isEqualTo(true);
			assertThat(contract.getContractStatus()).isEqualTo(ContractStatus.ACCEPTED);
		}
		
	//Tests HU11
	@Test
	@Transactional
	@WithMockUser(value = "company1", authorities = {
	        "company"
	    })
	void shouldFindContracts() {
		
		Collection<Contract> contracts = this.contractService.findContractsByCompanyId();
		assertThat(contracts.size()).isEqualTo(4);
		//FECHAS
		//OFERTA
		//Usando el tipo Date fallaba puesto que las fechas se inicializan en la BD como TimeStamp
		//Date offerDate = new Date(120, 7, 20, 16, 30, 0);
		Timestamp offerDate = new Timestamp(120, 7, 20, 16, 30, 0, 0); 
		//RESPUESTA
		Timestamp answerDate = new Timestamp(120, 7, 25, 18, 27, 0, 0); 
		//INICIO
		Timestamp startDate = new Timestamp(120, 8, 1, 0, 0, 0, 0); 
		//FIN
		Timestamp endDate = new Timestamp(120, 8, 30, 23, 59, 0, 0); 

		Contract contract = EntityUtils.getById(contracts, Contract.class, 1);
		assertThat(contract.getHeader()).isEqualTo("Mecenazgo EXCLUSIVO SEPTIEMBRE 2020");
		assertThat(contract.getBody()).isEqualTo("Por el presente contrato se estipula que Bookista ofrece"
				+ " un mecenazgo a Marco Medina Sandoval. Marco Medina se compromete a"
				+ " NO ACEPTAR PATROCINIOS de otras compañías durante el mes de SEPTIEMBRE 2020.");
		assertThat(contract.getOfferDate()).isEqualTo(offerDate);
		assertThat(contract.getStartDate()).isEqualTo(startDate);
		assertThat(contract.getEndDate()).isEqualTo(endDate);
		assertThat(contract.getAnswerDate()).isEqualTo(answerDate);
		assertThat(contract.getRemuneration()).isEqualTo(12000.5);
		assertThat(contract.getIsExclusive()).isEqualTo(true);
		assertThat(contract.getContractStatus()).isEqualTo(ContractStatus.ACCEPTED);
	}
	
	
}
