package br.com.dynamic.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Formatter;

import javax.faces.context.FacesContext;

import br.com.dynamic.entidade.Usuario;

public class LogMessagesUtil {
	
	public static String getPageView() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		
		Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
		String nomeUsuario = "Sem usuario";
		boolean temAcesso = false;
		
		if (FacesContext.getCurrentInstance().getViewRoot() == null) {
			formatter.format("EsteticaSofisticado/%s/%s/%s", true, nomeUsuario, new Date(new Date().getTime() - 10800000));
		} else {
			String currentPage = FacesContext.getCurrentInstance().getViewRoot().getViewId();
			currentPage = currentPage.replace(".xhtml", ".jsf");
			
			if (user != null) {
				temAcesso = user.temAcesso(currentPage, user.getPerfil());
				nomeUsuario = user.getNome();
			}
			
			formatter.format("EsteticaSofisticado/%s/%s%s/%s", temAcesso,  nomeUsuario, currentPage, new Date(new Date().getTime() - 10800000));
		}

		return sb.toString();
	}
	
	public static String getMailMessage(Throwable e) {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
		String nomeUsuario = (usuario == null) ? "Sem usuário" : usuario.getNome();
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		
		try {
			formatter.format("Usuário: %s\nData: %s\n\nPágina: %s\nSistema: %s\n\nErro: %s\n\nStacktrace: %s", 
				nomeUsuario, 
				new Date(), 
				FacesContext.getCurrentInstance().getViewRoot().getViewId(), 
				FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath(), 
				e.getMessage(), 
				stringWriter.toString()
			);
		} catch(Exception ex) {
			formatter.format("Usuário: %s\nData: %s\n\nErro: %s\n\nStacktrace: %s", 
				nomeUsuario, 
				new Date(), 
				e.getMessage(), 
				stringWriter.toString()
			);
		}
		
		return sb.toString();
	}

}