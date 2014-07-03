package estrutura;

import grafo.util.Vertice;

public class Veiculo {
	
	private double consumo; // em KM/L
	private double capTanque; // em litros
	private double tanque; // em litros
	private int tempoDirecao; // em minutos
	private int tempoMaxDir; // em minutos
	private double velocidade; // em Km/h
	private double odometroViagem; // em km
	private double odometroTotal; // em km
	

	public double getConsumo() {
		return consumo;
	}

	public double getCapTanque() {
		return capTanque;
	}

	public double getTanque() {
		return tanque;
	}

	public int getTempoDirecao() {
		return tempoDirecao;
	}

	public double getVelocidade() {
		return velocidade;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	public void setCapTanque(double capTanque) {
		this.capTanque = capTanque;
	}

	public void setTanque(double tanque) {
		this.tanque = tanque;
	}

	public void setTempoDirecao(int tempoDirecao) {
		this.tempoDirecao = tempoDirecao;
	}
	
	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}
		
	public double getOdometroViagem() {
		return odometroViagem;
	}

	public void setOdometroViagem(double odometroViagem) {
		this.odometroViagem = odometroViagem;
	}
	
	public int getTempoMaxDir() {
		return tempoMaxDir;
	}

	public void setTempoMaxDir(int tempoMaxDir) {
		this.tempoMaxDir = tempoMaxDir;
	}
	
	public double getOdometroTotal() {
		return odometroTotal;
	}

	public void setOdometroTotal(double odometroTotal) {
		this.odometroTotal = odometroTotal;
	}

	public Veiculo(double capTanque, double consumo, int tempoDirecao, double velocidade){
		this.capTanque = capTanque;
		this.consumo = consumo;
		this.tempoMaxDir = this.tempoDirecao = tempoDirecao;
		this.velocidade = velocidade;
		this.tanque = this.capTanque;
		this.odometroViagem = 0.0;
	}
	
	// reabastece o tanque completando-o
	public void reabastecer (){
		this.tanque = this.capTanque;
	}
	
	// desconta do tempo de direção e do tanque a km percorrida
	public void rodar(Vertice origem,Vertice destino){
		double km;
		km = calculaDistancia(origem, destino);
		
		// desconta gasolina
		this.tanque = this.tanque - (km / consumo);
		
		// desconta tempo de direcao
		this.tempoDirecao = tempoDirecao - (int)( km / velocidade * 60);
		
		// incrementa odometroViagem
		this.odometroViagem += km;
		
		// incrementa odometroTotal
		this.odometroTotal += km;
	}
	
	public boolean checarTempo(Vertice origem,Vertice destino){
		
		double km;
		km = calculaDistancia(origem, destino);
		
		if (this.tempoDirecao - ( km / velocidade * 60) >= 0 ){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checarTanque(Vertice origem,Vertice destino){
		
		double km;
		km = calculaDistancia(origem, destino);
		
		if (this.tanque - (km / consumo) >= 0 ){
			return true;
		}
		else {
			return false;
		}
	}
	
	public double calculaDistancia(Vertice origem,Vertice destino){
		return (float) Math.sqrt(Math.pow((destino.getPosX() - origem.getPosX()),2) + Math.pow((destino.getPosY() - origem.getPosY()),2));
	}
}
