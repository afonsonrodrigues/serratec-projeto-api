package org.serratec.dto.pedido;

import org.serratec.models.StatusPedido;

public class PedidoAtualizarStatusDTO {
	private String numeroPedido;
	private StatusPedido status;
	
	
	
	public String getNumeroPedido() {
		return numeroPedido;
	}
	public StatusPedido getStatus() {
		return status;
	}
	
	

}
