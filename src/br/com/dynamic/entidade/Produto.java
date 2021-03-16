package br.com.dynamic.entidade;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "produto", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Categoria categoria;
	private String nome;
	private Float valorCompra;
	private Float valorVenda;
	private Set<Estoque> estoques = new HashSet<Estoque>(0);
	private Set<Registro> registros = new HashSet<Registro>(0);

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idProduto", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Categoria_idCategoria",  nullable = false)
	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Column(name = "nome", unique = true, nullable = false, length = 80)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "valorCompra", nullable = false, precision = 12, scale = 0)
	public Float getValorCompra() {
		return this.valorCompra;
	}

	public void setValorCompra(Float valorCompra) {
		this.valorCompra = valorCompra;
	}

	@Column(name = "valorVenda", nullable = false, precision = 12, scale = 0)
	public Float getValorVenda() {
		return this.valorVenda;
	}

	public void setValorVenda(Float valorVenda) {
		this.valorVenda = valorVenda;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produto")
	public Set<Estoque> getEstoques() {
		return this.estoques;
	}

	public void setEstoques(Set<Estoque> estoques) {
		this.estoques = estoques;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produto")
	public Set<Registro> getRegistros() {
		return this.registros;
	}

	public void setRegistros(Set<Registro> registros) {
		this.registros = registros;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}