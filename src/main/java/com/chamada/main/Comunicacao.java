package com.chamada.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.chamada.reports.GeraRelatorio;

import net.sf.jasperreports.engine.JRParameter;

import javax.swing.UIManager;
import java.awt.Color;

@SuppressWarnings("unused")
public class Comunicacao extends JFrame {

	private JPanel contentPane;
	
	private JComboBox<String> cbo_porta;
	private JComboBox<String> cbo_baudRate;
	private JComboBox<String> cbo_dataBits;
	private JComboBox<String> cbo_paridade;
	private JComboBox<String> cbo_stopBits;

	private String baudRate[] = {"300", "600", "1200", "2400", "9600", "14400", "19200", "38400"};
	private String dataBits[] = {"5","6","7","8"};
	private String paridade[] = {"0","1","2","3","4"};
	private String stopBits[] = {"0","1","2","3"};
	
	private boolean portOpen = false;
	private int intBaudRate = 0;
	private int intDataBits = 0;
	private int intParidade = 0;
	private int intStopBits = 0;
	
	private Conexao conexao;

	
	private String dir;
	private JTextField txt_entradaPIC;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Comunicacao frame = new Comunicacao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Comunicacao() {
		getPathLib();
		initComponents();
		leiaBaudRate();
		leiaDataBits();
		leiaParidade();
		leiaStopBits();
		leiaPortas();
	}
	
	private void getPathLib() {	
		setDir(System.getProperty("user.dir"));
		try {
			System.load(getDir()+"\\rxtxSerial.dll");
			System.load(getDir()+"\\rxtxParallel.dll");
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void leiaPortas() {
		Conexao conexao = new Conexao();
		List<String> portasSistema = new ArrayList<String>();
		portasSistema = conexao.leiaPortas();
		if(portasSistema.isEmpty()||Objects.isNull(portasSistema)) {
			JOptionPane.showMessageDialog(null, "Nenhuma porta encontrada! - Verifique", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		int tamanho = portasSistema.size();
		cbo_porta.setModel(new DefaultComboBoxModel<String>(portasSistema.toArray(new String[tamanho])));
	}
	
	private void leiaBaudRate() {
		cbo_baudRate.setModel(new DefaultComboBoxModel<String>(this.getBaudRate()));
	}
	
	private void leiaDataBits() {
		cbo_dataBits.setModel(new DefaultComboBoxModel<String>(this.dataBits));
	}
	
	private void leiaParidade() {
		cbo_paridade.setModel(new DefaultComboBoxModel<String>(this.getParidade()));
	}
	
	private void leiaStopBits() {
		cbo_stopBits.setModel(new DefaultComboBoxModel<String>(this.getStopBits()));
	}

	private void criarConexao(ActionEvent e) {
		if(Objects.isNull(conexao)) {
			if(getIntBaudRate()==0) {
				conexao = new Conexao();
			}else {
				conexao = new Conexao(getIntBaudRate());
			}
			portOpen = conexao.openConnection(getCbo_porta().getSelectedItem().toString());
		}
	}
	
/*	
	private void enviarMensagem(ActionEvent e){
		Thread tarefa = new Thread(){
			String c = "1";
			public void run(){
				while(true){
					c = c.equals("1") ? "2":"1";
					conexao.sendData(c);
					try{
						Thread.sleep(10000);
					}catch(InterruptedException e){
				  }
				}
			}
		};
		tarefa.start();
	}
*/
	
	void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Labels
		JLabel lbl_porta = new JLabel("Porta:");
		JLabel lbl_dataBits = new JLabel("Data Bits:");
		JLabel lbl_baudRate = new JLabel("Baud Rate:");
		JLabel lbl_paridade = new JLabel("Paridade:");		
		JLabel lbl_stopBits = new JLabel("Stop Bits:");
		
		//ComboBox
		cbo_porta = new JComboBox<String>();
		cbo_baudRate = new JComboBox<String>();
		cbo_dataBits = new JComboBox<String>();
		cbo_paridade = new JComboBox<String>();
		cbo_stopBits = new JComboBox<String>();
		
		//Button
		JButton btn_conectar = new JButton("Conectar");
		JButton btn_desconectar = new JButton("Desconectar");
		JButton btn_enviar = new JButton("Enviar");
		
		//Configurando eventos dos botões
		btn_conectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				criarConexao(e);
			}
		});
		
		btn_enviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	//			enviarMensagem(e);
			}
		});
		
		btn_desconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton btn_tratamento = new JButton("Tratamento");
		btn_tratamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Conexao conexao = new Conexao();
				conexao.tratamento(txt_entradaPIC.getText());
			}
		});
		
		txt_entradaPIC = new JTextField();
		txt_entradaPIC.setColumns(10);
		
		JLabel lbl_entradaPIC = new JLabel("Entrada PIC:");
		
		JButton btn_geraRelatorio = new JButton("Gera Relatório");
		btn_geraRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomeArquivo = "relatorioChamada";
				Map<String, Object> params = new HashMap<String,Object>();
				params.put(JRParameter.REPORT_LOCALE, new Locale("pt","BR"));
				GeraRelatorio geraRelatorio = new GeraRelatorio(nomeArquivo, params);
				geraRelatorio.generateReports();
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "...", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(12)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(lbl_stopBits)
									.addComponent(lbl_dataBits)
									.addComponent(lbl_porta)
									.addComponent(lbl_paridade)
									.addComponent(lbl_baudRate))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(cbo_baudRate, 0, 187, Short.MAX_VALUE)
									.addComponent(cbo_paridade, 0, 187, Short.MAX_VALUE)
									.addComponent(cbo_dataBits, 0, 187, Short.MAX_VALUE)
									.addComponent(cbo_porta, 0, 187, Short.MAX_VALUE)
									.addComponent(cbo_stopBits, 0, 187, Short.MAX_VALUE)))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(btn_geraRelatorio, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(btn_conectar)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn_desconectar)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn_enviar)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lbl_entradaPIC)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_entradaPIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_tratamento)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(35)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
					.addGap(44))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_porta)
								.addComponent(cbo_porta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbo_baudRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_baudRate))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_dataBits)
								.addComponent(cbo_dataBits, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbo_paridade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_paridade))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_stopBits)
								.addComponent(cbo_stopBits, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btn_conectar)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(btn_desconectar)
									.addComponent(btn_enviar)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_geraRelatorio)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_entradaPIC)
								.addComponent(txt_entradaPIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btn_tratamento))))
					.addContainerGap(84, Short.MAX_VALUE))
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 269, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 188, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		leiaPortas();
	}

	
	//GETTERS
	public JComboBox<String> getCbo_porta() {
		return cbo_porta;
	}

	public JComboBox<String> getCbo_baudRate() {
		return cbo_baudRate;
	}

	public JComboBox<String> getCbo_dataBits() {
		return cbo_dataBits;
	}

	public JComboBox<String> getCbo_paridade() {
		return cbo_paridade;
	}

	public JComboBox<String> getCbo_stopBits() {
		return cbo_stopBits;
	}
	
	public String[] getBaudRate() {
		return baudRate;
	}

	public String[] getDataBits() {
		return dataBits;
	}
	
	public String[] getParidade() {
		return paridade;
	}

	public String[] getStopBits() {
		return stopBits;
	}

	public int getIntStopBits() {
		return intStopBits;
	}

	public boolean isPortOpen() {
		return portOpen;
	}

	public int getIntBaudRate() {
		return intBaudRate;
	}
	
	public int getIntDataBits() {
		return intDataBits;
	}

	public int getIntParidade() {
		return intParidade;
	}

	public String getDir() {
		return dir;
	}

	//SETTERS
	public void setCbo_porta(JComboBox<String> cbo_porta) {
		this.cbo_porta = cbo_porta;
	}

	public void setCbo_baudRate(JComboBox<String> cbo_baudRate) {
		this.cbo_baudRate = cbo_baudRate;
	}

	public void setCbo_dataBits(JComboBox<String> cbo_dataBits) {
		this.cbo_dataBits = cbo_dataBits;
	}

	public void setCbo_paridade(JComboBox<String> cbo_paridade) {
		this.cbo_paridade = cbo_paridade;
	}

	public void setCbo_stopBits(JComboBox<String> cbo_stopBits) {
		this.cbo_stopBits = cbo_stopBits;
	}

	public void setBaudRate(String[] baudRate) {
		this.baudRate = baudRate;
	}

	public void setDataBits(String[] dataBits) {
		this.dataBits = dataBits;
	}
	
	public void setParidade(String[] paridade) {
		this.paridade = paridade;
	}

	public void setStopBits(String[] stopBits) {
		this.stopBits = stopBits;
	}

	public void setPortOpen(boolean portOpen) {
		this.portOpen = portOpen;
	}

	public void setIntBaudRate(int intBaudRate) {
		this.intBaudRate = intBaudRate;
	}

	public void setIntDataBits(int intDataBits) {
		this.intDataBits = intDataBits;
	}

	public void setIntParidade(int intParidade) {
		this.intParidade = intParidade;
	}

	public void setIntStopBits(int intStopBits) {
		this.intStopBits = intStopBits;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}
