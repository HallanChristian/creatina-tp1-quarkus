insert into faixa (nome, preco) values('Faixa Branca', 100.0);
insert into faixa (nome, preco) values('Faixa Azul', 110.0);
insert into faixa (nome, preco) values('Faixa Roxa', 120.0);
insert into faixa (nome, preco) values('Faixa Maron', 130.0);
insert into faixa (nome, preco) values('Faixa Preta', 140.0);

insert into estado (nome, sigla) values('Tocantins', 'TO');
insert into estado (nome, sigla) values('Goiás', 'GO');
insert into estado (nome, sigla) values('São Paulo', 'SP');
insert into estado (nome, sigla) values('Rio de Janeiro', 'RJ');

insert into municipio (nome, id_estado) values('Palmas', 1);
insert into municipio (nome, id_estado) values('Paraiso', 1);
insert into municipio (nome, id_estado) values('Porto Nacional', 1);

-- Inserção de dados para Cliente
INSERT INTO Cliente (nome, email, dataNascimento, cpf) 
VALUES 
('João Silva', 'joao.silva@example.com', '1990-05-10', '12345678901'),
('Maria Souza', 'maria.souza@example.com', '1985-11-25', '98765432101');

-- Inserção de dados para Endereco
INSERT INTO Endereco (cep, cidade, estado, logradouro, numero, cliente_id) 
VALUES 
('12345678', 'São Paulo', 'SP', 'Rua das Flores', '100', 1),
('87654321', 'Rio de Janeiro', 'RJ', 'Avenida Brasil', '500', 2);

-- Inserção de dados para TelefoneCliente
INSERT INTO TelefoneCliente (ddd, numero, cliente_id) 
VALUES 
('11', '999999999', 1),
('21', '888888888', 2);

-- Inserção de dados para Fornecedor
INSERT INTO Fornecedor (nome, cnpj) 
VALUES 
('Suplementos Vida Ativa', '11222333444455'),
('Nutrição Esportiva Brasil', '55667788990011');

-- Inserção de dados para TelefoneFornecedor
INSERT INTO TelefoneFornecedor (ddd, numero, fornecedor_id) 
VALUES 
('31', '777777777', 1),
('41', '666666666', 2);

-- Inserção de dados para Creatina
INSERT INTO Creatina (nome, marca, quantidadeEmGramas, tipo, preco) 
VALUES 
('Creatina Monohidratada', 'Marca X', 300.00, 'Monohidratada', 79.90),
('Creatina Micronizada', 'Marca Y', 200.00, 'Micronizada', 99.90);
