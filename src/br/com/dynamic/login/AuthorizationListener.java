package br.com.dynamic.login;

import java.util.Date;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import br.com.dynamic.entidade.Usuario;

public class AuthorizationListener implements PhaseListener {

	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		String currentPage = facesContext.getViewRoot().getViewId();
		System.out.println(currentPage);
		Usuario user = null;
		boolean isLoginPage = (currentPage.lastIndexOf("/login.xhtml") > -1);
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);

		
		user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");//session.getAttribute("currentUser");
		System.out.println(user);

		if (!isLoginPage && user != null) {

		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("currentUser", null);
		}

		if (!isLoginPage && user == null) {
			System.out.println("EsteticaSofisticado/Login/"
					+ new Date(new Date().getTime() - 10800000));
			NavigationHandler nh = facesContext.getApplication()
					.getNavigationHandler();
			nh.handleNavigation(facesContext, null, "login");
		} else {

			boolean temAcesso = false;

			try {
				temAcesso = user.temAcesso(currentPage, user.getPerfil());
			} catch (Exception e) {

			}

			try {
					System.out.println("EsteticaSofisticado/" + temAcesso
							+ currentPage.toString() + "/" + user.getNome()
							+ "/" + new Date(new Date().getTime() - 10800000));
				} catch (Exception e) {
					System.out.println("EsteticaSofisticado/" + temAcesso
							+ currentPage.toString() + "/" + "Sem usuario" + "/"
							+ new Date(new Date().getTime() - 10800000));
			}

			if (temAcesso) {

			} else {
				NavigationHandler nh = facesContext.getApplication()
						.getNavigationHandler();
				nh.handleNavigation(facesContext, null, "login");
			}
		}

	}

	public void beforePhase(PhaseEvent event) {

	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}