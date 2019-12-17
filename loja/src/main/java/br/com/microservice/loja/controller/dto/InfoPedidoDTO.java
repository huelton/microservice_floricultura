package br.com.microservice.loja.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class InfoPedidoDTO {

	private Long id;
	
	private Integer tempoDePreparo;
	
	
}
