package com.chamada.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAB_ALUNO")
public class Aluno {
	
	private Long codigo;
	private String nome;
	private boolean status;
	private boolean entrada;
	private boolean confirmacao;
	private boolean presenca;
	private Date horaSaida;
	private Date horaVolta;
	private Aula aula;
	
	//CONSTRUTORES
	public Aluno() {
		super();
	}
	
	//GETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALUNO_ID")
	public Long getCodigo() {
		return codigo;
	}
	
	@Column(name = "ALUNO_NOME", nullable = false, length = 50)
	public String getNome() {
		return nome;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public boolean isEntrada() {
		return entrada;
	}
	
	public boolean isConfirmacao() {
		return confirmacao;
	}
	
	@Column(name = "ALUNO_PRESENCA", nullable = false, length = 1)
	public boolean isPresenca() {
		return presenca;
	}
	
	public Date getHoraSaida() {
		return horaSaida;
	}
	
	public Date getHoraVolta() {
		return horaVolta;
	}

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Aula.class)
	@JoinColumn(name = "AULA_ID", referencedColumnName = "AULA_ID", 
				foreignKey = @ForeignKey(name = "FK_AULA_ALUNO"), 
				nullable = false, columnDefinition = "BIGINT(20)")
	public Aula getAula() {
		return aula;
	}

	//SETTERS
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void setEntrada(boolean entrada) {
		this.entrada = entrada;
	}
	
	public void setConfirmacao(boolean confirmacao) {
		this.confirmacao = confirmacao;
	}
	
	public void setPresenca(boolean presenca) {
		this.presenca = presenca;
	}
	
	public void setHoraSaida(Date horaSaida) {
		this.horaSaida = horaSaida;
	}
	
	public void setHoraVolta(Date horaVolta) {
		this.horaVolta = horaVolta;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	//toString
	@Override
	public String toString() {
		return nome;
	}	
	
}