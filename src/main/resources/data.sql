INSERT INTO categoria (nome) VALUES ('Sobremesas');
INSERT INTO categoria (nome) VALUES ('Massas');
INSERT INTO categoria (nome) VALUES ('Bebidas');

INSERT INTO ingrediente (nome) VALUES ('açúcar');
INSERT INTO ingrediente (nome) VALUES ('água com gás');
INSERT INTO ingrediente (nome) VALUES ('vodka');
INSERT INTO ingrediente (nome) VALUES ('xarope de gengibre');
INSERT INTO ingrediente (nome) VALUES ('suco de limão');
INSERT INTO ingrediente (nome) VALUES ('gelo');
INSERT INTO ingrediente (nome) VALUES ('dente de alho');
INSERT INTO ingrediente (nome) VALUES ('manteiga');
INSERT INTO ingrediente (nome) VALUES ('óleo');
INSERT INTO ingrediente (nome) VALUES ('sal');
INSERT INTO ingrediente (nome) VALUES ('leite condensado');
INSERT INTO ingrediente (nome) VALUES ('achocolatado');
INSERT INTO ingrediente (nome) VALUES ('margarina sem sal');
INSERT INTO ingrediente (nome) VALUES ('chocolate granulado');

INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Macarrão ao alho e óleo', 'A presença do alho douradinho dá um sabor único a esse prato!', 30, 2);
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Moscow Mule', 'O drink original!', 15, 3);
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Brigadeiro', 'Enroladinho no granulado. O rei das festas!', 25, 1);

INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (1, 7, '5', 'unidades');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (1, 8, '1', 'colher(es) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (1, 9, '5', 'colher(es) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (1, 10, 'a gosto', 'sem unidade');

INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (2, 1, '1/2', 'colher(es) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (2, 2, '100', 'miligramas');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (2, 3, '50', 'miligramas');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (2, 4, '3', 'colher(es) de chá');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (2, 5, '1/2', 'sem unidade');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (2, 6, 'a gosto', 'sem unidade');

INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (3, 11, '1', 'caixa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (3, 12, '7', 'colher(res) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (3, 13, '1', 'colher(res) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida)
VALUES (3, 14, 'a gosto', 'sem unidade');