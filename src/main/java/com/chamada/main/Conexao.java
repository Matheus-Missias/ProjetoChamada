package com.chamada.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TooManyListenersException;

import org.hibernate.dialect.function.StandardAnsiSqlAggregationFunctions;

import com.chamada.model.Aluno;
import com.chamada.model.Aula;
import com.chamada.service.AlunoService;
import com.chamada.service.AulaService;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class Conexao implements SerialPortEventListener {

	private static final int NUL = (byte)'\0';
	private static final int LF = (byte)'\n';
	private static final int CR = (byte)'\r';
	
	private List<String> portas;
	
	private SerialPort serialPort;
	private CommPortIdentifier portIdentifier;
	private CommPort commPort;
	
//	private static List<String> resultList = Collections.synchronizedList(new ArrayList<>()); //Guarda o nome das portas que existem no sistema
//	private Thread readThread = null; //Lê a porta
//	private Thread writeThread = null; //Escreve na porta

	private BufferedReader leitura = null;
	private OutputStream escrita = null;
	
	private int estadoAtual;
	private int baudRate;
	private int dataBits;
	private int paridade;
	private int stopBits;
	private boolean portaAberta = false;
	private int totalPortas = 0;
	
	public Conexao() {
		this.baudRate = 9600;
		this.dataBits = SerialPort.DATABITS_8;
		this.paridade = SerialPort.PARITY_NONE;
		this.stopBits = SerialPort.STOPBITS_1;
		this.totalPortas++;
	}	

	public Conexao(int baudRate) {
		this.baudRate = baudRate;
		this.dataBits = SerialPort.DATABITS_8;
		this.paridade = SerialPort.PARITY_NONE;
		this.stopBits = SerialPort.STOPBITS_1;
		totalPortas++;
	}	
	
	public Conexao(int baudRate, int dataBits, int paridade, int stopBits) {
		super();
		//this.portas = portas;
		this.baudRate = baudRate;
		this.dataBits = dataBits;
		this.paridade = paridade;
		this.stopBits = stopBits;
		totalPortas++;
	}
	
	public List<String> leiaPortas() {	
		portas = new ArrayList<String>();
		Enumeration<?> portasSistema = CommPortIdentifier.getPortIdentifiers();
		while(portasSistema.hasMoreElements()) {
			portIdentifier = (CommPortIdentifier) portasSistema.nextElement();
			if(portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				portas.add(portIdentifier.getName());
			}
		}
		return portas;
	}
	
	public boolean openConnection(String porta) {
		if(!exists(porta)) {
			return false;
		}
		
		if(portaAberta==true) {
			portaAberta = false;
			close();
		}
		
		if(portaAberta==false) {
			try {
				portIdentifier = CommPortIdentifier.getPortIdentifier(porta);
				if(portIdentifier.isCurrentlyOwned()) {
					portaAberta = true;
					return portaAberta;
				}
				if(portaAberta == false) {
					commPort = portIdentifier.open("", 2000);
					serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(getBaudRate(), getDataBits(), getStopBits(), getParidade());
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
					leitura = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
					escrita = serialPort.getOutputStream();
					serialPort.addEventListener(this);
					serialPort.notifyOnDataAvailable(true);
					portaAberta = true;
				}
			}catch(IOException e) {
				e.printStackTrace();
			}catch(PortInUseException e) {
				e.printStackTrace();
			}catch(NoSuchPortException e) {
				e.printStackTrace();
			}catch(UnsupportedCommOperationException e) {
				e.printStackTrace();
			}catch(TooManyListenersException e) {
				e.printStackTrace();
		}
	}
		return portaAberta;
	}
	
	private synchronized void close() {
			if(serialPort != null) {
				serialPort.removeEventListener();
				serialPort.close();
			}
	}

	private boolean exists(String porta) {
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(porta);
		}catch(NoSuchPortException e) {
			return false;
		}
		return true;
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
	
	@Override
	public void serialEvent(SerialPortEvent event) {
		if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				estadoAtual = leitura.read();
				String strAsciiTab = Character.toString((char) estadoAtual);
				System.out.println("Recebendo dados na serial " +strAsciiTab);
				System.out.println("Teste: "+estadoAtual);
				tratamento(strAsciiTab);				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}//O método deverá retornar a string result para ser armazenada na classe de leitura

	//criar método para simular as escolhas com switch case
	
	public void tratamento(String escolha) {
		Aluno alunoBanco = new Aluno(); 				//CRIA um objeto aluno para receber os dados do banco 
//		Aluno aluno = new Aluno();						//cria um objeto para a manipulação dos dados	
		Aula aula = new Aula();
		AlunoService alunoService = new AlunoService(); //acessa o banco pra atualizar e pegar os dados do banco
		
		if(escolha.equals("A")) {
			alunoBanco = alunoService.consultarAluno(Long.valueOf(1));
			alunoBanco.setStatus(true);
			alunoService.alterarAluno(alunoBanco);
			aula = alunoBanco.getAula();
			registrar(alunoBanco, aula);
		}else if(escolha.equals("B")) {
			alunoBanco = alunoService.consultarAluno(Long.valueOf(1));
			alunoBanco.setStatus(false);
			alunoService.alterarAluno(alunoBanco);
			aula = alunoBanco.getAula();
			registrar(alunoBanco, aula);
		}else if(escolha.equals("C")) {
			alunoBanco = alunoService.consultarAluno(Long.valueOf(2));
			alunoBanco.setStatus(true);
			alunoService.alterarAluno(alunoBanco);
			aula = alunoBanco.getAula();
			registrar(alunoBanco, aula);
		}else if(escolha.equals("D")) {
			alunoBanco = alunoService.consultarAluno(Long.valueOf(2));
			alunoBanco.setStatus(false);
			alunoService.alterarAluno(alunoBanco);
			aula = alunoBanco.getAula();
			registrar(alunoBanco, aula);
		}
	}
	
	//método registrar aluno
	public void registrar(Aluno aluno, Aula aula) {
		SimpleDateFormat out = new SimpleDateFormat("HHmm");
		
		Date horaLeitura = new Date();
		horaLeitura.getTime();

		Long id = aula.getId();
		Aula aulaBanco = new Aula();
		AulaService aulaService = new AulaService();
		aulaBanco = aulaService.consultarAula(id);
		AlunoService alunoService = new AlunoService();
		
		int horaInicioEntrada;
		int horaFimEntrada;
		int horaInicioSaida;
		int horaFimSaida;
		int horaLeituraInt;
		
		horaLeituraInt = Integer.parseInt(out.format(horaLeitura));
		horaInicioEntrada = Integer.parseInt(out.format(aulaBanco.getInicioEntrada()));
		horaFimEntrada = Integer.parseInt(out.format(aulaBanco.getFimEntrada()));
		horaInicioSaida = Integer.parseInt(out.format(aulaBanco.getInicioSaida()));
		horaFimSaida = Integer.parseInt(out.format(aulaBanco.getFimSaida()));
		
		//inicio da aula e entrou na sala	
		if(horaLeituraInt >= horaInicioEntrada && horaLeituraInt <= horaFimEntrada && aluno.isStatus() == true) {
				aluno.setEntrada(true);
				aluno.setConfirmacao(false);
				aluno.setHoraVolta(horaLeitura);
				alunoService.alterarAluno(aluno);
				System.out.println("INICIO DA AULA E ENTROU NA SALA");
				System.out.println("O aluno "+aluno.getNome()+" entrou as "+horaLeitura);
				System.out.println("STATUS: "+aluno.isStatus());
				System.out.println("ENTRADA: "+aluno.isEntrada());
				System.out.println("CONFIRMAÇÃO: "+aluno.isConfirmacao());
				System.out.println("PRESENÇA: "+aluno.isPresenca());
				System.out.println("HORA VOLTA: "+aluno.getHoraVolta());
		}
		
		//inicio da aula e saiu da sala
		else if(horaLeituraInt >= horaInicioEntrada && horaLeituraInt <= horaFimEntrada && aluno.isStatus() == false) {
			aluno.setConfirmacao(false);
			aluno.setHoraSaida(horaLeitura);
			alunoService.alterarAluno(aluno);
			System.out.println("INICIO DA AULA E SAIU DA SALA");
			System.out.println("O aluno "+aluno.getNome()+" saiu as "+horaLeitura);
			System.out.println("STATUS: "+aluno.isStatus());
			System.out.println("ENTRADA: "+aluno.isEntrada());
			System.out.println("CONFIRMAÇÃO: "+aluno.isConfirmacao());
			System.out.println("PRESENÇA: "+aluno.isPresenca());
			System.out.println("HORA SAÍDA: "+aluno.getHoraSaida());
		}
		
		//meio da aula e entrou na sala
		else if(horaLeituraInt > horaFimEntrada && horaLeituraInt < horaInicioSaida && aluno.isStatus() == true) {
			aluno.setConfirmacao(false);
			aluno.setHoraVolta(horaLeitura);
			if(aluno.isEntrada()== true) {			
				if(tempoFora(aluno.getHoraSaida(), aluno.getHoraVolta()) == true) {
					aluno.setEntrada(false);
				}
			}
			alunoService.alterarAluno(aluno);
			System.out.println("MEIO DA AULA E ENTROU NA SALA");
			System.out.println("O aluno "+aluno.getNome()+" entrou as "+horaLeitura);
			System.out.println("STATUS: "+aluno.isStatus());
			System.out.println("ENTRADA: "+aluno.isEntrada());
			System.out.println("CONFIRMAÇÃO: "+aluno.isConfirmacao());
			System.out.println("PRESENÇA: "+aluno.isPresenca());
			System.out.println("HORA VOLTA: "+aluno.getHoraVolta());
		}
		
		//meio da aula e saiu da sala
		else if(horaLeituraInt > horaFimEntrada && horaLeituraInt < horaInicioSaida && aluno.isStatus() == false) {
			aluno.setConfirmacao(false);
			aluno.setHoraSaida(horaLeitura);
			alunoService.alterarAluno(aluno);
			System.out.println("MEIO DA AULA E SAIU DA SALA");
			System.out.println("O aluno "+aluno.getNome()+" saiu as "+horaLeitura);
			System.out.println("STATUS: "+aluno.isStatus());
			System.out.println("ENTRADA: "+aluno.isEntrada());
			System.out.println("CONFIRMAÇÃO: "+aluno.isConfirmacao());
			System.out.println("PRESENÇA: "+aluno.isPresenca());
			System.out.println("HORA SAÍDA: "+aluno.getHoraSaida());
		}
		
		//fim da aula e entrou na sala
		else if(horaLeituraInt >= horaInicioSaida && horaLeituraInt <= horaFimSaida && aluno.isStatus() == true) {
			aluno.setConfirmacao(false);
			aluno.setHoraVolta(horaLeitura);
			if(aluno.isEntrada()==true) {
				if(tempoFora(aluno.getHoraSaida(), aluno.getHoraVolta()) == true) {
					aluno.setEntrada(false);
				}
			}
			alunoService.alterarAluno(aluno);
			System.out.println("FIM DA AULA E ENTROU NA SALA");
			System.out.println("O aluno "+aluno.getNome()+" entrou as "+horaLeitura);
			System.out.println("STATUS: "+aluno.isStatus());
			System.out.println("ENTRADA: "+aluno.isEntrada());
			System.out.println("CONFIRMAÇÃO: "+aluno.isConfirmacao());
			System.out.println("PRESENÇA: "+aluno.isPresenca());
			System.out.println("HORA VOLTA: "+aluno.getHoraVolta());
		}
		
		//fim da aula e saiu da sala
		else if(horaLeituraInt >= horaInicioSaida && horaLeituraInt <= horaFimSaida && aluno.isStatus() == false) {
			aluno.setConfirmacao(true);
			aluno.setHoraSaida(horaLeitura);
			
			if(aluno.isEntrada() == true && aluno.isConfirmacao() == true) {
				aluno.setPresenca(true);
			}
			alunoService.alterarAluno(aluno);
			System.out.println("FIM DA AULA E SAIU DA SALA");
			System.out.println("O aluno "+aluno.getNome()+" saiu as "+horaLeitura);
			System.out.println("STATUS: "+aluno.isStatus());
			System.out.println("ENTRADA: "+aluno.isStatus());
			System.out.println("HORA SAIDA: "+aluno.getHoraSaida());
			System.out.println("CONFIRMAÇÃO: "+aluno.isConfirmacao());
			System.out.println("PRESENÇA: "+aluno.isPresenca());
		}
		
	}
	
/*
	public void sendData(String data) {
		try {
			if(this.portaAberta) {
				System.out.println("enviando dados na serial " +data);
				escrita.write(data.getBytes());
				escrita.flush();
			}
		}catch(IOException e){
		}
	}
*/

	public List<String> leiaPortas(int indice) {
		return portas;
	}
	
	public List<String> getPortas() {
		return portas;
	}
	
	public void setPortas(String[] portas) {
		
	}
	
	
	
	//GETTERS AND SETTERS
	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}

	public CommPortIdentifier getPortIdentifier() {
		return portIdentifier;
	}

	public void setPortIdentifier(CommPortIdentifier portIdentifier) {
		this.portIdentifier = portIdentifier;
	}

	public CommPort getCommPort() {
		return commPort;
	}

	public void setCommPort(CommPort commPort) {
		this.commPort = commPort;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	public int getDataBits() {
		return dataBits;
	}

	public void setDataBits(int dataBits) {
		this.dataBits = dataBits;
	}

	public int getParidade() {
		return paridade;
	}

	public void setParidade(int paridade) {
		this.paridade = paridade;
	}

	public int getStopBits() {
		return stopBits;
	}

	public void setStopBits(int stopBits) {
		this.stopBits = stopBits;
	}

	public int getEstadoAtual() {
		return estadoAtual;
	}

	public void setEstadoAtual(int estadoAtual) {
		this.estadoAtual = estadoAtual;
	}
	
	

	
	

}