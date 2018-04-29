package br.com.chamadosweb.service.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Laerte Guedes
 *         29/04/18
 */
@Entity
@Table(name = "ocorrencia")
public class Ocorrencia implements Serializable{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String requisitante;

   private String departamento;

   @Lob
   private String descricao;

   @Temporal(TemporalType.DATE)
   @Column(name = "data_abertura")
   private Date dataAbertura;

   @Column(name = "sistema_modulo")
   private String sistemaModulo;

   private String urgencia;

   private Integer prioridade;

   private String funcionalidade;

   @OneToOne
   @JoinColumn(name = "empresa_id")
   private Empresa empresa;

   public Ocorrencia() {
   }

   @OneToMany(mappedBy="ocorrencia", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
   private List<Atendimento> listaAtendimentos = new ArrayList<>();

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "data_atualizacao")
   private Date dataAtualizacao;

   @Column(name = "usuario_atualizacao")
   private String usuarioAtualizacao;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Empresa getEmpresa() {
      return empresa;
   }

   public void setEmpresa(Empresa empresa) {
      this.empresa = empresa;
   }

   public String getRequisitante() {
      return requisitante;
   }

   public void setRequisitante(String requisitante) {
      this.requisitante = requisitante;
   }

   public String getDepartamento() {
      return departamento;
   }

   public void setDepartamento(String departamento) {
      this.departamento = departamento;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public Date getDataAbertura() {
      return dataAbertura;
   }

   public void setDataAbertura(Date dataAbertura) {
      this.dataAbertura = dataAbertura;
   }

   public String getSistemaModulo() {
      return sistemaModulo;
   }

   public void setSistemaModulo(String sistemaModulo) {
      this.sistemaModulo = sistemaModulo;
   }

   public String getUrgencia() {
      return urgencia;
   }

   public void setUrgencia(String urgencia) {
      this.urgencia = urgencia;
   }

   public Integer getPrioridade() {
      return prioridade;
   }

   public void setPrioridade(Integer prioridade) {
      this.prioridade = prioridade;
   }

   public String getFuncionalidade() {
      return funcionalidade;
   }

   public void setFuncionalidade(String funcionalidade) {
      this.funcionalidade = funcionalidade;
   }

   public List<Atendimento> getListaAtendimentos() {
      return listaAtendimentos;
   }

   public void setListaAtendimentos(List<Atendimento> listaAtendimentos) {
      this.listaAtendimentos = listaAtendimentos;
   }

   public Date getDataAtualizacao() {
      return dataAtualizacao;
   }

   public void setDataAtualizacao(Date dataAtualizacao) {
      this.dataAtualizacao = dataAtualizacao;
   }

   public String getUsuarioAtualizacao() {
      return usuarioAtualizacao;
   }

   public void setUsuarioAtualizacao(String usuarioAtualizacao) {
      this.usuarioAtualizacao = usuarioAtualizacao;
   }

}
