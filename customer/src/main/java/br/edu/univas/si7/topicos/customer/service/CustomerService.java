package br.edu.univas.si7.topicos.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.univas.si7.topicos.customer.dto.CustomerDto;
import br.edu.univas.si7.topicos.customer.entity.CustomerEntity;
import br.edu.univas.si7.topicos.customer.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repo;

	@Autowired
	public CustomerService(CustomerRepository repo) {
		this.repo = repo;
	}

	public List<CustomerDto> findAll() {
		return repo.findAll().stream().map(p -> new CustomerDto(p)).collect(Collectors.toList());
	}

	public CustomerEntity findById(Integer code) {
		Optional<CustomerEntity> obj = repo.findById(code);
		CustomerEntity entity = obj.orElseThrow(() -> new ObjectNotFoundException("Product " + code + " not found"));
		return entity;
	}

	public void createCustomer(CustomerDto customer) {
		repo.save(toEntity(customer));
	}

	public CustomerEntity toEntity(CustomerDto cust) {
		return new CustomerEntity(cust.getId(), cust.getName(), cust.getEmail(), cust.getPhoneNumber(), );
	}

	public void updateProduct(ProductEntity product, Integer code) {
		if (code == null || product == null || !code.equals(product.getCode())) {
			throw new ProductException("Invalid product code.");
		}
		ProductEntity existingObj = findById(code);
		updateData(existingObj, product);
		repo.save(existingObj);
	}

	private void updateData(ProductEntity existingObj, ProductEntity obj) {
		existingObj.setName(obj.getName());
	}

	public void deleteProduct(Integer code) {
		if (code == null) {
			throw new ProductException("Product code can not be null.");
		}
		ProductEntity obj = findById(code);
		try {
			repo.delete(obj);
			// desativar o produto (ao invés de deletar)
		} catch (DataIntegrityViolationException e) {
			throw new ProductException("Can not delete a Product with dependencies constraints.");
		}
	}

}