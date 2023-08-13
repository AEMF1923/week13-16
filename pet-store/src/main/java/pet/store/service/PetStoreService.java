package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;
import pet.store.entity.Customer;

/*
 * 
 * Add a PetStoreDao object named petStoreDao as a private instance
 *  variable. Annotate the instance variable with 
 * @Autowired so that Spring can inject the DAO object into the variable.
 * 
 * Long is an integer but bigger
 */
@Service
public class PetStoreService {

	@Autowired
	private PetStoreDao petStoreDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);

		copyPetStoreFields(petStore, petStoreData); // this is a method call. it calls upon a method
		return new PetStoreData(petStoreDao.save(petStore));
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {

		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (Objects.isNull(petStoreId)) {
			return new PetStore();
		} else {
			return findPetStoreById(petStoreId);
		}
	}

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet Store with ID = " + petStoreId + " was not found."));
	}

	@Autowired
	private EmployeeDao employeeDao;

	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);

		copyEmployeeFields(employee, petStoreEmployee); // this is a method call. it calls upon a method. Remember you
														// have to create the method

		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee); // placing the employee in the table or saving it

		return new PetStoreEmployee(employeeDao.save(employee)); // this is ok to do. this is considered shorthand

	}

	/*
	 * order in how you pass the parameters matters.
	 */

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		if (Objects.isNull(employeeId)) { // if you can't find an id it means it is a new employee
			return new Employee();
		} else {
			return findEmployeeById(petStoreId, employeeId);
		}
	}

	/*
	 * This takes the data from the url into these objects
	 * takes your pet store object and makes into what you see below
	 */
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());

	}

	/*
	 * Remember that the DAO extends the JPA-CRUD operations. remember how this
	 * travels: URL -> controller -> service -> Dao (takes the data from the DTO) 
	 * The no such element exception is
	 * 404 error but it makes more sense and is readable
	 */
	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId) // I have to create an employee object and not return new.
																// Also remember you injected an instance
				.orElseThrow(
						() -> new NoSuchElementException("No employee with ID = " + employeeId + " was not found."));

		/*
		 * I have to check whether the petstore id matches to each employee id. this is
		 * why we must do the this if statement
		 */
		if (employee.getPetStore().getPetStoreId() != petStoreId) {
			throw new NoSuchElementException(
					" The employee id " + employeeId + " is not employed by this pet store " + petStoreId);
		}
		return employee;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Autowired
	private CustomerDao customerDao;

	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {

		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();// remember you built this a week ago inside of PetStoreData
		Customer customer = findOrCreateCustomer(petStoreId, customerId);

		copyCustomerFields(customer, petStoreCustomer); // This is a method call that says grab the data from the url
														// and we make it into objects in here

		customer.getPetStores().add(petStore); // remember in the method. We state that is takes the petstoreid
												// parameter
		petStore.getCustomers().add(customer); // we are getting a pet store and not setting because a
		// a customer can shop at multiple stores

		return new PetStoreCustomer(customerDao.save(customer)); // this is a faster way to write the dbCustomer code

	}

	/*
	 * think of this of a contributor taking infromation from the customer enity and
	 * filling it in
	 */
	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}

	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {

		if (Objects.isNull(customerId)) { // if you can't find an id it means it is a new customer
			return new Customer();
		} else {
			return findCustomerById(petStoreId, customerId);
		}
	}

	/*
	 * This method has a many to many relationship
	 */
	private Customer findCustomerById(Long petStoreId, Long customerId) {

		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID = " + customerId + " was not found."));
		boolean found = false; // think of this of a new variable that helps you find a match

		for (PetStore petStore : customer.getPetStores()) { // the customer is linked to pet store. remember the entity
															// relantioship
			if (petStore.getPetStoreId() == petStoreId) {
				found = true;
				break; // this stops the loop when the pet store id is found
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"Customer with ID = " + customerId + " doesn't belong to the pet store with ID = " + petStoreId);
		}
		return customer;
	}

	/*
	 * Remember that capitalization is important to dictate what kind of objects you
	 * are calling and referencing
	 * 
	 */
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();

		for (PetStore petStore : petStores) {
			PetStoreData psd = new PetStoreData(petStore);
			psd.getCustomers().clear(); // we don't want a list with customers. Only the stores
			psd.getEmployees().clear();

			result.add(psd);

		}

		return result;
	}

	@Transactional(readOnly = true)
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);

		if (petStore == null) {
			throw new IllegalStateException("Pet Store with Id = " + petStoreId + " does not exist");

		}

		return new PetStoreData(petStore); // remember you made an object that is called petStore that returns the pet
											// store Id
	}

	public void deletePetStoreById(Long petStoreId) {

		PetStore petStore = findPetStoreById(petStoreId); // this is you finding the pet store first
		petStoreDao.delete(petStore); // this is you deleting it
	}


}
