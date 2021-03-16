package br.com.dynamic.entidade;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plano")
public class Plano implements Serializable {

	private Integer idPlano;
	private String nome;
	private int formula;
	private float valor;

	public Plano() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idPlano", unique = true, nullable = false)
	public Integer getIdPlano() {
		return this.idPlano;
	}

	public void setIdPlano(Integer idAuditoria) {
		this.idPlano = idAuditoria;
	}

	@Column(name = "nome", nullable = false, length = 45)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "valor", nullable = false, precision = 12, scale = 0)
	public float getValor() {
		return this.valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	@Column(name = "formula", nullable = false)
	public int getFormula() {
		return this.formula;
	}

	public void setFormula(int formula) {
		this.formula = formula;
	}

}
