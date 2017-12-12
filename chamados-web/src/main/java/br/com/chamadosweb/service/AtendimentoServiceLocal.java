package br.com.chamadosweb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.chamadosweb.padrao.GenericServiceInterface;
import br.com.chamadosweb.service.model.Atendimento;
import br.com.chamadosweb.service.model.Chamado;
import br.com.chamadosweb.service.model.dto.EstatisticasAtendimentosAnalistas;
import br.com.chamadosweb.service.model.dto.EstatisticasChamadosAnalistas;

/**
 * @author Renan Celso
 */
@Local
public interface AtendimentoServiceLocal extends GenericServiceInterface{

	public List<Chamado> consultarChamados(Chamado chamadoFiltroConsulta, 
										   Date dataAberturaInicio, 
										   Date dataAberturaFinal,
										   Long empresa);

	public List<Atendimento> consultarAtendimentosPorChamado(Chamado chamado, Long empresa);
	
	public Long consultarQuantidadeAtendimentosPorChamado(Chamado chamado, Long empresa);

	public List<Atendimento> consultarAtendimentosPorFiltros(Date dataRespostaClienteInicial, 
															 Date dataRespostaClienteFinal, 
															 Atendimento atendimentoFiltroConsulta,
															 Long empresa);

	public List<EstatisticasAtendimentosAnalistas>  consultarEstatisticasQAtendimentosAnalistas(Date dataRespostaClienteInicial, 
																							    Date dataRespostaClienteFinal, 
																							    Atendimento atendimentoFiltroConsulta,
																							    Long empresa);
	
	public List<EstatisticasChamadosAnalistas>  consultarEstatisticasQChamadosAnalistas(Date dataRespostaClienteInicial, 
																						Date dataRespostaClienteFinal, 
																						Atendimento atendimentoFiltroConsulta,
																						Long empresa);


}
