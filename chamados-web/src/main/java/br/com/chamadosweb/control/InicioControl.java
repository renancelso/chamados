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
		
	private List<Atendimento> listaAtendimentosAnteontem;
	
	private List<Chamado> listaChamadosAnteontem;
	
	private Calendar hoje;
	
	private Calendar ontem;
	
	private Calendar anteontem;
	
	@PostConstruct
	public void init() {	
		
		/**Hoje**/
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
		
		/**Ontem**/
		ontem = Calendar.getInstance();		
		
		do {			
			ontem.add(Calendar.DAY_OF_MONTH, -1);				
			listaAtendimentosOntem = atendimentoService.consultarAtendimentosPorFiltros(ontem.getTime(), 
																						hoje.getTime(), 
																					    new Atendimento());	
		} while ((ontem.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY 
				 || ontem.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				&& (listaAtendimentosOntem == null || listaAtendimentosOntem.isEmpty()));
		
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
	
		/**Anteontem**/		
//		anteontem = Calendar.getInstance();		
//		anteontem.add(Calendar.DAY_OF_MONTH, -1);
//		do {			
//			anteontem.add(Calendar.DAY_OF_MONTH, -1);				
//			listaAtendimentosAnteontem = atendimentoService.consultarAtendimentosPorFiltros(anteontem.getTime(), 
//																						    ontem.getTime(), 
//																					        new Atendimento());	
//		} while ((anteontem.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY 
//				 || anteontem.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
//				&& (listaAtendimentosAnteontem == null || listaAtendimentosAnteontem.isEmpty()));
//		
//		listaAtendimentosAnteontem = atendimentoService.consultarAtendimentosPorFiltros(anteontem.getTime(), 
//																						ontem.getTime(), 
//																						new Atendimento());			
//		
//		listaChamadosAnteontem = new ArrayList<Chamado>();
//		if(listaAtendimentosAnteontem != null && !listaAtendimentosAnteontem.isEmpty()) {			
//			for (Atendimento atendimentoAnteontem : listaAtendimentosAnteontem) {
//				if(!listaChamadosAnteontem.contains(atendimentoAnteontem.getChamado())){
//					listaChamadosAnteontem.add(atendimentoAnteontem.getChamado());
//				}
//			}			
//		}			
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

	public Calendar getAnteontem() {
		return anteontem;
	}

	public void setAnteontem(Calendar anteontem) {
		this.anteontem = anteontem;
	}

	public List<Atendimento> getListaAtendimentosAnteontem() {
		return listaAtendimentosAnteontem;
	}

	public void setListaAtendimentosAnteoOntem(
			List<Atendimento> listaAtendimentosAnteontem) {
		this.listaAtendimentosAnteontem = listaAtendimentosAnteontem;
	}

	public List<Chamado> getListaChamadosAnteontem() {
		return listaChamadosAnteontem;
	}

	public void setListaChamadosAnteontem(List<Chamado> listaChamadosAnteontem) {
		this.listaChamadosAnteontem = listaChamadosAnteontem;
	}
}
