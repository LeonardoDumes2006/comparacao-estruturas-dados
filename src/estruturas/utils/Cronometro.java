package estruturas.utils;

import estruturas.EstruturaDeDados;
import estruturas.arrays.ArrayLinear;
import estruturas.arrays.ArrayOrdenado;
import estruturas.hash.TabelaHash;
import estruturas.listas.ListaDuplamenteEncadeada;

public class Cronometro {
    private GerenciadorDeTestes gerador;
    private String tipoEstrutura;
    private int n;

    public Cronometro(GerenciadorDeTestes gerador, String tipoEstrutura, int n) {
        this.gerador = gerador;
        this.tipoEstrutura = tipoEstrutura;
        this.n = n;
    }

    /**
     * Método simples e direto para instanciar a estrutura correta baseada no nome.
     */
    private EstruturaDeDados criarEstrutura() {
        if (tipoEstrutura.equals("ArrayLinear")) {
            return new ArrayLinear(n);
        } else if (tipoEstrutura.equals("ArrayOrdenado")) {
            return new ArrayOrdenado(n);
        } else if (tipoEstrutura.equals("ListaDuplamenteEncadeada")) {
            return new ListaDuplamenteEncadeada();
        } else if (tipoEstrutura.equals("TabelaHash")) {
            // Usa n para a capacidade inicial
            return new TabelaHash(n); 
        }
        return null; // Caso passe um nome inválido
    }

    /**
     * Reconstroi a estrutura com os 'n' valores iniciais
     * antes de cada repetição, conforme exigido
     */
    private EstruturaDeDados prepararEstrutura() {
        EstruturaDeDados estrutura = criarEstrutura();
        for (int valor : gerador.dadosIniciais) {
            estrutura.inserir(valor);
        }
        return estrutura;
    }

    public long[] testarConstrucao() {
        long[] tempos = new long[5];
        // 6 execuções: i=0 (aquecimento), i=1 a 5 (medições)
        for (int i = 0; i < 6; i++) {
            EstruturaDeDados estrutura = criarEstrutura(); // Começa vazio
            
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
