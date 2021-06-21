package org.serratec.dto.pedido;

import org.serratec.exceptions.MensagemException;
import org.serratec.models.Cliente;
import org.serratec.models.Pedido;
import org.serratec.repository.PedidoRepository;

public class PedidoFinalizarDTO {
	
	private String email;
	
	public Pedido toPedido(PedidoRepository pedidoRepository, Cliente cliente) throws MensagemException {

		Pedido pedido = pedidoRepository.findByCliente(cliente)
				.orElse(new Pedido());
		
		return pedido;
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	


	
	

}
