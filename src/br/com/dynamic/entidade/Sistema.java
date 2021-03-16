package br.com.dynamic.entidade;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sistema")
public class Sistema implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private Integer dias;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idSistema")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nomeEmpresa", nullable = false, length = 50)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "diasAniversario", nullable = false)
	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

}