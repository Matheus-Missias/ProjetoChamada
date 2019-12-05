package com.chamada.service;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.swing.JOptionPane;
import com.chamada.model.Aluno;
import com.chamada.repository.AlunoRepository;

@SuppressWarnings("unused")
public class AlunoService<alunoRepository> extends ConexaoBancoService{
		private AlunoRepository alunoRepository;
		
		private static Integer ERRO_INCLUSAO = 1;
		private static Integer ERRO_ALTERACAO = 2;
		private static Integer ERRO_EXCLUSAO = 3;
		private static Integer SUCESSO_TRANSACAO = 0;
		
		public AlunoService() {
			alunoRepository = new AlunoRepository(getEntityManager());
		}
			
		public Integer salvarAluno(Aluno aluno) {
			EntityTransaction transacao = this.getEntityManager().getTransaction();
			try {
				transacao.begin();
				alunoRepository.save(aluno);
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
		
		public Integer alterarAluno(Aluno aluno) {
			EntityTransaction transacao = this.getEntityManager().getTransaction();
			try {
				transacao.begin();
				alunoRepository.update(aluno);
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
		
		public Integer excluiraluno(Aluno aluno) {
			EntityTransaction transacao = this.getEntityManager().getTransaction();
			try {
				transacao.begin();
				alunoRepository.remove(aluno);
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
		
		public Integer listarAluno(Aluno aluno) {
			EntityTransaction transacao = this.getEntityManager().getTransaction();
			try {
				transacao.begin();
				alunoRepository.remove(aluno);
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
		
		public Aluno consultarAluno(Long id) {
			Aluno aluno = null;
			try {
				aluno = alunoRepository.findById(id);
			} catch (Throwable e) {
				JOptionPane.showMessageDialog(null, "Aluno não cadastrado! Erro: " + e);
			}
			return aluno;
		}

		public List<Aluno> listarTodosAlunos(){
			return alunoRepository.findAll(Aluno.class);
		}
		
}