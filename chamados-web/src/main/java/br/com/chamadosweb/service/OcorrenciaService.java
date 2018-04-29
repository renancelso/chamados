package br.com.chamadosweb.service;

import br.com.chamadosweb.padrao.GenericService;
import br.com.chamadosweb.service.model.Ocorrencia;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

/**
 * @author Laerte Guedes
 *         29/04/18
 */
@Stateless
public class OcorrenciaService extends GenericService implements OcorrenciaServiceLocal {

   @Override
   public List<Ocorrencia> listar(Ocorrencia ocorrenciaFiltro, Date dataAberturaInicio, Date dataAberturaFim, Long empresa) {
      StringBuilder query = new StringBuilder("select o from Ocorrencia o where 1=1 ");

      if (ocorrenciaFiltro != null && ocorrenciaFiltro.getId() > 0)
         query.append(" and o.id = ").append(ocorrenciaFiltro.getId());

      if (dataAberturaInicio != null && dataAberturaFim != null){
         query.append(" and o.dataAbertura BETWEEN ").append(dataAberturaInicio).append(" and ").append(dataAberturaFim);
      }else{
         if (dataAberturaInicio != null)
            query.append(" and o.dataAbertura >= ").append(dataAberturaInicio);

         if (dataAberturaFim != null)
            query.append(" and o.dataAbertura <= ").append(dataAberturaFim);
      }

      if (empresa != null)
         query.append(" and o.empresa = ").append(empresa);

      return (List<Ocorrencia>) consultarPorQuery(query.toString(), 0, 0);
   }

}
