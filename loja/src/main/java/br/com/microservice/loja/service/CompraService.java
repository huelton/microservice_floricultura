package br.com.microservice.loja.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.microservice.loja.client.FornecedorClient;
import br.com.microservice.loja.client.TransportadorClient;
import br.com.microservice.loja.controller.dto.CompraDTO;
import br.com.microservice.loja.controller.dto.InfoEntregaDTO;
import br.com.microservice.loja.controller.dto.InfoFornecedorDTO;
import br.com.microservice.loja.controller.dto.InfoPedidoDTO;
import br.com.microservice.loja.controller.dto.VoucherDTO;
import br.com.microservice.loja.model.Compra;
import br.com.microservice.loja.repository.CompraRepository;

@Service
public class CompraService {

	@Autowired
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private TransportadorClient transportadorClient;
	
	@Autowired
	private CompraRepository compraRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	@HystrixCommand( threadPoolKey = "getByIdThreadPool")
	public Compra getById(Long id) {		
		return compraRepository.findById(id).orElse(new Compra());
	}

	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compraDTO) {

		InfoFornecedorDTO info = fornecedorClient.getInfoPorEstado(compraDTO.getEndereco().getEstado());

		InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compraDTO.getItens());
		
		InfoEntregaDTO entregaDto = new InfoEntregaDTO();
		entregaDto.setPedidoId(pedido.getId());
		entregaDto.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entregaDto.setEnderecoOrigem(info.getEndereco());
		entregaDto.setEnderecoDestino(compraDTO.getEndereco().toString());
		
		VoucherDTO voucher = transportadorClient.reservaEntrega(entregaDto);
		
		Compra compraSalva = new Compra();
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setEnderecoDestino(info.getEndereco().toString());
		compraSalva.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		compraSalva.setVoucher(voucher.getNumero());
		
		compraRepository.save(compraSalva);
		
        return compraSalva;
	}

	public Compra realizaCompraFallback(CompraDTO compraDTO) {
		
		Compra compraFallback = new Compra();		
		compraFallback.setEnderecoDestino(compraDTO.getEndereco().toString());		
		return compraFallback;
	}

}
