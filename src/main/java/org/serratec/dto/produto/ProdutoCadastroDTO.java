package org.serratec.dto.produto;

import java.time.LocalDate;

import org.apache.tomcat.util.codec.binary.Base64;
import org.serratec.models.Categoria;
import org.serratec.models.Produto;

public class ProdutoCadastroDTO {

	private String nome;
	private String descricao;	
	private Double preco;
	private int quantidadeEstoque;
	private LocalDate dataCadastroProduto;
	private Categoria categoria;
	private byte[] img;
	
	public Produto toProduto() {
		
		Produto produto = new Produto();
		
		produto.setNome(this.nome);
		produto.setDescricao(this.descricao);
		produto.setPreco(this.preco);
		produto.setQuantidadeEstoque(this.quantidadeEstoque);
		produto.setDataCadastroProduto(this.dataCadastroProduto);
		produto.setCategoria(this.categoria);
		
		if(img != null) {
			byte[] imagem = Base64.decodeBase64(img);
			produto.setImg(imagem);	
		}
		
		return produto;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public int getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(int quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	public LocalDate getDataCadastroProduto() {
		return dataCadastroProduto;
	}
	public void setDataCadastroProduto(LocalDate dataCadastroProduto) {
		this.dataCadastroProduto = dataCadastroProduto;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
