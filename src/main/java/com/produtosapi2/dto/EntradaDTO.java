package com.produtosapi2.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.produtosapi2.entities.Entrada;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EntradaDTO {

 @NotNull(message = "ID do produto á ser cadastrado é obrigatório")
 @Min(value = 1, message = "O ID do produto deve ser maior que zero")
 public Long idProduto;

 @NotNull(message = "Quantidade de entrada no estoque é obrigatório")
 @Min(value = 1, message = "Quantidade de produtos não pode ser menor que 1")
 public Integer quantidadeEntrada;

 public LocalDate dataEntrada;

 // Construtor para converter uma EntradaDTO em Entrada -> Mapper Manual
 public EntradaDTO(Entrada entrada) {
  this.idProduto = entrada.getIdProduto();
  this.quantidadeEntrada = Integer.valueOf(entrada.getQuantidadeEntrada());
  this.dataEntrada = entrada.getDataEntrada();
  }

}
