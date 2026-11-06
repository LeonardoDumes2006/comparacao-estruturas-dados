package estruturas.arvores;

import estruturas.EstruturaDeDados;

public class ArvoreAVL implements EstruturaDeDados {
    
    private class No {
        int valor; 
        No esq, dir;
        int alturaEsq, alturaDir;
        
        public No(int valor) {
            this.valor = valor;
            esq = dir = null;
            alturaEsq = alturaDir = 0;
        }
    }
    
    private No raiz;
    
    public ArvoreAVL() {
        raiz = null;
    }
    
    @Override
    public void inserir(int valor) {
        raiz = inserirNo(raiz, valor);
    }
    
    private No inserirNo(No atual, int valor) {
        if(atual == null) {
            return new No(valor);
        }
        
        // Inserção tradicional
        if (valor < atual.valor) {
            atual.esq = inserirNo(atual.esq, valor);
        } else if (valor > atual.valor) {
            atual.dir = inserirNo(atual.dir, valor);
        } else {
            // Se for igual, ignora a duplicata silenciosamente
            return atual; 
        }
        
        // Atualiza a altura do nó atual e verifica se desbalanceou
        atualizarAlturas(atual);
        return balancear(atual);
    }
    
    @Override
    public boolean buscar(int valor) {
        No atual = raiz;
        while(atual != null) {
            if(valor == atual.valor) {
                return true;
            }
            if(valor < atual.valor) {
                atual = atual.esq;
            } else {
                atual = atual.dir;
            }
        }
        return false;
    }
    
    @Override
    public void remover(int valor) {
        raiz = removerNo(raiz, valor);
    }
    
    private No removerNo(No atual, int valor) {
        if (atual == null) {
            return null;
        }
        
        // Passo 1: Busca e remoção padrão de Árvore Binária
        if (valor < atual.valor) {
            atual.esq = removerNo(atual.esq, valor);
        } else if (valor > atual.valor) {
            atual.dir = removerNo(atual.dir, valor);
        } else {
            // Nó encontrado (Caso 1 e 2: zero ou um filho)
            if (atual.esq == null) return atual.dir;
            if (atual.dir == null) return atual.esq;
            
            // Caso 3: dois filhos (pega o menor dos maiores)
            No sucessor = encontrarMenor(atual.dir);
            atual.valor = sucessor.valor;
            atual.dir = removerNo(atual.dir, sucessor.valor);
        }
        
        // Passo 2: Na volta da recursão, atualiza as alturas e rebalanceia
        atualizarAlturas(atual);
        return balancear(atual);
    }
    
    private No encontrarMenor(No no) {
        while (no.esq != null) {
            no = no.esq;
        }
        return no;
    }
    
    @Override
    public int consultarIntervalo(int minimo, int maximo) {
        return contarIntervaloRecursivo(raiz, minimo, maximo);
    }
    
    private int contarIntervaloRecursivo(No no, int minimo, int maximo) {
        if (no == null) return 0;
        
        int contagem = 0;
        
        if (no.valor >= minimo && no.valor <= maximo) {
            contagem = 1;
        }
        
        // Mesma otimização de poda utilizada na árvore binária simples
        if (no.valor > minimo) {
            contagem += contarIntervaloRecursivo(no.esq, minimo, maximo);
        }
        
        if (no.valor < maximo) {
            contagem += contarIntervaloRecursivo(no.dir, minimo, maximo);
        }
        
        return contagem;
    }
    
    // --- MÉTODOS DE ROTAÇÃO E BALANCEAMENTO ORIGINAIS MANTIDOS ---
    
    private No balancear(No atual) {
        int fatorBalanceamento = atual.alturaDir - atual.alturaEsq;
        
        // Desbalanceamento à direita
        if(fatorBalanceamento == 2) {
            if(atual.dir.alturaDir >= atual.dir.alturaEsq) {
                return rotacaoEsquerda(atual);
            } else {
                atual.dir = rotacaoDireita(atual.dir);
                return rotacaoEsquerda(atual);
            }
        }
        // Desbalanceamento à esquerda
        else if (fatorBalanceamento == -2) {
            if(atual.esq.alturaEsq >= atual.esq.alturaDir) {
                return rotacaoDireita(atual);
            } else {
                atual.esq = rotacaoEsquerda(atual.esq);
                return rotacaoDireita(atual);
            }
        }
        
        return atual;
    }
    
    private No rotacaoEsquerda(No raiz) {
        No novaRaiz = raiz.dir; 
        raiz.dir = novaRaiz.esq;
        novaRaiz.esq = raiz;
        
        atualizarAlturas(raiz);
        atualizarAlturas(novaRaiz);
        
        return novaRaiz;
    }
    
    private No rotacaoDireita(No raiz) {
        No novaRaiz = raiz.esq;
        raiz.esq = novaRaiz.dir;
        novaRaiz.dir = raiz;
        
        atualizarAlturas(raiz);
        atualizarAlturas(novaRaiz);
        
        return novaRaiz;
    }
    
    private void atualizarAlturas(No no) {
        if(no != null) {
            no.alturaEsq = (no.esq == null) ? 0 : Math.max(no.esq.alturaEsq, no.esq.alturaDir) + 1;
            no.alturaDir = (no.dir == null) ? 0 : Math.max(no.dir.alturaEsq, no.dir.alturaDir) + 1;
        }
    }
}
