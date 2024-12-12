
 insert into estado (nome, sigla) values('Tocantins', 'TO');
 insert into estado (nome, sigla) values('Goiás', 'GO');
 insert into estado (nome, sigla) values('São Paulo', 'SP');
 insert into estado (nome, sigla) values('Rio de Janeiro', 'RJ');

 insert into municipio (nome, id_estado) values('Palmas', 1);
 insert into municipio (nome, id_estado) values('Paraiso', 1);
 insert into municipio (nome, id_estado) values('Porto Nacional', 1);

-- Inserindo dados na tabela Municipio
INSERT INTO Municipio (nome, id_estado) VALUES
('São Paulo', 3),
('Rio de Janeiro', 4);

-- Inserindo dados na tabela Pessoa
INSERT INTO Pessoa (nome) VALUES
('João Silva'),
('Maria Oliveira'),
('Pedro Souza');

-- Inserindo dados na tabela Endereco
INSERT INTO Endereco (logradouro, numero, complemento, bairro, cep, id_municipio, id_pessoa) VALUES
('Rua das Flores', '123', 'Apto 12', 'Centro', '01001-000', 1, 1),
('Avenida Atlântica', '456', 'Cobertura', 'Copacabana', '22010-000', 2, 2);

INSERT INTO EnderecoEntrega (id_endereco) VALUES
(1),
(2);

-- Inserindo dados na tabela Telefone
INSERT INTO Telefone (ddd, numero, id_pessoa) VALUES
('11', '987654321', 1),
('21', '988776655', 2),
('31', '905071884', 3);

-- Inserindo dados na tabela PessoaFisica
INSERT INTO PessoaFisica (id, cpf, dataNascimento) VALUES
(1, '123.456.789-00', '1990-01-01'),
(2, '987.654.321-00', '1985-05-15');

INSERT INTO pessoajuridica (id, cnpj) VALUES  
(3, '73395303000124');

-- Inserindo dados na tabela Categoria
INSERT INTO Categoria (nome, descricao) VALUES
('Hiperproteína', 'Creatinas de alta proteína'),
('Energético', 'Creatinas energéticas');

-- Inserindo dados na tabela Usuario
INSERT INTO Usuario(username, senha, email) VALUES 
('joao', 'qOf0Qw7LIP5PqpJJMwKqcX6BZ+t+elaNZW+fBPY70EsAhd2deno3eb2KnjlTMC06GwBMhShPtQJM8k308HXQog==', 'joao@gmail.com'), -- Senha é flamengo
('maria', 'qOf0Qw7LIP5PqpJJMwKqcX6BZ+t+elaNZW+fBPY70EsAhd2deno3eb2KnjlTMC06GwBMhShPtQJM8k308HXQog==', 'maria@gmail.com'), -- Senha é flamengo
('ADM', 'xzp7AP+QWnPSmgzJYRBVWiUX7nsVqbfYVtuvPOPw2TRuCrd6T8+/fEhQoxtROBveRpbEyyBB/Xlxxc+rWHzmzQ==', 'adm@email.com'); -- Senha: senhaadm

-- Inserindo dados na tabela Cliente
INSERT INTO Cliente (id_pessoaFisica, id_usuario) VALUES
(1, 1);

-- Inserindo dados na tabela Funcionario
INSERT INTO Funcionario (id_pessoaFisica, id_usuario, cargo, salario, dataContratacao) VALUES 
(2, 2, 'Analista de Sistemas', 15000, '2023-02-09');

INSERT INTO Fornecedor (id) VALUES
(3);

-- Inserindo dados na tabela Perfil_Usuario
INSERT INTO Perfil_Usuario (perfil, id_usuario) VALUES
(1, 3), -- ADM
(2, 1), -- Cliente
(3, 2); -- Funcionário

INSERT INTO Creatina (nome, marca, quantidadeEmGramas, tipo, preco, id_categoria, id_fornecedor) VALUES
('Creatina Monohidratada', 'Optimum Nutrition', 300, 'Monohidratada', 149.90, 1, 3),
('Creatina Micronizada', 'Universal Nutrition', 500, 'Micronizada', 179.90, 2, 3);

INSERT INTO Lote (id_creatina, dataValidade, dataFabricacao, codigo, estoque) VALUES
(1, '2025-06-30', '2023-06-01', 'L2023ON300', 100), -- Lote da Creatina Monohidratada
(1, '2025-09-15', '2023-09-01', 'L2023ON301', 50),  -- Outro lote da mesma creatina
(2, '2026-03-20', '2024-03-01', 'L2024UV500', 200), -- Lote da Creatina Micronizada
(2, '2025-12-31', '2023-12-01', 'L2023IM250', 80),  -- Lote da Creatina Hardcore
(1, '2026-06-15', '2024-06-01', 'L2024IM251', 120); -- Outro lote da mesma creatina

