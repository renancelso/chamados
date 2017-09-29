package br.com.chamadosweb.service.model.dto;

/**
 * 
 * @author Renan Celso
 *
 */
public class EstatisticasChamadosAnalistas {

	private String nomeAnalista;	
	private Long qtdChamados;
	
	public String getNomeAnalista() {
		return nomeAnalista;
	}
	public void setNomeAnalista(String nomeAnalista) {
		this.nomeAnalista = nomeAnalista;
	}
	public Long getQtdChamados() {
		return qtdChamados;
	}
	public void setQtdChamados(Long qtdChamados) {
		this.qtdChamados = qtdChamados;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nomeAnalista == null) ? 0 : nomeAnalista.hashCode());
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
		EstatisticasChamadosAnalistas other = (EstatisticasChamadosAnalistas) obj;
		if (nomeAnalista == null) {
			if (other.nomeAnalista != null)
				return false;
		} else if (!nomeAnalista.equals(other.nomeAnalista))
			return false;
		return true;
	}
	
	
	
}
