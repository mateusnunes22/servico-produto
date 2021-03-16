package br.com.dynamic.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EstoqueId implements Serializable {

	private int produtoIdProduto;
	private int produtoCategoriaIdCategoria;

	public EstoqueId() {
	}

	public EstoqueId(int produtoIdProduto, int produtoCategoriaIdCategoria) {
		this.produtoIdProduto = produtoIdProduto;
		this.produtoCategoriaIdCategoria = produtoCategoriaIdCategoria;
	}

	@Column(name = "Produto_idProduto", nullable = false)
	public int getProdutoIdProduto() {
		return this.produtoIdProduto;
	}

	public void setProdutoIdProduto(int produtoIdProduto) {
		this.produtoIdProduto = produtoIdProduto;
	}

	@Column(name = "Produto_Categoria_idCategoria", nullable = false)
	public int getProdutoCategoriaIdCategoria() {
		return this.produtoCategoriaIdCategoria;
	}

	public void setProdutoCategoriaIdCategoria(int produtoCategoriaIdCategoria) {
		this.produtoCategoriaIdCategoria = produtoCategoriaIdCategoria;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EstoqueId))
			return false;
		EstoqueId castOther = (EstoqueId) other;

		return (this.getProdutoIdProduto() == castOther.getProdutoIdProduto())
				&& (this.getProdutoCategoriaIdCategoria() == castOther
						.getProdutoCategoriaIdCategoria());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getProdutoIdProduto();
		result = 37 * result + this.getProdutoCategoriaIdCategoria();
		return result;
	}

}