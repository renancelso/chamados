package br.com.chamadosweb.control;

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
@ManagedBean(name = "chamadoConsultaControl")
@ViewScoped
public class ChamadoConsultaControl extends BaseControl {
	
	private static final long serialVersionUID = 4639964089872253615L;

	@EJB
	private AtendimentoServiceLocal atendimentoService;
	
	private Chamado chamadoFiltroConsulta;	
	
	private List<Chamado> listaChamadosConsulta;
	
	private Chamado chamadoDetalhar; 	
	
	private List<Atendimento> listaAtendimentosDetalhes;
	
	@PostConstruct
	public void init() {		
		chamadoFiltroConsulta = new Chamado();
		listaChamadosConsulta = new ArrayList<Chamado>();
		chamadoDetalhar = new Chamado();
		chamadoDetalhar.setListaAtendimentos(new ArrayList<Atendimento>());
	}
	
	public String limpar(){
		chamadoFiltroConsulta = new Chamado();
		listaChamadosConsulta = new ArrayList<Chamado>();
		chamadoDetalhar = new Chamado();
		chamadoDetalhar.setListaAtendimentos(new ArrayList<Atendimento>());
		listaAtendimentosDetalhes = new ArrayList<Atendimento>();
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
		
		chamadoDetalhar.setListaAtendimentos(new ArrayList<Atendimento>());				
		chamadoDetalhar.setListaAtendimentos(atendimentoService.consultarAtendimentosPorChamado(chamadoDetalhar));		
		listaAtendimentosDetalhes = chamadoDetalhar.getListaAtendimentos();
		
		if(chamadoDetalhar.getListaAtendimentos() == null 
				|| chamadoDetalhar.getListaAtendimentos().isEmpty()) {			
			chamadoDetalhar.setListaAtendimentos(new ArrayList<Atendimento>());		
			listaAtendimentosDetalhes = new ArrayList<Atendimento>();
		}
		
		return null;
	}	
	
public String atualizarDadosApenasChamado(){
		
		try {
			
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
			
			chamadoDetalhar.setLoginUsuAtu(usuarioLogado.getLogin());			
			chamadoDetalhar.setDhAtu(new Date());	
			
			atendimentoService.atualizar(chamadoDetalhar);
			
			addInfoMessage("Chamado "+chamadoDetalhar.getNrChamado()+" atualizado com sucesso.");			 
		} catch(Exception e){
			addErrorMessage("Erro ao atualizar chamado."+e.getMessage());
		}
		
		return null;
		
	}
	
	public String voltar() {
		
		chamadoDetalhar = new Chamado();
		chamadoDetalhar.setListaAtendimentos(new ArrayList<Atendimento>());
		listaAtendimentosDetalhes = new ArrayList<Atendimento>();
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

	public List<Atendimento> getListaAtendimentosDetalhes() {
		return listaAtendimentosDetalhes;
	}

	public void setListaAtendimentosDetalhes(
			List<Atendimento> listaAtendimentosDetalhes) {
		this.listaAtendimentosDetalhes = listaAtendimentosDetalhes;
	}
	
	
	
}
