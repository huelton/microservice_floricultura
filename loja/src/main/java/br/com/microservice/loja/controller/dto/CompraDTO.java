package br.com.microservice.loja.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CompraDTO {

	@JsonIgnore
	private Long compraId;
	
	private List<ItemDaCompraDTO> itens;
	
	private EnderecoDTO endereco;
}
