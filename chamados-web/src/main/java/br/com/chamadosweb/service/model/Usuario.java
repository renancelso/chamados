package br.com.chamadosweb.service.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.Index;

import br.com.chamadosweb.padrao.Md5;

/**
 *
 * @author Renan Celso
 * 
 */
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "login", unique = true, nullable = false, length=255)
    @Index(name = "index_login_user")
	private String login;
	
	@Column(name = "email", unique = true, nullable = false, length=255)
    @Index(name = "index_email_user")
	private String email;		
	
	@Column(name = "senha", nullable = false, length=255)
	private String senha;
		
	@Column(name="nome_completo", length=255)
	private String nomeCompleto;
	
	@Column(name="municipio", length=255)
	private String municipio;
	
	@Column(name="uf", length = 10)
	private String uf;
	
	@Column(name = "data_nascimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;
	
	@Column(name = "telefone", length=50)   
    private Date telefone;
	
	@Column(name = "ocupacao",length=200)   
    private Date ocupacao;
	
	@Column(name = "datahora_atualizacao")   
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHoraAtualizacao;	
	
	/**
	 * A = Ativo
	 * I = Inativo
	 */
	@Column(name = "situacao", nullable = false, length=10)  	
    private String situacao;	
		
	@Column(name = "senha_nova", length=255)
	private String senhaNova;
	
	/**
	 * PADRAO
	 * ADMIN		
	 * ?????  
	 */
	@Column(name="tipo_usuario", length = 30)
	private String tipoUsuario;
		
	@ManyToOne
	@JoinColumn(name="empresa")		
	private Empresa empresa; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {	
		
		if(senha != null && !"".equalsIgnoreCase(senha)){
			this.senha = Md5.getMd5Digest(senha);
		} else {
			this.senha = senha;
		}		
		
	}

	public String getNomeCompleto() {
		return nomeCompleto != null ? nomeCompleto : "";
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}	
	
	public Date getTelefone() {
		return telefone;
	}

	public void setTelefone(Date telefone) {
		this.telefone = telefone;
	}

	public Date getOcupacao() {
		return ocupacao;
	}

	public void setOcupacao(Date ocupacao) {
		this.ocupacao = ocupacao;
	}

	public Date getDataHoraAtualizacao() {
		return dataHoraAtualizacao;
	}

	public void setDataHoraAtualizacao(Date dataHoraAtualizacao) {
		this.dataHoraAtualizacao = dataHoraAtualizacao;
	}			

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		if(senhaNova != null && !"".equalsIgnoreCase(senhaNova)){
			this.senhaNova = Md5.getMd5Digest(senhaNova);
		} else {
			this.senhaNova = senhaNova;
		}	
	}
		
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
		

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}
	   
}
