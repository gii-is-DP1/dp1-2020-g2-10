package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.Stories;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.samples.petclinic.service.CompanyService;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.samples.petclinic.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies/{companyId}")
public class ContractController {
	
	private final ContractService contractService;
	private final CompanyService companyService;
	private static final String VIEW_LIST_CONTRACTS="contracts/listContracts";
	private static final String VIEW_SHOW_CONTRACTS="contracts/showContracts";
	
	@Autowired
	public ContractController(ContractService contractService, CompanyService companyService) {

		this.contractService = contractService;
		this.companyService = companyService;
	}
	
	@InitBinder
 	public void setAllowedFields(WebDataBinder dataBinder) {
 		dataBinder.setDisallowedFields("id");
 	}
			
	// HU-11: Listar y mostrar contratos generados por una compañia.
	
	@GetMapping(value = { "/contracts" })
	public String listContractsOfCompany(@PathVariable("companyId") int companyId, ModelMap modelMap) {
		Iterable<Contract> contracts = this.contractService.findContractsByCompanyId(companyId);
		modelMap.put("contracts", contracts);
		modelMap.put("companyId", companyId);
		return VIEW_LIST_CONTRACTS;
	}
	
	@GetMapping(value = { "/contracts/{contractId}" })
	public String showContract(@PathVariable("contractId") int contractId, ModelMap modelMap) {
		Contract contract = this.contractService.findContractById(contractId);
		modelMap.put("contract", contract);
		return VIEW_SHOW_CONTRACTS;
	}
	
}

