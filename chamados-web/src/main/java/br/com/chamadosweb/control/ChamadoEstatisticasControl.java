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
import br.com.chamadosweb.service.model.Chamado;
import br.com.chamadosweb.service.model.ChamadoPK;
import br.com.chamadosweb.service.model.dto.EstatisticasAtendimentosAnalistas;
import br.com.chamadosweb.service.model.dto.EstatisticasChamadosAnalistas;

/**
*
* @author Renan Celso
*/
@ManagedBean(name = "chamadoEstatisticasControl")
@ViewScoped
public class ChamadoEstatisticasControl extends BaseControl {
	
	private static final long serialVersionUID = -909006813485219277L;
	
	@EJB
	private AtendimentoServiceLocal atendimentoService;
		
	private Date dataRespostaClienteInicial;
	
	private Date dataRespostaClienteFinal;
	
	private Atendimento atendimentoFiltroConsulta;	
	
	private List<String> listaNomesAnalistas;
	
	private List<Chamado> listaChamadosConsulta;
	
	private List<EstatisticasAtendimentosAnalistas> listaEstatisticasAtendimentosAnalistas;
	
	private Long qtdAtendimentosConsulta;
	
	private List<EstatisticasChamadosAnalistas> listaEstatisticasChamadosAnalistas;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {	
		
		listaEstatisticasAtendimentosAnalistas = new ArrayList<EstatisticasAtendimentosAnalistas>();
		listaEstatisticasChamadosAnalistas = new ArrayList<EstatisticasChamadosAnalistas>();
		listaChamadosConsulta = new ArrayList<Chamado>();
		
		atendimentoFiltroConsulta = new Atendimento();
		atendimentoFiltroConsulta.setChamado(new Chamado());
		atendimentoFiltroConsulta.getChamado().setId(new ChamadoPK());		
		
		if(atendimentoFiltroConsulta.getChamado().getId().getNrChamado()  == null
				|| atendimentoFiltroConsulta.getChamado().getId().getNrChamado()  == 0){
			atendimentoFiltroConsulta.getChamado().getId().setNrChamado(null);			
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);				
		dataRespostaClienteInicial = c.getTime();
		
		dataRespostaClienteFinal = new Date();
		Calendar cf = Calendar.getInstance();
		cf.setTime(dataRespostaClienteFinal);
		cf.add(Calendar.DAY_OF_MONTH, 1);
		dataRespostaClienteFinal = cf.getTime();
		
		listaNomesAnalistas = new ArrayList<String>();		
		listaNomesAnalistas = (List<String>) atendimentoService.consultarPorQuery
											("SELECT distinct(o.nomeAnalista) FROM Atendimento o "
											+"where o.nomeAnalista is not null and o.nomeAnalista <> '' "
											+"and o.nomeAnalista in (SELECT distinct(u.nomeCompleto) FROM Usuario u)"		
											+"order by o.nomeAnalista", 0, 0);		
		
		listaNomesAnalistas.addAll((List<String>) atendimentoService.consultarPorQuery
											("SELECT distinct(o.nomeAnalista) FROM Atendimento o "
											+"where o.nomeAnalista is not null and o.nomeAnalista <> '' "
											+"and o.nomeAnalista not in (SELECT distinct(u.nomeCompleto) FROM Usuario u)"		
											+"order by o.nomeAnalista", 0, 0));	
		
	}
	
	
	public String consultar() {
						
		List<Atendimento> listaAtendimentosConsulta = new ArrayList<Atendimento>();	
		
						
		listaAtendimentosConsulta = atendimentoService.consultarAtendimentosPorFiltros(dataRespostaClienteInicial, 
																					   dataRespostaClienteFinal, 
																					   atendimentoFiltroConsulta,
																					   getUsuarioLogado().getEmpresa().getId());		
		listaChamadosConsulta = new ArrayList<Chamado>();
		if(listaAtendimentosConsulta != null && !listaAtendimentosConsulta.isEmpty()) {			
			for (Atendimento atendimento : listaAtendimentosConsulta) {
				if(!listaChamadosConsulta.contains(atendimento.getChamado())){
					listaChamadosConsulta.add(atendimento.getChamado());
				}
			}			
		}	
		
		listaEstatisticasAtendimentosAnalistas = new ArrayList<EstatisticasAtendimentosAnalistas>();
		listaEstatisticasAtendimentosAnalistas = atendimentoService.consultarEstatisticasQAtendimentosAnalistas(dataRespostaClienteInicial, 
																												dataRespostaClienteFinal, 
																												atendimentoFiltroConsulta,
																												getUsuarioLogado().getEmpresa().getId());
		
		
		listaEstatisticasChamadosAnalistas = new ArrayList<EstatisticasChamadosAnalistas>();
		
		listaEstatisticasChamadosAnalistas = atendimentoService.consultarEstatisticasQChamadosAnalistas(dataRespostaClienteInicial, 
																										dataRespostaClienteFinal, 
																										atendimentoFiltroConsulta,
																										getUsuarioLogado().getEmpresa().getId());
		
		qtdAtendimentosConsulta = new Long(0);
		
		for (EstatisticasAtendimentosAnalistas est : listaEstatisticasAtendimentosAnalistas) {
			qtdAtendimentosConsulta += est.getQtdAtendimentos();			
		}
		
		if(atendimentoFiltroConsulta.getChamado().getId().getNrChamado()  == null
				|| atendimentoFiltroConsulta.getChamado().getId().getNrChamado()  == 0){
			atendimentoFiltroConsulta.getChamado().getId().setNrChamado(null);			
		}
		
		if(listaAtendimentosConsulta == null || listaAtendimentosConsulta.isEmpty()){
			listaAtendimentosConsulta = new ArrayList<Atendimento>();
			addErrorMessage("Não existem dados cadastrados com os filtros informados.");
			return null;
		}
						
		return null;
	}
	
	public String limpar() {
		
		atendimentoFiltroConsulta = new Atendimento();
		atendimentoFiltroConsulta.setChamado(new Chamado());
		atendimentoFiltroConsulta.getChamado().setId(new ChamadoPK());
		
		if(atendimentoFiltroConsulta.getChamado().getId().getNrChamado()  == null
				|| atendimentoFiltroConsulta.getChamado().getId().getNrChamado()  == 0){
			atendimentoFiltroConsulta.getChamado().getId().setNrChamado(null);			
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);				
		dataRespostaClienteInicial = c.getTime();
		
		dataRespostaClienteFinal = new Date();
		Calendar cf = Calendar.getInstance();
		cf.setTime(dataRespostaClienteFinal);
		cf.add(Calendar.DAY_OF_MONTH, 1);
		dataRespostaClienteFinal = cf.getTime();
		
		listaEstatisticasAtendimentosAnalistas = new ArrayList<EstatisticasAtendimentosAnalistas>();
		listaChamadosConsulta = new ArrayList<Chamado>();
		listaEstatisticasChamadosAnalistas = new ArrayList<EstatisticasChamadosAnalistas>();
		
		return null;
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

	public Atendimento getAtendimentoFiltroConsulta() {
		return atendimentoFiltroConsulta;
	}

	public void setAtendimentoFiltroConsulta(Atendimento atendimentoFiltroConsulta) {
		this.atendimentoFiltroConsulta = atendimentoFiltroConsulta;
	}

	public List<String> getListaNomesAnalistas() {
		return listaNomesAnalistas;
	}

	public void setListaNomesAnalistas(List<String> listaNomesAnalistas) {
		this.listaNomesAnalistas = listaNomesAnalistas;
	}


	public List<Chamado> getListaChamadosConsulta() {
		return listaChamadosConsulta;
	}


	public void setListaChamadosConsulta(List<Chamado> listaChamadosConsulta) {
		this.listaChamadosConsulta = listaChamadosConsulta;
	}


	public List<EstatisticasAtendimentosAnalistas> getListaEstatisticasAtendimentosAnalistas() {
		return listaEstatisticasAtendimentosAnalistas;
	}


	public void setListaEstatisticasAtendimentosAnalistas(
			List<EstatisticasAtendimentosAnalistas> listaEstatisticasAtendimentosAnalistas) {
		this.listaEstatisticasAtendimentosAnalistas = listaEstatisticasAtendimentosAnalistas;
	}


	public Long getQtdAtendimentosConsulta() {
		return qtdAtendimentosConsulta;
	}


	public void setQtdAtendimentosConsulta(Long qtdAtendimentosConsulta) {
		this.qtdAtendimentosConsulta = qtdAtendimentosConsulta;
	}


	public List<EstatisticasChamadosAnalistas> getListaEstatisticasChamadosAnalistas() {
		return listaEstatisticasChamadosAnalistas;
	}


	public void setListaEstatisticasChamadosAnalistas(
			List<EstatisticasChamadosAnalistas> listaEstatisticasChamadosAnalistas) {
		this.listaEstatisticasChamadosAnalistas = listaEstatisticasChamadosAnalistas;
	}	
		
}
