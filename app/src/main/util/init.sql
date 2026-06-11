CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    descricao VARCHAR(255),
    material VARCHAR(100),
    tamanho VARCHAR(50),
    valor FLOAT,
    quant_estoque INT
);
CREATE TABLE IF NOT EXISTS pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    descricao VARCHAR(255),
    material VARCHAR(100),
    tamanho VARCHAR(50),
    valor FLOAT,
    quant_estoque INT
);