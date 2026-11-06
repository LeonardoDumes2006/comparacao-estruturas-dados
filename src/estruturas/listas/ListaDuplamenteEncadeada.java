package estruturas.listas;

import estruturas.EstruturaDeDados;

public class ListaDuplamenteEncadeada implements EstruturaDeDados {
    private NodoDuplo inicio;
    private NodoDuplo fim;
    
    public ListaDuplamenteEncadeada() {
        inicio = null;
        fim = null;
    }

    @Override
    public boolean buscar(int valor) {
        NodoDuplo aux = inicio;
        while(aux != null) {
            if (aux.getDado() == valor) {
                return true;
            }
            aux = aux.getProx();    
        }
        return false;
    }

    @Override
    public void inserir(int valor) {
        // A verificação de duplicatas faz parte da inserção[cite: 1]
        if (buscar(valor)) {
            return;
        }
        
        NodoDuplo novoNodo = new NodoDuplo(valor);
        
        // Se a lista estiver vazia
        if(inicio == null) {
            inicio = novoNodo;
            fim = novoNodo;
            return;
        }
        
        // Reaproveitando a sua lógica do inserirFinal
        fim.setProx(novoNodo);
        novoNodo.setAnt(fim);
        fim = novoNodo;
    }
    
    @Override
    public void remover(int valor) {
        if(inicio == null) {
            return;
        }
        
        // Caso 1: O valor está no inicio da lista
        if(inicio.getDado() == valor) {
            // Se for o único elemento
            if(inicio == fim) {
                inicio = null;
                fim = null;
                return;
            }
            inicio = inicio.getProx();
            inicio.setAnt(null);
            return;
        }
    
        //Caso 2: o valor está no fim
        if(fim.getDado() == valor) {
            fim = fim.getAnt();
            fim.setProx(null);
            return;
        }
        
        // Caso 3: o valor está no meio da lista
        NodoDuplo aux = inicio.getProx();
        while(aux != null) {
            if(aux.getDado() == valor) {
                aux.getAnt().setProx(aux.getProx());
                aux.getProx().setAnt(aux.getAnt());
                return;
            }
            aux = aux.getProx();
        }
    }

    @Override
    public int consultarIntervalo(int minimo, int maximo) {
        int contagem = 0;
        NodoDuplo aux = inicio;
        
        // Conta quantos valores estão entre o mínimo e máximo, percorrendo os nós[cite: 2]
        while(aux != null) {
            if (aux.getDado() >= minimo && aux.getDado() <= maximo) {
                contagem++;
            }
            aux = aux.getProx();
        }
        return contagem;
    }
}