package br.com.chamadosweb.service.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the chamado database table.
 * 
 */
@Entity
@Table(name="chamado")
public class Chamado implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChamadoPK id;

	@Column(name = "requisitante")
	private String requisitante; 
	
	@Column(name = "departamento")
	private String departamento; 
		
	@Lob
	@Column(name = "descricao")
	private String descricao; 
		
	@Temporal(TemporalType.DATE)
	@Column(name = "dataAbertura")
	private Date dataAbertura; 
	
	@Column(name = "sistemaModulo")
	private String sistemaModulo;
	
	@Column(name = "ambiente")
	private String ambiente;			
	
	@Column(name = "classificacao")
	private String classificacao;
	
	@Column(name = "impacto")
	private String impacto;
	
	@Column(name = "urgencia")
	private String urgencia;
	
	@Column(name = "prioridade")
	private Integer prioridade;
	
	@Column(name = "funcionalidade")
	private String funcionalidade;
	
	@Column(name = "tipo")
	private String tipo;	
	
	@OneToMany(mappedBy="chamado", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Atendimento> listaAtendimentos;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dhAtu")
	private Date dhAtu; 
	
	@Column(name = "loginUsuAtu")
	private String loginUsuAtu; 
	
	@Column(name = "qtdAtendimentos")
	private Long qtdAtendimentos;
			

	public Chamado() {
	}

	public ChamadoPK getId() {
		return id;
	}

	public void setId(ChamadoPK id) {
		this.id = id;
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

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public String getImpacto() {
		return impacto;
	}

	public void setImpacto(String impacto) {
		this.impacto = impacto;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Atendimento> getListaAtendimentos() {
		return listaAtendimentos;
	}

	public void setListaAtendimentos(List<Atendimento> listaAtendimentos) {
		this.listaAtendimentos = listaAtendimentos;
	}

	public Date getDhAtu() {
		return dhAtu;
	}

	public void setDhAtu(Date dhAtu) {
		this.dhAtu = dhAtu;
	}

	public String getLoginUsuAtu() {
		return loginUsuAtu;
	}

	public void setLoginUsuAtu(String loginUsuAtu) {
		this.loginUsuAtu = loginUsuAtu;
	}

	public Long getQtdAtendimentos() {
		return qtdAtendimentos;
	}

	public void setQtdAtendimentos(Long qtdAtendimentos) {
		this.qtdAtendimentos = qtdAtendimentos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chamado other = (Chamado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
}