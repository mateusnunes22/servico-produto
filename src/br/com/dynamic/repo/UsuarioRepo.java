package br.com.dynamic.repo;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.dynamic.entidade.Usuario;
import br.com.dynamic.repo.interfaces.IUsuarioRepo;

@Repository
public class UsuarioRepo extends JpaRepository<Usuario, Integer> implements IUsuarioRepo {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer getInsertedId() {
		return getEntityManager().createQuery("select max(u.id) from Usuario u", Integer.class).getSingleResult();
	}

	@Override
	public Usuario findByNome(String nome) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> q = cb.createQuery(Usuario.class);
		Root<Usuario> root = q.from(Usuario.class);

		q.where(cb.equal(root.get("nome"), nome));

		try {
			return getEntityManager().createQuery(q.select(root)).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}