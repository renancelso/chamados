package br.com.chamadosweb.service;

import java.util.List;

import javax.ejb.Local;

import br.com.chamadosweb.padrao.GenericServiceInterface;
import br.com.chamadosweb.service.model.Versao;

/**
 * @author Renan Celso
 */
@Local
public interface VersaoServiceLocal extends GenericServiceInterface{

	public List<Versao> consultarVersoes(Versao versaoFiltro);


}
