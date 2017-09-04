package br.com.chamadosweb.service;

import java.util.List;

import javax.ejb.Local;

import br.com.chamadosweb.padrao.GenericServiceInterface;
import br.com.chamadosweb.service.model.Usuario;

/**
 * @author Renan Celso
 */
@Local
public interface LoginServiceLocal extends GenericServiceInterface{

	
	public List<Usuario> buscarUsuarioPorLoginEmail(String loginEmail);

}
