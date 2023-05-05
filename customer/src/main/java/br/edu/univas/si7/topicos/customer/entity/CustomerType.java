package br.edu.univas.si7.topicos.customer.entity;

import java.util.stream.Stream;

public enum CustomerType {
	PESSOAFISICA(10), PESSOAJURIDICA(20);

	private int code;

	private CustomerType (int code) {
	this.code = code;
	}

	
	public static CustomerType getUnit(Integer code) {
		
		return Stream.of(CustomerType.values())
				.filter(t -> t.getCode() == code)
				.findFirst()
				.orElseThrow(() -> new InvalidDataException("Invalid unit: " +

						code));
	}

	public int getCode() {
	return code;

	
	}
}

