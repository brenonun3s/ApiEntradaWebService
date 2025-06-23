package com.produtosapi2.exceptions;



import jakarta.ws.rs.NotFoundException;

public class EntradaNaoLocalizadaException extends NotFoundException {
    public EntradaNaoLocalizadaException(Long id) {
        super("Entrada não encontrado com esse ID: " + id);
    }
}