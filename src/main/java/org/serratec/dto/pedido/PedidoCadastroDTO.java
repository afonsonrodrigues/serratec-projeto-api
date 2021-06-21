package org.serratec.dto.pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.serratec.models.Cliente;
import org.serratec.models.ProdutoPedido;

public class PedidoCadastroDTO {
	
	private String numeroPedido;
	
	@OneToMany(mappedBy = "pedido")
	private List<ProdutoPedido> produtosPedido = new ArrayList<>();
	private Double valorTotal;
	private LocalDate dataPedido;
	private String status;
	
	@ManyToOne
	private Cliente cliente;

	
	
	
	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public List<ProdutoPedido> getProdutosPedido() {
		return produtosPedido;
	}

	public void setProdutosPedido(List<ProdutoPedido> produtosPedido) {
		this.produtosPedido = produtosPedido;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
