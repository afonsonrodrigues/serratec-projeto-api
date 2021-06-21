package org.serratec.dto.cliente;

import java.time.LocalDate;

import org.serratec.models.Cliente;
import org.serratec.models.Endereco;

public class ClienteDetalhesDTO {

	private String email;
	private String nome;
	private String telefone;
	private LocalDate dataNascimento;
	private Endereco endereco;
	
	public ClienteDetalhesDTO(Cliente cliente) {
		this.email = cliente.getEmail();
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
		this.dataNascimento = cliente.getDataNascimento();
		this.endereco = cliente.getEndereco();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
	
}
