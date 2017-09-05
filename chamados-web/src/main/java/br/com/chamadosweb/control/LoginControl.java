package br.com.chamadosweb.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.chamadosweb.padrao.BaseControl;
import br.com.chamadosweb.padrao.Md5;
import br.com.chamadosweb.service.LoginServiceLocal;
import br.com.chamadosweb.service.model.Usuario;

/**
*
* @author Renan Celso
*/
@ManagedBean(name = "loginControl")
@ViewScoped
public class LoginControl extends BaseControl {

	private static final long serialVersionUID = 1L;
		
	@EJB
	private LoginServiceLocal loginService;		
	
	private Usuario novoUsuario;
	
	private String senhaConfirmacao;
	
	public String emailEsqueceuSenha;
			
	private String emailLoginLogar;	
	
	private String senhaLogar; 
		
	@PostConstruct
	public void init() {		
		novoUsuario = new Usuario();		
		senhaConfirmacao = "";
		emailEsqueceuSenha = "";
		emailLoginLogar = "";
		senhaLogar = "";
		
	}
	
	
	
	public String cadastrarNovoUsuario() {
		try {
			
			if("".equalsIgnoreCase(novoUsuario.getNomeCompleto()) 
				|| "".equalsIgnoreCase(novoUsuario.getEmail()) 
				|| "".equalsIgnoreCase(novoUsuario.getLogin())					
				|| "".equalsIgnoreCase(novoUsuario.getSenha())
				|| "".equalsIgnoreCase(senhaConfirmacao)) {		
				
				addErrorMessage("Os campos nome, e-mail, login e senha são obrigatórios. Favor preenchê-los.");	
				novoUsuario.setSenha("");
				senhaConfirmacao = "";
				return null;
				
			}		
						
			
			if(!novoUsuario.getSenha().equals(Md5.getMd5Digest(senhaConfirmacao))){			
				addErrorMessage("Senha e confirmação de senha não conferem.");
				novoUsuario.setSenha("");
				senhaConfirmacao = "";
				return null;
			}			
			
			List<Usuario> listaUsuarios = new ArrayList<Usuario>();			
			listaUsuarios = loginService.buscarUsuarioPorLoginEmail(novoUsuario.getEmail());
			
			if(listaUsuarios != null && !listaUsuarios.isEmpty()) {								
				addErrorMessage("e-mail e/ou login já cadastrado.");
				novoUsuario.setEmail("");
				novoUsuario.setSenha("");
				senhaConfirmacao = "";
				return null;
			}
				
			novoUsuario.setTipoUsuario("PADRAO");			
			novoUsuario.setSituacao("A");			
			novoUsuario.setDataHoraAtualizacao(new Date());			
			novoUsuario = (Usuario) loginService.atualizar(novoUsuario);
			
			addInfoMessage("Usuário "+novoUsuario.getLogin()+" ("+novoUsuario.getEmail()+") cadastrado com sucesso");						
			init();
			
			return null;			
			
		}catch(Exception e){
			log.error(e);
			addErrorMessage("Erro ao cadastrar novo usuário. "+e.getMessage());
			return null;
		}
	}
		
	public String solicitarNovaSenha() {
		
		if(emailEsqueceuSenha == null || "".equalsIgnoreCase(emailEsqueceuSenha)){
			addErrorMessage("É necessário informar um e-mail");
			return null;
		}		
		
		List<Usuario> listaUsuarios = loginService.buscarUsuarioPorLoginEmail(emailEsqueceuSenha);		
		
		if (listaUsuarios == null || listaUsuarios.isEmpty()) {
			addErrorMessage("Usuário "+emailEsqueceuSenha+" inexistente");
			return null;			
		}		
		
		try {
			
			for (Usuario usuario : listaUsuarios) {
				//gerar nova senha
				//enviar e-mail
			}	
			
			addInfoMessage("Nova senha enviada para "+emailEsqueceuSenha);	
			
			init();
			
		} catch(Exception e){
			log.error("Erro ao gerar nova senha para o usuario "+emailEsqueceuSenha+" - "+ e.getMessage());
			addErrorMessage("Erro ao gerar nova senha para o usuario "+emailEsqueceuSenha);
			return null;
		}
						
		return null;
	}
	
	public String logar() {	
		
		try {
								
			HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			if(emailLoginLogar == null || "".equalsIgnoreCase(emailLoginLogar)){
				addErrorMessage("Informe seu e-mail ou login");
				return null;
			}
			
			if(senhaLogar == null || "".equalsIgnoreCase(senhaLogar)){
				addErrorMessage("Informe sua senha");
				return null;
			}			
				
			List<Usuario> listaUsuarios = loginService.buscarUsuarioPorLoginEmail(emailLoginLogar);
								
			if(listaUsuarios == null || listaUsuarios.isEmpty()) {					
				addErrorMessage("Usuário inválido");
				return null;							
			} 
			
			Usuario usuarioBase = new Usuario();			
			usuarioBase = listaUsuarios.get(0);
			
			if (usuarioBase != null && usuarioBase.getEmail() != null && usuarioBase.getSenha() != null
					&& !"".equalsIgnoreCase(usuarioBase.getEmail()) && !"".equalsIgnoreCase(usuarioBase.getSenha())) {	
				
				if(!usuarioBase.getSenha().equals(Md5.getMd5Digest(senhaLogar)) 
						&& !usuarioBase.getSenhaNova().equalsIgnoreCase(Md5.getMd5Digest(senhaLogar))) {						
					
					addErrorMessage("Senha inválida");
					return null;
					
				} else {				
				
					if(usuarioBase.getSenha().equals(Md5.getMd5Digest(senhaLogar))) {																
						
						sessao.setAttribute("usuarioLogado", usuarioBase);	
						
						log.info("Usuário validado. Vai redirecionar aplicação para '/inicio'");
						redirect("/inicio");				
						return null;
					}
					
					if(usuarioBase.getSenhaNova() != null && !"".equalsIgnoreCase(usuarioBase.getSenhaNova())
						&& usuarioBase.getSenhaNova().equalsIgnoreCase(Md5.getMd5Digest(senhaLogar))) {
						
						usuarioBase.setSenha(senhaLogar);
						usuarioBase.setSenhaNova(null);	
						usuarioBase = (Usuario) loginService.atualizar(usuarioBase);
						
						sessao.setAttribute("usuarioLogado", usuarioBase);				
						
						log.info("Usuário validado. Vai redirecionar aplicação para /inicio");
						redirect("/inicio");					
						return null;					
					}	
				}
				
			} 	
			
		} catch(Exception e){
			log.error("Erro ao efetuar login no sistema"+e.getMessage());		
			addErrorMessage("Erro ao efetuar login no sistema");	
			return null;
		}		
		
		return null;
	}



	public Usuario getNovoUsuario() {
		return novoUsuario;
	}



	public void setNovoUsuario(Usuario novoUsuario) {
		this.novoUsuario = novoUsuario;
	}



	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}



	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}



	public String getEmailEsqueceuSenha() {
		return emailEsqueceuSenha;
	}



	public void setEmailEsqueceuSenha(String emailEsqueceuSenha) {
		this.emailEsqueceuSenha = emailEsqueceuSenha;
	}



	public String getEmailLoginLogar() {
		return emailLoginLogar;
	}



	public void setEmailLoginLogar(String emailLoginLogar) {
		this.emailLoginLogar = emailLoginLogar;
	}



	public String getSenhaLogar() {
		return senhaLogar;
	}



	public void setSenhaLogar(String senhaLogar) {
		this.senhaLogar = senhaLogar;
	}
	
}
