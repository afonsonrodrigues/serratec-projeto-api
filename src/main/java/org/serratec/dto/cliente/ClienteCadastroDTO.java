package org.serratec.dto.cliente;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.serratec.dto.endereco.EnderecoCadastroCompletoDTO;
import org.serratec.models.Cliente;
import org.serratec.models.Endereco;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ClienteCadastroDTO {

	@NotNull
	@NotBlank
	@Column(unique = true)
	private String email;
	
	@NotNull
	@NotBlank
	private String usuario;
	
	@NotNull
	@NotBlank
	private String senha;
	
	@NotNull
	@NotBlank
	private String nome;
	
	@NotNull
	@NotBlank
	@Column(length = 11, unique = true)
	private String cpf;
	private String telefone;
	private LocalDate dataNascimento;
	private String cep;
	private String numero;
	private String complemento;
	private boolean status;
	
	
	public Cliente toCliente() {
		
		Cliente cliente = new Cliente();
		
		cliente.setEmail(this.email);
		cliente.setUsuario(this.usuario);
		cliente.setNome(this.nome);
		cliente.setCpf(this.cpf);
		cliente.setTelefone(this.telefone);
		cliente.setDataNascimento(this.dataNascimento);
		cliente.setStatus(true);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCodificada = encoder.encode(this.senha);
		
		cliente.setSenha(senhaCodificada);
		
		EnderecoCadastroCompletoDTO enderecoCep = new EnderecoCadastroCompletoDTO();
		enderecoCep.setCep(this.cep);
		enderecoCep.setNumero(this.numero);
		enderecoCep.setComplemento(this.complemento);
		
		Endereco endereco = enderecoCep.toEndereco();
		
		cliente.setEndereco(endereco);
	
		return cliente;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


}
