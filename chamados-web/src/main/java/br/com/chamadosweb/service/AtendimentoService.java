package br.com.chamadosweb.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import br.com.chamadosweb.padrao.GenericService;
import br.com.chamadosweb.service.model.Atendimento;
import br.com.chamadosweb.service.model.Chamado;
import br.com.chamadosweb.service.model.dto.EstatisticasAtendimentosAnalistas;
import br.com.chamadosweb.service.model.dto.EstatisticasChamadosAnalistas;

/**
 * @author Renan Celso
 */
@Stateless
public class AtendimentoService extends GenericService implements AtendimentoServiceLocal {

	private static final long serialVersionUID = 3168122228285071290L;

	@Override
	public List<Chamado> consultarChamados(Chamado chamadoFiltroConsulta, 
										   Date dataAberturaInicio, 
										   Date dataAberturaFinal,
										   Long empresa) {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			List<Chamado> listaChamados = new ArrayList<Chamado>();
					
			StringBuilder sql = new StringBuilder();
			sql.append("select o from ").append(Chamado.class.getSimpleName()).append(" o where 1=1");			
			
			if(chamadoFiltroConsulta.getId().getNrChamado() != null && chamadoFiltroConsulta.getId().getNrChamado() > 0){
				sql.append(" and o.id.nrChamado = ").append(chamadoFiltroConsulta.getId().getNrChamado());
			}		
			
			if(dataAberturaInicio != null && dataAberturaFinal != null){
				sql.append(" and (o.dataAbertura >= '").append(sdf.format(dataAberturaInicio)).append("'");
				sql.append(" and o.dataAbertura <= '").append(sdf.format(dataAberturaFinal)).append("')");
			}
			
			if(dataAberturaInicio != null && dataAberturaFinal == null){
				sql.append(" and (o.dataAbertura >= '").append(sdf.format(dataAberturaInicio)).append("')");					
			}
			
			if(dataAberturaInicio == null && dataAberturaFinal != null){				
				sql.append(" and (o.dataAbertura <= '").append(sdf.format(dataAberturaFinal)).append("')");					
			}
			
			if(chamadoFiltroConsulta.getDescricao() != null 
					&& !"".equalsIgnoreCase(chamadoFiltroConsulta.getDescricao())) {
				sql.append(" and o.descricao like '%").append(chamadoFiltroConsulta.getDescricao()).append("%'");
			}
			
			if(empresa != null && empresa != 0) {
				sql.append(" and o.id.empresa = ").append(empresa);
			}
			
			sql.append(" order by o.id.nrChamado desc");
			
			listaChamados = (List<Chamado>) consultarPorQuery(sql.toString(),0, 0);
								
			return listaChamados;
			
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	@Override
	public List<Atendimento> consultarAtendimentosPorChamado(Chamado chamado, Long empresa) {
		try {
			List<Atendimento> listaAtendimento = new ArrayList<Atendimento>();
					
			StringBuilder sql = new StringBuilder();
			sql.append("select o from ").append(Atendimento.class.getSimpleName()).append(" o where 1=1");	
			sql.append(" and o.chamado.id.nrChamado = ").append(chamado.getId().getNrChamado());	
			
			if(empresa != null && empresa != 0) {
				sql.append("and o.chamado.id.empresa = ").append(empresa);
			}
			
			sql.append(" order by o.nrSq desc");
						
			listaAtendimento = (List<Atendimento>) consultarPorQuery(sql.toString(), 0, 0);
								
			return listaAtendimento;
			
		} catch(Exception e){
			e.printStackTrace();
			return new ArrayList<Atendimento>();
		}		
	}
	
	
	@Override
	public Long consultarQuantidadeAtendimentosPorChamado(Chamado chamado, Long empresa) {
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) from ").append(Atendimento.class.getSimpleName()).append(" o where 1=1");	
			sql.append(" and o.nrChamado = ").append(chamado.getId().getNrChamado());	
			
			if(empresa != null && empresa != 0) {
				sql.append("and o.empresa = ").append(empresa);
			}
			
			sql.append(" order by o.nrSq desc");
						
			List<BigInteger> qtdAtendimentos = (List<BigInteger>) consultarPorQueryNativa(sql.toString(), 0, 0);
						
			return qtdAtendimentos.get(0).longValue();						
			
		} catch(Exception e){
			e.printStackTrace();
			return new Long(0);
		}		
	}
	
	
	@Override
	public List<Atendimento> consultarAtendimentosPorFiltros(Date dataRespostaClienteInicial, 
												             Date dataRespostaClienteFinal,
												             Atendimento atendimentoFiltroConsulta,
												             Long empresa) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			List<Atendimento> listaAtendimento = new ArrayList<Atendimento>();
					
			StringBuilder sql = new StringBuilder();
			sql.append("select o from ").append(Atendimento.class.getSimpleName()).append(" o where 1=1");	
			
			if(dataRespostaClienteInicial != null && dataRespostaClienteFinal != null){
				sql.append(" and ((o.dhRespostaCliente >= '").append(sdf.format(dataRespostaClienteInicial)).append("'");
				sql.append(" and o.dhRespostaCliente <= '").append(sdf.format(dataRespostaClienteFinal)).append("')");	
				sql.append(")");
			}
			
			if(dataRespostaClienteInicial != null && dataRespostaClienteFinal == null){
				sql.append(" and (o.dhRespostaCliente >= '").append(sdf.format(dataRespostaClienteInicial)).append("'");	
				sql.append(")");
			}
			
			if(dataRespostaClienteInicial == null && dataRespostaClienteFinal != null){				
				sql.append(" and (o.dhRespostaCliente <= '").append(sdf.format(dataRespostaClienteFinal)).append("'");	
				sql.append(")");	
			}
			
			if(atendimentoFiltroConsulta.getNomeAnalista() != null 
					&& !"".equalsIgnoreCase(atendimentoFiltroConsulta.getNomeAnalista())) {
				sql.append(" and o.nomeAnalista = '").append(atendimentoFiltroConsulta.getNomeAnalista()).append("'");
			}
			
			if(atendimentoFiltroConsulta.getChamado() != null 
					&& atendimentoFiltroConsulta.getChamado().getId().getNrChamado() != null
					&& atendimentoFiltroConsulta.getChamado().getId().getNrChamado() > 0) {
				sql.append(" and o.chamado.id.nrChamado = ").append(atendimentoFiltroConsulta.getChamado().getId().getNrChamado());
			}
			
			if(atendimentoFiltroConsulta.getDescricaoAtendimento() != null 
					&& !"".equalsIgnoreCase(atendimentoFiltroConsulta.getDescricaoAtendimento())) {
				sql.append(" and o.descricaoAtendimento like '%").append(atendimentoFiltroConsulta.getDescricaoAtendimento()).append("%'");
			}
			
			if(empresa != null && empresa != 0) {
				sql.append("and o.chamado.id.empresa = ").append(empresa);
			}
					
			sql.append(" order by o.dhRespostaCliente desc, o.chamado.id.nrChamado desc, o.nrSq desc");
						
			listaAtendimento = (List<Atendimento>) consultarPorQuery(sql.toString(), 0, 0);
								
			return listaAtendimento;
			
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}		
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstatisticasAtendimentosAnalistas> consultarEstatisticasQAtendimentosAnalistas
															(Date dataRespostaClienteInicial, 
												             Date dataRespostaClienteFinal,
												             Atendimento atendimentoFiltroConsulta,
												             Long empresa) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			List<EstatisticasAtendimentosAnalistas> listaEstatisticasAtendimentosAnalistas = new ArrayList<EstatisticasAtendimentosAnalistas>();
					
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT o.nomeAnalista, count(*) qtdAtendimentos FROM atendimento o where 1 = 1 ");	
			
			if(dataRespostaClienteInicial != null && dataRespostaClienteFinal != null){
				sql.append(" and ((o.dhRespostaCliente >= '").append(sdf.format(dataRespostaClienteInicial)).append("'");
				sql.append(" and o.dhRespostaCliente <= '").append(sdf.format(dataRespostaClienteFinal)).append("')");	
				sql.append(")");
			}
			
			if(dataRespostaClienteInicial != null && dataRespostaClienteFinal == null){
				sql.append(" and (o.dhRespostaCliente >= '").append(sdf.format(dataRespostaClienteInicial)).append("'");	
				sql.append(")");
			}
			
			if(dataRespostaClienteInicial == null && dataRespostaClienteFinal != null){				
				sql.append(" and (o.dhRespostaCliente <= '").append(sdf.format(dataRespostaClienteFinal)).append("'");	
				sql.append(")");	
			}
			
			if(atendimentoFiltroConsulta.getNomeAnalista() != null 
					&& !"".equalsIgnoreCase(atendimentoFiltroConsulta.getNomeAnalista())) {
				sql.append(" and o.nomeAnalista = '").append(atendimentoFiltroConsulta.getNomeAnalista()).append("'");
			}	
			
			if(empresa != null && empresa != 0) {
				sql.append("and o.empresa = ").append(empresa);
			}
			
			sql.append(" and o.nomeAnalista in (SELECT distinct (u.nome_completo) FROM Usuario u)");
					
			sql.append(" group by o.nomeAnalista order by qtdAtendimentos desc");
				
			List<Object[]> listaObjetos = (List<Object[]>) consultarPorQueryNativa(sql.toString(), 0, 0);
			
			for (Object[] obj : listaObjetos) {
				EstatisticasAtendimentosAnalistas estatistica = new EstatisticasAtendimentosAnalistas();
				estatistica.setNomeAnalista(String.valueOf(obj[0]));
				estatistica.setQtdAtendimentos(new BigInteger(String.valueOf(obj[1])).longValue());	
				listaEstatisticasAtendimentosAnalistas.add(estatistica);
			}
								
			return listaEstatisticasAtendimentosAnalistas;
			
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstatisticasChamadosAnalistas> consultarEstatisticasQChamadosAnalistas(Date dataRespostaClienteInicial, 
																					   Date dataRespostaClienteFinal,
																					   Atendimento atendimentoFiltroConsulta,
																					   Long empresa) {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			List<EstatisticasChamadosAnalistas> listaEstatisticasChamadosAnalistas = new ArrayList<EstatisticasChamadosAnalistas>();
					
			StringBuilder sql = new StringBuilder();
			sql.append("select t.nomeAnalista, sum(t.contador) totalChamados from");	
			
			sql.append(" (SELECT nomeAnalista, nrChamado, 1 contador FROM atendimento o where nomeAnalista is not null ");
						
			if(dataRespostaClienteInicial != null && dataRespostaClienteFinal != null){
				sql.append(" and ((o.dhRespostaCliente >= '").append(sdf.format(dataRespostaClienteInicial)).append("'");
				sql.append(" and o.dhRespostaCliente <= '").append(sdf.format(dataRespostaClienteFinal)).append("')");	
				sql.append(")");
			}
			
			if(dataRespostaClienteInicial != null && dataRespostaClienteFinal == null){
				sql.append(" and (o.dhRespostaCliente >= '").append(sdf.format(dataRespostaClienteInicial)).append("'");	
				sql.append(")");
			}
			
			if(dataRespostaClienteInicial == null && dataRespostaClienteFinal != null){				
				sql.append(" and (o.dhRespostaCliente <= '").append(sdf.format(dataRespostaClienteFinal)).append("'");	
				sql.append(")");	
			}
			
			if(atendimentoFiltroConsulta.getNomeAnalista() != null 
					&& !"".equalsIgnoreCase(atendimentoFiltroConsulta.getNomeAnalista())) {
				sql.append(" and o.nomeAnalista = '").append(atendimentoFiltroConsulta.getNomeAnalista()).append("'");
			}		
			
			if(empresa != null && empresa != 0) {
				sql.append("and o.empresa = ").append(empresa);
			}
			
			sql.append(" group by nomeAnalista, nrChamado) t");			
			sql.append(" where t.nomeAnalista in (SELECT nome_completo FROM usuario) group by nomeAnalista");
			sql.append(" order by totalChamados desc");
							
			List<Object[]> listaObjetos = (List<Object[]>) consultarPorQueryNativa(sql.toString(), 0, 0);
			
			
			for (Object[] obj : listaObjetos) {
				EstatisticasChamadosAnalistas estatistica = new EstatisticasChamadosAnalistas();
				estatistica.setNomeAnalista(String.valueOf(obj[0]));
				estatistica.setQtdChamados(new BigInteger(String.valueOf(obj[1])).longValue());	
				listaEstatisticasChamadosAnalistas.add(estatistica);
			}
								
			return listaEstatisticasChamadosAnalistas;
			
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
}



