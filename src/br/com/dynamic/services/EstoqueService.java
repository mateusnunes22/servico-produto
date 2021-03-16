package br.com.dynamic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dynamic.entidade.Estoque;
import br.com.dynamic.entidade.EstoqueId;
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.repo.interfaces.IEstoqueRepo;
import br.com.dynamic.repo.interfaces.IProdutoRepo;

@Service
public class EstoqueService implements IEstoqueService {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IProdutoRepo produtoRepo;
	
	@Autowired
	private IEstoqueRepo estoqueRepo;
	
	@Transactional
	public String armazenarProduto(Produto produto, int quantidadeBaixa) {
		String retorno = "null";
		
		if (produto.getId() == null) {
			produtoRepo.add(produto);
		} else {
			produtoRepo.update(produto);
			retorno = "edit";
		}

		produto = produtoRepo.findByNome(produto.getNome());
		
		if (retorno.equalsIgnoreCase("null")){
			Estoque estoque = new Estoque();
			EstoqueId id = new EstoqueId();
			id.setProdutoIdProduto(produto.getId());
			id.setProdutoCategoriaIdCategoria(produto.getCategoria().getId());
			estoque.setId(id);
			estoque.setProduto(produto);
			estoque.setQuantidadeAtual(0);
			estoque.setQuantidadeTotal(0);
			estoque.setQuantidadeBaixa(quantidadeBaixa);
			
			estoqueRepo.add(estoque);
		}
		else if (retorno.equalsIgnoreCase("edit"))
		{
			Estoque estoque = estoqueRepo.findByProduto(produto);
			estoque.setQuantidadeBaixa(quantidadeBaixa);
			estoqueRepo.update(estoque);
		}
		else if(retorno.equalsIgnoreCase("erro"))
		{
			retorno = "null";
		}
		
		return retorno; 
	}

}
