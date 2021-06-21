package org.serratec.dto.status;

import org.serratec.models.PedidoRealizado;
import org.serratec.models.StatusPedido;

public class PedidoStatusDTO {
	
	private StatusPedido status;
	
	public PedidoStatusDTO (PedidoRealizado pedido) {
		this.status = pedido.getStatus().get(pedido.getStatus().size() - 1).getStatus();
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}
	
	
}
