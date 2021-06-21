package org.serratec.dto.pedido;

import org.serratec.models.ProdutoPedido;

public class PedidoProdutoDTO {
	
	private String produto;
	private Integer quantidade;
	private Double precoUnidade;
	
	public PedidoProdutoDTO (ProdutoPedido produtoPedido) {
		this.produto = produtoPedido.getProduto().getNome();
		this.quantidade = produtoPedido.getQuantidade();
		this.precoUnidade = produtoPedido.getProduto().getPreco();
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrecoUnidade() {
		return precoUnidade;
	}

	public void setPrecoUnidade(Double precoUnidade) {
		this.precoUnidade = precoUnidade;
	}

	
}
