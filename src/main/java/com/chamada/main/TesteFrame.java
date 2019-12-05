package com.chamada.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jboss.logging.Logger;

import com.chamada.model.Aluno;
import com.chamada.model.Aula;
import com.chamada.service.AlunoService;
import com.chamada.service.AulaService;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TesteFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txt_buscar;
	private JTextField txt_inicioEntrada;
	private JTextField txt_fimEntrada;
	private JTextField txt_inicioSaida;
	private JTextField txt_fimSaida;
	private JTextField txt_nomeAluno;
	private JTextField txt_opc;
	private JTextField txt_status;
	private JTextField txt_entrada;
	private JTextField txt_confirmacao;
	private JTextField txt_presenca;
	private JTextField txt_horaSaida;
	private JTextField txt_horaVolta;
	private JButton btn_registrar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TesteFrame frame = new TesteFrame();
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
	
	protected Aluno pegarDadosAlunoFromTela() {
		Aluno aluno = new Aluno();
		Aula aula = new Aula();
		aula = aluno.getAula();
		aluno.setCodigo(Long.parseLong(txt_buscar.getText()));
		aluno.setNome(txt_nomeAluno.getText());
		return aluno;
	}
	
	protected Aula pegarDadosAulaFromTela() {
		Aula aula = new Aula();
		aula.setInicioEntrada(getStringToDate(txt_inicioEntrada.getText()));//Converter date para string
		aula.setFimEntrada(getStringToDate(txt_fimEntrada.getText()));
		aula.setInicioSaida(getStringToDate(txt_inicioSaida.getText()));
		aula.setFimSaida(getStringToDate(txt_fimSaida.getText()));
		return aula;
	}
	
	protected void pegarDadosAulaFromTabela(Aluno aluno) {
		SimpleDateFormat out = new SimpleDateFormat("HH:mm");
		Aula aula = new Aula();
		aula = aluno.getAula();
		txt_nomeAluno.setText(aluno.getNome());
		txt_inicioEntrada.setText(out.format(aula.getInicioEntrada()));
		txt_fimEntrada.setText(out.format(aula.getFimEntrada()));
		txt_inicioSaida.setText(out.format(aula.getInicioSaida()));
		txt_fimSaida.setText(out.format(aula.getFimSaida()));
	}
	
	public boolean tempoFora(Date horaSaida, Date horaVolta) {
		long tempoFora;
		long horaSaidaMS;
		long horaVoltaMS;
		System.out.println("chegou na funcao tempoFora");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(horaSaida);
		
		horaSaidaMS = gc.getTimeInMillis();
		
		gc.setTime(horaVolta);
		
		horaVoltaMS = gc.getTimeInMillis();
		
		if((horaVoltaMS - horaSaidaMS) > 30000) {
			System.out.println("Hora volta em milisegundos"+horaVoltaMS);
			System.out.println("Hora saida em milisegundos"+horaSaidaMS);
			return true;
		}
		return false;
	}
	
	public TesteFrame() {
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 529, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lbl_buscar = new JLabel("Buscar:");
		
		txt_buscar = new JTextField();
		txt_buscar.setColumns(10);
		
		JButton btn_buscar = new JButton("Buscar");
		
		btn_buscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txt_buscar.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o código do aluno!");
					txt_buscar.requestFocus();
				}else {
					AlunoService alunoService = new AlunoService();
					Aluno aluno = pegarDadosAlunoFromTela();
					aluno = alunoService.consultarAluno(Long.valueOf(aluno.getCodigo()));
					pegarDadosAulaFromTabela(aluno);
				}
			}
		});
		
		JLabel lbl_inicioEntrada = new JLabel("Inicio Entrada:");
		
		txt_inicioEntrada = new JTextField();
		txt_inicioEntrada.setEditable(false);
		txt_inicioEntrada.setColumns(10);
		
		JLabel lbl_fimEntrada = new JLabel("Fim Entrada:");
		
		txt_fimEntrada = new JTextField();
		txt_fimEntrada.setEditable(false);
		txt_fimEntrada.setColumns(10);
		
		JLabel lbl_inicioSaida = new JLabel("Inicio Saida:");
		
		JLabel lbl_fimSaida = new JLabel("Fim Saida:");
		
		txt_inicioSaida = new JTextField();
		txt_inicioSaida.setEditable(false);
		txt_inicioSaida.setColumns(10);
		
		txt_fimSaida = new JTextField();
		txt_fimSaida.setEditable(false);
		txt_fimSaida.setColumns(10);
		
		JLabel lbl_nomeAluno = new JLabel("Nome do Aluno:");
		
		txt_nomeAluno = new JTextField();
		txt_nomeAluno.setEditable(false);
		txt_nomeAluno.setColumns(10);
		
		txt_opc = new JTextField();
		txt_opc.setColumns(10);
		
		JLabel lbl_opc = new JLabel("Opcao:");
		
		btn_registrar = new JButton("Registrar");
		
		btn_registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int opc;
				opc = Integer.parseInt(txt_opc.getText());
				Aluno aluno = pegarDadosAlunoFromTela();
				Aula aula = pegarDadosAulaFromTela();	
				AlunoService alunoService = new AlunoService();
				
				aluno = alunoService.consultarAluno(Long.valueOf(aluno.getCodigo()));
				
		//		GregorianCalendar gc = new GregorianCalendar();
			
				SimpleDateFormat out = new SimpleDateFormat("HHmm");		
				
				Date horaLeitura = new Date();
				int horaInicioEntrada;
				int horaFimEntrada;
				int horaInicioSaida;
				int horaFimSaida;
				int horaLeituraInt;
				
				horaLeitura.getTime();
				
				horaLeituraInt = Integer.parseInt(out.format(horaLeitura));
				
				horaInicioEntrada = Integer.parseInt(out.format(aula.getInicioEntrada()));
				horaFimEntrada = Integer.parseInt(out.format(aula.getFimEntrada()));
				horaInicioSaida = Integer.parseInt(out.format(aula.getInicioSaida()));
				horaFimSaida = Integer.parseInt(out.format(aula.getFimSaida()));
				
			    //inicio da aula e entrou na sala	
				if(horaLeituraInt >= horaInicioEntrada && horaLeituraInt <= horaFimEntrada && opc == 1) {
						aluno.setEntrada(true);
						aluno.setConfirmacao(false);
						aluno.setHoraVolta(horaLeitura);
						alunoService.alterarAluno(aluno);
						txt_status.setText(Integer.toString(opc));
						txt_entrada.setText(String.valueOf(aluno.isEntrada()));
						txt_confirmacao.setText(String.valueOf(aluno.isConfirmacao()));
						txt_presenca.setText(String.valueOf(aluno.isPresenca()));
						txt_horaVolta.setText(out.format(aluno.getHoraVolta()));
				}
				//inicio da aula e saiu da sala
				else if(horaLeituraInt >= horaInicioEntrada && horaLeituraInt <= horaFimEntrada && opc == 0) {
					aluno.setConfirmacao(false);
					aluno.setHoraSaida(horaLeitura);
					alunoService.alterarAluno(aluno);
					txt_status.setText(Integer.toString(opc));
					txt_entrada.setText(String.valueOf(aluno.isEntrada()));
					txt_confirmacao.setText(String.valueOf(aluno.isConfirmacao()));
					txt_presenca.setText(String.valueOf(aluno.isPresenca()));
					txt_horaSaida.setText(out.format(aluno.getHoraSaida()));
				}
				
				//meio da aula e entrou na sala
				else if(horaLeituraInt > horaFimEntrada && horaLeituraInt < horaInicioSaida && opc == 1) {
					aluno.setConfirmacao(false);
					aluno.setHoraVolta(horaLeitura);
					if(aluno.isEntrada()==true) {
						if(tempoFora(aluno.getHoraSaida(), aluno.getHoraVolta()) == true) {
							aluno.setEntrada(false);
						}
					}
					alunoService.alterarAluno(aluno);
					txt_status.setText(Integer.toString(opc));
					txt_entrada.setText(String.valueOf(aluno.isEntrada()));
					txt_confirmacao.setText(String.valueOf(aluno.isConfirmacao()));
					txt_presenca.setText(String.valueOf(aluno.isPresenca()));
					txt_horaVolta.setText(out.format(aluno.getHoraVolta()));
				}
				
				//meio da aula e saiu da sala
				else if(horaLeituraInt > horaFimEntrada && horaLeituraInt < horaInicioSaida && opc == 0) {
					aluno.setConfirmacao(false);
					aluno.setHoraSaida(horaLeitura);
					alunoService.alterarAluno(aluno);
					txt_status.setText(Integer.toString(opc));
					txt_entrada.setText(String.valueOf(aluno.isEntrada()));
					txt_confirmacao.setText(String.valueOf(aluno.isConfirmacao()));
					txt_presenca.setText(String.valueOf(aluno.isPresenca()));
					txt_horaSaida.setText(out.format(aluno.getHoraSaida()));
				}
				
				//fim da aula e entrou na sala
				else if(horaLeituraInt >= horaInicioSaida && horaLeituraInt <= horaFimSaida && opc == 1) {
					aluno.setConfirmacao(false);
					aluno.setHoraVolta(horaLeitura);
					if(aluno.isEntrada()==true) {	
						if(tempoFora(aluno.getHoraSaida(), aluno.getHoraVolta()) == true) {
							aluno.setEntrada(false);
						}	
					}
					alunoService.alterarAluno(aluno);
					txt_status.setText(Integer.toString(opc));
					txt_entrada.setText(String.valueOf(aluno.isEntrada()));
					txt_confirmacao.setText(String.valueOf(aluno.isConfirmacao()));
					txt_presenca.setText(String.valueOf(aluno.isPresenca()));
					txt_horaVolta.setText(out.format(aluno.getHoraVolta()));
				}
				
				//fim da aula e saiu da sala
				else if(horaLeituraInt >= horaInicioSaida && horaLeituraInt <= horaFimSaida && opc == 0) {
					aluno.setConfirmacao(true);
					aluno.setHoraSaida(horaLeitura);
					if(aluno.isEntrada() == true && aluno.isConfirmacao() == true) {
						aluno.setPresenca(true);
					}
					alunoService.alterarAluno(aluno);
					txt_status.setText(Integer.toString(opc));
					txt_entrada.setText(String.valueOf(aluno.isEntrada()));
					txt_horaSaida.setText(out.format(aluno.getHoraSaida()));
					txt_confirmacao.setText(String.valueOf(aluno.isConfirmacao()));
					txt_presenca.setText(String.valueOf(aluno.isPresenca()));
				}
				System.out.println("horaLeituraINT: "+horaLeituraInt);
			}	
		});
		
		JLabel lbl_status = new JLabel("Status:");
		JLabel lbl_entrada = new JLabel("Entrada:");
		JLabel lbl_confirmacao = new JLabel("Confirmação:");
		JLabel lbl_presenca = new JLabel("Presença:");
		JLabel lbl_horaSaida = new JLabel("Hora Saída:");
		JLabel lbl_horaVolta = new JLabel("Hora Volta:");
		
		txt_status = new JTextField();
		txt_status.setEditable(false);
		txt_status.setColumns(10);
		
		txt_entrada = new JTextField();
		txt_entrada.setEditable(false);
		txt_entrada.setColumns(10);
		
		txt_confirmacao = new JTextField();
		txt_confirmacao.setEditable(false);
		txt_confirmacao.setColumns(10);
		
		txt_presenca = new JTextField();
		txt_presenca.setEditable(false);
		txt_presenca.setColumns(10);
		
		txt_horaSaida = new JTextField();
		txt_horaSaida.setEditable(false);
		txt_horaSaida.setColumns(10);
		
		txt_horaVolta = new JTextField();
		txt_horaVolta.setEditable(false);
		txt_horaVolta.setColumns(10);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lbl_fimEntrada)
								.addComponent(lbl_inicioEntrada)
								.addComponent(lbl_inicioSaida)
								.addComponent(lbl_fimSaida)
								.addComponent(lbl_nomeAluno))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txt_nomeAluno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lbl_horaSaida))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(txt_fimSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lbl_presenca))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(txt_inicioSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lbl_confirmacao))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(txt_fimEntrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lbl_entrada))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txt_inicioEntrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(68)
									.addComponent(lbl_status))
								.addComponent(lbl_horaVolta, Alignment.TRAILING))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txt_horaVolta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_horaSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_presenca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_confirmacao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_entrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lbl_opc)
								.addComponent(lbl_buscar))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txt_opc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btn_registrar))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txt_buscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btn_buscar)))))
					.addContainerGap(55, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_buscar)
						.addComponent(txt_buscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_buscar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txt_opc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_opc)
						.addComponent(btn_registrar))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_inicioEntrada)
						.addComponent(txt_inicioEntrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_status)
						.addComponent(txt_status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_fimEntrada)
						.addComponent(txt_fimEntrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_entrada)
						.addComponent(txt_entrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_inicioSaida)
						.addComponent(txt_inicioSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_confirmacao)
						.addComponent(txt_confirmacao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_fimSaida)
						.addComponent(txt_fimSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_presenca)
						.addComponent(txt_presenca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_nomeAluno)
						.addComponent(txt_nomeAluno, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_horaSaida)
						.addComponent(txt_horaSaida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_horaVolta)
						.addComponent(txt_horaVolta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
