package com.chamada.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.chamada.persistencia.ConexaoBanco;

public abstract class ConexaoBancoService {

	@PersistenceContext(unitName = "chamada")
	private EntityManager entityManager;

	public ConexaoBancoService() {
		this.entityManager = ConexaoBanco.getConexaoBanco().getEntityManager();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void close() {
		if (this.getEntityManager().isOpen()) {
			this.getEntityManager().close();
		}
	}
	
}