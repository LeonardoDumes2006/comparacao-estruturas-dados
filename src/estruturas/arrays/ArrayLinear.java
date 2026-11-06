package estruturas.arrays;
import estruturas.EstruturaDeDados;

public class ArrayLinear implements EstruturaDeDados {
	private int[] array;
    private int tamanho;

    /**
     * O construtor recebe a quantidade inicial 'n' 
     * Conforme a regra, reserva espaço para n + 10% de n elementos.
     */
    public ArrayLinear(int n) {
        int capacidade = n + (int) (n * 0.10);
        this.array = new int[capacidade];
        this.tamanho = 0;
    }

    @Override
    public void inserir(int valor) {
        // A verificação de duplicatas faz parte da inserção
        // Se a busca retornar true, a tentativa de inserir é ignorada
        if (buscar(valor)) {
            return; 
        }
        
        // Insere no final se houver espaço
        if (tamanho < array.length) {
            array[tamanho] = valor;
            tamanho++;
        }
    }

    @Override
    public boolean buscar(int valor) {
        // Implementação de busca sequencial exigida para o array linear
        for (int i = 0; i < tamanho; i++) {
            if (array[i] == valor) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void remover(int valor) {
        // O tempo da operação deve incluir a busca pelo valor e a sua remoção
        for (int i = 0; i < tamanho; i++) {
            if (array[i] == valor) {
                // Como o array não é ordenado, a forma mais eficiente de remover
                // é substituir o elemento encontrado pelo último elemento válido do array.
                // Isso evita o custo de deslocar todos os elementos seguintes.
                array[i] = array[tamanho - 1];
                tamanho--;
                return;
            }
        }
    }

    @Override
    public int consultarIntervalo(int minimo, int maximo) {
        int contagem = 0;
        // Conta quantos valores estão entre mínimo e máximo, incluindo os limites[cite: 2]
        for (int i = 0; i < tamanho; i++) {
            if (array[i] >= minimo && array[i] <= maximo) {
                contagem++;
            }
        }
        return contagem;
    }
}
