
package exe;

import grafo.util.Dijkstra;
import grafo.util.Grafo;
import grafo.util.Vertice;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import estrutura.Veiculo;

public class Exe {
	
	private static double valorComb;
	
     public static void main(String[] args) throws FileNotFoundException, IOException {
    	
        Scanner ler = new Scanner(System.in);
        String[] pontosPass;
        ArrayList<Integer> pontos = new ArrayList<>();
        //int origem,destino;
        Grafo g = new Grafo();
        leArquivo(g);
        //System.out.println(g);
        
        
        System.out.print("Digite os pontos de passagem separados por vírgula: ");
        pontosPass = ler.next().split(",");
        System.out.print("Digite o preço do combustível: ");
        try{
        	valorComb = Double.parseDouble(ler.next());
        }
        catch(Exception ex){
        	System.out.println("Entre com um valor Válido!");
        	valorComb = 0;
        }
                
        for (int i = 0;i < pontosPass.length;i++){
        	pontos.add(Integer.parseInt(pontosPass[i]));
        }
        
        
        
        calculaCaminho(g,pontos);
        
        System.out.print("\n\n... processamento finalizado !\n");
        ler.close();
    }
           
    public static void calculaCaminho(Grafo g,ArrayList<Integer> pts){
    	
        List<Vertice> nodes = g.getVertices();
        Dijkstra d = new Dijkstra(g);
        ArrayList<Integer> trajeto = new ArrayList<>(); // guarda o trajeto final a ser percorrido
        ArrayList<Vertice> caminho; // guarda o caminho de um ponto ao outro
        int pos = -1;
        Double custoPedagio = 0.0;
        Double menorCusto = null;
        Double custoTotal;
        
        // cria um novo objeto carro
        //capTanque (litros),consumo (km/litro),tempoVolante (minutos), velocidade (km/h)
        Veiculo veiculo = new Veiculo(40.0,15.0,180,80.0);
        
        // inicializa trajeto com o ponto de origem
        trajeto.add(0,pts.get(0));
        pts.remove(0);
        
        while (pts.size() > 1){
        	
        	for (int i = 0; i <= pts.size() - 2;i++){
        		
            	d.calcula(nodes.get(trajeto.get(trajeto.size() - 1)));
            	caminho = d.getCaminho(nodes.get(pts.get(i)));
                custoPedagio = d.custo.get(caminho.get(caminho.size() - 1));
        		custoTotal = veiculo.calculaDistancia(nodes.get(trajeto.get(trajeto.size() - 1)), nodes.get(pts.get(i))) * valorComb  + custoPedagio;
        		        		
        		if (menorCusto == null || custoTotal < menorCusto){
        			pos = i;
        			menorCusto = custoTotal;
        		}
        	}
        	
        	trajeto.add(pts.get(pos));
        	pts.remove(pos);
        	menorCusto = null;
        }
        
        // adiciona o destino no final
        trajeto.add(pts.get(pts.size() - 1));
        
            
        System.out.println("\n---- CALCULANDO A ROTA MAIS EFICIENTE -------------------\n");
        
        int t = 0;
        
        System.out.print("Rota traçada: ");
        for (t= 0; t< trajeto.size();t++){
        	
        	if (t != 0){
        		System.out.print(" -> ");
        	}
        	
        	System.out.print(trajeto.get(t));
        }
        System.out.print("\n");
        
        System.out.println("\n---- SIMULANDO VIAGEM  -------------------");
        System.out.println("Dados do veículo:");
        System.out.println("Capacidade do tanque: "+ round(veiculo.getCapTanque(),3) +" Litros");
        System.out.println("Nível do tanque: "+ round(veiculo.getTanque(),3) +" Litros");
        System.out.println("Odômetro de viagem: "+ round(veiculo.getOdometroViagem(),3) +" KM \n\n\n");
        
        double totalPedagio = 0.0;
        @SuppressWarnings("unused")
		double totalCombustivel = 0.0;
                
        for (t=0;t<trajeto.size() - 1;t++){
        	
        	System.out.println("---------------------------------------------------------------> PARTIDA: " + nodes.get(trajeto.get(t)).getNome() + " DESTINO:" + nodes.get(trajeto.get(t + 1)).getNome() +" <--------" );
        	
        	d.calcula(nodes.get(trajeto.get(t)));
        	caminho = d.getCaminho(nodes.get(trajeto.get(t + 1)));
            custoPedagio = d.custo.get(caminho.get(caminho.size() - 1));
        
            int c = 0;
            
	        do {
	        	
	        	if (c == caminho.size() - 1){
	
	        		double dist = round(veiculo.getOdometroViagem(),3);
	        		double comb = round(dist / veiculo.getConsumo(),3);
	        		double ped = round(custoPedagio,2);
	        		double custComb = round((valorComb * comb),2);
	        		
	        		System.out.println("\n---- PARCIAIS -----");
	        		System.out.println("Km percorridos: " + dist);
	        		System.out.println("Foram utilizados: " + comb + " litros de combustivel");
	        		System.out.println("Custo com pedágio: R$ " + ped);
	        		System.out.println("Custo com combustível: R$ " + custComb);
	        		System.out.println("\nCUSTO PARCIAL: R$ " + round((custComb + ped),2));
	
	        		totalPedagio += ped;
	        		totalCombustivel += comb;
	        		
	        		break;
	        	}
	        		
	    		if (veiculo.checarTanque(caminho.get(c), caminho.get(c + 1))){
	    			if (veiculo.checarTempo(caminho.get(c), caminho.get(c + 1))){
	    				System.out.println("Do ponto "+ caminho.get(c).getNome() + " ate o ponto "+ caminho.get(c + 1).getNome()); 
	    				veiculo.rodar(caminho.get(c), caminho.get(c + 1));
	    				c++;
	    			}
	    			else {
	    				if (veiculo.getTempoDirecao() == veiculo.getTempoMaxDir()){
	    					System.out.println("---> DISTÂNCIA IMPOSSÍVEL DE SER ALCANÇADA NO TEMPO EXTIPULADO VIAJANDO NA ATUAL VELOCIDADE, AUMENTE A VELOCIDADE OU O TEMPO AO VOLANTE <------");
	    					System.exit(-1);
	    				}
	    				System.out.println("---> PARADA para descançar, ponto: "+ caminho.get(c).getNome());
	    				veiculo.setTempoDirecao(180);
	    			}
	    		}
	    		else {
	    			System.out.println("---> PARADA para abastecer, ponto: "+ caminho.get(c).getNome());
	    			veiculo.reabastecer();
	    		}
	        
	        } while (c < caminho.size());

	        // zero odometro do carro para prox trecho
	        veiculo.setOdometroViagem(0.0);
	        
        }

        System.out.println("\n\n\n\n--------------------------> CUSTOS TOTAIS <---------------------------------------");
        System.out.println(" Total rodado: "+ round(veiculo.getOdometroTotal(),3) + " km");
        System.out.println(" Combustivel: R$ " + round(veiculo.getOdometroTotal()/veiculo.getConsumo() * valorComb,2));
        System.out.println(" Pedágio: R$ " + round(totalPedagio,2));
        System.out.println("\n\nVALOR TOTAL: R$ "+ round((veiculo.getOdometroTotal()/veiculo.getConsumo() * valorComb) + totalPedagio,2));
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
     
    public static void leArquivo(Grafo grafo) throws FileNotFoundException, IOException{
    	    	
    	ArrayList<Vertice> lstVert = new ArrayList<>();
    	
        FileInputStream fstream = new FileInputStream("grafo.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String linha;
        char dataType = ' ';
        String valor[];

        while ((linha = br.readLine()) != null)   {
        	
        	// se linha estiver vazia ignora
        	if (linha.contentEquals("")){
        		continue;
        	}
        	
        	if (linha.contentEquals("vertices")){
        		dataType = 'v';
        		continue;
        	}
        	
        	if (linha.contentEquals("arestas")){
        		dataType = 'a';
        		continue;
        	}
        	
        	
            valor = linha.split(" ");
        	
        	if (dataType ==  'v'){ // lendo vértices
        		lstVert.add(grafo.addVertice(Integer.parseInt(valor[0]),Double.parseDouble(valor[1]),Double.parseDouble(valor[2])));
        	}
        	else if (dataType ==  'a') { // lendo arestas
        		grafo.addAresta(lstVert.get(Integer.parseInt(valor[0])),lstVert.get(Integer.parseInt(valor[1])),Double.parseDouble(valor[2]));
        	}
        	
        }


        br.close();
    }
}
