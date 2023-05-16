package br.edu.univas.si7.topicos.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.univas.si7.topicos.customer.dto.CustomerDto;
import br.edu.univas.si7.topicos.customer.entity.CustomerEntity;
import br.edu.univas.si7.topicos.customer.entity.CustomerType;
import br.edu.univas.si7.topicos.customer.exception.CustomerException;
import br.edu.univas.si7.topicos.customer.exception.ObjectNotFoundException;
import br.edu.univas.si7.topicos.customer.repository.CustomerRepository;
import br.edu.univas.si7.topicos.customer.service.CustomerService;


public class CustomerApplicationTests {

	private static CustomerRepository repo;
	private CustomerService service;
	
	@BeforeEach
	public void setup() {
		repo = Mockito.mock(CustomerRepository.class);
		service = new CustomerService(repo);
		
		CustomerEntity prod01 = new CustomerEntity("1", "carlos", "carlos@outo.com","8853-9954" , CustomerType.PESSOAFISICA);
		Mockito.when(repo.findById("1")).thenReturn(Optional.of(prod01));
		
		List<CustomerEntity> listProd = new ArrayList<>();
		listProd.add(prod01);
		Mockito.when(repo.findAll()).thenReturn(listProd);
	}

	@Test
	void testGetAllCustomer() {
		List<CustomerDto> allCustomer = service.findAll();
		assertNotNull(allCustomer);
		assertEquals(1, allCustomer.size());
		assertEquals("1", allCustomer.get(0).getId());
	}

	@Test
	void testGetCustomerById() {
		CustomerEntity customer = service.findById("1");
		assertNotNull(customer);
		assertEquals("1", customer.getId());
	}
	
	@Test
	void testSaveCustomer() {
		CustomerEntity prod01 = new CustomerEntity("1", "carlos", "carlos@outo.com","8853-9954" , CustomerType.PESSOAFISICA);
		Mockito.when(repo.save(Mockito.any(CustomerEntity.class))).thenReturn(prod01);
		
		service.createCustomer(new CustomerDto(prod01));
		Mockito.verify(repo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	void testUpdateCustomer() {
		CustomerEntity prod01 = new CustomerEntity("1", "Lucas", "Lucas@outo.com","1111-1111" , CustomerType.PESSOAJURIDICA);
		Mockito.when(repo.save(Mockito.any(CustomerEntity.class))).thenReturn(prod01);
		
		service.createCustomer(new CustomerDto(prod01));
		prod01.setName("new_name");
		
		service.updateCustomer(prod01, "1");

		Mockito.verify(repo, Mockito.times(2)).save(Mockito.any());
		
	}
	
	
	@Test
	void testDeleteSuccess() {
		Mockito.doNothing().when(repo).delete(Mockito.any());
		service.deleteCustomer("1");
	}
	
	@Test
	void testDeleteWithCodeNull() {
		assertThrows(CustomerException.class, () -> service.deleteCustomer(null));
	}
	
	@Test
	void testDeleteWithExcetion() {
		Mockito.doThrow(DataIntegrityViolationException.class)
			.when(repo).delete(Mockito.any());
		assertThrows(DataIntegrityViolationException.class, () -> service.deleteCustomer("1"));
	}
	
	@Test
	void testDeleteNonExistingCustomer() {
		assertThrows(ObjectNotFoundException.class, () -> service.deleteCustomer("2"));
	}
}