package com.exemplo.reports;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.exemplo.persistencia.DataSource;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;


public class GeraRelatorio extends JFrame{

	private static final long serialVersionUID = -5757035330709382362L;
	
	private static final String ARQUIVO_DIRETORIO ="./reports/"; 
	
	private Map<String, Object> params = new HashMap<String, Object>();

	private String nomeArquivo;
	
	private Collection<?> collection;
	
	
	public GeraRelatorio(String nomeArquivo, Map<String, Object> params ) {
		this.nomeArquivo = ARQUIVO_DIRETORIO+nomeArquivo+".jasper";
		this.params = params;
		configureFrame();
	}
	
	
	public GeraRelatorio(String nomeArquivo, Map<String, Object> params, Collection<?> collection ) {
		this.nomeArquivo = ARQUIVO_DIRETORIO+nomeArquivo+".jasper";
		this.params = params;
		this.collection = collection;
		configureFrame();
	}
	
	
	public void configureFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setTitle("Visualização de Relatórios do Sistema");
	}
	
	
	public void callReport() {
        JasperPrint jasperPrint = generateReport();
        JRViewer viewer = new JRViewer(jasperPrint);
        getContentPane().add(viewer);
        setVisible(true);
    }
	
	public JasperPrint generateReport() {
        try {
        	 JasperPrint jasperPrint = null;
             jasperPrint = JasperFillManager.fillReport(this.getNomeArquivo(), getParams(), new JRBeanCollectionDataSource(this.getCollection()));
             return jasperPrint;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
 
    }
	
	
	public void generateReports() {
	
		DataSource dataSource = new DataSource();
		if (dataSource.conexaoBanco() != null) {
			try {
				JasperPrint print = JasperFillManager.fillReport(this.getNomeArquivo(),
																 this.getParams(), 
																 dataSource.conexaoBanco());
				JRViewer viewer = new JRViewer(print);
	            getContentPane().add(viewer);
	            setVisible(true);
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Sem conexão com o banco de dados, verifique!", 
									 	  "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	
	
	
	
	


	public Collection<?> getCollection() {
		return collection;
	}


	public void setCollection(Collection<?> collection) {
		this.collection = collection;
	}


	public Map<String, Object> getParams() {
		return params;
	}


	public void setParams(Map<String, Object> params) {
		this.params = params;
	}


	public String getNomeArquivo() {
		return nomeArquivo;
	}


	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}


	public static String getArquivoDiretorio() {
		return ARQUIVO_DIRETORIO;
	}
	
	
	

		
	
	
}
