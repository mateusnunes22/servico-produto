package br.com.dynamic.controle;

import java.io.Serializable;
import java.util.ResourceBundle;

import javassist.NotFoundException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.com.dynamic.entidade.Usuario;
import br.com.dynamic.infra.excecoes.SenhaInvalidaException;
import br.com.dynamic.repo.interfaces.IUsuarioRepo;
import br.com.dynamic.services.ILoginService;
import br.com.dynamic.util.SessionUtil;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 4880932516591410523L;
	
	@ManagedProperty(value="#{usuarioRepo}")
	private IUsuarioRepo usuarioRepo;
	
	@ManagedProperty(value="#{loginService}")
	private ILoginService loginService;
	
	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;
	
	private Usuario usuario = new Usuario();
	private Usuario usuarioLogin = new Usuario();

	public String salvar() {
		String retorno = null;

		if (usuarioLogin.getSenha().equalsIgnoreCase(usuario.getSenha())) {
			HttpSession session2 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			Usuario usu = (Usuario) session2.getAttribute("currentUser");
			usu.setSenha(usuario.getSenha());
			usuarioRepo.add(usu);
			return "certo";
		} else {
			SessionUtil.addErrorMessage("SenhaVerificacao");
		}

		return retorno;
	}

	public String logar() {
		try {
			usuarioLogin = loginService.authorize(usuario.getNome(), usuario.getSenha());			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", usuarioLogin);
			usuario = new Usuario();

			return "logado";
		} catch(SenhaInvalidaException se) {
			Logger.getLogger(getClass().getName()).log(Level.WARN, usuario.getNome(), se);
			Usuario trocaSenhaUsuario = usuarioRepo.findByNome(usuario.getNome());
			
			if (trocaSenhaUsuario != null && usuario.getSenha().equalsIgnoreCase("agil")) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", trocaSenhaUsuario);
				usuario = new Usuario();
				return "senha";
			}
			
			SessionUtil.addErrorMessage(se.getMessage());
		} catch(final NotFoundException nfe) {
			Logger.getLogger(getClass().getName()).log(Level.WARN, bundle.getString("usuario.erro.invalido"), nfe);
			SessionUtil.addErrorMessage("usuario.erro.invalido");
		}

		return null;
	}
	
	public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml";
    }
	
	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public IUsuarioRepo getUsuarioRepo() {
		return usuarioRepo;
	}

	public void setUsuarioRepo(IUsuarioRepo usuarioRepo) {
		this.usuarioRepo = usuarioRepo;
	}

	public ILoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

}