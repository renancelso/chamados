package br.com.chamadosweb.service.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

/**
 *
 * @author Renan Celso
 * Histórico de versões que foram para produção
 */
@Entity
@Table(name="versao")
public class Versao implements Serializable {

	private static final long serialVersionUID = 7902830136309805284L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nrVersao")
	@Index(name = "index_nr_versao")
	private String nrVersao;
	
	/**
	 * FABRICA 
	 * ou 
	 * SUSTENTACAO
	 */
	@Index(name = "index_resp_versao")
	@Column(name = "responsavelVersao")
	private String responsavelVersao;
	
	@Lob
	@Column(name = "descricaoVersao")
	private String descricaoVersao;
		
	@Index(name = "index_sdm_versao")
	@Column(name = "nrSdmVersao")
	private String nrSdmVersao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dataEnvioArtefatos")
	private Date dataEnvioArtefatos;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataEntradaProducao")
	private Date dataEntradaProducao;
	
	@Lob
	@Column(name = "localDocumentosArtefatos")
	private String localDocumentosArtefatos;
		
	@Column(name = "sistemasRelacionados")
	private String sistemasRelacionados;
	
	@ManyToOne
	@JoinColumn(name="empresa")		
	private Empresa empresa; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNrVersao() {
		return nrVersao;
	}

	public void setNrVersao(String nrVersao) {
		this.nrVersao = nrVersao;
	}

	public String getResponsavelVersao() {
		return responsavelVersao;
	}

	public void setResponsavelVersao(String responsavelVersao) {
		this.responsavelVersao = responsavelVersao;
	}

	public String getDescricaoVersao() {
		return descricaoVersao;
	}

	public void setDescricaoVersao(String descricaoVersao) {
		this.descricaoVersao = descricaoVersao;
	}

	public String getNrSdmVersao() {
		return nrSdmVersao;
	}

	public void setNrSdmVersao(String nrSdmVersao) {
		this.nrSdmVersao = nrSdmVersao;
	}

	public Date getDataEnvioArtefatos() {
		return dataEnvioArtefatos;
	}

	public void setDataEnvioArtefatos(Date dataEnvioArtefatos) {
		this.dataEnvioArtefatos = dataEnvioArtefatos;
	}

	public Date getDataEntradaProducao() {
		return dataEntradaProducao;
	}

	public void setDataEntradaProducao(Date dataEntradaProducao) {
		this.dataEntradaProducao = dataEntradaProducao;
	}

	public String getLocalDocumentosArtefatos() {
		return localDocumentosArtefatos;
	}

	public void setLocalDocumentosArtefatos(String localDocumentosArtefatos) {
		this.localDocumentosArtefatos = localDocumentosArtefatos;
	}
		
	public String getSistemasRelacionados() {
		return sistemasRelacionados;
	}

	public void setSistemasRelacionados(String sistemasRelacionados) {
		this.sistemasRelacionados = sistemasRelacionados;
	}
		
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
		Versao other = (Versao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}		

}
