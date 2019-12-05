package com.chamada.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.exolab.castor.types.Date;

import com.chamada.model.Aluno;
import com.chamada.model.Aula;
import com.chamada.model.TabelaPresenca;
import com.chamada.service.AlunoService;
import com.chamada.service.AulaService;

import javassist.expr.NewArray;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AulaFrame extends JFrame {
	
	private JPanel contentPane;
	private JTextField txt_inicioEntrada;
	private JTextField txt_fimEntrada;
	private JTextField txt_inicioSaida;
	private JTextField txt_fimSaida;
	private JTextField txt_idAula;
	private JButton btn_registrar;
	private JButton btn_matricular;
	private JButton btn_buscar;
	private JButton btn_listaPresenca;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AulaFrame frame = new AulaFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public AulaFrame() {
		initComponents();
		createEvents();
	}
	
	public java.util.Date getStringToDate(String horaRecebida) {
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
		java.util.Date horaFormatada = null;
		try {
			horaFormatada = formato.parse(horaRecebida);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return horaFormatada;
	}
	
	protected Aula pegarDadosAulaFromTela(int op) {
		Aula aula = new Aula();
		if(op == 1) {
		aula.setInicioEntrada(getStringToDate(txt_inicioEntrada.getText()));//Converter date para string
		aula.setFimEntrada(getStringToDate(txt_fimEntrada.getText()));
		aula.setInicioSaida(getStringToDate(txt_inicioSaida.getText()));
		aula.setFimSaida(getStringToDate(txt_fimSaida.getText()));
		return aula;
		}else {
			aula.setId(Long.parseLong(txt_idAula.getText()));
			aula.setInicioEntrada(getStringToDate(txt_inicioEntrada.getText()));//Converter date para string
			aula.setFimEntrada(getStringToDate(txt_fimEntrada.getText()));
			aula.setInicioSaida(getStringToDate(txt_inicioSaida.getText()));
			aula.setFimSaida(getStringToDate(txt_fimSaida.getText()));
			return aula;
		}
	}
	
	protected void pegarDadosAulaFromTabela(Aula aula) {
		SimpleDateFormat out = new SimpleDateFormat("HH:mm");
		
		txt_idAula.setText(Long.toString(aula.getId()));
		txt_inicioEntrada.setText(out.format(aula.getInicioEntrada()));
		txt_fimEntrada.setText(out.format(aula.getFimEntrada()));
		txt_inicioSaida.setText(out.format(aula.getInicioSaida()));
		txt_fimSaida.setText(out.format(aula.getFimSaida()));
	}
	
	//INICIALIZA COMPONENTES
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//JLabel
		JLabel lbl_inicioEntrada = new JLabel("Início Entrada:");
		JLabel lbl_fimEntrada = new JLabel("Fim Entrada:");
		JLabel lbl_inicioSaida = new JLabel("Início Saída:");
		JLabel lbl_fimSaida = new JLabel("Fim Saída:");
		JLabel lbl_idAula = new JLabel("ID Aula:");
		
		//JTextField
		txt_inicioEntrada = new JTextField();
		txt_inicioEntrada.setToolTipText("");
		txt_inicioEntrada.setColumns(10);
		//txt_inicioEntrada.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date (System.currentTimeMillis())));

		txt_fimEntrada = new JTextField();
		txt_fimEntrada.setColumns(10);
		
		txt_inicioSaida = new JTextField();
		txt_inicioSaida.setColumns(10);
		
		txt_fimSaida = new JTextField();
		txt_fimSaida.setColumns(10);
		
		txt_idAula = new JTextField();
		txt_idAula.setColumns(10);
		
		//JButton
		btn_registrar = new JButton("Registrar");
		btn_matricular = new JButton("Matricular Aluno");
		btn_matricular.setEnabled(false);		
		btn_buscar = new JButton("Buscar");
		
		btn_listaPresenca = new JButton("Lista de Presença");
		btn_listaPresenca.setEnabled(false);
		
		JButton btn_finalizarAula = new JButton("Finalizar Aula");	
		
		//
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btn_matricular)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lbl_fimSaida)
								.addComponent(lbl_inicioSaida)
								.addComponent(lbl_fimEntrada)
								.addComponent(lbl_inicioEntrada))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txt_inicioEntrada)
								.addComponent(txt_fimEntrada)
								.addComponent(txt_inicioSaida)
								.addComponent(txt_fimSaida)
								.addComponent(btn_registrar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addGap(71)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbl_idAula)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_idAula, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btn_buscar)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btn_finalizarAula, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btn_listaPresenca, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap(108, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_inicioEntrada)
						.addComponent(txt_inicioEntrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_idAula)
						.addComponent(txt_idAula, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_fimEntrada)
						.addComponent(txt_fimEntrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_buscar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_inicioSaida)
						.addComponent(txt_inicioSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_listaPresenca))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_fimSaida)
						.addComponent(txt_fimSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_finalizarAula))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_registrar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_matricular)
					.addContainerGap(27, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	//EVENTOS
	protected void limparDadosDigitacao() {
		txt_inicioEntrada.setText("");
		txt_inicioSaida.setText("");
		txt_fimEntrada.setText("");
		txt_fimSaida.setText("");
	}
	
	private void createEvents() {
		btn_registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AulaService aulaService = new AulaService();
				Aula aula = pegarDadosAulaFromTela(1);
				AlunoService alunoService = new AlunoService();
				aulaService.salvarAula(aula);
				limparDadosDigitacao();
			}
		});
	
		btn_matricular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Aula aula = pegarDadosAulaFromTela(0);
				AlunoFrame aluno = new AlunoFrame(aula);
				aluno.setVisible(true);
			}
		});
		
		btn_buscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txt_idAula.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o ID da aula!");
					txt_idAula.requestFocus();
				}else {
					AulaService aulaService = new AulaService();
					Aula aula = pegarDadosAulaFromTela(0);
					aula = aulaService.consultarAula(Long.valueOf(aula.getId()));
					pegarDadosAulaFromTabela(aula);
					btn_matricular.setEnabled(true);
					btn_listaPresenca.setEnabled(true);
				}
			}
		});
		
		btn_listaPresenca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Aula aula = pegarDadosAulaFromTela(0);
				TabelaPresencaFrame tabela = new TabelaPresencaFrame();
				tabela.setVisible(true);
			}
		});
	
	}
}
