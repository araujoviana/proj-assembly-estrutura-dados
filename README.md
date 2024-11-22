# proj-assembly-estrutura-dados
REPL de uma linguagem estilo Assembly simplificada em Java


## Instalação

``` shell
# Clona o repositório
git clone https://github.com/araujoviana/proj-assembly-estrutura-dados
cd proj-assembly-estrutura-dados/

# Compila o código
javac src/*.java

# Inicia o REPL
java Program
```

## Uso

### Entrada

A entrada do usuário deve ser feita seguindo o seguinte modelo:

``` 
<comando> <número de linha> <instrução> <argumentos>
```

Caso o usuário queira escrever o código em um editor de texto, não é necessário inserir o comando direto no arquivo `.ed1`.

### Comandos

#### Insert (ins)

Insere uma instrução no buffer

``` 
ins <linha> <instrução>
```


#### Delete (del)

Remove uma ou um intervalo de linhas do buffer atual

``` 
del <linha> 
del <início> <fim>
```

#### List (list)

Lista todo o conteúdo do buffer atual

``` 
list
```

#### Run (run)

Executa todas as instruções carregadas no buffer 

``` 
run
```

#### Save (save)

Salva o conteúdo do buffer para um arquivo `.ed1`

``` 
save
save <nome do arquivo>.ed1
```

#### Load (load)

Carrega o arquivo para o buffer atual

``` 
load <nome do arquivo>.ed1
```

#### Exit (exit)

Finaliza a execução do REPL

``` 
exit
```

### Instruções

- **mov x y**: Copia `y` para `x`.
- **inc x**: Incrementa `x` em 1.
- **dec x**: Decrementa `x` em 1.
- **add x y**: Soma `y` a `x`.
- **sub x y**: Subtrai `y` de `x`.
- **mul x y**: Multiplica `x` por `y`.
- **div x y**: Divide `x` por `y`.
- **jnz x y**: Salta para linha `y` se `x ≠ 0`.
- **out x**: Exibe o valor de `x`.
