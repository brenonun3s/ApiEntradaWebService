CREATE TABLE tb_entradas (
    id SERIAL PRIMARY KEY,
    id_produto BIGINT NOT NULL,
    quantidade_entrada INTEGER NOT NULL,
    data_entrada TIMESTAMP NOT NULL
);
