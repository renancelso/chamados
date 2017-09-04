package br.com.chamadosweb.service;

import java.util.List;

import javax.ejb.Local;

import br.com.chamadosweb.padrao.GenericServiceInterface;
import br.com.chamadosweb.service.model.Atendimento;
import br.com.chamadosweb.service.model.Chamado;

/**
 * @author Renan Celso
 */
@Local
public interface AtendimentoServiceLocal extends GenericServiceInterface{

	public List<Chamado> consultarChamados(Chamado chamadoFiltroConsulta);

	public List<Atendimento> consultarAtendimentosPorChamado(Chamado chamado);	

	

}
