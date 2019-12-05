package com.chamada.repository;

import javax.persistence.EntityManager;
import com.chamada.model.Aluno;

public class AlunoRepository extends GenericRepository<Aluno, Long>{
	public AlunoRepository(EntityManager entityManager) {
		super(entityManager);
	}
}