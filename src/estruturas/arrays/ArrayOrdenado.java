package estruturas.arrays;
import estruturas.EstruturaDeDados;

public class ArrayOrdenado implements EstruturaDeDados {
    private int[] array;
    private int tamanho;

    public ArrayOrdenado(int n) {
        // Reserva espaço para n mais 10% de n elementos[cite: 3]
        int capacidade = n + (int) (n * 0.10);
        this.array = new int[capacidade];
        this.tamanho = 0;
    }

    // Método auxiliar privado para realizar a busca binária
    private int indiceDe(int valor) {
        int inicio = 0;
        int fim = tamanho - 1;
        
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            if (array[meio] == valor) {
                return meio;
            } else if (array[meio] < valor) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        return -1;
    }

    @Override
    public boolean buscar(int valor) {
        // Retorna true se a busca binária encontrar o valor[cite: 1]
        return indiceDe(valor) != -1; 
    }

    @Override
    public void inserir(int valor) {
        // A verificação de duplicatas faz parte da inserção[cite: 1]
        if (buscar(valor)) {
            return; 
        }
        
        if (tamanho < array.length) {
            int i;
            // Localiza a posição correta e desloca os elementos para a direita[cite: 3]
            for (i = tamanho - 1; (i >= 0 && array[i] > valor); i--) {
                array[i + 1] = array[i];
            }
            // Insere o valor na posição que ficou vazia
            array[i + 1] = valor;
            tamanho++;
        }
    }

    @Override
    public void remover(int valor) {
        int pos = indiceDe(valor);
        if (pos != -1) {
            // Ao remover, é necessário deslocar os elementos seguintes para a esquerda
            // para não deixar "buracos" no array e manter a continuidade e ordenação.
            for (int i = pos; i < tamanho - 1; i++) {
                array[i] = array[i + 1];
            }
            tamanho--;
        }
    }

    @Override
    public int consultarIntervalo(int minimo, int maximo) {
        int contagem = 0;
        for (int i = 0; i < tamanho; i++) {
            // Como o array está ordenado[cite: 1], podemos interromper o ciclo
            // assim que encontrarmos um valor maior do que o limite máximo.
            if (array[i] > maximo) {
                break;
            }
            if (array[i] >= minimo) {
                contagem++;
            }
        }
        return contagem;
    }
}