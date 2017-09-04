package br.com.chamadosweb.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.chamadosweb.padrao.GenericService;
import br.com.chamadosweb.service.model.Usuario;

/**
 * @author Renan Celso
 */
@Stateless
public class LoginService extends GenericService implements LoginServiceLocal {

	private static final long serialVersionUID = -2774516333703246986L;

	@Override
    public List<Usuario> buscarUsuarioPorLoginEmail(String loginEmail) {		
    	try {	
    		
    		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
    		
    		StringBuilder sql = new StringBuilder();
    		sql.append("select o from ").append(Usuario.class.getSimpleName()).append(" o ");
    		sql.append(" where (o.email = '").append(loginEmail).append("' or");
    		sql.append(" o.login = '").append(loginEmail).append("')");
    		    		
    		listaUsuarios = (List<Usuario>) consultarPorQuery(sql.toString(), 0, 0);
    		
	    	return listaUsuarios; 	    	
	    	
    	} catch(Exception e) {
    		log.error(e);
    		return null;
    	}
    }		

}
