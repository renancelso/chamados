package br.com.chamadosweb.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.chamadosweb.service.model.Chamado;


public class JobAtualizaNrAtendimentosChamados implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat dh = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {						
			Logger.getLogger(getClass().getName()).log(Level.INFO, "------------------------ Job JobAtualizaNrAtendimentosChamados: Executando Job JobAtualizaNrAtendimentosChamados "+dh.format(new Date())+" ------------------------","");
			
			AtendimentoServiceLocal atendimentoService = lookup();
			List<Chamado> listaChamados = new ArrayList<Chamado>();			
			listaChamados = atendimentoService.consultarChamados(new Chamado(), null, null, null);	
			
			if(listaChamados != null && !listaChamados.isEmpty()) {
				for (Chamado chamado : listaChamados) {
					
					long qtdAtendimentos = chamado.getQtdAtendimentos();					
					
					chamado.setQtdAtendimentos(atendimentoService.consultarQuantidadeAtendimentosPorChamado(chamado, chamado.getId().getEmpresa()));
					
					if(qtdAtendimentos != chamado.getQtdAtendimentos()){
						atendimentoService.atualizar(chamado);
					}
					
				}
			}
			
						
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e.getMessage());
		} finally{
			Logger.getLogger(getClass().getName()).log(Level.INFO, "------------------------ Job JobAtualizaNrAtendimentosChamados: Finalizando Job JobAtualizaNrAtendimentosChamados "+dh.format(new Date())+" ------------------------","");	
		}
		
	}
	
	
	private AtendimentoServiceLocal lookup() {
		try {
			Context c = new InitialContext();
			
			return (AtendimentoServiceLocal) 
						c.lookup("java:global/chamados-web/AtendimentoService"
								+"!br.com.chamadosweb.service.AtendimentoServiceLocal");
			
		} catch (Exception ne) {
			try {				
				Context c = new InitialContext();
				
				return (AtendimentoServiceLocal) 
						c.lookup("java:global/chamados/AtendimentoService"
								+"!br.com.chamadosweb.service.AtendimentoServiceLocal");
				
			} catch (Exception ne2) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne2);
				throw new RuntimeException(ne2);
			}
		}
	}
}
