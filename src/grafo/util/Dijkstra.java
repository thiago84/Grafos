
package grafo.util;


import java.util.*;

public class Dijkstra {
    
    @SuppressWarnings("unused")
	private final List<Vertice> nodes;
    private final List<Aresta> arestas;
    private Set<Vertice> nodeMarcado;
    private Set<Vertice> nodeNaoMarcado;
    private Map<Vertice, Vertice> precedentes;
    public Map<Vertice, Double> custo;
    
    public Dijkstra(Grafo g) {
        this.nodes = new ArrayList<Vertice>(g.getVertices());
        this.arestas = new ArrayList<Aresta>(g.getArestas());
    }
    
     public void calcula(Vertice source) {
        
        nodeMarcado     = new HashSet<Vertice>();
        nodeNaoMarcado  = new HashSet<Vertice>();
        custo       = new HashMap<Vertice, Double>();
        precedentes     = new HashMap<Vertice, Vertice>();
        custo.put(source, 0.0);
        nodeNaoMarcado.add(source);
        
        while (nodeNaoMarcado.size() > 0) {
            Vertice node = getMenor(nodeNaoMarcado);
            nodeMarcado.add(node);
            nodeNaoMarcado.remove(node);
            getMenorDistancia(node);
        }
    }
     
    private List<Vertice> getVizinhos(Vertice node) {
        List<Vertice> vizinhos = new ArrayList<Vertice>();
        
        for (Aresta aresta : arestas) {
            if (aresta.getOrigem().equals(node) && !isMarcado(aresta.getDestino())) {
                vizinhos.add(aresta.getDestino());
            }
        }
        return vizinhos;
    }
     
    private void getMenorDistancia(Vertice node) {
        List<Vertice> nodesAdjacentes = getVizinhos(node);
        for (Vertice alvo : nodesAdjacentes) {
            if (menorDistancia(alvo) > menorDistancia(node) + getDistancia(node, alvo)) {
                custo.put(alvo, menorDistancia(node) + getDistancia(node, alvo));
                precedentes.put(alvo, node);
                nodeNaoMarcado.add(alvo);
            }
        }
    }
    
    private double menorDistancia(Vertice destino) {

    	if (custo.get(destino) == null){
    		return Double.MAX_VALUE;
        } else {
        	return custo.get(destino);
        }
    	
    }
    
    private Vertice getMenor(Set<Vertice> vertices) {
        Vertice minimo = null;
        for (Vertice vertice : vertices) {
            if (minimo == null) {
                minimo = vertice;
            } else {
                if (menorDistancia(vertice) < menorDistancia(minimo)) {
                    minimo = vertice;
                }
            }
        }
        return minimo;
    }
    
    private double getDistancia(Vertice node, Vertice alvo) {
        for (Aresta aresta : arestas) {
            if (aresta.getOrigem().equals(node) && aresta.getDestino().equals(alvo)) {
                return aresta.getCusto();
            }
        }
        throw new RuntimeException("erro");
    }
    
    private boolean isMarcado(Vertice vertice) {
        return nodeMarcado.contains(vertice);
    }
    
    
    
    public ArrayList<Vertice> getCaminho(Vertice alvo) {
    	
        LinkedList<Vertice> path = new LinkedList<Vertice>();
        ArrayList<Vertice> retVert = new ArrayList<>();
        
        Vertice c = alvo;
        
        path.add(c);
        while (precedentes.get(c) != null) {
            c = precedentes.get(c);
            path.add(c);
            
        }
        
        
        Collections.reverse(path);
                
        retVert.addAll(path);
                
        return retVert;
        
    }
}
