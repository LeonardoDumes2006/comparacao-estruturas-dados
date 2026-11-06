package estruturas.hash;

import estruturas.EstruturaDeDados;

public class TabelaHash implements EstruturaDeDados {

    private class Entrada {
        int valor;

        public Entrada(int valor) {
            this.valor = valor;
        }
    }

    private final double FATOR_DE_CARGA = 0.7;
    private Entrada[] array;
    private int tamanhoAtual;
    private Entrada REMOVIDO = new Entrada(-1); 

    public TabelaHash(int capacidadeInicial) {
        this.array = new Entrada[capacidadeInicial];
        this.tamanhoAtual = 0;
    }

    private int hash(int valor) {
        return Math.abs(Integer.hashCode(valor)) % array.length;
    }

    @Override
    public void inserir(int valor) {
        if (buscar(valor)) {
            return; 
        }

        if ((double) tamanhoAtual / array.length >= FATOR_DE_CARGA) {
            redimensionar();
        }

        int indiceBase = hash(valor);
        int indice = indiceBase;
        int colisao = 0;

        while (array[indice] != null && array[indice] != REMOVIDO) {
            colisao++;
            indice = (indiceBase + colisao) % array.length;
        }
        
        array[indice] = new Entrada(valor);
        tamanhoAtual++;
    }

    @Override
    public boolean buscar(int valor) {
        int indiceBase = hash(valor);
        int indice = indiceBase;
        int colisao = 0;

        while (array[indice] != null) {
            if (array[indice] != REMOVIDO && array[indice].valor == valor) {
                return true;
            }
            colisao++;
            indice = (indiceBase + colisao) % array.length;
        }
        return false;
    }

    @Override
    public void remover(int valor) {
        int indiceBase = hash(valor);
        int indice = indiceBase;
        int colisao = 0;

        while (array[indice] != null) {
            if (array[indice] != REMOVIDO && array[indice].valor == valor) {
                array[indice] = REMOVIDO; 
                tamanhoAtual--;
                return;
            }
            colisao++;
            indice = (indiceBase + colisao) % array.length;
        }
    }

    @Override
    public int consultarIntervalo(int minimo, int maximo) {
        int contagem = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i] != REMOVIDO) {
                if (array[i].valor >= minimo && array[i].valor <= maximo) {
                    contagem++;
                }
            }
        }
        return contagem;
    }

    private void redimensionar() {
        Entrada[] antigoArray = array;
        array = new Entrada[antigoArray.length * 2];
        tamanhoAtual = 0;

        for (Entrada entrada : antigoArray) {
            if (entrada != null && entrada != REMOVIDO) {
                inserir(entrada.valor);
            }
        }
    }
}
