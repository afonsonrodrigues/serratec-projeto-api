package org.serratec.resources;

import java.util.List;

import org.serratec.dto.endereco.EnderecoCadastroCompletoDTO;
import org.serratec.models.Endereco;
import org.serratec.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API de consulta de endereços")
@RestController
public class EnderecoResource {
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@ApiOperation(value = "Pesquisa todos os endereços")
	@GetMapping("/endereco/todos")
	public ResponseEntity<?> getTodos(){
		List<Endereco> enderecos = enderecoRepository.findAll();
		return new ResponseEntity<>(enderecos, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Adiciona um endereço")
	@PostMapping("/endereco")
	public ResponseEntity<?> postEndereco(@RequestBody EnderecoCadastroCompletoDTO dto) {
		
		Endereco endereco = dto.toEndereco();
		
		enderecoRepository.save(endereco);
		
		return new ResponseEntity<>("Cadastrado com sucesso", HttpStatus.OK);
	}
}
