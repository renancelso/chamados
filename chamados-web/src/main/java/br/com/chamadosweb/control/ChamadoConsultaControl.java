package br.com.chamadosweb.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.chamadosweb.padrao.BaseControl;
import br.com.chamadosweb.service.AtendimentoServiceLocal;
import br.com.chamadosweb.service.model.Atendimento;
import br.com.chamadosweb.service.model.Chamado;

/**
*
* @author Renan Celso
*/
@ManagedBean(name = "chamadoConsultaControl")
@ViewScoped
public class ChamadoConsultaControl extends BaseControl {
	
	private static final long serialVersionUID = 4639964089872253615L;

	@EJB
	private AtendimentoServiceLocal atendimentoService;
	
	private Chamado chamadoFiltroConsulta;	
	
	private List<Chamado> listaChamadosConsulta;
	
	private Chamado chamadoDetalhar; 	
	
	@PostConstruct
	public void init() {		
		chamadoFiltroConsulta = new Chamado();
		listaChamadosConsulta = new ArrayList<Chamado>();
		chamadoDetalhar = new Chamado();
	}
	
	public String limpar(){
		chamadoFiltroConsulta = new Chamado();
		listaChamadosConsulta = new ArrayList<Chamado>();
		chamadoDetalhar = new Chamado();
		return null;
	}
	

	public String consultarChamados() {
		
		listaChamadosConsulta = new ArrayList<Chamado>();
		listaChamadosConsulta = atendimentoService.consultarChamados(chamadoFiltroConsulta);	
		
		if(chamadoFiltroConsulta.getNrChamado() == null || chamadoFiltroConsulta.getNrChamado() == 0){
			chamadoFiltroConsulta = new Chamado();
		}		
		
		if(listaChamadosConsulta == null || listaChamadosConsulta.isEmpty()){
			addErrorMessage("Não foi possível consulta chamados com os parãmetros informados");
			chamadoFiltroConsulta = new Chamado();
			return null;
		}
				
		return null;
	}
	
	public String detalhar(){
		
		chamadoDetalhar.setListaAtendimentos(atendimentoService.consultarAtendimentosPorChamado(chamadoDetalhar));
		
		if(chamadoDetalhar.getListaAtendimentos() == null 
				|| chamadoDetalhar.getListaAtendimentos().isEmpty()) {			
			chamadoDetalhar.setListaAtendimentos(new ArrayList<Atendimento>());			
		}
		
		return null;
	}
	
	
	public String voltar() {
		
		chamadoDetalhar = new Chamado();
		if(chamadoFiltroConsulta.getNrChamado() == null || chamadoFiltroConsulta.getNrChamado() == 0){
			chamadoFiltroConsulta = new Chamado();
		}	
		
		return null;
	}

	public Chamado getChamadoFiltroConsulta() {
		return chamadoFiltroConsulta;
	}

	public void setChamadoFiltroConsulta(Chamado chamadoFiltroConsulta) {
		this.chamadoFiltroConsulta = chamadoFiltroConsulta;
	}

	public List<Chamado> getListaChamadosConsulta() {
		return listaChamadosConsulta;
	}

	public void setListaChamadosConsulta(List<Chamado> listaChamadosConsulta) {
		this.listaChamadosConsulta = listaChamadosConsulta;
	}

	public Chamado getChamadoDetalhar() {
		return chamadoDetalhar;
	}

	public void setChamadoDetalhar(Chamado chamadoDetalhar) {
		this.chamadoDetalhar = chamadoDetalhar;
	}
	
	
	
}
