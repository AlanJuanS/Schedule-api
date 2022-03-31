package com.Schedule.crm.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import com.Schedule.crm.Entity.Client;
import com.Schedule.crm.service.ClientService;


@RestController
@RequestMapping("/client")
public class ClientController {
	
	
	@Autowired
	ClientService clientService;
	

	@GetMapping
	public List<Client> getAll() {
	  return clientService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Client> getById(@PathVariable Long id) {
		Optional <Client> cliente = clientService.findById(id);
		if(!cliente.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(cliente.get());	
	}
	@PostMapping
	@ResponseStatus (HttpStatus.CREATED)
	public Client post(@RequestBody @Valid Client response) {
		return  clientService.salve(response);
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable long id) {
		Optional<Client> client = clientService.findById(id);
		if (!client.isPresent()) {
			return "cliente nao encontrado";
		}
		clientService.delete(id);
	   return "deletado com sucesso";
	   
	}
	
	@PutMapping
	public String update(@RequestBody Client response) {
		Optional<Client> client = clientService.findById(response.getId());
		if (!client.isPresent()) {
			return "cliente nao encontrado";
		}
		Client save = clientService.salve(response);
	   return "atualizado com sucesso";	   
	}
	

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> handleValidationException(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName =((FieldError) error).getField();
			String errorsMessage = ( error).getDefaultMessage();
			errors.put(fieldName, errorsMessage);
		});
		
		return errors;
	}
	
	
}
