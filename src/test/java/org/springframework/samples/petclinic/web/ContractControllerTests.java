package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.service.CompanyService;
import org.springframework.samples.petclinic.service.ContractService;

@WebMvcTest(value = ContractController.class, excludeAutoConfiguration = SecurityConfiguration.class)
public class ContractControllerTests {

	private static final int TEST_CONTRACT_ID = 1;
	
	private static final int TEST_COMPANY_ID = 1;
	
	@Autowired
	private ContractController contractController;
	
	@MockBean
	private ContractService contractService;
	
	@MockBean
	private CompanyService companyService;
	
	@BeforeEach
	void setup() {
		Company comp = new Company();
		Optional<Company> opCom =Optional.of(comp);
		
		given(this.contractService.findContractById(TEST_CONTRACT_ID)).willReturn(new Contract());
		given(this.companyService.findCompanyById(TEST_COMPANY_ID)).willReturn( opCom);
	}

	

}
