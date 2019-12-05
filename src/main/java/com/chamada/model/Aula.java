package com.chamada.model;

import java.util.*;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "TAB_AULA")
public class Aula {
	
	private Long id;
	private Date inicioEntrada;
	private Date fimEntrada;
	private Date inicioSaida;
	private Date fimSaida;	
	
	//CONSTRUTOR
	public Aula() {
	}
	
	public Aula(Long id, Date inicioEntrada, Date fimEntrada, Date inicioSaida, Date fimSaida) {
		this.id = id;
		this.inicioEntrada = inicioEntrada;
		this.fimEntrada = fimEntrada;
		this.inicioSaida = inicioSaida;
		this.fimSaida = fimSaida;
	}

	//GETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AULA_ID")
	public Long getId() {
		return id;
	}

	@Column(name = "InicioEntrada")
	public Date getInicioEntrada() {
		return inicioEntrada;
	}

	@Column(name = "FimEntrada")
	public Date getFimEntrada() {
		return fimEntrada;
	}
	
	@Column(name = "InicioSaida")
	public Date getInicioSaida() {
		return inicioSaida;
	}
	
	@Column(name = "FimSaida")
	public Date getFimSaida() {
		return fimSaida;
	}
	

	//SETTERS
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setInicioEntrada(Date inicioEntrada) {
		this.inicioEntrada = inicioEntrada;
	}

	public void setFimEntrada(Date fimEntrada) {
		this.fimEntrada = fimEntrada;
	}

	public void setInicioSaida(Date inicioSaida) {
		this.inicioSaida = inicioSaida;
	}

	public void setFimSaida(Date fimSaida) {
		this.fimSaida = fimSaida;
	}

	//hashCode 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	//Equals 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	//toString
	@Override
	public String toString() {
		return "Aula [id=" + id + ", inicioEntrada=" + inicioEntrada + ", fimEntrada=" + fimEntrada + ", inicioSaida="
				+ inicioSaida + ", fimSaida=" + fimSaida + "]";
	}
	
}