package estruturas.utils;

import java.util.Random;

public class GerenciadorDeTestes {
    private int n;
    private Random random;

    // Arrays que guardarão os dados pré-gerados para os testes
    public int[] dadosIniciais;
    public int[] buscasPresentes;
    public int[] buscasAusentes;
    public int[] insercoes;
    public int[] remocoes;
    public int[] intervalosMinimos;

    public GerenciadorDeTestes(int n, String ordem) {
        this.n = n;
        // Fixa a semente 42 para a geração aleatória, garantindo testes justos
        this.random = new Random(42); 
        gerarTudo(ordem);
    }

    private void gerarTudo(String ordem) {
        int qtdDezPorcento = (int) (n * 0.10);

        // 1. DADOS INICIAIS: pares de 2 até 2n[cite: 1]
        dadosIniciais = new int[n];
        for (int i = 0; i < n; i++) {
            dadosIniciais[i] = (i + 1) * 2;
        }

        if (ordem.equals("DECRESCENTE")) {
            reverterArray(dadosIniciais);
        } else if (ordem.equals("ALEATORIA")) {
            embaralharArray(dadosIniciais);
        }

        // 2. BUSCA PRESENTE: 2.000 buscas por valores sorteados do conjunto inicial
        buscasPresentes = new int[2000];
        for (int i = 0; i < 2000; i++) {
            buscasPresentes[i] = dadosIniciais[random.nextInt(n)];
        }

        // 3. BUSCA AUSENTE: 2.000 buscas por ímpares sorteados entre 1 e 2n-1
        buscasAusentes = new int[2000];
        for (int i = 0; i < 2000; i++) {
            buscasAusentes[i] = random.nextInt(n) * 2 + 1; 
        }

        // 4. INSERÇÃO: 10% de n elementos, ímpares distintos entre 1 e 2n-1
        insercoes = gerarImparesDistintos(qtdDezPorcento);

        // 5. REMOÇÃO: 10% de n elementos distintos, sorteados do conjunto inicial
        remocoes = gerarRemocoesDistintas(qtdDezPorcento);

        // 6. INTERVALO: 200 consultas, mínimo sorteado entre 1 e 2n - 100
        intervalosMinimos = new int[200];
        int limiteMaximoMinimo = (2 * n) - 100;
        for (int i = 0; i < 200; i++) {
            intervalosMinimos[i] = random.nextInt(limiteMaximoMinimo) + 1;
        }
    }

    // --- Métodos Auxiliares ---

    private void reverterArray(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    private void embaralharArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private int[] gerarImparesDistintos(int quantidade) {
        int[] todosImpares = new int[n];
        for (int i = 0; i < n; i++) {
            todosImpares[i] = i * 2 + 1;
        }
        embaralharArray(todosImpares); // Embaralha para pegar aleatórios distintos
        
        int[] resultado = new int[quantidade];
        System.arraycopy(todosImpares, 0, resultado, 0, quantidade);
        return resultado;
    }

    private int[] gerarRemocoesDistintas(int quantidade) {
        int[] copiaIniciais = new int[n];
        System.arraycopy(dadosIniciais, 0, copiaIniciais, 0, n);
        embaralharArray(copiaIniciais);
        
        int[] resultado = new int[quantidade];
        System.arraycopy(copiaIniciais, 0, resultado, 0, quantidade);
        return resultado;
    }
}
