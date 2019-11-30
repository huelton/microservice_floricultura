package br.com.microservice.loja.controller.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CompraDTO {

	private List<ItemDaCompraDTO> itens;
	
	private EnderecoDTO endereco;
}
