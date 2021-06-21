package org.serratec.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PedidoRealizado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String numeroPedido;
	
	@OneToMany(mappedBy = "pedidoRealizado", cascade = CascadeType.ALL)
	private List<ProdutoPedidoRealizado> produtosPedidoRealizado = new ArrayList<>();
	
	private Double valorTotal;
	
	private LocalDate dataPedido;
	
	@OneToMany(mappedBy = "pedidoRealizado", cascade = CascadeType.ALL)
	private List<PedidoStatus> status = new ArrayList<>();
	
	@ManyToOne
	private Cliente cliente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumeroPedido() {
		return numeroPedido;
	}
	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	public List<ProdutoPedidoRealizado> getProdutosPedidoRealizado() {
		return produtosPedidoRealizado;
	}
	public void setProdutosPedidoRealizado(List<ProdutoPedidoRealizado> produtosPedidoRealizado) {
		this.produtosPedidoRealizado = produtosPedidoRealizado;
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
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<PedidoStatus> getStatus() {
		return status;
	}
	public void setStatus(List<PedidoStatus> status) {
		this.status = status;
	}
	
	public String gerarNumeroPedido() {
		if (this.numeroPedido == null || this.numeroPedido.isBlank()) {

			LocalDateTime agora = LocalDateTime.now();
			Random randomico = new Random();
			String codigo = "";
			codigo += agora.getYear();
			codigo += agora.getMonthValue();
			codigo += agora.getDayOfMonth();
			codigo += agora.getHour();
			codigo += agora.getMinute();
			codigo += agora.getSecond();

			for (int i = 0; i < 5; i++) {
				codigo += randomico.nextInt(5);
			}
			this.numeroPedido = codigo;
		}

		return this.numeroPedido;
	}
	
}
