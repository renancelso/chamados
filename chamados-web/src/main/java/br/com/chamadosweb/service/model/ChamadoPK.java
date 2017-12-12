package br.com.chamadosweb.service.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the chamado database table.
 * 
 */
@Embeddable
public class ChamadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="nrChamado")
	private Long nrChamado;

	@Column(name="empresa")
	private Long empresa;

	public ChamadoPK() {
	}
	public Long getNrChamado() {
		return this.nrChamado;
	}
	public void setNrChamado(Long nrChamado) {
		this.nrChamado = nrChamado;
	}
	public Long getEmpresa() {
		return this.empresa;
	}
	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result
				+ ((nrChamado == null) ? 0 : nrChamado.hashCode());
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
		ChamadoPK other = (ChamadoPK) obj;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (nrChamado == null) {
			if (other.nrChamado != null)
				return false;
		} else if (!nrChamado.equals(other.nrChamado))
			return false;
		return true;
	}
	
}