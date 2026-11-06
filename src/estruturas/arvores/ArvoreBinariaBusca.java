package estruturas.arvores;

import estruturas.EstruturaDeDados;
import java.util.Stack;

public class ArvoreBinariaBusca implements EstruturaDeDados {
    
    private class No {
        int valor;
        No esq, dir;
        
        public No(int valor) {
            this.valor = valor;
            esq = dir = null;
        }
    }
    
    private No raiz;
    
    public ArvoreBinariaBusca() {
        raiz = null;
    }

    @Override
    public void inserir(int valor) {
        if (raiz == null) {
            raiz = new No(valor);
            return;
        }
        No atual = raiz;
        while (true) {
            if (valor == atual.valor) return; // Ignora duplicatas
            
            if (valor < atual.valor) {
                if (atual.esq == null) {
                    atual.esq = new No(valor);
                    return;
                }
                atual = atual.esq;
            } else {
                if (atual.dir == null) {
                    atual.dir = new No(valor);
                    return;
                }
                atual = atual.dir;
            }
        }
    }

    @Override
    public boolean buscar(int valor) {
        No atual = raiz;
        while (atual != null) {
            if (valor == atual.valor) return true;
            if (valor < atual.valor) atual = atual.esq;
            else atual = atual.dir;
        }
        return false;
    }

    @Override
    public void remover(int valor) {
        No atual = raiz;
        No pai = null;
        
        // Busca iterativa pelo nó a remover
        while (atual != null && atual.valor != valor) {
            pai = atual;
            if (valor < atual.valor) atual = atual.esq;
            else atual = atual.dir;
        }
        
        if (atual == null) return; // Valor não encontrado
        
        // Caso 1 e 2: O nó tem no máximo um filho
        if (atual.esq == null || atual.dir == null) {
            No substituto = (atual.esq != null) ? atual.esq : atual.dir;
            if (pai == null) {
                raiz = substituto;
            } else if (atual == pai.esq) {
                pai.esq = substituto;
            } else {
                pai.dir = substituto;
            }
        } 
        // Caso 3: O nó tem dois filhos (busca o sucessor iterativamente)
        else {
            No paiSucessor = atual;
            No sucessor = atual.dir;
            while (sucessor.esq != null) {
                paiSucessor = sucessor;
                sucessor = sucessor.esq;
            }
            atual.valor = sucessor.valor;
            if (paiSucessor.esq == sucessor) {
                paiSucessor.esq = sucessor.dir;
            } else {
                paiSucessor.dir = sucessor.dir;
            }
        }
    }

    @Override
    public int consultarIntervalo(int minimo, int maximo) {
        if (raiz == null) return 0;
        
        int contagem = 0;
        // Pilha (Stack) dinâmica para substituir a recursão no DFS
        Stack<No> pilha = new Stack<>();
        pilha.push(raiz);
        
        while (!pilha.isEmpty()) {
            No atual = pilha.pop();
            
            if (atual.valor >= minimo && atual.valor <= maximo) {
                contagem++;
            }
            // Poda iterativa
            if (atual.valor > minimo && atual.esq != null) {
                pilha.push(atual.esq);
            }
            if (atual.valor < maximo && atual.dir != null) {
                pilha.push(atual.dir);
            }
        }
        return contagem;
    }
}