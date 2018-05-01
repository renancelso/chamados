package br.com.chamadosweb.service;

import br.com.chamadosweb.padrao.GenericService;
import br.com.chamadosweb.service.model.Ocorrencia;

import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Laerte Guedes
 *         29/04/18
 */
@Stateless
public class OcorrenciaService extends GenericService implements OcorrenciaServiceLocal {

   @Override
   public List<Ocorrencia> buscar(Ocorrencia ocorrenciaFiltro, Date dataAberturaInicio, Date dataAberturaFim, Long empresa) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

      StringBuilder query = new StringBuilder("select o from Ocorrencia o where 1=1 ");

      if (ocorrenciaFiltro != null){
         if (ocorrenciaFiltro.getId() > 0)
            query.append(" and o.id = ").append(ocorrenciaFiltro.getId());

         if (!ocorrenciaFiltro.getDescricao().equals(""))
            query.append(" and o.descricao LIKE '%").append(ocorrenciaFiltro.getDescricao()).append("%'");
      }

      if (dataAberturaInicio != null && dataAberturaFim != null){
         query.append(" and o.dataAbertura BETWEEN '").append(sdf.format(dataAberturaInicio)).append("' and '").append(sdf.format(dataAberturaFim)+"'");
      }else{
         if (dataAberturaInicio != null)
            query.append(" and o.dataAbertura >= '").append(sdf.format(dataAberturaInicio)+"'");

         if (dataAberturaFim != null)
            query.append(" and o.dataAbertura <= '").append(sdf.format(dataAberturaFim)+"'");
      }

      if (empresa != null)
         query.append(" and o.empresa = ").append(empresa);

      return (List<Ocorrencia>) consultarPorQuery(query.toString(), 0, 0);
   }

}
