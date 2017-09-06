package br.com.chamadosweb.control;

import java.util.ArrayList;
import java.util.Calendar;
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
 * @author Renan Celso
 */
@ManagedBean(name = "inicioControl")
@ViewScoped
public class InicioControl extends BaseControl{
	
	private static final long serialVersionUID = -3002939753215367595L;
	
	@EJB
	private AtendimentoServiceLocal atendimentoService;
	
	private List<Atendimento> listaAtendimentosHoje;
	
	private List<Chamado> listaChamadosHoje;
		
	private List<Atendimento> listaAtendimentosOntem;
	
	private List<Chamado> listaChamadosOntem;
	
	private Calendar hoje;
	
	private Calendar ontem;
	
	@PostConstruct
	public void init() {	
		
		hoje = Calendar.getInstance();
		
		listaAtendimentosHoje = atendimentoService.consultarAtendimentosPorFiltros(hoje.getTime(), 
																				   null, 
																				   new Atendimento());			
		
		listaChamadosHoje = new ArrayList<Chamado>();
		if(listaAtendimentosHoje != null && !listaAtendimentosHoje.isEmpty()) {			
			for (Atendimento atendimentoHoje : listaAtendimentosHoje) {
				if(!listaChamadosHoje.contains(atendimentoHoje.getChamado())){
					listaChamadosHoje.add(atendimentoHoje.getChamado());
				}
			}			
		}
		
		ontem = Calendar.getInstance();		
		ontem.add(Calendar.DAY_OF_MONTH, -1);	
		listaAtendimentosOntem = atendimentoService.consultarAtendimentosPorFiltros(ontem.getTime(), 
																					hoje.getTime(), 
																				    new Atendimento());			
		
		listaChamadosOntem = new ArrayList<Chamado>();
		if(listaAtendimentosOntem != null && !listaAtendimentosOntem.isEmpty()) {			
			for (Atendimento atendimentoOntem : listaAtendimentosOntem) {
				if(!listaChamadosOntem.contains(atendimentoOntem.getChamado())){
					listaChamadosOntem.add(atendimentoOntem.getChamado());
				}
			}			
		}
		
		
	}

	public List<Atendimento> getListaAtendimentosHoje() {
		return listaAtendimentosHoje;
	}

	public void setListaAtendimentosHoje(List<Atendimento> listaAtendimentosHoje) {
		this.listaAtendimentosHoje = listaAtendimentosHoje;
	}

	public List<Chamado> getListaChamadosHoje() {
		return listaChamadosHoje;
	}

	public void setListaChamadosHoje(List<Chamado> listaChamadosHoje) {
		this.listaChamadosHoje = listaChamadosHoje;
	}

	public Calendar getHoje() {
		return hoje;
	}

	public void setHoje(Calendar hoje) {
		this.hoje = hoje;
	}

	public List<Atendimento> getListaAtendimentosOntem() {
		return listaAtendimentosOntem;
	}

	public void setListaAtendimentosOntem(List<Atendimento> listaAtendimentosOntem) {
		this.listaAtendimentosOntem = listaAtendimentosOntem;
	}

	public List<Chamado> getListaChamadosOntem() {
		return listaChamadosOntem;
	}

	public void setListaChamadosOntem(List<Chamado> listaChamadosOntem) {
		this.listaChamadosOntem = listaChamadosOntem;
	}

	public Calendar getOntem() {
		return ontem;
	}

	public void setOntem(Calendar ontem) {
		this.ontem = ontem;
	}
}
