package br.com.dynamic.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "estoque")
public class Estoque implements Serializable {

	private static final long serialVersionUID = 1L;

	private EstoqueId id;
	private Produto produto;
	private int quantidadeAtual;
	private int quantidadeTotal;
	private Integer quantidadeBaixa;
	private Date validade;

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "produtoIdProduto", column = @Column(name = "Produto_idProduto", nullable = false)),
			@AttributeOverride(name = "produtoCategoriaIdCategoria", column = @Column(name = "Produto_Categoria_idCategoria", nullable = false)) })
	public EstoqueId getId() {
		return this.id;
	}

	public void setId(EstoqueId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Produto_idProduto", referencedColumnName = "idProduto", nullable = false, insertable = false, updatable = false)
	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Column(name = "quantidadeAtual", nullable = false)
	public int getQuantidadeAtual() {
		return this.quantidadeAtual;
	}

	public void setQuantidadeAtual(int quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}

	@Column(name = "quantidadeTotal", nullable = false)
	public int getQuantidadeTotal() {
		return this.quantidadeTotal;
	}

	public void setQuantidadeTotal(int quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	@Column(name = "quantidadeBaixa")
	public Integer getQuantidadeBaixa() {
		return this.quantidadeBaixa;
	}

	public void setQuantidadeBaixa(Integer quantidadeBaixa) {
		this.quantidadeBaixa = quantidadeBaixa;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "validade", length = 10)
	public Date getValidade() {
		return this.validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

}