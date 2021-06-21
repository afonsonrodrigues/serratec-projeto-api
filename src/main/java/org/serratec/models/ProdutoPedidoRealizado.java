package org.serratec.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProdutoPedidoRealizado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Produto produto;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	private PedidoRealizado pedidoRealizado;
	
	private int quantidade;
	private Double preco;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public PedidoRealizado getPedidoRealizado() {
		return pedidoRealizado;
	}
	public void setPedidoRealizado(PedidoRealizado pedidoRealizado) {
		this.pedidoRealizado = pedidoRealizado;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
}
