package org.serratec.dto.pedido;

import java.util.ArrayList;
import java.util.List;

import org.serratec.models.Endereco;
import org.serratec.models.Pedido;
import org.serratec.models.ProdutoPedido;

public class PedidoDetalhesDTO {

	private String email;
	private String nome;
	private List<PedidoProdutoDTO> produtos;
	private Double precoTotal;
	private Endereco endereco;
	
	public PedidoDetalhesDTO (Pedido pedido) {
		this.email = pedido.getCliente().getEmail();
		this.nome = pedido.getCliente().getNome();
		this.produtos = new ArrayList<>();
		
		for(ProdutoPedido pp : pedido.getProdutosPedido()) {
			this.produtos.add(new PedidoProdutoDTO(pp));
		}
		
		this.precoTotal = pedido.totalPedido();
		this.endereco = pedido.getCliente().getEndereco();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<PedidoProdutoDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<PedidoProdutoDTO> produtos) {
		this.produtos = produtos;
	}

	public Double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(Double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
}
