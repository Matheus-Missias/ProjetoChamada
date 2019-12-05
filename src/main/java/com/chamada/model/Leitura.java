package com.chamada.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Leitura {
	private Long id;
	private Long cod;
	private boolean status;

	//CONSTRUTORES
	public Leitura() {
	}
	
	public Leitura(Long id, boolean status) {
		super();
		this.id = id;
		this.status = status;
	}

	//GETTERS
	@Id
	@Column(name = "LEITURA_ID")
	public Long getId() {
		return id;
	}

	@Column(name = "LEITURA_STATUS")
	public boolean isStatus() {
		return status;
	}

	//SETTERS
	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
}