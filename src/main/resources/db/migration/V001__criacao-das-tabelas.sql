    
    create table acao (
       id int8 generated by default as identity,
        nome varchar(255) not null unique,
        preco numeric(19, 2) not null,
        primary key (id)
    );
    
    create table item_acao (
       quantidade int4 not null,
        acao_id int8 not null,
        trader_id int8 not null,
        primary key (acao_id, trader_id)
    );
    
    create table perfil (
       trader_id int8 not null,
        perfis varchar(6) not null
    );
    
    create table trader (
       id int8 generated by default as identity,
        email varchar(255) not null unique,
        nome varchar(255) not null,
        saldo numeric(19, 2) not null,
        senha varchar(255) not null,
        primary key (id)
    );
    
    alter table if exists item_acao 
       add constraint fk_item_acao_acao_id 
       foreign key (acao_id) 
       references acao;
    
    alter table if exists item_acao 
       add constraint fk_item_acao_trader_id 
       foreign key (trader_id) 
       references trader;
    
    alter table if exists perfil 
       add constraint fk_perfil_trader_id 
       foreign key (trader_id) 
       references trader;