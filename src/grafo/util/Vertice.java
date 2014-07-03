
package grafo.util;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
        
        int nome;
        List<Aresta> listaAdj;
		private double posX;
        private double posY;
        
        public double getPosX() {
			return posX;
		}

		public double getPosY() {
			return posY;
		}

		public void setPosX(double posX) {
			this.posX = posX;
		}

		public void setPosY(double posY) {
			this.posY = posY;
		}


        public Vertice(int nome,double posX, double posY) {
            this.nome = nome;
            this.listaAdj = new ArrayList<Aresta>();
            this.posX = posX;
            this.posY = posY;
        }

        public void adicionaLista(Aresta a) {
            listaAdj.add(a);
        }
        public int getNome(){
            return this.nome;
        }
}
 