package pet.store.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MYSQL will manage this
	private Long employeeId; 
	
	
	private String employeeFirstName; //these are columns in the database
	private String employeeLastName; 
	private String employeePhone;  
	private String employeeJobTitle; 
	
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude  //takes care of the recursion but not sure how
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet_store_id")
	private PetStore petStore;
	

}
