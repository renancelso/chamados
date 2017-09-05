package br.com.chamadosweb.control;

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
@ManagedBean(name = "minhaContaControl")
@ViewScoped
public class MinhaContaControl extends BaseControl {

	private static final long serialVersionUID = 6670457419746614063L;
	
	@EJB
	private LoginServiceLocal loginService;		
	private Usuario usuarioLogado;
	
	private String senhaConfirmacao;
	
	@PostConstruct
	public void init() {
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");	
		usuarioLogado.setSenha("");
		senhaConfirmacao = "";
	}	
	
	public String alterarMinhaConta() {
		
		if("".equalsIgnoreCase(usuarioLogado.getNomeCompleto()) 
				|| "".equalsIgnoreCase(usuarioLogado.getEmail()) 
				|| "".equalsIgnoreCase(usuarioLogado.getLogin())					
				|| "".equalsIgnoreCase(usuarioLogado.getSenha())
				|| "".equalsIgnoreCase(senhaConfirmacao)) {		
				
				addErrorMessage("Os campos nome, e-mail, login e senha são obrigatórios. Favor preenchê-los.");	
				usuarioLogado.setSenha("");
				senhaConfirmacao = "";
				return null;
				
		}				
			
		if(!usuarioLogado.getSenha().equals(Md5.getMd5Digest(senhaConfirmacao))){	
			
			addErrorMessage("Senha e confirmação de senha não conferem.");
			usuarioLogado.setSenha("");
			senhaConfirmacao = "";
			return null;
			
		} else {
			usuarioLogado.setSenha(senhaConfirmacao);
			loginService.atualizar(usuarioLogado);
			addInfoMessage("Dados alterados com sucesso.");
		}
		
		return null;
	}	

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}	
		
}
