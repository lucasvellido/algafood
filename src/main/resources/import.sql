insert into estados (id, nome) values (1, 'São Paulo');
insert into cidades (id, nome, estado_id) values (1, 'Campinas', 1);

insert into cozinhas (id, nome) values (1, 'Tailandesa');
insert into cozinhas (id, nome) values (2, 'Indiana');

insert into restaurantes (endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, nome, taxa_frete, cozinha_id, endereco_cidade_id, data_cadastro, data_atualizacao) values ('flamboyant', '13092305', 'sem complemento', 'av. josé bonifacio', '2656', 'Thai Gourmet', 0.00, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into restaurantes (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Thai Delivery', 9.5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into restaurantes (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Tuk tuk Comida Indiana', 15, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into forma_pagamentos (id, nome) values (1, 'Cartão de credito');
insert into forma_pagamentos (id, nome) values (2, 'Cartão de debito');
insert into forma_pagamentos (id, nome) values (3, 'Dinheiro');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2, 3);

insert into produtos (id, ativo, descricao, nome, preco, restaurante_id) values (1, true, 'seco', 'tomate', 2.50, 1);
