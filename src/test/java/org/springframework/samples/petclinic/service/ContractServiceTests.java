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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Company;
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
	
	//Tests HU11
	@Test
	void shouldFindContracts() {
		
		Collection<Contract> contracts = this.contractService.findContractsByCompanyId(1);
		assertThat(contracts.size()).isEqualTo(1);
		//FECHAS
		//OFERTA
		Date offerDate = new Date(120, 11, 8, 15, 0, 0);
		//RESPUESTA
		Date answerDate = new Date(120, 11, 15, 12, 0, 0);
		//INICIO
		Date startDate = new Date(121, 0, 2, 14, 0, 0);
		//FIN
		Date endDate = new Date(121, 1, 2, 14, 0, 0); //2020-08-15 12:00','2021-01-01 14:00','2021-02-02 14:00
		
		Contract contract = EntityUtils.getById(contracts, Contract.class, 1);
		assertThat(contract.getHeader()).isEqualTo("Oferta de contrato 1");
		assertThat(contract.getBody()).isEqualTo("Nos ponemos en contacto con usted porque estamos "
				+ "interesados en contratarle en nuestra editorial");
//		assertThat(contract.getOfferDate()).isEqualTo(offerDate);
//		assertThat(contract.getStartDate()).isEqualTo(startDate);
//		assertThat(contract.getEndDate()).isEqualTo(startDate);
//		assertThat(contract.getAnswerDate()).isEqualTo(startDate);
		assertThat(contract.getRemuneration()).isEqualTo(6.89);
		
	}
}
