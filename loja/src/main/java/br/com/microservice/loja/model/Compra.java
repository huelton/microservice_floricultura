package br.com.microservice.loja.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class Compra {

	private Long pedidoId;
	
	private Integer tempoDePreparo;
	
	private String enderecoDestino;
}
