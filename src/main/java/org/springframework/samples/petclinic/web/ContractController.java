package org.springframework.samples.petclinic.web;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractStatus;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.CompanyService;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contracts")
public class ContractController {

	private static final String VIEWS_CONTRACT_CREATE_FORM = "contracts/createContractForm";

	@Autowired
	private final ContractService contractService;

	public ContractController(ContractService contractService, AuthorService authorService, CompanyService companyService) {
		super();
		this.contractService = contractService;
		
	}
	
	@GetMapping(value = "/new")
	public String initCreationContractForm(Author author,Company company , ModelMap model) {

		Contract contract = contractService.createContract();
		model.put("contract", contract);		
			
		model.put("contractStatus", Arrays.asList(ContractStatus.values()));
		return VIEWS_CONTRACT_CREATE_FORM;
	}
	
	@PostMapping(value = "/new")
	public String processCreationForm(Author author,Company company, @Valid Contract contract, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("contract", contract);
			System.out.println(result);
			return VIEWS_CONTRACT_CREATE_FORM;
		}
		else {
			contractService.saveContract(contract);
            return "redirect:/";

		}
	}
	
	
}
	

