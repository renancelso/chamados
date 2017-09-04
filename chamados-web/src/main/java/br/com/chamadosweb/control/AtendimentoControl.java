package br.com.chamadosweb.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.chamadosweb.padrao.BaseControl;
import br.com.chamadosweb.service.AtendimentoServiceLocal;
import br.com.chamadosweb.service.model.Atendimento;
import br.com.chamadosweb.service.model.Chamado;
import br.com.chamadosweb.service.model.Usuario;

/**
*
* @author Renan Celso
*/
@ManagedBean(name = "atendimentoControl")
@ViewScoped
public class AtendimentoControl extends BaseControl {
		
	private static final long serialVersionUID = -7849388492275760965L;

	@EJB
	private AtendimentoServiceLocal atendimentoService;
	
	private Chamado chamado;	
	private Atendimento atendimento;
	
	private String dataAtendimentoTransferidoEquipe;
	
	private String horaAtendimentoTransferidoEquipe;
	
	private String dataAtendimentoRespostaCliente;
	
	private String horaAtendimentoRespostaCliente;
			
	
			
	@PostConstruct
	public void init() {
		chamado = new Chamado();
		atendimento = new Atendimento();		
		dataAtendimentoTransferidoEquipe = "";		
		horaAtendimentoTransferidoEquipe = "";		
		dataAtendimentoRespostaCliente = "";		
		horaAtendimentoRespostaCliente = "";
		
	}
	
	public String limpar(){
		chamado = new Chamado();
		atendimento = new Atendimento();		
		dataAtendimentoTransferidoEquipe = "";		
		horaAtendimentoTransferidoEquipe = "";		
		dataAtendimentoRespostaCliente = "";		
		horaAtendimentoRespostaCliente = "";
		return null;
	}
	
	public String buscarChamado() {
		
		Long nrChamadoAux = chamado.getNrChamado();		
				
		if(nrChamadoAux == null || nrChamadoAux == 0){
			addErrorMessage("Informe um chamado.");
			chamado = new Chamado();
			chamado.setNrChamado(null);		
			return null;
		}
		
		chamado = (Chamado) atendimentoService.consultarPorChavePrimaria(chamado, chamado.getNrChamado());
		
		if(chamado == null){
			chamado = new Chamado();
			chamado.setNrChamado(nrChamadoAux);								
		}
		
		nrChamadoAux = null;	
				
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
		atendimento = new Atendimento();
		atendimento.setChamado(chamado);
		
		List<Atendimento> listaAtend = new ArrayList<Atendimento>();
		listaAtend = atendimentoService.consultarAtendimentosPorChamado(chamado);
		
		atendimento.setNrSq(new Long((listaAtend != null && !listaAtend.isEmpty()) 
									  ? (listaAtend.size()+1) 
									  : 1));
		
		atendimento.setNomeAnalista(usuarioLogado.getNomeCompleto());
									
		return null;
	}
	
	public String atualizarDadosApenasChamado(){
		
		try {
			atendimentoService.atualizar(chamado);
			addInfoMessage("Chamado "+chamado.getNrChamado()+" atualizado com sucesso.");			 
		} catch(Exception e){
			addErrorMessage("Erro ao atualizar chamado."+e.getMessage());
		}
		
		return null;
		
	}
	
	public String incluirAtendimento() {
		try {
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			if(dataAtendimentoTransferidoEquipe != null 
    				&& horaAtendimentoTransferidoEquipe != null
    				&& !dataAtendimentoTransferidoEquipe.equalsIgnoreCase("") 
    				&& !horaAtendimentoTransferidoEquipe.equalsIgnoreCase("")){
	    		Date dhAtendimentoTransfEquipe = 
	    				(Date) formatter.parse(dataAtendimentoTransferidoEquipe + " "+horaAtendimentoTransferidoEquipe);	   
	    		atendimento.setDhTransferidoEquipe(dhAtendimentoTransfEquipe);
			}
    		    		
    		if(dataAtendimentoRespostaCliente != null 
    				&& horaAtendimentoRespostaCliente != null
    				&& !dataAtendimentoRespostaCliente.equalsIgnoreCase("") 
    				&& !horaAtendimentoRespostaCliente.equalsIgnoreCase("")){
	    		Date dhAtendimentoRespostaCliente = 
	    				(Date) formatter.parse(dataAtendimentoRespostaCliente + " "+horaAtendimentoRespostaCliente);	
	    		atendimento.setDhRespostaCliente(dhAtendimentoRespostaCliente);    	
    		}
    		
    		atendimento.setChamado(chamado);    		
			
    		atendimentoService.atualizar(chamado);
			atendimentoService.atualizar(atendimento);			
			addInfoMessage("Atendimento "+atendimento.getNrSq()+" incluído com sucesso no chamado "+chamado.getNrChamado());	
			limpar();
		} catch(Exception e){
			addErrorMessage("Erro ao atualizar chamado."+e.getMessage());
		}
		
		return null;
	}
		
//	public String importarChamadosExcel(){
//		try {
//			//CW	Sq	Requisitante	Departamento	Abertura	Encaminhado por	    Transf TOTVS	Resposta	Analista
//			//0      1        2              3             4                5                6             7           8    
//	
//			//Sistema/Módulo	Ambiente	Classificação	Impacto	     Urgência	Prioridade	Módulo/Funcionalidade	 Tipo   
//			//	   9              10            11            12           13          14             15                  16		
//			
//			String linha;
//			File f = new File("D:\\chamadosImportar");
//			
//			for (File arquivo : f.listFiles()) {
//	
//	            RandomAccessFile raf = new RandomAccessFile(arquivo, "r");
//	
//	            int i = 1;
//	            System.out.println("Iniciando arquivo: " + arquivo.getName());
//	            while ((linha = raf.readLine()) != null) {
//	            	
//	            	String[] campos = linha.split(";");	  
//	            	Long cw = Long.parseLong(campos[0]);
//	            	Chamado chamado = new Chamado();	  
//	            	
//	            	chamado = (Chamado) atendimentoService.consultarPorChavePrimaria(chamado, cw);	
//	            	
//	            	if(chamado == null) {
//	            		
//	            		chamado = new Chamado();
//	            		
//	            		chamado.setNrChamado(cw);
//		            	try{
//		            		chamado.setAmbiente(campos[10]);
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		
//		            	try{
//		            		chamado.setClassificacao(campos[11]);	            		
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//	            		Date dataAbertura = (Date) formatter.parse(campos[4]);	            		
//	            		chamado.setDataAbertura(dataAbertura);
//	            		
//	            		chamado.setDepartamento(campos[3]);		
//	            		
//	            		try{
//	            			chamado.setFuncionalidade(campos[15]);
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		
//	            		try{
//	            			chamado.setImpacto(campos[12]);	 
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		try{
//	            			chamado.setPrioridade(campos[14] != null ? new Integer(campos[14]) : null);
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		chamado.setRequisitante(campos[2]);
//	            		
//	            		try{
//	            			chamado.setSistemaModulo(campos[9]);
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		
//	            		try{
//	            			chamado.setTipo(campos[16]);
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		
//	            		try{
//	            			chamado.setUrgencia(campos[13]);	 
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		
//	            		atendimentoService.atualizar(chamado);
//	            	
//	            	}
//	            	
//	            }
//	            
//			}						
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}		
//		
//		return null;
//	}
	
	
	
//	public String importarAtendimentosExcel(){
//		try {
//			//CW	Sq	Requisitante	Departamento	Abertura	Encaminhado por	    Transf TOTVS	Resposta	Analista
//			//0      1        2              3             4                5                6             7           8    
//	
//			//Sistema/Módulo	Ambiente	Classificação	Impacto	     Urgência	Prioridade	Módulo/Funcionalidade	 Tipo   
//			//	   9              10            11            12           13          14             15                  16		
//			
//			String linha;
//			File f = new File("D:\\chamadosImportar");
//			
//			for (File arquivo : f.listFiles()) {
//	
//	            RandomAccessFile raf = new RandomAccessFile(arquivo, "r");
//	
//	            int i = 1;
//	            System.out.println("Iniciando arquivo: " + arquivo.getName());
//	            while ((linha = raf.readLine()) != null) {
//	            	
//	            	String[] campos = linha.split(";");	  
//	            	Long cw = Long.parseLong(campos[0]);
//	            	Chamado cham = new Chamado();	  
//	            	
//	            	cham = (Chamado) atendimentoService.consultarPorChavePrimaria(cham, cw);	
//	            	
//	            	if(cham != null) {
//	            		
//	            		Atendimento a = new Atendimento();
//	            		a.setChamado(cham);	         
//	            		
//	            		if(campos[1].equalsIgnoreCase("0")) {
//	            			a.setNrSq(new Long(1));            			
//	            		} else {
//	            			a.setNrSq(new Long(campos[1])+1); 
//	            		}
//	            		
//	            		try {
//	            			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//		            		Date dhRespCliente = (Date) formatter.parse(campos[7]);	   
//		            		a.setDhRespostaCliente(dhRespCliente);	            			
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		
//	            		try {
//	            			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//		            		Date dhTransfEquipe = (Date) formatter.parse(campos[6]);	   
//		            		a.setDhTransferidoEquipe(dhTransfEquipe);	            			
//	            		} catch(Exception e){
//	            			
//	            		}	            		
//
//	            		try {	            			
//		            		a.setEncaminhador(campos[5]);	            			
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		
//	            		try {	            			
//		            		a.setNomeAnalista(campos[8].toUpperCase());	            			
//	            		} catch(Exception e){
//	            			
//	            		}
//	            		
//	            		atendimentoService.atualizar(a);
//	            	
//	            	}
//	            	
//	            }
//	            
//			}						
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}		
//		
//		return null;
//	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public String getDataAtendimentoTransferidoEquipe() {
		return dataAtendimentoTransferidoEquipe;
	}

	public void setDataAtendimentoTransferidoEquipe(
			String dataAtendimentoTransferidoEquipe) {
		this.dataAtendimentoTransferidoEquipe = dataAtendimentoTransferidoEquipe;
	}

	public String getHoraAtendimentoTransferidoEquipe() {
		return horaAtendimentoTransferidoEquipe;
	}

	public void setHoraAtendimentoTransferidoEquipe(
			String horaAtendimentoTransferidoEquipe) {
		this.horaAtendimentoTransferidoEquipe = horaAtendimentoTransferidoEquipe;
	}

	public String getDataAtendimentoRespostaCliente() {
		return dataAtendimentoRespostaCliente;
	}

	public void setDataAtendimentoRespostaCliente(
			String dataAtendimentoRespostaCliente) {
		this.dataAtendimentoRespostaCliente = dataAtendimentoRespostaCliente;
	}

	public String getHoraAtendimentoRespostaCliente() {
		return horaAtendimentoRespostaCliente;
	}

	public void setHoraAtendimentoRespostaCliente(
			String horaAtendimentoRespostaCliente) {
		this.horaAtendimentoRespostaCliente = horaAtendimentoRespostaCliente;
	}		
}
