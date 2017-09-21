package br.com.chamadosweb.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.chamadosweb.padrao.GenericService;
import br.com.chamadosweb.service.model.Versao;

/**
 * @author Renan Celso
 */
@Stateless
public class VersaoService extends GenericService implements VersaoServiceLocal {

	private static final long serialVersionUID = -6490589960116880534L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Versao> consultarVersoes(Versao versaoFiltro) {
		
		List<Versao> listaVersoes = new ArrayList<Versao>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select o from ").append(Versao.class.getSimpleName()).append(" o where 1 = 1 ");		
//		sql.append("");
//		sql.append("");
//		sql.append("");
//		sql.append("");
//		sql.append("");
//		sql.append("");		
		sql.append(" order by o.dataEntradaProducao desc");
		
		listaVersoes = (List<Versao>) consultarPorQuery(sql.toString(), 0,0);
				
		return listaVersoes;		
	}


}
