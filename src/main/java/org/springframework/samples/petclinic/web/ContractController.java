package org.springframework.samples.petclinic.web;


import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@Autowired
	AuthorService authorService;
	
	// TODO: Si peta, crear el constructor exlicitamente y poner private final para los atributos
	
	private static final String VIEW_LIST_CONTRACTS="contracts/listContracts";
	
	private static final String VIEW_SHOW_CONTRACTS="contracts/showContracts";
	
	private static final String VIEWS_CONTRACT_CREATE_FORM = "contracts/createContractForm";
	
	
	public ContractController(ContractService contractService, CompanyService companyService) {
		super();
		this.contractService = contractService;
		this.companyService = companyService;
	}


	@InitBinder
 	public void setAllowedFields(WebDataBinder dataBinder) {
 		dataBinder.setDisallowedFields("id");
 	}
	
	// HU-09 Listar contrato como Autor
	// HU-09 Listar contrato como Empresa
	
	@GetMapping(value = { "/list" })
	public String listContracts(Map<String, Object> model) {
		
		Collection<Contract> pendingContracts = contractService.findByPrincipalAndStatus(ContractStatus.PENDING);
		Collection<Contract> acceptedContracts = contractService.findByPrincipalAndStatus(ContractStatus.ACCEPTED);
		Collection<Contract> allContracts = contractService.findByPrincipalAndStatus(null);
		
		model.put("pendingContracts", pendingContracts);
		model.put("acceptedContracts", acceptedContracts);
		model.put("allContracts", allContracts);
		
		return VIEW_LIST_CONTRACTS;
	}
	
	// HU-08 Envío de un contrato de una empresa a un autor
	
	@GetMapping(value = "/new")
	public String initCreationContractForm(Author author,Company company , ModelMap model) {

		Contract contract = contractService.createContract();
		contract.setContractStatus(ContractStatus.PENDING);
		model.put("contract", contract);		
			
		return VIEWS_CONTRACT_CREATE_FORM;
	}
	
	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Contract contract, BindingResult result, ModelMap model) {		
		
		//Validación de que la fecha fin no puede ser ni igual ni anterior a la fecha de inicio del contrato
		
		if (contract.getEndDate()!=null && contract.getStartDate()!=null && !(contract.getEndDate().after(contract.getStartDate()))) {
			
			ObjectError error1 = new ObjectError("endDateIsBeforeStartDate", "EndDate is  before startDate");
            result.addError(error1);
		}
		
		//Validación: el id del author no puede ser nulo, la solicitud del contrato tiene que tener una id del autor
		
		Integer auId= contract.getAuthor().getId();
		
		 if(!( auId!= null)) {
            ObjectError error2 = new ObjectError("idAuthor", "You must put the author's id");
            result.addError(error2);
        }
		
		if (result.hasErrors()) {
			model.put("contract", contract);
			
			if(auId == null) {
				model.addAttribute("idAuthor", true);

			}else {
				model.addAttribute("idAuthor", false);

			}
			
	
			if(contract.getEndDate()!=null && contract.getStartDate()!=null && (contract.getEndDate().before(contract.getStartDate()))){
				model.addAttribute("endDateIsBeforeStartDate", true);
				
			}else  if ((contract.getEndDate()!=null && contract.getStartDate()!=null) && (contract.getEndDate().after(contract.getStartDate())
					)){
				model.addAttribute("endDateIsBeforeStartDate", false);
			}
			
			System.out.println(result);
			return VIEWS_CONTRACT_CREATE_FORM;
		}
		else {
			System.out.println(contract);
			System.out.println("=================ID del Autor======================:" +contract.getAuthor().getId());
			contract.setCompany(companyService.getPrincipal());
			
			try {
				contract.setAuthor(authorService.findAuthorById(contract.getAuthor().getId()));
				contractService.saveContract(contract);

			}catch (DataAccessException ex) {
				result.rejectValue("author.id","notExist" ,"You have to put an author that exists");
				return VIEWS_CONTRACT_CREATE_FORM;
			}
			
	
			model.addAttribute("messageSuccess", "¡El contrato se ha enviado correctamente!");
			return "redirect:/contracts/list";

		}
	}
	
	// H10: Aceptar o rechazar contratos recibidos (Autor)
	@GetMapping(value = { "/{contractId}/accept" })
	public String acceptContract(@PathVariable("contractId") int contractId, ModelMap modelMap) throws DataAccessException {
		
		contractService.answerContract(contractId, ContractStatus.ACCEPTED);
		return "redirect:/contracts/list";
	}
	
	@GetMapping(value = { "/{contractId}/reject" })
	public String rejectContract(@PathVariable("contractId") int contractId, ModelMap modelMap) throws DataAccessException  {
		
		contractService.answerContract(contractId, ContractStatus.REJECTED);
		return "redirect:/contracts/list";
	}
	
	// HU-11: Listar y mostrar contratos generados por una compañia.
	
	@GetMapping(value = { "/company/list" })
	public String listContractsOfCompany(ModelMap modelMap) {
		Collection<Contract> contracts = this.contractService.findContractsByCompanyId();
		modelMap.put("contracts", contracts);
		return VIEW_LIST_CONTRACTS;
	}
	
	@GetMapping(value = { "/{contractId}/show" })
	public String showContract(@PathVariable("contractId") int contractId, Map<String, Object> model) {
		System.out.println("===================showContract===================");
		System.out.println("======================================");
		System.out.println("======================================");
		System.out.println("======================================");
		System.out.println("contractId: " + contractId);
		Contract contract = contractService.findContractById(contractId);
		System.out.println(contract);
		model.put("contract", contract);
		return VIEW_SHOW_CONTRACTS;
	}

}
