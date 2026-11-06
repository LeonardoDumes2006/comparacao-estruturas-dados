package estruturas.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class GerenciadorDeTestes {

    public void executarTodosOsTestes() {
        int[] tamanhos = {5000, 10000, 100000};
        String[] ordens = {"CRESCENTE", "DECRESCENTE", "ALEATORIA"};
        String[] estruturas = {
            "ArrayLinear", 
            "ArrayOrdenado", 
            "ListaDuplamenteEncadeada", 
            "TabelaHash", 
            "ArvoreBinariaBusca", 
            "ArvoreAVL", 
            "ArvoreRubroNegra"
        };

        // Prepara o ficheiro CSV para escrita
        try (PrintWriter writer = new PrintWriter(new FileWriter("resultados.csv"))) {
            // Cabeçalho do CSV
            writer.println("Estrutura;Tamanho;Ordem;Operacao;TempoTotalMediana(ms);TempoPorOperacao(ns)");

            for (int n : tamanhos) {
                for (String ordem : ordens) {
                    System.out.println("A gerar dados para Tamanho: " + n + " | Ordem: " + ordem);
                    GeradorDeDados gerador = new GeradorDeDados(n, ordem);

                    for (String nomeEstrutura : estruturas) {
                        System.out.println("A testar: " + nomeEstrutura + "...");
                        
                        Cronometro cronometro = new Cronometro(gerador, nomeEstrutura, n);

                        // 1. Construção
                        long[] tConstrucao = cronometro.testarConstrucao();
                        salvarResultado(writer, nomeEstrutura, n, ordem, "Construcao", tConstrucao, n);

                        // 2. Busca Presente
                        long[] tBuscaP = cronometro.testarBuscaPresente();
                        salvarResultado(writer, nomeEstrutura, n, ordem, "Busca Presente", tBuscaP, 2000);

                        // 3. Busca Ausente
                        long[] tBuscaA = cronometro.testarBuscaAusente();
                        salvarResultado(writer, nomeEstrutura, n, ordem, "Busca Ausente", tBuscaA, 2000);

                        // 4. Inserção (10%)
                        long[] tInsercao = cronometro.testarInsercao();
                        int qtdDezPorcento = (int) (n * 0.10);
                        salvarResultado(writer, nomeEstrutura, n, ordem, "Insercao", tInsercao, qtdDezPorcento);

                        // 5. Remoção (10%)
                        long[] tRemocao = cronometro.testarRemocao();
                        salvarResultado(writer, nomeEstrutura, n, ordem, "Remocao", tRemocao, qtdDezPorcento);

                        // 6. Consulta por Intervalo
                        long[] tIntervalo = cronometro.testarConsultaPorIntervalo();
                        salvarResultado(writer, nomeEstrutura, n, ordem, "Consulta Intervalo", tIntervalo, 200);
                    }
                }
            }
            System.out.println("Testes concluídos! Resultados guardados em 'resultados.csv'.");

        } catch (IOException e) {
            System.out.println("Erro ao gravar o ficheiro CSV: " + e.getMessage());
        }
    }

    private void salvarResultado(PrintWriter writer, String estrutura, int tamanho, String ordem, 
                                 String operacao, long[] tempos, int qtdOperacoes) {
        // Ordena os 5 tempos e pega a mediana (índice 2)
        Arrays.sort(tempos);
        long medianaNanos = tempos[2];
        
        double medianaMillis = medianaNanos / 1_000_000.0;
        long tempoPorOperacao = medianaNanos / qtdOperacoes;

        // Formata a linha separada por ponto e vírgula
        // Substituímos o ponto decimal por vírgula para o Excel reconhecer os milissegundos em PT-BR
        String linha = String.format("%s;%d;%s;%s;%.2f;%d", 
                estrutura, tamanho, ordem, operacao, medianaMillis, tempoPorOperacao);
        
        writer.println(linha);
    }
}