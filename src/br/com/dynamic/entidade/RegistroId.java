package br.com.dynamic.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RegistroId implements Serializable {

	private int idVenda;
	private int usuarioIdUsuario;

	public RegistroId() {
	}

	public RegistroId(int idVenda, int usuarioIdUsuario) {
		this.idVenda = idVenda;
		this.usuarioIdUsuario = usuarioIdUsuario;
	}

	@Column(name = "idVenda", nullable = false)
	public int getIdVenda() {
		return this.idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	@Column(name = "Usuario_idUsuario", nullable = false)
	public int getUsuarioIdUsuario() {
		return this.usuarioIdUsuario;
	}

	public void setUsuarioIdUsuario(int usuarioIdUsuario) {
		this.usuarioIdUsuario = usuarioIdUsuario;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RegistroId))
			return false;
		RegistroId castOther = (RegistroId) other;

		return (this.getIdVenda() == castOther.getIdVenda())
				&& (this.getUsuarioIdUsuario() == castOther
						.getUsuarioIdUsuario());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdVenda();
		result = 37 * result + this.getUsuarioIdUsuario();
		return result;
	}

}
