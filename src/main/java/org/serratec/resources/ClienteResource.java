package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.serratec.dto.cliente.ClienteCadastroDTO;
import org.serratec.dto.cliente.ClienteDetalhesDTO;
import org.serratec.dto.cliente.ClienteStatusDTO;
import org.serratec.exceptions.MensagemException;
import org.serratec.models.Cliente;
import org.serratec.repository.ClienteRepository;
import org.serratec.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API de consulta de clientes")
@RestController
public class ClienteResource {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@ApiOperation(value = "Mostra as informações do cliente logado")
	@GetMapping("/cliente")
	public ResponseEntity<?> getCliente(){
		Cliente cliente = authenticationService.getCliente();	
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Pesquisa todos os clientes e mostra com dto")
	@GetMapping("/cliente/todos")
	public ResponseEntity<?> getTodos(){
		List<Cliente> clientes = clienteRepository.findAll();
		List<ClienteDetalhesDTO> dtos = new ArrayList<>();
		
		for(Cliente cliente : clientes)
			dtos.add(new ClienteDetalhesDTO(cliente));
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Adiciona um cliente")
	@PostMapping("/cliente")
	public ResponseEntity<?> postCliente(@Validated @RequestBody ClienteCadastroDTO dto){
		
		try {
		Cliente cliente = dto.toCliente();
		
		if(cliente.valida(cliente.getCpf())) {
			clienteRepository.save(cliente);	
			return new ResponseEntity<>("Cadastrado com sucesso", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("CPF inválido", HttpStatus.NOT_ACCEPTABLE);
		}
		

		}catch (ConstraintViolationException e) {	
			return new ResponseEntity<>("As informações inseridas são inválidas", HttpStatus.BAD_REQUEST);
		}catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Já existe um cliente com essas informações", HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Modifica um cliente por email")
	@PutMapping("/cliente/{email}")
	public ResponseEntity<?> atualizarCliente(@PathVariable String email, @RequestBody ClienteCadastroDTO dto) throws MensagemException{
			
			try {
				Cliente cliente = dto.toCliente();
				Cliente clienteAlterar = clienteRepository.findByEmail(email).orElseThrow(() -> new MensagemException("Cliente nao encontrado"));
				List<Cliente> clientes = clienteRepository.findAll();
				
				if(!cliente.getCpf().equals(clienteAlterar.getCpf()))
					return new ResponseEntity<>("Não pode alterar o cpf!", HttpStatus.NOT_ACCEPTABLE);
				
				for (Cliente c : clientes) {
					if(c.getId().equals(clienteAlterar.getId())) {
						if(!c.getEmail().equals(cliente.getEmail()))
							c.setEmail(cliente.getEmail());
						if(!c.getUsuario().equals(cliente.getUsuario()))
							c.setUsuario(cliente.getUsuario());		
						if(!c.getSenha().equals(cliente.getSenha()))
							c.setSenha(cliente.getSenha());
						if(!c.getNome().equals(cliente.getNome()))
							c.setNome(cliente.getNome());
						if(!c.getTelefone().equals(cliente.getTelefone()))
							c.setTelefone(cliente.getTelefone());
						if(!c.getDataNascimento().equals(cliente.getDataNascimento()))
							c.setDataNascimento(cliente.getDataNascimento());
						
						clienteRepository.save(c);
					}
				}
				
				return new ResponseEntity<>("Cliente alterado com sucesso", HttpStatus.OK);
				
				}catch (MensagemException e) {
					return new ResponseEntity<>("Não foi alterado", HttpStatus.BAD_REQUEST);
				}
			
			}
	
	@PutMapping("/cliente/status")
	public ResponseEntity<?> clienteStatus (@RequestBody ClienteStatusDTO dto) {
			Cliente cliente = authenticationService.getCliente();
			cliente.setStatus(dto.isStatus());
			clienteRepository.save(cliente);
			return new ResponseEntity<>("Status alterado com sucesso", HttpStatus.OK);
		}
	
	}
