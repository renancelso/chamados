package br.com.chamadosweb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.chamadosweb.padrao.GenericServiceInterface;
import br.com.chamadosweb.service.model.Atendimento;
import br.com.chamadosweb.service.model.Chamado;
import br.com.chamadosweb.service.model.dto.EstatisticasAtendimentosAnalistas;

/**
 * @author Renan Celso
 */
@Local
public interface AtendimentoServiceLocal extends GenericServiceInterface{

	public List<Chamado> consultarChamados(Chamado chamadoFiltroConsulta);

	public List<Atendimento> consultarAtendimentosPorChamado(Chamado chamado);
	
	public Long consultarQuantidadeAtendimentosPorChamado(Chamado chamado);

	public List<Atendimento> consultarAtendimentosPorFiltros(
			Date dataRespostaClienteInicial, Date dataRespostaClienteFinal, Atendimento atendimentoFiltroConsulta);

	public List<EstatisticasAtendimentosAnalistas> consultarEstatisticasQAtendimentosAnalistas(Date dataRespostaClienteInicial, Date dataRespostaClienteFinal,
																							   Atendimento atendimentoFiltroConsulta);

}
