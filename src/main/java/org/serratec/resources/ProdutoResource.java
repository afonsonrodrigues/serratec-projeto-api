package org.serratec.resources;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.serratec.dto.produto.ProdutoCadastroDTO;
import org.serratec.exceptions.MensagemException;
import org.serratec.models.Produto;
import org.serratec.repository.ProdutoRepository;
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

@Api(value = "API de consulta de produtos")
@RestController
public class ProdutoResource {

	@Autowired
	ProdutoRepository produtoRepository;
	
	@ApiOperation(value = "Pesquisa todos os produtos")
	@GetMapping("/produto/todos")
	public ResponseEntity<?> getTodos(){
		List<Produto> produtos = produtoRepository.findAll();
		return new ResponseEntity<>(produtos, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Pesquisa o produto por nome")
	@GetMapping("/produto/por-nome/{nome}")
	public ResponseEntity<?> getPorNome(@PathVariable String nome) throws MensagemException{
		Produto produto = produtoRepository.findByNome(nome).orElseThrow(() -> new MensagemException("Nome não encontrado"));
		return new ResponseEntity<>(produto, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Adiciona um produto")
	@PostMapping("/produto")
	public ResponseEntity<?> postCliente(@RequestBody ProdutoCadastroDTO dto) {
		
		try {
		Produto produto = dto.toProduto();
		produtoRepository.save(produto);
		
		return new ResponseEntity<>("Cadastrado com sucesso", HttpStatus.OK);	
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(new MensagemException("Erro nas informações inseridas"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Modifica um produto")
	@PutMapping("/produto/por-id/{id}")
	public ResponseEntity<?> atualizarProduto(@RequestBody Produto modificado, @PathVariable Long id) throws MensagemException {
		
		Produto produtoAlterar = produtoRepository.findById(id).orElseThrow(() -> new MensagemException("ID não encontrado")); 	
		
		produtoAlterar.setNome(modificado.getNome());
		produtoAlterar.setDescricao(modificado.getDescricao());
		produtoAlterar.setPreco(modificado.getPreco());
		produtoAlterar.setDataCadastroProduto(modificado.getDataCadastroProduto());
		produtoAlterar.setCategoria(modificado.getCategoria());
		produtoAlterar.setImg(modificado.getImg());
		
		produtoRepository.save(produtoAlterar);
		
		return new ResponseEntity<>("Produto alterado com sucesso", HttpStatus.OK);
			
	}
	
	@ApiOperation(value = "Deleta um produto por id")
	@DeleteMapping("produto/por-id/{id}")
    public ResponseEntity<?> deletePorId(@PathVariable long id) throws DataIntegrityViolationException, MensagemException {
		try {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new MensagemException("ID não encontrado"));   
        produtoRepository.delete(produto);
        return new ResponseEntity<>("Produto deletado com sucesso", HttpStatus.OK);
        
		}catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Não foi possível deletar pois já existe outras entidades usando essa categoria.", HttpStatus.OK);
		}
    }
}
