package org.springframework.samples.petclinic.web;


import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractStatus;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.CompanyService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contracts")
public class ContractController {
	
	@Autowired
	ContractService contractService;
	
	@Autowired
	CompanyService companyService;
	
	// TODO: Si peta, crear el constructor exlicitamente y poner private final para los atributos
	
	private static final String VIEW_LIST_CONTRACTS="contracts/listContracts";
	
	private static final String VIEW_SHOW_CONTRACTS="contracts/showContracts";
	
	private static final String VIEWS_CONTRACTS_LIST = "contracts/contractsList";
	
	private static final String VIEWS_CONTRACTS_SHOW = "contracts/contractsShow";
	
	private static final String VIEWS_CONTRACT_CREATE_FORM = "contracts/createContractForm";
	
	@InitBinder
 	public void setAllowedFields(WebDataBinder dataBinder) {
 		dataBinder.setDisallowedFields("id");
 	}
	
	@GetMapping(value = { "/list" })
	public String listContracts(Map<String, Object> model) {
		Collection<Contract> contracts = contractService.findByAuthorPrincipalAndStatus(null);
		model.put("contracts", contracts);
		return VIEWS_CONTRACTS_LIST;
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
	
	// HU-11: Listar y mostrar contratos generados por una compañia.
	
	@GetMapping(value = { "/company/{companyId}/list" })
	public String listContractsOfCompany(@PathVariable("companyId") int companyId, ModelMap modelMap) {
		Iterable<Contract> contracts = this.contractService.findContractsByCompanyId(companyId);
		modelMap.put("contracts", contracts);
		modelMap.put("companyId", companyId);
		return VIEW_LIST_CONTRACTS;
	}
	
	@GetMapping(value = { "/{contractId}/show" })
	public String showContract(@PathVariable("contractId") int contractId, ModelMap modelMap) {
		Contract contract = this.contractService.findContractById(contractId);
		modelMap.put("contract", contract);
		return VIEW_SHOW_CONTRACTS;
	}

}
