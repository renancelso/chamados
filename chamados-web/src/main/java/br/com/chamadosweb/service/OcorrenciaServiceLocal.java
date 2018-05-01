package br.com.chamadosweb.service;

import br.com.chamadosweb.padrao.GenericServiceInterface;
import br.com.chamadosweb.service.model.Ocorrencia;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * @author Laerte Guedes
 *         29/04/18
 */
@Local
public interface OcorrenciaServiceLocal extends GenericServiceInterface{

   List<Ocorrencia> buscar(Ocorrencia ocorrenciaFiltro, Date dataInicio, Date dataFim, Long empresa);
}
