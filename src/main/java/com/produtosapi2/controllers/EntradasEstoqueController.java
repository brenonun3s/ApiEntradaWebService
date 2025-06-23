package com.produtosapi2.controllers;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.produtosapi2.api.ProdutoApiClient;
import com.produtosapi2.dto.EntradaDTO;
import com.produtosapi2.entities.Entrada;
import com.produtosapi2.repositories.EntradaRepository;
import com.produtosapi2.service.EntradaService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@Path("/api")
@Produces("application/json")
@Consumes("application/json")
@Tag(name = "Entradas", description = "Operações de gerenciamento de entradas dos produtos no estoque")
public class EntradasEstoqueController {

  @Inject
  EntradaService service;

  @Inject
  EntradaRepository repository;

  @Inject
  @RestClient
  ProdutoApiClient apiClient;

  @GET
  @Path("/entradas")
  @Operation(summary = "Listar todos as entradas de produtos efetuadas")
  @APIResponse(responseCode = "200", description = "Lista de entradas efetuadas")
  @APIResponse(responseCode = "204", description = "Nenhuma entrada encontrada")
  public Response listarEntradas() {

    List<Entrada> listaDeEntradas = service.listar();

    if (listaDeEntradas.isEmpty()) {
      return Response.noContent().build();
    }
    return Response.ok(listaDeEntradas).build();
  }

  @GET
  @Path("/{id}")
  @Operation(summary = "Pesquisar entrada de produtos por ID")
  @APIResponse(responseCode = "200", description = "Entrada encontrada")
  @APIResponse(responseCode = "404", description = "Entrada não encontrada")
  public Entrada listarEntradaPorId(@PathParam("id") Long id) {
    return service.buscarEntradaPorId(id);

  }

  @POST
  @Path("/nova-entrada")
  @Transactional
  @Operation(summary = "Efetuar uma nova entrada")
  @APIResponse(responseCode = "201", description = "Entrada efetuada no sistema")
  @APIResponse(responseCode = "400", description = "Dados de entrada inválidos")
  @APIResponse(responseCode = "404", description = "Produto não existe na base, não permitindo o cadastro!")
  public Response cadastrarEntrada(@Valid EntradaDTO dto, @Context UriInfo uriInfo) {
    try {

      apiClient.buscarProdutoPorId(dto.idProduto);

      Entrada entrada = service.salvar(dto);

      UriBuilder builder = uriInfo.getAbsolutePathBuilder()
          .path(Long.toString(entrada.getId()));

      return Response.created(builder.build())
          .entity(entrada)
          .build();

    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Produto com ID " + dto.idProduto + " não encontrado na API de produtos.")
          .build();

    } catch (ValidationException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Erro de validação: " + e.getMessage())
          .build();

    } catch (jakarta.ws.rs.WebApplicationException e) {
      return Response.status(e.getResponse().getStatus())
          .entity("Erro ao buscar produto: " + e.getMessage())
          .build();
  }
  }

}
