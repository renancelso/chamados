package br.com.chamadosweb.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.chamadosweb.padrao.BaseControl;
import br.com.chamadosweb.service.AtendimentoServiceLocal;
import br.com.chamadosweb.service.model.Atendimento;

/**
*
* @author Renan Celso
*/
@ManagedBean(name = "atendimentoConsultaControl")
@ViewScoped
public class AtendimentoConsultaControl extends BaseControl {
			
	private static final long serialVersionUID = 6165894204487345759L;

	@EJB
	private AtendimentoServiceLocal atendimentoService;
	
	private Atendimento atendimentoFiltroConsulta;	
	
	private List<Atendimento> listaAtendimentosConsulta;
	
	private Atendimento atendimentoDetalhar; 	
	
	private Date dataRespostaClienteInicial;
	
	private Date dataRespostaClienteFinal;
		
	@PostConstruct
	public void init() {		
		atendimentoFiltroConsulta = new Atendimento();
		listaAtendimentosConsulta = new ArrayList<Atendimento>();
		atendimentoDetalhar = new Atendimento();
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -7);			
		dataRespostaClienteInicial = c.getTime();
		
		dataRespostaClienteFinal = new Date();
		Calendar cf = Calendar.getInstance();
		cf.setTime(dataRespostaClienteFinal);
		cf.add(Calendar.DAY_OF_MONTH, 1);
		dataRespostaClienteFinal = cf.getTime();
	}
	
	public String limpar(){
		atendimentoFiltroConsulta = new Atendimento();
		listaAtendimentosConsulta = new ArrayList<Atendimento>();
		atendimentoDetalhar = new Atendimento();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -7);	
		dataRespostaClienteInicial = c.getTime();
		
		dataRespostaClienteFinal = new Date();
		Calendar cf = Calendar.getInstance();
		cf.setTime(dataRespostaClienteFinal);
		cf.add(Calendar.DAY_OF_MONTH, 1);
		dataRespostaClienteFinal = cf.getTime();
		
		return null;
	}
	

	public String consultarAtendimentos() {
		
		atendimentoDetalhar = new Atendimento();
		
		listaAtendimentosConsulta = new ArrayList<Atendimento>();
				
		listaAtendimentosConsulta = atendimentoService.consultarAtendimentosPorFiltros(dataRespostaClienteInicial, dataRespostaClienteFinal);
		
		if(listaAtendimentosConsulta == null || listaAtendimentosConsulta.isEmpty()){
			addErrorMessage("Não existem atendimentos cadastrados com os filtros informados.");
			return null;
		}
						
		return null;
	}
	
	public String detalhar(){
				
		
		return null;
	}
	
	
	public String voltar() {		
		
		atendimentoDetalhar = new Atendimento();		
		
		return null;
	}

	public Atendimento getAtendimentoFiltroConsulta() {
		return atendimentoFiltroConsulta;
	}

	public void setAtendimentoFiltroConsulta(Atendimento atendimentoFiltroConsulta) {
		this.atendimentoFiltroConsulta = atendimentoFiltroConsulta;
	}
	
	public List<Atendimento> getListaAtendimentosConsulta() {
		return listaAtendimentosConsulta;
	}

	public void setListaAtendimentosConsulta(
			List<Atendimento> listaAtendimentosConsulta) {
		this.listaAtendimentosConsulta = listaAtendimentosConsulta;
	}

	public Atendimento getAtendimentoDetalhar() {
		return atendimentoDetalhar;
	}

	public void setAtendimentoDetalhar(Atendimento atendimentoDetalhar) {
		this.atendimentoDetalhar = atendimentoDetalhar;
	}

	public Date getDataRespostaClienteInicial() {
		return dataRespostaClienteInicial;
	}

	public void setDataRespostaClienteInicial(Date dataRespostaClienteInicial) {
		this.dataRespostaClienteInicial = dataRespostaClienteInicial;
	}

	public Date getDataRespostaClienteFinal() {
		return dataRespostaClienteFinal;
	}

	public void setDataRespostaClienteFinal(Date dataRespostaClienteFinal) {
		this.dataRespostaClienteFinal = dataRespostaClienteFinal;
	}				
}
