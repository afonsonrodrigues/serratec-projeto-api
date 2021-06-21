package org.serratec.resources;

import java.util.List;
import java.util.Optional;

import org.serratec.exceptions.MensagemException;
import org.serratec.models.Categoria;
import org.serratec.repository.CategoriaRepository;
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

@Api(value = "API de consulta de categorias")
@RestController
public class CategoriaResource {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@ApiOperation(value = "Pesquisa todas as categorias")
	@GetMapping("/categoria/todos")
	public ResponseEntity<?> getTodos(){
		List<Categoria> categorias = categoriaRepository.findAll();
		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Pesquisa a categoria pelo nome")
	@GetMapping("/categoria/por-nome/{nome}")
	public ResponseEntity<?> getPorNome(@PathVariable String nome) throws MensagemException{
		Categoria categoria = categoriaRepository.findByNome(nome).orElseThrow(() -> new MensagemException("Nome não encontrado"));
		return new ResponseEntity<>(categoria, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Adiciona uma categoria")
	@PostMapping("/categoria")
	public ResponseEntity<?> postCategoria(@RequestBody Categoria categoria) {
		
		categoriaRepository.save(categoria);
		return new ResponseEntity<>("Cadastrado com sucesso", HttpStatus.OK);
	}
	
	@ApiOperation(value = "Deleta uma categoria")
	@DeleteMapping("categoria/por-id/{id}")
    public ResponseEntity<?> deletePorId(@PathVariable long id) throws DataIntegrityViolationException, MensagemException {
		try {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new MensagemException("ID não encontrado"));
        categoriaRepository.delete(categoria);
        return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
        
		}catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Não foi possível deletar pois já existe outras entidades usando essa categoria.", HttpStatus.BAD_REQUEST);

		}
    }
	
	@ApiOperation(value = "Modifica uma categoria")
	@PutMapping("/categoria/por-id/{id}")
	public ResponseEntity<?> atualizarCategoria(@RequestBody Categoria modificado, @PathVariable Long id) {
		Optional<Categoria> categoriaParaAlterar = categoriaRepository.findById(id);
		
		if(categoriaParaAlterar.isEmpty())
			return new ResponseEntity<>("Categoria inexistente", HttpStatus.NOT_FOUND);
		
		Categoria categoriaAlterar = categoriaParaAlterar.get();
		
		categoriaAlterar.setNome(modificado.getNome());
		categoriaAlterar.setDescricao(modificado.getDescricao());
		
		categoriaRepository.save(categoriaAlterar);
		
		return new ResponseEntity<>("Categoria alterada com sucesso", HttpStatus.OK);
		
		
	}
	
}
