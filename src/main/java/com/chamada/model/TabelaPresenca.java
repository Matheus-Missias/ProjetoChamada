package com.chamada.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TabelaPresenca extends AbstractTableModel{
	private static final long serialVersionUID = 6893553424676264421L; //conferir
	
	private final String colunas[] = {"Aluno", "Presença"};
	
	private List<Aluno> listaAlunos;
	
	private static final int NOME = 0;
	private static final int PRESENCA = 1;
	
	public TabelaPresenca() {
		listaAlunos = new ArrayList<Aluno>();
	}
	
	public Aluno getAluno(int linhaIndex) {
		return this.getListaAlunos().get(linhaIndex-1);
	}
	
	public void addAluno(Aluno aluno) {
		this.getListaAlunos().add(aluno);
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1 );
	}
	
	public void updateAlunos(Aluno aluno, int linhaIndex) {
		this.getListaAlunos().set(linhaIndex, aluno);
		fireTableRowsUpdated(linhaIndex, linhaIndex);
	}
	
	public void removeAlunos(int linhaIndex) {
		this.getListaAlunos().remove(linhaIndex);
		fireTableRowsDeleted(linhaIndex, linhaIndex);
	}
	
	public void removeTodosAlunos() {
		this.getListaAlunos().clear();
	}

	//GETTERS AND SETTERS
	
	@Override
	public String getColumnName(int nomeColuna) {
		return this.colunas[nomeColuna];
	}
	
	
	@Override
	public int getColumnCount() {
		return this.getColunas().length;
	}

	@Override
	public int getRowCount() {
		return this.getListaAlunos().size();
	}
	
	@Override
	public Object getValueAt(int linhaIndex, int colunaIndex) {
		
		Aluno aluno = this.getListaAlunos().get(linhaIndex);
		
		switch(colunaIndex) {
		case NOME:
			return aluno.getNome();
		case PRESENCA:
			return aluno.isPresenca();
		default:
			return aluno;
		
		}
	}
	
	@Override
	public Class<?> getColumnClass(int colunaIndex){
		switch(colunaIndex) {
		case NOME:
			return String.class;
		case PRESENCA:
			return Boolean.class;
		}
		return null;
	}

	//GETTERS
	public String[] getColunas() {
		return colunas;
	}

	public List<Aluno> getListaAlunos() {
		return listaAlunos;
	}

	public static int getNome() {
		return NOME;
	}

	public static int getPresenca() {
		return PRESENCA;
	}

	//SETTERS
	public void setListaAlunos(List<Aluno> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}
	
}