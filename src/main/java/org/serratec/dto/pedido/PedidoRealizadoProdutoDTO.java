package org.serratec.dto.pedido;

import org.serratec.models.ProdutoPedidoRealizado;

public class PedidoRealizadoProdutoDTO {

	private String produto;
	private Integer quantidade;
	private Double precoUnidade;
	
	public PedidoRealizadoProdutoDTO (ProdutoPedidoRealizado produtoPedidoRealizado) {
		this.produto = produtoPedidoRealizado.getProduto().getNome();
		this.quantidade = produtoPedidoRealizado.getQuantidade();
		this.precoUnidade = produtoPedidoRealizado.getProduto().getPreco();
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
