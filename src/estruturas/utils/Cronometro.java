package estruturas.utils;

import estruturas.EstruturaDeDados;
import estruturas.arrays.ArrayLinear;
import estruturas.arrays.ArrayOrdenado;
import estruturas.hash.TabelaHash;
import estruturas.listas.ListaDuplamenteEncadeada;
import estruturas.arvores.ArvoreBinariaBusca;
import estruturas.arvores.ArvoreAVL;
import estruturas.arvores.ArvoreRubroNegra;

public class Cronometro {
    private GeradorDeDados gerador;
    private String tipoEstrutura;
    private int n;

    public Cronometro(GeradorDeDados gerador, String tipoEstrutura, int n) {
        this.gerador = gerador;
        this.tipoEstrutura = tipoEstrutura;
        this.n = n;
    }

    private EstruturaDeDados criarEstrutura() {
        switch (tipoEstrutura) {
            case "ArrayLinear":
                return new ArrayLinear(n);
            case "ArrayOrdenado":
                return new ArrayOrdenado(n);
            case "ListaDuplamenteEncadeada":
                return new ListaDuplamenteEncadeada();
            case "TabelaHash":
                return new TabelaHash(n);
            case "ArvoreBinariaBusca":
                return new ArvoreBinariaBusca();
            case "ArvoreAVL":
                return new ArvoreAVL();
            case "ArvoreRubroNegra":
                return new ArvoreRubroNegra();
            default:
                return null;
        }
    }

    private EstruturaDeDados prepararEstrutura() {
        EstruturaDeDados estrutura = criarEstrutura();
        for (int valor : gerador.dadosIniciais) {
            estrutura.inserir(valor);
        }
        return estrutura;
    }

    public long[] testarConstrucao() {
        long[] tempos = new long[5];
        for (int i = 0; i < 6; i++) {
            EstruturaDeDados estrutura = criarEstrutura();
            
            long inicio = System.nanoTime();
            for (int valor : gerador.dadosIniciais) {
                estrutura.inserir(valor);
            }
            long fim = System.nanoTime();
            
            if (i > 0) tempos[i - 1] = (fim - inicio);
        }
        return tempos;
    }

    public long[] testarBuscaPresente() {
        long[] tempos = new long[5];
        for (int i = 0; i < 6; i++) {
            EstruturaDeDados estrutura = prepararEstrutura();
            
            long inicio = System.nanoTime();
            for (int valor : gerador.buscasPresentes) {
                estrutura.buscar(valor);
            }
            long fim = System.nanoTime();
            
            if (i > 0) tempos[i - 1] = (fim - inicio);
        }
        return tempos;
    }

    public long[] testarBuscaAusente() {
        long[] tempos = new long[5];
        for (int i = 0; i < 6; i++) {
            EstruturaDeDados estrutura = prepararEstrutura();
            
            long inicio = System.nanoTime();
            for (int valor : gerador.buscasAusentes) {
                estrutura.buscar(valor);
            }
            long fim = System.nanoTime();
            
            if (i > 0) tempos[i - 1] = (fim - inicio);
        }
        return tempos;
    }

    public long[] testarInsercao() {
        long[] tempos = new long[5];
        for (int i = 0; i < 6; i++) {
            EstruturaDeDados estrutura = prepararEstrutura();
            
            long inicio = System.nanoTime();
            for (int valor : gerador.insercoes) {
                estrutura.inserir(valor);
            }
            long fim = System.nanoTime();
            
            if (i > 0) tempos[i - 1] = (fim - inicio);
        }
        return tempos;
    }

    public long[] testarRemocao() {
        long[] tempos = new long[5];
        for (int i = 0; i < 6; i++) {
            EstruturaDeDados estrutura = prepararEstrutura();
            
            long inicio = System.nanoTime();
            for (int valor : gerador.remocoes) {
                estrutura.remover(valor);
            }
            long fim = System.nanoTime();
            
            if (i > 0) tempos[i - 1] = (fim - inicio);
        }
        return tempos;
    }

    public long[] testarConsultaPorIntervalo() {
        long[] tempos = new long[5];
        for (int i = 0; i < 6; i++) {
            EstruturaDeDados estrutura = prepararEstrutura();
            
            long inicio = System.nanoTime();
            for (int minimo : gerador.intervalosMinimos) {
                int maximo = minimo + 100;
                estrutura.consultarIntervalo(minimo, maximo);
            }
            long fim = System.nanoTime();
            
            if (i > 0) tempos[i - 1] = (fim - inicio);
        }
        return tempos;
    }
}