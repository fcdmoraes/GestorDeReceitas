-- --------------------------------------------------------
-- Tabela: categoria
-- --------------------------------------------------------
INSERT INTO categoria (nome) VALUES ('Sobremesas');
INSERT INTO categoria (nome) VALUES ('Massas');
INSERT INTO categoria (nome) VALUES ('Bebidas');
INSERT INTO categoria (nome) VALUES ('Sopas');
INSERT INTO categoria (nome) VALUES ('Carnes');
INSERT INTO categoria (nome) VALUES ('Saladas');

-- --------------------------------------------------------
-- Tabela: ingrediente
-- --------------------------------------------------------
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
INSERT INTO ingrediente (nome) VALUES ('tomate');
INSERT INTO ingrediente (nome) VALUES ('cebola');
INSERT INTO ingrediente (nome) VALUES ('manjericão fresco');
INSERT INTO ingrediente (nome) VALUES ('creme de leite');
INSERT INTO ingrediente (nome) VALUES ('frango desfiado');
INSERT INTO ingrediente (nome) VALUES ('batata');
INSERT INTO ingrediente (nome) VALUES ('azeite de oliva extra virgem');
INSERT INTO ingrediente (nome) VALUES ('pimenta do reino');
INSERT INTO ingrediente (nome) VALUES ('alface americana');
INSERT INTO ingrediente (nome) VALUES ('peito de frango');
INSERT INTO ingrediente (nome) VALUES ('limão');
INSERT INTO ingrediente (nome) VALUES ('mel');
INSERT INTO ingrediente (nome) VALUES ('mostarda dijon');
INSERT INTO ingrediente (nome) VALUES ('café expresso');
INSERT INTO ingrediente (nome) VALUES ('queijo mascarpone');
INSERT INTO ingrediente (nome) VALUES ('biscoito champagne');
INSERT INTO ingrediente (nome) VALUES ('cacau em pó');
INSERT INTO ingrediente (nome) VALUES ('arroz');
INSERT INTO ingrediente (nome) VALUES ('carne seca');
INSERT INTO ingrediente (nome) VALUES ('pimentão');
INSERT INTO ingrediente (nome) VALUES ('vinho tinto');
INSERT INTO ingrediente (nome) VALUES ('laranja');
INSERT INTO ingrediente (nome) VALUES ('cenoura');

-- --------------------------------------------------------
-- Tabela: receita
-- --------------------------------------------------------
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Macarrão ao alho e óleo', 'A presença do alho douradinho dá um sabor único a esse prato!', 30, 2);
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Moscow Mule', 'O drink original!', 15, 3);
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Brigadeiro', 'Enroladinho no granulado. O rei das festas!', 25, 1);
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Sopa Creme de Tomate', 'Uma sopa cremosa e saborosa, perfeita para dias frios.', 45, 4);
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Frango Grelhado com Salada', 'Um prato leve e saudável.', 20, 5);
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Salada Caesar Simples', 'Clássica e refrescante, com molho caseiro.', 25, 6);
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Tiramisu Clássico', 'Sobremesa italiana, cremosa e com sabor intenso de café.', 40, 1);
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Risoto de Carne Seca', 'Um risoto cremoso e muito saboroso, ideal para o prato principal.', 50, 2);
INSERT INTO receita (nome, descricao, tempo_de_preparo, categoria_id)
VALUES ('Caipirinha de Laranja', 'Um clássico brasileiro, fácil de preparar e refrescante.', 10, 3);

-- --------------------------------------------------------
-- Tabela: receita_ingrediente
-- --------------------------------------------------------

-- Macarrão ao alho e óleo (ID: 1)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (1, 7, '5', 'unidades');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (1, 8, '1', 'colher(es) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (1, 9, '5', 'colher(es) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (1, 10, 'a gosto', 'sem unidade');

-- Moscow Mule (ID: 2)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (2, 1, '1/2', 'colher(es) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (2, 2, '100', 'miligramas');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (2, 3, '50', 'miligramas');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (2, 4, '3', 'colher(es) de chá');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (2, 5, '1/2', 'sem unidade');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (2, 6, 'a gosto', 'sem unidade');

-- Brigadeiro (ID: 3)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (3, 11, '1', 'caixa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (3, 12, '7', 'colher(res) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (3, 13, '1', 'colher(res) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (3, 14, 'a gosto', 'sem unidade');

-- Sopa Creme de Tomate (ID: 4)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (4, 15, '1', 'quilograma');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (4, 16, '1/2', 'unidade');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (4, 17, 'a gosto', 'folhas');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (4, 18, '200', 'mililitros');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (4, 10, 'a gosto', 'sem unidade');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (4, 22, 'a gosto', 'sem unidade');

-- Frango Grelhado com Salada (ID: 5)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (5, 24, '2', 'filetes');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (5, 23, '1', 'pé');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (5, 10, 'a gosto', 'sem unidade');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (5, 22, 'a gosto', 'sem unidade');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (5, 9, '1', 'colher(es) de sopa');

-- Salada Caesar Simples (ID: 6)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (6, 23, '1', 'pé');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (6, 25, '1/2', 'unidade');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (6, 26, '1', 'colher(es) de chá');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (6, 27, '1', 'colher(es) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (6, 21, '3', 'colher(es) de sopa');

-- Tiramisu Clássico (ID: 7)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (7, 28, '200', 'mililitros');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (7, 29, '500', 'gramas');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (7, 30, '250', 'gramas');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (7, 31, 'a gosto', 'sem unidade');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (7, 1, '100', 'gramas');

-- Risoto de Carne Seca (ID: 8)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (8, 32, '2', 'xícaras (chá)');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (8, 33, '300', 'gramas');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (8, 16, '1/2', 'unidade');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (8, 7, '2', 'dentes');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (8, 35, '1/2', 'xícara (chá)');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (8, 8, '1', 'colher(es) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (8, 10, 'a gosto', 'sem unidade');

-- Caipirinha de Laranja (ID: 9)
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (9, 36, '2', 'unidades');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (9, 1, '2', 'colher(es) de sopa');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (9, 3, '50', 'mililitros');
INSERT INTO receita_ingrediente (receita_id, ingrediente_id, quantidade, unidade_medida) VALUES (9, 6, 'a gosto', 'sem unidade');