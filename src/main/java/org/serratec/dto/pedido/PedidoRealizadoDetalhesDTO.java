package org.serratec.dto.pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.dto.status.PedidoStatusDTO;
import org.serratec.models.Endereco;
import org.serratec.models.PedidoRealizado;
import org.serratec.models.ProdutoPedidoRealizado;

public class PedidoRealizadoDetalhesDTO {
	
	private String numeroPedido;
	private	List<PedidoRealizadoProdutoDTO> produtos;
	private Double valorTotal;
	private LocalDate dataPedido;
	private PedidoStatusDTO status;
	private String email;
	private String nome;
	private String telefone;
	private Endereco endereco;
	
	public PedidoRealizadoDetalhesDTO (PedidoRealizado pedidoRealizado) {
		this.numeroPedido = pedidoRealizado.getNumeroPedido();
		this.produtos = new ArrayList<>();
		
		for(ProdutoPedidoRealizado pp : pedidoRealizado.getProdutosPedidoRealizado()) {
			this.produtos.add(new PedidoRealizadoProdutoDTO(pp));
		}
		this.valorTotal = pedidoRealizado.getValorTotal();
		this.dataPedido = pedidoRealizado.getDataPedido();
		this.status = new PedidoStatusDTO(pedidoRealizado);
		this.email = pedidoRealizado.getCliente().getEmail();
		this.nome = pedidoRealizado.getCliente().getNome();
		this.telefone = pedidoRealizado.getCliente().getTelefone();
		this.endereco = pedidoRealizado.getCliente().getEndereco();
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public List<PedidoRealizadoProdutoDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<PedidoRealizadoProdutoDTO> produtos) {
		this.produtos = produtos;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public PedidoStatusDTO getStatus() {
		return status;
	}

	public void setStatus(PedidoStatusDTO status) {
		this.status = status;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
}
