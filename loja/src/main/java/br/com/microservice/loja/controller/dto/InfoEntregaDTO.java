package br.com.microservice.loja.controller.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfoEntregaDTO {

	private Long pedidoId;

	private LocalDate dataParaEntrega;

	private String enderecoOrigem;

	private String enderecoDestino;
}
