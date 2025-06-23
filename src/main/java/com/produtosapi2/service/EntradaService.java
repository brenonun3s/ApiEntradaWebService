package com.produtosapi2.service;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.produtosapi2.api.ProdutoApiClient;
import com.produtosapi2.dto.EntradaDTO;
import com.produtosapi2.entities.Entrada;
import com.produtosapi2.exceptions.EntradaNaoLocalizadaException;
import com.produtosapi2.exceptions.ProdutoNaoLocalizadoException;
import com.produtosapi2.repositories.EntradaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EntradaService {

  @Inject
  EntradaRepository repository;

  @Inject
  @RestClient
  ProdutoApiClient apiClient;

  public List<Entrada> listar() {
    return repository.findAll().list();

  }

  public Entrada buscarEntradaPorId(Long id) {
    return repository.findByIdOptional(id)
        .orElseThrow(() -> new EntradaNaoLocalizadaException(id));
  }

  @Transactional
  public Entrada salvar(EntradaDTO dto) {
    try {
      apiClient.buscarProdutoPorId(dto.idProduto);
    } catch (NotFoundException e) {
      throw new ProdutoNaoLocalizadoException(dto.idProduto);
    }

    Entrada novaEntrada = new Entrada();

    novaEntrada.setIdProduto(dto.idProduto);
    novaEntrada.setQuantidadeEntrada(dto.quantidadeEntrada);
    novaEntrada.setDataEntrada(dto.dataEntrada);

    repository.persist(novaEntrada);

    return novaEntrada;
  }
}
