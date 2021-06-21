package org.serratec.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.serratec.dto.pedido.PedidoAtualizarProdutoDTO;
import org.serratec.dto.pedido.PedidoAtualizarStatusDTO;
import org.serratec.dto.pedido.PedidoDetalhesDTO;
import org.serratec.dto.pedido.PedidoFinalizarDTO;
import org.serratec.dto.pedido.PedidoRealizadoDetalhesDTO;
import org.serratec.exceptions.MensagemException;
import org.serratec.models.Cliente;
import org.serratec.models.Pedido;
import org.serratec.models.PedidoRealizado;
import org.serratec.models.PedidoStatus;
import org.serratec.models.ProdutoPedido;
import org.serratec.models.ProdutoPedidoRealizado;
import org.serratec.models.StatusPedido;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.PedidoRealizadoRepository;
import org.serratec.repository.PedidoRepository;
import org.serratec.repository.ProdutoPedidoRepository;
import org.serratec.repository.ProdutoRepository;
import org.serratec.security.service.AuthenticationService;
import org.serratec.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API de consulta de pedidos")
@RestController
public class PedidoResource {
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ProdutoPedidoRepository produtoPedidoRepository;
	
	@Autowired
	PedidoRealizadoRepository pedidoRealizadoRepository;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	EmailService emailService;
	
	@ApiOperation(value = "Pesquisa todos os pedidos e mostra com dto")
	@GetMapping("/pedido/todos")
	public ResponseEntity<?> getTodos(){
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoDetalhesDTO> dtos = new ArrayList<>();
		
		for(Pedido pedido : pedidos)
			dtos.add(new PedidoDetalhesDTO(pedido));
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Pesquisa todos os pedidos realizados")
	@GetMapping("/pedido-realizado/todos")
	public ResponseEntity<?> getTodosFinalizados(){
		List<PedidoRealizado> pedidos = pedidoRealizadoRepository.findAll();
		List<PedidoRealizadoDetalhesDTO> dtos = new ArrayList<>();
		
		for(PedidoRealizado pedidoRealizado : pedidos) {
			dtos.add(new PedidoRealizadoDetalhesDTO(pedidoRealizado));
		}
			
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@GetMapping("/pedido")
	public ResponseEntity<?> getDetalhes () throws MensagemException{
		
		try {
			
		Cliente cliente = authenticationService.getCliente();
		Pedido pedido = pedidoRepository.findByCliente(cliente).orElseThrow(() -> new MensagemException("Venda não encontrada!"));
		PedidoDetalhesDTO dto = new PedidoDetalhesDTO(pedido);
					
		return new ResponseEntity<>(dto, HttpStatus.OK);
			
		} catch (MensagemException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@ApiOperation(value = "Adiciona um pedido ou modifica um pedido")
	@PutMapping("/pedido")
	public ResponseEntity<?> atualizarPedido(@RequestBody PedidoAtualizarProdutoDTO dto){
		
		try {
			
			Cliente cliente = authenticationService.getCliente();
			Pedido pedido = dto.toPedido(cliente, pedidoRepository);
			
			for (ProdutoPedido i : pedido.getProdutosPedido()) {
				if (i.getProduto().getId().equals(dto.getId())) {
					if (dto.getQuantidade() == 0) {
						pedido.getProdutosPedido().remove(i);	
					}else {
						i.setQuantidade(dto.getQuantidade());
						i.setPreco(dto.getQuantidade() * i.getProduto().getPreco());
					}
					
					if(pedido.getProdutosPedido().isEmpty()) {
						pedidoRepository.delete(pedido);
						return new ResponseEntity<>("Sem pedido", HttpStatus.OK);
					} else {
						pedidoRepository.save(pedido);
						return new ResponseEntity<>("Pedido atualizado com sucesso!", HttpStatus.OK);
					}	
				}
			}
			
			ProdutoPedido produtoPedido = dto.toProduto(produtoRepository);
			produtoPedido.setPedido(pedido);
			produtoPedidoRepository.save(produtoPedido);
			
			pedido.getProdutosPedido().add(produtoPedido);
			pedidoRepository.save(pedido);
			
			return new ResponseEntity<>("Item adicionado com sucesso.", HttpStatus.OK);
			
		}catch (MensagemException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}	
	
	@ApiOperation(value = "Finaliza um pedido")
	@PostMapping("/pedido/finalizar")
	public ResponseEntity<?> finalizarPedido(@RequestBody PedidoFinalizarDTO dto){
		
		try {
			
		Cliente cliente = authenticationService.getCliente();
		Pedido pedido = dto.toPedido(pedidoRepository, cliente);
		PedidoRealizado realizado = new PedidoRealizado();						
		realizado.setCliente(pedido.getCliente());
				
			for (ProdutoPedido p : pedido.getProdutosPedido()) {
				ProdutoPedidoRealizado produtos = new ProdutoPedidoRealizado();
				produtos.setPedidoRealizado(realizado);
				produtos.setProduto(p.getProduto());
				produtos.setQuantidade(p.getQuantidade());
				produtos.setPreco(p.getPreco());
				realizado.getProdutosPedidoRealizado().add(produtos);
			}
				
		realizado.setNumeroPedido(realizado.gerarNumeroPedido());	
		realizado.setValorTotal(pedido.totalPedido());
		realizado.setDataPedido(LocalDate.now());
						
		PedidoStatus novo = new PedidoStatus();
		novo.setPedidoRealizado(realizado);
		novo.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
		novo.setDataStatus(LocalDateTime.now());
		
		realizado.getStatus().add(novo);
		
		pedidoRealizadoRepository.save(realizado);
        pedidoRepository.deleteById(pedido.getId());
						
		return new ResponseEntity<>("Pedido Finalizado com Sucesso!!!.", HttpStatus.OK);
				
		} catch(MensagemException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@ApiOperation(value = "Modifica os status do pedido")
	@PutMapping("/pedido/status")
	public ResponseEntity<?> putStatus(@RequestBody PedidoAtualizarStatusDTO dto) {

        try {
            PedidoRealizado pedido = pedidoRealizadoRepository.findByNumeroPedido(dto.getNumeroPedido()).orElseThrow(() -> new MensagemException("Venda não encontrada!"));

            PedidoStatus corrente = pedido.getStatus().get(pedido.getStatus().size() - 1);

            PedidoStatus novo = new PedidoStatus();
            novo.setPedidoRealizado(pedido);
            novo.setStatus(dto.getStatus());
            novo.setDataStatus(LocalDateTime.now());
            pedido.getStatus().add(novo);
            
            pedidoRealizadoRepository.save(pedido);
            
            if(novo.getStatus().equals(StatusPedido.FINALIZADO)) {
            	emailService.enviar("Atualização do status do pedido",
            			"Status da venda alterado de " + corrente.getStatus() + " para " + novo.getStatus() + "\nSeu pedido " + pedido.getNumeroPedido() + " será entregue em sua casa em até 10 dias úteis.\n"
            			+ "Produtos: \n\n" + pedido.getProdutosPedidoRealizado().toString() + "\n Valor total: " + pedido.getValorTotal(),
            			pedido.getCliente().getEmail());
            	
            }else {
            	emailService.enviar("Atualização do status do pedido","Status da venda alterado de " + corrente.getStatus() + " para " + novo.getStatus(), pedido.getCliente().getEmail());
            }
            
            return new ResponseEntity<>(
                    "Status do Pedido alterado de " + corrente.getStatus() + " para " + novo.getStatus(), HttpStatus.OK);

        } catch (MensagemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
	
	@ApiOperation(value = "Deleta um pedido")
	@DeleteMapping("pedido/por-id/{id}")
    public ResponseEntity<?> deletePorId(@PathVariable long id) throws DataIntegrityViolationException, MensagemException {
		
		try {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new MensagemException("ID não encontrado"));
        pedidoRepository.delete(pedido);
        return new ResponseEntity<>("O pedido foi deletado com sucesso.", HttpStatus.OK);	
        
		}catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Não foi possível deletar pois já existe outras entidades usando esse pedido.", HttpStatus.BAD_REQUEST);	
		}
    }
	
	
	
	
}
