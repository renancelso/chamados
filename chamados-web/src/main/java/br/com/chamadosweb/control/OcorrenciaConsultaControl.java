package br.com.chamadosweb.control;

import br.com.chamadosweb.padrao.BaseControl;
import br.com.chamadosweb.service.OcorrenciaServiceLocal;
import br.com.chamadosweb.service.model.Ocorrencia;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Laerte Guedes
       29/04/18
 */
@ManagedBean(name = "ocorrenciaConsultaControl")
@ViewScoped
public class OcorrenciaConsultaControl extends BaseControl{

   @EJB
   private OcorrenciaServiceLocal ocorrenciaService;

   private Ocorrencia ocorrenciaDetalhes;
   private Ocorrencia ocorrenciaFiltro;
   private List<Ocorrencia> ocorrencias;
   private List<Ocorrencia> ocorrenciasDetalhes;
   private Date dataAberturaInicio;
   private Date dataAberturaFinal;
   private Ocorrencia ocorrenciaNova;
   private boolean mostrarBotaoNovo;

   @PostConstruct
   public void init(){
      limpar();
   }

   public void limpar(){
      ocorrenciaFiltro = new Ocorrencia();
      ocorrenciaNova = new Ocorrencia();

      ocorrencias = new ArrayList<>();
      ocorrenciasDetalhes = new ArrayList<>();

      ocorrenciaDetalhes = null;
      dataAberturaInicio = null;
      dataAberturaFinal = null;

      mostrarBotaoNovo = true;
   }

   public void buscar(){
      ocorrencias = ocorrenciaService.buscar(ocorrenciaFiltro, dataAberturaInicio, dataAberturaFinal, getUsuarioLogado().getEmpresa().getId());
   }

   public void novo(){
      ocorrenciaDetalhes = ocorrenciaNova;
   }

   public void salvar(){
      try {
         ocorrenciaDetalhes.setEmpresa(getUsuarioLogado().getEmpresa());
         ocorrenciaDetalhes.setUsuarioAtualizacao(getUsuarioLogado().getLogin());
         ocorrenciaDetalhes.setDataAtualizacao(new Date());

         ocorrenciaService.atualizar(ocorrenciaDetalhes);

         addInfoMessage("Ocorrência salva com sucesso!");
      }catch (Exception ex) {
         addErrorMessage("Erro ao salvar ocorrência."+ex.getMessage());
      }
   }

   public void voltar(){

      ocorrenciaDetalhes = null;

      if(ocorrenciaFiltro == null)
         ocorrenciaFiltro = new Ocorrencia();
   }

   public Ocorrencia getOcorrenciaDetalhes() {
      return ocorrenciaDetalhes;
   }

   public void setOcorrenciaDetalhes(Ocorrencia ocorrenciaDetalhes) {
      this.ocorrenciaDetalhes = ocorrenciaDetalhes;
   }

   public Ocorrencia getOcorrenciaFiltro() {
      return ocorrenciaFiltro;
   }

   public void setOcorrenciaFiltro(Ocorrencia ocorrenciaFiltro) {
      this.ocorrenciaFiltro = ocorrenciaFiltro;
   }

   public List<Ocorrencia> getOcorrencias() {
      return ocorrencias;
   }

   public void setOcorrencias(List<Ocorrencia> ocorrencias) {
      this.ocorrencias = ocorrencias;
   }

   public List<Ocorrencia> getOcorrenciasDetalhes() {
      return ocorrenciasDetalhes;
   }

   public void setOcorrenciasDetalhes(List<Ocorrencia> ocorrenciasDetalhes) {
      this.ocorrenciasDetalhes = ocorrenciasDetalhes;
   }

   public Date getDataAberturaInicio() {
      return dataAberturaInicio;
   }

   public void setDataAberturaInicio(Date dataAberturaInicio) {
      this.dataAberturaInicio = dataAberturaInicio;
   }

   public Date getDataAberturaFinal() {
      return dataAberturaFinal;
   }

   public void setDataAberturaFinal(Date dataAberturaFinal) {
      this.dataAberturaFinal = dataAberturaFinal;
   }

   public Ocorrencia getOcorrenciaNova() {
      return ocorrenciaNova;
   }

   public void setOcorrenciaNova(Ocorrencia ocorrenciaNova) {
      this.ocorrenciaNova = ocorrenciaNova;
   }

   public boolean isMostrarBotaoNovo() {
      return mostrarBotaoNovo;
   }

   public void setMostrarBotaoNovo(boolean mostrarBotaoNovo) {
      this.mostrarBotaoNovo = mostrarBotaoNovo;
   }
}
