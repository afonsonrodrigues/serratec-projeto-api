package org.serratec.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PedidoStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	private PedidoRealizado pedidoRealizado;
	
	private LocalDateTime dataStatus;
	
	@Enumerated(EnumType.ORDINAL)
	private StatusPedido status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public PedidoRealizado getPedidoRealizado() {
		return pedidoRealizado;
	}

	public void setPedidoRealizado(PedidoRealizado pedidoRealizado) {
		this.pedidoRealizado = pedidoRealizado;
	}

	public LocalDateTime getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(LocalDateTime dataStatus) {
		this.dataStatus = dataStatus;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	
}
