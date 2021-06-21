package org.serratec.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProdutoPedido> produtosPedido = new ArrayList<>();
	
	@ManyToOne
	private Cliente cliente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<ProdutoPedido> getProdutosPedido() {
		return produtosPedido;
	}
	public void setProdutosPedido(List<ProdutoPedido> produtosPedido) {
		this.produtosPedido = produtosPedido;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Double totalPedido() {
		Double total = 0.0;
		for (ProdutoPedido p : produtosPedido) {
			total += p.precoQuantidade();
		}
		return total;
	}
}
