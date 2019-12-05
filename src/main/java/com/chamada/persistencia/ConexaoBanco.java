package com.chamada.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConexaoBanco {
	
	private static ConexaoBanco CONEXAO_BANCO;
	private static EntityManagerFactory FACTORY;
	
	private ConexaoBanco() {
		if (FACTORY == null) {
			FACTORY = getFactory();
		}
	}
	
	public static ConexaoBanco getConexaoBanco() {
		if (CONEXAO_BANCO == null) {
			CONEXAO_BANCO = new ConexaoBanco();
		}
		return CONEXAO_BANCO;
	}

	public EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}
	
	private EntityManagerFactory getFactory() {
		return Persistence.createEntityManagerFactory("chamada");
	}
	
}