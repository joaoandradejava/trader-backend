truncate table acao restart identity cascade;
truncate table trader restart identity cascade;
truncate table perfil restart identity cascade;
truncate table item_acao restart identity cascade;

insert into acao (nome, preco) values
('BMW', 179), ('Google', 58), ('Apple', 171), ('Twitter', 13);

insert into trader(nome, email, senha, saldo) values
('João Andrade', 'john@gmail.com', '$2a$10$liJUmBjXY4GmPjEw50lz9OM4dldXdY7s1zXB5ch1Jb/ZD/MEQZ6m6', 1000), ('Leonardo Leitão', 'leo@gmail.com', '$2a$10$liJUmBjXY4GmPjEw50lz9OM4dldXdY7s1zXB5ch1Jb/ZD/MEQZ6m6', 1000);

insert into perfil(trader_id, perfis) values
(1, 'TRADER'), (1, 'ADMIN'),
(2, 'TRADER');

insert into item_acao(acao_id, trader_id, quantidade) values
(1, 1, 5), (2, 1, 3), (3, 1, 5),
(1, 2, 3);