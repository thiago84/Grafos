package grafo.util;

public class Aresta {
    public Vertice origem;
    public Vertice destino;
    public double custo;

    public Aresta(Vertice origem, Vertice destino, Double custo) {
        this.origem = origem;
        this.destino = destino;
        this.custo = custo;
    }
    
    public Vertice getOrigem() {
        return this.origem;
    }
    
    public Vertice getDestino() {
        return this.destino;
    }
    
    public double getCusto() {
        return this.custo;
    }
}
