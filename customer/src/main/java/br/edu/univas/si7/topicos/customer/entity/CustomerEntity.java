package br.edu.univas.si7.topicos.customer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data

public class CustomerEntity {

	public CustomerEntity(String id2, String name2, String email2, String phoneNumber2) {
		super();
		this.id = id;
		this.name=name;
		this.email=email;
		this.phoneNumber=phoneNumber;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private String id;

	@Column(name = "nome")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "telefone")
	private String phoneNumber;
}
