
package grafo.util;

import java.util.ArrayList;
import java.util.List;


public class Grafo {
    
    List<Vertice> vertices;
    List<Aresta> arestas;

    public Grafo() {
        vertices = new ArrayList<Vertice>();
        arestas = new ArrayList<Aresta>();
    }
    public List<Vertice> getVertices() {
        return vertices;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }
    
    public Vertice addVertice(int nome, double posX, double posY) {
        Vertice v = null;
        
        int existe = existeVertice(nome);
        
        if(existe == -1){
            v = new Vertice(nome,posX,posY);
            vertices.add(v);            
        }else{
            v = vertices.get(existe);
        }
        return v;
    }
    
    
    
    public int existeVertice(int nome){
        int existe = -1;
        
        for(int i=0;i<vertices.size();i++){
            if(vertices.get(i).nome == nome){
                existe = i;
                break;
            }else{
                existe = -1;
            }
        }
        
        return existe;
    }
    
    public Aresta addAresta(Vertice origem, Vertice destino, double peso) {
        Aresta a = new Aresta(origem, destino,peso);
        origem.adicionaLista(a);
        arestas.add(a);
        return a;
    }

    public String toString() {
        String r = "";
        for (Vertice u : vertices) {
            r += u.nome + " -> ";
            for (Aresta e : u.listaAdj) {
                Vertice v = e.destino;
                r += v.nome + ", ";
            }
            r += "\n";
        }
        return r;
    }

    
}
 