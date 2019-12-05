package com.chamada.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.chamada.model.Aula;

public class AulaRepository extends GenericRepository<Aula, Long>{

	public AulaRepository(EntityManager entityManager) {
		super(entityManager);
	}

	public List<Aula> listarTodosRegistrosComPaginacao(Integer primeiro, Integer tamanhoPagina){
		List<Aula> listaAula = new ArrayList<Aula>();
		try {
			TypedQuery<Aula> query = this.getEntityManager()
			                                .createQuery("SELECT c FROM Cliente c", Aula.class)
			                                .setFirstResult(primeiro)
			                                .setMaxResults(tamanhoPagina);
			listaAula = query.getResultList();
		} catch( Exception e) {
			e.printStackTrace();
		}
		return listaAula;
	}
	
}