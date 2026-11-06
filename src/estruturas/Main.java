package estruturas;

import estruturas.utils.GeradorDeDados;
import estruturas.utils.Cronometro;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Definir os parâmetros básicos para este primeiro teste
        int n = 5000;
        String ordem = "ALEATORIA";
        String nomeEstrutura = "ArrayLinear"; // Pode trocar para "ArrayOrdenado", etc.

        System.out.println("A iniciar testes...");
        System.out.println("Estrutura: " + nomeEstrutura + " | Tamanho: " + n + " | Ordem: " + ordem);
        System.out.println("--------------------------------------------------");

        // 1. Gerar os dados antes de iniciar o cronómetro
        GeradorDeDados gerador = new GeradorDeDados(n, ordem);
        
        // 2. Instanciar o cronómetro
        Cronometro cronometro = new Cronometro(gerador, nomeEstrutura, n);

        // 3. Executar os testes e guardar os arrays de 5 tempos
        long[] temposConstrucao = cronometro.testarConstrucao();
        mostrarResultado("Construção", temposConstrucao, n);

        long[] temposBuscaPresente = cronometro.testarBuscaPresente();
        mostrarResultado("Busca Presente", temposBuscaPresente, 2000); // 2000 buscas[cite: 2]

        long[] temposBuscaAusente = cronometro.testarBuscaAusente();
        mostrarResultado("Busca Ausente", temposBuscaAusente, 2000); // 2000 buscas[cite: 2]

        long[] temposInsercao = cronometro.testarInsercao();
        int qtdInsercoes = (int) (n * 0.10);
        mostrarResultado("Inserção (10%)", temposInsercao, qtdInsercoes); // 10% de n[cite: 2]

        long[] temposRemocao = cronometro.testarRemocao();
        mostrarResultado("Remoção (10%)", temposRemocao, qtdInsercoes); // 10% de n[cite: 2]

        long[] temposIntervalo = cronometro.testarConsultaPorIntervalo();
        mostrarResultado("Consulta Intervalo", temposIntervalo, 200); // 200 consultas[cite: 2]
    }

    /**
     * Método auxiliar para calcular a mediana e imprimir os resultados.
     */
    private static void mostrarResultado(String nomeTeste, long[] tempos, int qtdOperacoes) {
        // Ordenar os cinco tempos obtidos para encontrar a mediana[cite: 3]
        Arrays.sort(tempos);
        
        // A mediana é o terceiro elemento (índice 2) do array ordenado[cite: 3]
        long medianaNanos = tempos[2]; 
        
        // Converter para milissegundos (mediana do tempo total)[cite: 3]
        double medianaMillis = medianaNanos / 1_000_000.0;
        
        // Calcular o tempo médio por operação (em nanossegundos)[cite: 3]
        long tempoPorOperacao = medianaNanos / qtdOperacoes;

        System.out.printf("%-20s -> Total: %8.2f ms | Por operação: %6d ns\n", 
                          nomeTeste, medianaMillis, tempoPorOperacao);
    }
}
