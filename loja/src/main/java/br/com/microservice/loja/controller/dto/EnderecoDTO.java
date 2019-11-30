package br.com.microservice.loja.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class EnderecoDTO {

	private String rua;
	private int numero;
	private String estado;
}
