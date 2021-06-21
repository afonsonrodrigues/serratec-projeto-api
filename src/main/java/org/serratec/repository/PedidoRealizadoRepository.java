package org.serratec.repository;

import java.util.Optional;

import org.serratec.models.PedidoRealizado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRealizadoRepository extends JpaRepository<PedidoRealizado, Long> {

	Optional<PedidoRealizado> findByNumeroPedido(String numeroPedido);

}
