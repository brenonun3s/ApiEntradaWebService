package com.produtosapi2.exceptions;

import jakarta.ws.rs.NotFoundException;

public class ProdutoNaoLocalizadoException extends NotFoundException {
 public ProdutoNaoLocalizadoException(Long id) {
     super("Produto não cadastrado com esse ID: " + id + "na base de dados! Gentileza usar a API de Produtos para cadastrar o produto antes de dar entrada no estoque!");
 }
}
