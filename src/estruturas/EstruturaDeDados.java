package estruturas;

public interface EstruturaDeDados {

	void inserir(int valor);

    boolean buscar(int valor);

    void remover(int valor);

    int consultarIntervalo(int minimo, int maximo);
    
}
