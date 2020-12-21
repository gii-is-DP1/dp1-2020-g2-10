package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contracts")
public class ContractController {
	
	@Autowired
	ContractService contractService;
	
	private static final String VIEWS_CONTRACTS_LIST = "contracts/contractsList";
	
	private static final String VIEWS_CONTRACTS_SHOW = "contracts/contractsShow";
	
	@GetMapping(value = { "/list" })
	public String listContracts(Map<String, Object> model) {
		Collection<Contract> contracts = contractService.findByAuthorPrincipalAndStatus(null);
		model.put("contracts", contracts);
		return VIEWS_CONTRACTS_LIST;
	}
	
	
	
	

}
