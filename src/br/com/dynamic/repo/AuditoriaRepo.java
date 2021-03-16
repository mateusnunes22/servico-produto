package br.com.dynamic.repo;

import java.sql.Time;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import br.com.dynamic.entidade.Auditoria;
import br.com.dynamic.entidade.Usuario;
import br.com.dynamic.repo.interfaces.IAuditoriaRepo;

@Repository
public class AuditoriaRepo implements IAuditoriaRepo {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void auditar(Auditoria auditoria) {
		HttpSession session2 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Usuario usu = (Usuario) session2.getAttribute("currentUser");

		auditoria.setData(new Date());
		auditoria.setHora(new Time(new Date().getTime()-10800000));
		auditoria.setNomeUsuario(usu.getNome());

		em.merge(auditoria);
	}

}