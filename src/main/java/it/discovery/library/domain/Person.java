package it.discovery.library.domain;

import it.discovery.monolith.domain.BaseEntity;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table
public class Person extends BaseEntity{
	private String name;
	
	private String biography;
	
	@OneToMany
	private List<Book> books;
	
	private LocalDate birthDate;
	
}
