package com.produtosapi2.api;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.produtosapi2.dto.ProdutoResponseDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient(configKey = "api-produtos")
@Path("/produtos")
public interface ProdutoApiClient {

 @GET
 @Path("/{id}")
 ProdutoResponseDTO buscarProdutoPorId(@PathParam("id") Long id);

}
