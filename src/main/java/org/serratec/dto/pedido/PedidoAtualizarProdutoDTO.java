package org.serratec.dto.pedido;

import org.serratec.exceptions.MensagemException;
import org.serratec.models.Cliente;
import org.serratec.models.Pedido;
import org.serratec.models.Produto;
import org.serratec.models.ProdutoPedido;
import org.serratec.repository.PedidoRepository;
import org.serratec.repository.ProdutoRepository;

public class PedidoAtualizarProdutoDTO {
	
	private String email;
	private Long id;
	private Integer quantidade;
	
	public Pedido toPedido(Cliente cliente, PedidoRepository pedidoRepository) throws MensagemException{	
		
		Pedido pedido = pedidoRepository.findByCliente(cliente)
				.orElse(new Pedido());
		
		pedido.setCliente(cliente);
		
		return pedido;
	}
	
	public ProdutoPedido toProduto(ProdutoRepository produtoRepository) throws MensagemException {

		ProdutoPedido produtoPedido = new ProdutoPedido();

		produtoPedido.setQuantidade(this.quantidade);

		Produto produto = produtoRepository.findById(this.id)
				.orElseThrow(() -> new MensagemException("Id do produto não encontrado!."));

		produtoPedido.setProduto(produto);
		produtoPedido.setPreco(produtoPedido.precoQuantidade());

		return produtoPedido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	

}
