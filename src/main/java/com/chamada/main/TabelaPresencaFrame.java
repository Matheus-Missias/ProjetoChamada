package com.chamada.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.chamada.model.Aluno;
import com.chamada.model.TabelaPresenca;
import com.chamada.service.AlunoService;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class TabelaPresencaFrame extends JFrame {

	private static final long serialVersionUID = -352389726581513577L;
	private JPanel contentPane;
	private JTable table_Presenca;

	private static final int NOME = 0;
	private static final int PRESENCA = 1;
	
	private TabelaPresenca tabelaPresenca;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabelaPresencaFrame frame = new TabelaPresencaFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public TabelaPresencaFrame() {
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(25)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(91, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(39)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(47, Short.MAX_VALUE))
		);
		
		table_Presenca = new JTable();
		scrollPane.setViewportView(table_Presenca);
		contentPane.setLayout(gl_contentPane);
		loadDataAlunoFromTable();
	}
	
	public void loadDataAlunoFromTable() {
		tabelaPresenca = new TabelaPresenca();
		tabelaPresenca.setListaAlunos(listarAlunos());
		table_Presenca.setModel(tabelaPresenca);
		
		table_Presenca.setFillsViewportHeight(true);
		table_Presenca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table_Presenca.getColumnModel().getColumn(NOME).setWidth(100);
		table_Presenca.getColumnModel().getColumn(PRESENCA).setWidth(11);
		
		setLocationRelativeTo(null);
	}
	
	public List<Aluno> listarAlunos(){
		AlunoService alunoService = new AlunoService();
		return alunoService.listarTodosAlunos();
	}
}
