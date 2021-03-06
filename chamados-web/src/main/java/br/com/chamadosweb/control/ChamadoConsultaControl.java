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
import br.com.chamadosweb.service.model.ChamadoPK;
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
	
	private Date dataAberturaInicio;
	
	private Date dataAberturaFinal;		
	
	boolean mostrarBotaoCadastrarNovoChamado;
		
	private Chamado chamadoNovo;
	
	@PostConstruct
	public void init() {	
		
		chamadoFiltroConsulta = new Chamado();
		chamadoFiltroConsulta.setId(new ChamadoPK());
		
		
		listaChamadosConsulta = new ArrayList<Chamado>();
		chamadoDetalhar = new Chamado();
		chamadoDetalhar.setListaAtendimentos(new ArrayList<Atendimento>());
		dataAberturaInicio = null;
		dataAberturaFinal = null;
		mostrarBotaoCadastrarNovoChamado = false;		
		chamadoNovo = new Chamado();
		chamadoNovo.setId(new ChamadoPK());		
	}
	
	public String limpar(){
		
		chamadoFiltroConsulta = new Chamado();
		chamadoFiltroConsulta.setId(new ChamadoPK());
		
		listaChamadosConsulta = new ArrayList<Chamado>();
		chamadoDetalhar = new Chamado();
		chamadoDetalhar.setListaAtendimentos(new ArrayList<Atendimento>());
		listaAtendimentosDetalhes = new ArrayList<Atendimento>();
		dataAberturaInicio = null;
		dataAberturaFinal = null;
		mostrarBotaoCadastrarNovoChamado = false;
		chamadoNovo = new Chamado();
		chamadoNovo.setId(new ChamadoPK());	
		return null;
	}
	

	public String consultarChamados() {
		
		listaChamadosConsulta = new ArrayList<Chamado>();
		listaChamadosConsulta = atendimentoService.consultarChamados(chamadoFiltroConsulta, 
																     dataAberturaInicio, 
																     dataAberturaFinal,
																     getUsuarioLogado().getEmpresa().getId());	
		
		if(chamadoFiltroConsulta == null){
			chamadoFiltroConsulta = new Chamado();
			chamadoFiltroConsulta.setId(new ChamadoPK());
		}		
		
		if(chamadoFiltroConsulta.getId().getNrChamado()  == 0){
			chamadoFiltroConsulta.getId().setNrChamado(null);			
		}		
		
		if(listaChamadosConsulta == null || listaChamadosConsulta.isEmpty()){
			
			addErrorMessage("Não foi possível consulta chamados com os parãmetros informados");			
			
			chamadoNovo = new Chamado();
			chamadoNovo.setId(new ChamadoPK());
			chamadoNovo.getId().setNrChamado(chamadoFiltroConsulta.getId().getNrChamado());
			
			chamadoFiltroConsulta = new Chamado();	
			chamadoFiltroConsulta.setId(new ChamadoPK());
			
			chamadoFiltroConsulta.getId().setNrChamado(chamadoNovo.getId().getNrChamado());
			
			if (chamadoNovo.getId() != null
					&& chamadoNovo.getId().getNrChamado() != null
					&& chamadoNovo.getId().getNrChamado() > 0) {				
				mostrarBotaoCadastrarNovoChamado = true;				
			} else {				
				mostrarBotaoCadastrarNovoChamado = false;				
			}
			
			return null;
		}
				
		return null;
	}
	
	public String iniciarCadastramentoChamado() {	
		
		chamadoDetalhar = new Chamado();
		chamadoDetalhar.setId(new ChamadoPK());
		
		chamadoDetalhar = chamadoNovo;	
		chamadoDetalhar.setId(chamadoNovo.getId());
		
		detalhar();
		
		return null;
	}
	
	public String detalhar(){
		
		chamadoDetalhar.setListaAtendimentos(new ArrayList<Atendimento>());				
		chamadoDetalhar.setListaAtendimentos(atendimentoService.consultarAtendimentosPorChamado(chamadoDetalhar,getUsuarioLogado().getEmpresa().getId()));		
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
			
			try {
				Long qtdAtendimentos = chamadoDetalhar.getQtdAtendimentos();
				chamadoDetalhar.setQtdAtendimentos(atendimentoService.consultarQuantidadeAtendimentosPorChamado(chamadoDetalhar,getUsuarioLogado().getEmpresa().getId()));
				
				if(qtdAtendimentos != chamadoDetalhar.getQtdAtendimentos()){
					atendimentoService.atualizar(chamadoDetalhar);
				}
			} catch(Exception e){
				e.printStackTrace();
			}
			
			chamadoDetalhar.setLoginUsuAtu(usuarioLogado.getLogin());			
			chamadoDetalhar.setDhAtu(new Date());	
			
			atendimentoService.atualizar(chamadoDetalhar);
			
			addInfoMessage("Chamado "+chamadoDetalhar.getId().getNrChamado() +" atualizado com sucesso.");			 
		} catch(Exception e){
			addErrorMessage("Erro ao atualizar chamado."+e.getMessage());
		}
		
		return null;
		
	}
	
	public String voltar() {
		
		chamadoDetalhar = new Chamado();
		chamadoDetalhar.setListaAtendimentos(new ArrayList<Atendimento>());
		listaAtendimentosDetalhes = new ArrayList<Atendimento>();
		
		if(chamadoFiltroConsulta == null){
			chamadoFiltroConsulta = new Chamado();			
		}	
		
		if(chamadoFiltroConsulta.getId() == null){			
			chamadoFiltroConsulta.setId(new ChamadoPK());
		}	
		
		if(chamadoFiltroConsulta.getId().getNrChamado()  == 0){
			chamadoFiltroConsulta.getId().setNrChamado(null);			
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

	public Date getDataAberturaInicio() {
		return dataAberturaInicio;
	}

	public void setDataAberturaInicio(Date dataAberturaInicio) {
		this.dataAberturaInicio = dataAberturaInicio;
	}

	public Date getDataAberturaFinal() {
		return dataAberturaFinal;
	}

	public void setDataAberturaFinal(Date dataAberturaFinal) {
		this.dataAberturaFinal = dataAberturaFinal;
	}

	public boolean isMostrarBotaoCadastrarNovoChamado() {
		return mostrarBotaoCadastrarNovoChamado;
	}

	public void setMostrarBotaoCadastrarNovoChamado(
			boolean mostrarBotaoCadastrarNovoChamado) {
		this.mostrarBotaoCadastrarNovoChamado = mostrarBotaoCadastrarNovoChamado;
	}

	public Chamado getChamadoNovo() {
		return chamadoNovo;
	}

	public void setChamadoNovo(Chamado chamadoNovo) {
		this.chamadoNovo = chamadoNovo;
	}	
	
	
	
}
