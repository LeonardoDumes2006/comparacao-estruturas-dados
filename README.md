# Comparação de Desempenho: Estruturas de Dados em Java

Repositório dedicado à implementação e análise empírica de desempenho de diferentes estruturas de dados em cenários de estresse. O projeto avalia o tempo de execução (em milissegundos e nanossegundos) de operações fundamentais aplicadas a volumes escalonáveis de dados (5.000, 10.000 e 100.000 elementos) em ordens crescente, decrescente e aleatória.

## Estruturas Implementadas

Foram desenvolvidas integralmente do zero as seguintes estruturas (sem uso das coleções nativas do Java):
- **Baseadas em Arrays:** Array Linear e Array Ordenado (com busca binária e reserva de 10% de capacidade).
- **Baseadas em Nós:** Lista Duplamente Encadeada.
- **Dispersão:** Tabela Hash.
- **Árvores:** Árvore Binária de Busca (ABB), Árvore AVL e Árvore Rubro-Negra.

## Operações Avaliadas

A automação interna testa as seguintes rotinas para cada estrutura:
1. **Construção:** Inserção do conjunto inicial de *n* elementos.
2. **Busca Presente:** 2.000 consultas por elementos previamente inseridos.
3. **Busca Ausente:** 2.000 consultas por elementos inexistentes.
4. **Inserção:** Adição de novos elementos correspondentes a 10% do tamanho inicial.
5. **Remoção:** Exclusão de elementos correspondentes a 10% do tamanho inicial.
6. **Consulta por Intervalo:** 200 buscas simultâneas definindo parâmetros de mínimo e máximo.

## Metodologia de Teste

Para garantir o rigor acadêmico e a confiabilidade dos resultados:
* **Reprodutibilidade:** Geração de dados através da classe Random nativa do Java com semente fixa (`seed = 42`).
* **Warm-up da JVM:** Seis execuções por cenário de teste, descartando a primeira iteração (aquecimento inicial) e computando apenas as cinco rodadas seguintes.
* **Mediana Estatística:** O resultado final de cada lote adota a mediana dos tempos coletados, mitigando distorções causadas por processos de segundo plano do sistema operacional.
* **Prevenção de StackOverflow:** A Árvore Binária de Busca comum foi estruturada utilizando travessia iterativa (laços `while`) em substituição à recursão padrão, viabilizando o teste de pior caso absoluto (100.000 dados ordenados) sem estourar a memória da pilha de chamadas.

## Arquitetura do Projeto

    comparacao-estruturas-dados/
    ├── src/
    │   ├── estruturas/
    │   │   ├── arrays/      # ArrayLinear, ArrayOrdenado
    │   │   ├── arvores/     # ArvoreBinariaBusca, ArvoreAVL, ArvoreRubroNegra
    │   │   ├── hash/        # TabelaHash
    │   │   ├── listas/      # ListaDuplamenteEncadeada
    │   │   ├── utils/       # Cronometro, GeradorDeDados, GerenciadorDeTestes
    │   │   ├── EstruturaDeDados.java
    │   │   └── Main.java

## Como Executar

1. Clone o repositório para o seu ambiente local:
   > git clone https://github.com/LeonardoDumes2006/comparacao-estruturas-dados.git

2. Importe o projeto na sua IDE de preferência (Eclipse, IntelliJ, NetBeans, etc).
3. Execute a classe principal `Main.java`.
4. O sistema irá iniciar a bateria de testes e, ao finalizar, compilará automaticamente um ficheiro `resultados.csv` na raiz do diretório do projeto, pronto para geração de gráficos no Excel ou Google Sheets.

## Autor

**Leonardo Dümes de Souza**  
Ciência da Computação - Instituto Federal Catarinense (IFC) Campus Rio do Sul
