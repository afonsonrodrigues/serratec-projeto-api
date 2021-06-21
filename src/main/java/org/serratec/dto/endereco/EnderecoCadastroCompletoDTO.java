package org.serratec.dto.endereco;

import org.serratec.models.Endereco;
import org.springframework.web.client.RestTemplate;

public class EnderecoCadastroCompletoDTO {

	private String cep;
	private String numero;
	private String complemento;

	public Endereco toEndereco() {
		
	    Endereco endereco = new Endereco();
	    
	    endereco.setCep(this.cep);
	    endereco.setNumero(this.numero);
	    endereco.setComplemento(this.complemento);
	    
		String uri = "https://viacep.com.br/ws/" + endereco.getCep() + "/json/";

	    RestTemplate rest = new RestTemplate();
	    EnderecoViaCepDTO viaCep = rest.getForObject(uri, EnderecoViaCepDTO.class);
	    
	    endereco.setRua(viaCep.getLogradouro());
	    endereco.setBairro(viaCep.getBairro());
	    endereco.setCidade(viaCep.getLocalidade());
	    endereco.setEstado(viaCep.getUf());
	    
	    return endereco;    
	    
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



	
}
