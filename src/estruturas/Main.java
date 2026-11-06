package estruturas;

import estruturas.utils.GerenciadorDeTestes;

public class Main {
    public static void main(String[] args) {
        System.out.println("A iniciar a bateria completa de testes. Isto pode demorar alguns minutos...");
        
        GerenciadorDeTestes gerenciador = new GerenciadorDeTestes();
        gerenciador.executarTodosOsTestes();
    }
}