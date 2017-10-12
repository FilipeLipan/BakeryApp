package com.github.filipelipan.bakeryapp.common;

import com.github.filipelipan.bakeryapp.util.rx.RxHttpError;

/**
 * Created by jonathan on 13/10/16.
 *
 * Contrato de tratamento de erro para o app
 *
 */
public interface IErrorHandlerView {
    /**
     * Quando ocorrer um erro de Http
     * Onde as propriedades de erro estao definidas pela resposta do servidor
     * @param error
     */
    void error(RxHttpError error);

    /**
     * Para erros nao tratados
     * Error generico, geralmente qnd ocorre crash do app ex: parse de objeto errado
     * @param e
     */
    void error(Throwable e);
}