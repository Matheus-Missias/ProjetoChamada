package com.chamada.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.jdbc.Work;
import org.hibernate.Session;

public class DataSource {
	@PersistenceContext(unitName = "chamada")
	private final EntityManager entityManager;

	private Connection connection;
	
	public DataSource() {
		this.entityManager = ConexaoBanco.getConexaoBanco().getEntityManager();
	}
	
	public Connection conexaoBanco() {
		Session session = this.entityManager.unwrap(Session.class);
		Conexao conexao = new Conexao();
		session.doWork(conexao);
		connection = conexao.getConnection();
		return connection;
	}
	
	private static class Conexao implements Work{
		private Connection connection;
		
		@Override
		public void execute(Connection connection) throws SQLException{
			this.connection = connection;
		}

		public Connection getConnection() {
			return connection;
		}
			
	}
}