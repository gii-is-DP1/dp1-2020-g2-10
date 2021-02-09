package org.springframework.samples.petclinic.service.exceptions.contract;

public class ExclusivityContractConflict extends Exception{

	public ExclusivityContractConflict(String message){
		 super(message);
	}
}
