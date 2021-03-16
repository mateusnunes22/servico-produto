package br.com.dynamic.infra.factories;

import java.util.Comparator;

import br.com.dynamic.entidade.Categoria;
import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Estoque;
import br.com.dynamic.entidade.Fornecedor;
import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.entidade.Servico;

public class ComparatorUtil {

	public static Comparator<Categoria> getCategoriaComparator() {
		return new Comparator<Categoria>() {
			@Override
			public int compare(Categoria o1, Categoria o2) {
				return o1.getDescricao().compareToIgnoreCase(o2.getDescricao());
			}
		};
	}
	
	public static Comparator<Cliente> getClienteComparator() {
		return new Comparator<Cliente>() {
			@Override
			public int compare(Cliente o1, Cliente o2) {
				return o1.getNome().compareToIgnoreCase(o2.getNome());
			}
		};
	}
	
	public static Comparator<Estoque> getEstoqueComparator() {
		return new Comparator<Estoque>() {
			@Override
			public int compare(Estoque o1, Estoque o2) {
				return o1.getProduto().getNome().compareToIgnoreCase(o2.getProduto().getNome());
			}
		};
	}
	
	public static Comparator<Fornecedor> getFornecedorComparator() {
		return new Comparator<Fornecedor>() {
			@Override
			public int compare(Fornecedor o1, Fornecedor o2) {
				return o1.getNome().compareToIgnoreCase(o2.getNome());
			}
		};
	}

	public static Comparator<Funcionario> getFuncionarioComparator() {
		return new Comparator<Funcionario>() {
			@Override
			public int compare(Funcionario o1, Funcionario o2) {
				return o1.getNome().compareToIgnoreCase(o2.getNome());
			}
		};
	}

	public static Comparator<Produto> getProdutoComparator() {
		return new Comparator<Produto>() {
			@Override
			public int compare(Produto o1, Produto o2) {
				return o1.getNome().compareToIgnoreCase(o2.getNome());
			}
		};
	}
	
	public static Comparator<Produto> getProdutoComparatorPorValorVendaDesc() {
		return new Comparator<Produto>() {
			@Override
			public int compare(Produto o1, Produto o2) {
				if (o1.getValorVenda() > o2.getValorVenda()) {
					return -1;
				} else if (o1.getValorVenda() < o2.getValorVenda()) {
					return 1;
				}
				return 0;
			}
		};
	}
	
	public static Comparator<Servico> getServicoComparator() {
		return new Comparator<Servico>() {
			@Override
			public int compare(Servico o1, Servico o2) {
				return o1.getNome().compareToIgnoreCase(o2.getNome());
			}
		};
	}
	
	public static Comparator<Servico> getServicoComparatorPorValorDesc() {
		return new Comparator<Servico>() {
			@Override
			public int compare(Servico o1, Servico o2) {
				if (o1.getValor() > o2.getValor()) {
					return -1;
				} else if (o1.getValor() < o2.getValor()) {
					return 1;
				}
				return 0;
			}
		};
	}

}