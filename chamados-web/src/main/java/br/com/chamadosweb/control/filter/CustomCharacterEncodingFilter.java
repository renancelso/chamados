package br.com.chamadosweb.control.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;

/**
 * 
 * @author Renan Celso
 *
 */
public class CustomCharacterEncodingFilter implements Filter {

	@Override
    public void init(FilterConfig config) throws ServletException {
       Log.info("init(FilterConfig config) CustomCharacterEncodingFilter");       
    }

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                                                           throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");         
        ((HttpServletResponse) response).addHeader("X-UA-Compatible", "IE=Edge");
        chain.doFilter(request, response);
    }

	@Override
    public void destroy() {
		Log.info("destroy() CustomCharacterEncodingFilter");
    }
}
