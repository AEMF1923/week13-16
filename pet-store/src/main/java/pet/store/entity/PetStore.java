package pet.store.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



@Entity
@Data
public class PetStore {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MYSQL will manage this
	private Long petStoreId;
	
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip; 
	private String petStorePhone; 
	
	
	/*
	 * I believe this is a form of recursion. 
	 * customers can shop at any pet store...but in the ERD I made 
	 * there was no join table like I did projects.According to Michael you can 
	 * create a table with annotation here in JAVA but by using the thing called JPA
	 */
	@EqualsAndHashCode.Exclude
	@ToString.Exclude  //takes care of the recursion but not sure how
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "pet_store_customer", 
	joinColumns = @JoinColumn(name = "pet_store_id"), 
	inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>(); 
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude  //takes care of the recursion but not sure how
	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>(); 
	
	
}
