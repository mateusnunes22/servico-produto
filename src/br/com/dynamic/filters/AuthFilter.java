package br.com.dynamic.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.MDC;

import br.com.dynamic.entidade.Usuario;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.jsf"})
public class AuthFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession ses = req.getSession(false);

		String reqURI = req.getRequestURI();
			
		if (reqURI.indexOf("/login.jsf") >= 0 || reqURI.contains("javax.faces.resource")) {
			chain.doFilter(request, response);
			return;
		}
		
		if (ses != null && ses.getAttribute("currentUser") != null) {
			Usuario usuario = (Usuario) ses.getAttribute("currentUser");
			String page = req.getRequestURI().toString();
			int index = page.indexOf("/", 1);
			page = page.substring(index);
			boolean temAcesso = usuario.temAcesso(page, usuario.getPerfil());
			
			MDC.put("nomeUsuario", usuario.getNome());
			
			if (temAcesso || true) {
				chain.doFilter(request, response);
				return;
			}
		}
		
		res.sendRedirect(req.getContextPath() + "/login.jsf");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}