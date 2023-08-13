package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

import java.util.List;
import java.util.Map;
import java.util.LinkedList;



//@RestController indicates that this class is a controller for handling RESTful API requests.
//@RequestMapping("/pet_store") specifies that the base URL for this controller is "/pet_store".
//@Slf4j is used for logging.
@RestController
@RequestMapping("/pet_store") //this relates to the ARC api stuff
@Slf4j
public class PetStoreController {

	@Autowired
	private PetStoreService petStoreService;
	
	@PostMapping //this relates to the ARC api stuff
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	/*
	 * @PutMapping annotation to map PUT requests to this method. 
	 * The annotation should specify that a pet store ID is present in the HTTP request URI.
	 */
	@PutMapping("/{petStoreId}") //this relates to the ARC api stuff; specifically the url 
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, 
			@RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	
	/*
	 * THIS IS CREATE creating a new employee
	 * POST = CREATE
	 */
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED) //this is 201 created
	public PetStoreEmployee addEmployee(@PathVariable Long petStoreId, 
			@RequestBody PetStoreEmployee petStoreEmployee) {
		
		log.info("Adding a new pet store employee {}", petStoreEmployee);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	/*
	 * Get = READ 
	 * Read or list all pet stores no specification on identification 
	 */
	@GetMapping
	public List<PetStoreData> retreieveAllPetStores () {
		
		log.info("Retrieving all pet stores: ");
		return petStoreService.retrieveAllPetStores(); 
	}
	
	
	//Read pet store by Id
	// remember the brackets as a placeholder for that column of values
	@GetMapping("/{petStoreId}") 
	public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
		log.info("Retrieving pet store with ID={}", petStoreId);

		return petStoreService.retrievePetStoreById(petStoreId);
	}
	
	/*
	 * The log means that you are logging the request. in other words, you are 
	 * tracking this request and if it errors out or fails. you will know where it failed
	 * Delete mapping = delete 
	 * Maps are used for key-value associations like dictionary, the word is the key 
	 * and the definiton is the value
	 * Here we are using it as a map of errors codes and the values we placed 
	 */
	@DeleteMapping("/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
	
		petStoreService.deletePetStoreById(petStoreId); //this is what calls the service and deletes the pet store
		
	log.info("Deleting pet store with id {}", petStoreId);
	return Map.of("message", "Deletion of Pet Store ID: " + petStoreId + " was succesful!!"); 
}
	
	/*
	 * THIS IS CREATE creating a new CUSTOMER
	 * POST = CREATE
	 */
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED) //this is 201 created
	public PetStoreCustomer addCustomer(@PathVariable Long petStoreId, 
			@RequestBody PetStoreCustomer petStoreCustomer) {
		
		log.info("Adding a new pet store customer {}", petStoreCustomer);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
}
