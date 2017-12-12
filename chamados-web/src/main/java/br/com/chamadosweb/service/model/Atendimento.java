package br.com.chamadosweb.service.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

/**
 *
 * @author Renan Celso
 * 
 */
@Entity
@Table(name="atendimento")
public class Atendimento implements Serializable {
		
	private static final long serialVersionUID = 4678461063573174892L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="nrChamado", referencedColumnName="nrChamado", insertable=true, updatable=true),
		@JoinColumn(name="empresa", referencedColumnName="empresa" , insertable=true, updatable=true),		
		})	
	@Index(name = "index_atend_nrChamado")
	private Chamado chamado; 
		
	@Column(name = "nrSq", nullable = false)
	@Index(name = "index_atend_nrSq")
	private Long nrSq;
	
	@Column(name = "encaminhador")	
	private String encaminhador;
		
	
	//Data e Hora que o Cliente transferiu o chamado para a equipe
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dhTransferidoEquipe")
	private Date dhTransferidoEquipe; 
	
	//Data e Hora que a Equipe transferiu o chamado para o Cliente
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dhRespostaCliente")
	private Date dhRespostaCliente; 	
		
	@Column(name = "nomeAnalista")
	private String nomeAnalista; 

	@Lob
	@Column(name = "descricaoAtendimento")
	private String descricaoAtendimento; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dhAtu")
	private Date dhAtu; 
	
	@Column(name = "loginUsuAtu")
	private String loginUsuAtu; 
		

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public Long getNrSq() {
		return nrSq;
	}

	public void setNrSq(Long nrSq) {
		this.nrSq = nrSq;
	}
		
	public String getEncaminhador() {
		return encaminhador;
	}

	public void setEncaminhador(String encaminhador) {
		this.encaminhador = encaminhador;
	}

	public Date getDhTransferidoEquipe() {
		return dhTransferidoEquipe;
	}

	public void setDhTransferidoEquipe(Date dhTransferidoEquipe) {
		this.dhTransferidoEquipe = dhTransferidoEquipe;
	}

	public Date getDhRespostaCliente() {
		return dhRespostaCliente;
	}

	public void setDhRespostaCliente(Date dhRespostaCliente) {
		this.dhRespostaCliente = dhRespostaCliente;
	}

	public String getNomeAnalista() {
		return nomeAnalista;
	}

	public void setNomeAnalista(String nomeAnalista) {
		this.nomeAnalista = nomeAnalista;
	}
		
	public String getDescricaoAtendimento() {
		return descricaoAtendimento;
	}

	public void setDescricaoAtendimento(String descricaoAtendimento) {
		this.descricaoAtendimento = descricaoAtendimento;
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
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chamado == null) ? 0 : chamado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nrSq == null) ? 0 : nrSq.hashCode());
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
		Atendimento other = (Atendimento) obj;
		if (chamado == null) {
			if (other.chamado != null)
				return false;
		} else if (!chamado.equals(other.chamado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nrSq == null) {
			if (other.nrSq != null)
				return false;
		} else if (!nrSq.equals(other.nrSq))
			return false;
		return true;
	}	
	   
}