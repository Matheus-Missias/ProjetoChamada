package com.chamada.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.chamada.model.Aluno;
import com.chamada.model.Aula;
import com.chamada.service.AlunoService;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class AlunoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Component btn_registrar = null;
	private JPanel contentPane;
	private Aula aulas;
	private JTextField txt_codAluno;
	private JTextField txt_nomeAluno;
	private JButton btn_voltar;
	/**
	 * Launch the application.
	 */
	
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlunoFrame frame = new AlunoFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	
	public Aluno pegarAluno(Aula aula) {
		List<Aluno> alunoUser = new ArrayList<Aluno>();
		Aluno aluno = new Aluno();
		aluno.setNome(txt_nomeAluno.getText());
		aluno.setAula(aula);
		return aluno;
	}
	
	public AlunoFrame(Aula aula) {
		initComponents(aula);
	}


	//INICIALIZA COMPONENTES
	private void initComponents(Aula aula) {
		setType(Type.UTILITY);
		this.aulas = aula;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//JLabel
		JLabel lbl_codAluno = new JLabel("Código do Aluno:");
		JLabel lbl_nomeAluno = new JLabel("Nome do Aluno:");
		JLabel lbl_aula = new JLabel("Aula:");
		JLabel lbl_dynamic = new JLabel("<dynamic>");
		lbl_dynamic.setText(Long.toString(aula.getId()));
		
		//JTextField
		txt_codAluno = new JTextField();
		txt_codAluno.setEditable(false);
		txt_codAluno.setColumns(10);
		
		txt_nomeAluno = new JTextField();
		txt_nomeAluno.setColumns(10);
		
		//JButton
		JButton btn_registrar = new JButton("Registrar");
		JButton btn_voltar= new JButton("Voltar");
		
		btn_registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlunoService alunoService = new AlunoService();
				Aluno aluno = pegarAluno(aulas);
				alunoService.salvarAluno(aluno);
				JOptionPane.showMessageDialog(null, "O aluno foi salvo!");
				int Confirm = JOptionPane.showConfirmDialog(null, "Deseja incluir mais um aluno?","Adicionar outro aluno", JOptionPane.YES_NO_OPTION);
					if(Confirm ==JOptionPane.YES_OPTION) {
						txt_nomeAluno.setText("");
					}else if(Confirm == JOptionPane.NO_OPTION) {
						dispose();
					}
			}
		});
		
		btn_voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//CONFIGURAÇÕES DE LAYOUT
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lbl_aula)
						.addComponent(lbl_nomeAluno)
						.addComponent(lbl_codAluno)
						.addComponent(btn_voltar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btn_registrar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(txt_codAluno, Alignment.LEADING)
							.addComponent(txt_nomeAluno, Alignment.LEADING))
						.addComponent(lbl_dynamic))
					.addContainerGap(240, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_codAluno)
						.addComponent(txt_codAluno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_nomeAluno)
						.addComponent(txt_nomeAluno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_aula)
						.addComponent(lbl_dynamic))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_registrar)
						.addComponent(btn_voltar))
					.addContainerGap(145, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
		
}