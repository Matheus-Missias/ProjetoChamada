package com.chamada.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import com.chamada.model.Aula;
import com.chamada.repository.AulaRepository;

@SuppressWarnings("unused")
public class AulaService extends ConexaoBancoService {
	
	private AulaRepository aulaRepository;
	
	private static Integer ERRO_INCLUSAO = 1;
	private static Integer ERRO_ALTERACAO = 2;
	private static Integer ERRO_EXCLUSAO = 3;
	private static Integer SUCESSO_TRANSACAO = 0;
	
	public AulaService() {
		aulaRepository = new AulaRepository(getEntityManager());
	}
		
	public Integer salvarAula(Aula aula) {
		EntityTransaction transacao = this.getEntityManager().getTransaction();
		try {
			transacao.begin();
			aulaRepository.save(aula);
			transacao.commit();
		}catch (Throwable e) {
			e.printStackTrace();
	        if (transacao.isActive()) {
	        	transacao.rollback();
	        	close();
	        }
			return ERRO_INCLUSAO;
		} finally {
			close();
		}
		return SUCESSO_TRANSACAO;
	}
	
	public Integer alterarAula(Aula aula) {
		EntityTransaction transacao = this.getEntityManager().getTransaction();
		try {
			transacao.begin();
			aulaRepository.update(aula);
			transacao.commit();
		} catch(Throwable e) {
			e.printStackTrace();
			if (transacao.isActive()) {
				transacao.rollback();
				close();
			}
			return ERRO_ALTERACAO;
		} finally {
			close();
		}
		return SUCESSO_TRANSACAO;
	}
	
	public Integer excluirAula(Aula aula) {
		EntityTransaction transacao = this.getEntityManager().getTransaction();
		try {
			Aula aulaRemovido = consultarAula(aula.getId());
			transacao.begin();
			aulaRepository.remove(aulaRemovido);
			transacao.commit();
		} catch(Throwable e) {
			e.printStackTrace();
			if (transacao.isActive()) {
				transacao.rollback();
				close();
			}
			return ERRO_EXCLUSAO;
		} finally {
			close();
		}
		return SUCESSO_TRANSACAO;
	}
	
	public Aula consultarAula(Long id) {
		return aulaRepository.findById(id);
	}
	
	public List<Aula> listarTodasAulas(){
		return aulaRepository.findAll(Aula.class);
	}

	public Integer calcularTotalRegistros() {
		return aulaRepository.calcularTotalRegistros(Aula.class);
	}
	
	public List<Aula> listarTodosRegistrosComPaginacao(Integer primeiro, Integer tamanhoPagina){
	    return aulaRepository.listarTodosRegistrosComPaginacao(primeiro, tamanhoPagina);
	}
	
}