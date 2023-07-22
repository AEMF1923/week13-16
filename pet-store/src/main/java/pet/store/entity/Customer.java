package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



/*
 * The @Entity annotation declares that the class represents an entity.
 *  @Id declares the attribute which acts as the primary key of the entity. 
 *  Additional annotations may be used to declare additional metadata 
 *  (for example changing the default table name in the @Table annotation), 
 *  or to create associations between entities
 *  The annotations are not comments they do stuff...this was not toally specified. I asked this in class...
 *  they seems calling upon a set of actions from the imports above
 */

@Entity  //jakarta.persistence. Specifies that the class is an entity. This annotation is applied to the entity class.
@Data  //lombok
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //MYSQL will manage this
	private Long customerId;
	
	private String customerFirstName; 
	private String customerLastName; 
	private String customerEmail;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude  //takes care of the recursion but not sure how
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	private Set<PetStore> petStores = new HashSet<>(); 
}
