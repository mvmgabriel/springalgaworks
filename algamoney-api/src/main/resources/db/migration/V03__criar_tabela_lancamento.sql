CREATE TABLE lancamento (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento  DATE NULL,
	valor DECIMAL(10,2) NULL,
	observacao VARCHAR(100) NULL,
	tipo VARCHAR(50) NOT NULL,
	codigo_categoria BIGINT(20) NULL,
	codigo_pessoa BIGINT(20) NULL,
	FOREIGN KEY(codigo_categoria) REFERENCES categoria(codigo),
	FOREIGN KEY(codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO lancamento
(descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa)
VALUES
('Teste', '2017-10-01', '2017-10-01', 10.00, 'Testes', 'RECEITA', 6, 2);