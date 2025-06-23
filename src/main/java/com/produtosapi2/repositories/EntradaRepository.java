package com.produtosapi2.repositories;

import com.produtosapi2.entities.Entrada;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntradaRepository implements PanacheRepository<Entrada> {

}
