package campeonato.equipes;

import campeonato.pistas.Interlagos;

/**
 * Classe responsável por criar os veículos que vazem parte da corrida.
 * 	
 * @author  angelo_foletto
 * @version 3.0
 * @since   2020-11-19
 * @update  2020-11-26
 */
public class Carro extends Thread {

	/**
	 * Declaração de objetos
	 */
	private static Object sync = new Object();
	private StringBuilder sb_atualizacao;
	private StringBuilder sb_chegada;
	
	/**
	 * Declaração de variáveis estáticas
	 */
	private final double MAXIMO_QUILOMETRO_PERCORRER = 5;
	private final double MAXIMO_TEMPO_PIT_STOP = 2000; //Tempo em milisegundos 

	/**
	 * Declaração de variáveis dinâmicas
	 */
	private String nome;
	private double distanciaTotal;
	private double distanciaPercorrida;
	private double distanciaAtual;
	
	//Contador das posições de cada veículo ao cruzar a linha de chegada.
	private static int colocacao; 

	/**
	 * Construtor
	 * 
	 * @param nome String
	 * @param distanciaTotal Integer
	 * 
	 * @author  angelo_foletto
	 * @version 2.5
	 * @since   2020-11-19
	 * @update  2020-11-26
	 */
	public Carro(String nome, int distanciaTotal) {
		this.nome 			= nome;
		this.distanciaTotal = distanciaTotal;
	}
	
	/**
	 * Faz a contabilizacao do avanço (em KM) de um veículo no percurso. 
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.1
	 * @since   2020-11-19
	 */
	private void avancar() {	
		this.distanciaAtual = (Math.random() * this.MAXIMO_QUILOMETRO_PERCORRER);
		this.distanciaPercorrida += this.distanciaAtual;
		
		if (this.distanciaPercorrida > this.distanciaTotal)
			this.distanciaPercorrida = this.distanciaTotal;
	}
		
	/**
	 * Tempo para sincornização das threads ou parada dos veículos no pit stop.
	 * 
	 * @return String
	 * 
	 * @author  angelo_foletto
	 * @version 1.5
	 * @since   2020-11-19
	 * @update  2020-11-26
	 */
	private String pitStop() {
		double tempo = (double) (Math.random() * this.MAXIMO_TEMPO_PIT_STOP);
		try {
			Thread.sleep((long) tempo);
			return pitStopMessenger(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "PitStop fechado";
		}
	}
	
	private String pitStopMessenger(double tempo) {
		StringBuilder sb = new StringBuilder();
		sb.append("--> Carro ");
		sb.append(this.nome);
		sb.append(" fez um pitStop por ");
		sb.append(String.format("%.3f", (tempo / 1000)));
		sb.append(" segundos");
		sb.append(" <--");
		return sb.toString();
	}
	
	/**
	 * Retorna informações dos veículos que cruzaram a linha de chegada
	 * 
	 * @return String
	 * 
	 * @author  angelo_foletto
	 * @version 2.1
	 * @since   2020-11-19
	 * @update  2020-11-26
	 */
	private String chegada() {
		synchronized (sync) {
			this.sb_chegada = new StringBuilder();
			this.sb_chegada.append("-----> Carro ");
			this.sb_chegada.append(this.nome);
			this.sb_chegada.append(" cruzou a linha de chegada em ");
			this.sb_chegada.append(++colocacao); // acomulador da colocação
			this.sb_chegada.append("º lugar!");
			this.sb_chegada.append(" <-----");
		}
		return this.sb_chegada.toString();
	}
	
	/**
	 * Atualização das distâncias percorridas pelos veículos.
	 * 
	 * @return String
	 * 
	 * @author  angelo_foletto
	 * @version 2.1
	 * @since   2020-11-19
	 * @update  2020-11-26
	 */
	private String atualizacao() {
		this.sb_atualizacao = new StringBuilder();
		this.sb_atualizacao.append(this.nome);
		this.sb_atualizacao.append(" deslocou ");
		// Para entregar a distância formtada com duas casas decimais.
		this.sb_atualizacao.append(String.format("%.2f", this.distanciaAtual));
		this.sb_atualizacao.append(" Km.");

		return this.sb_atualizacao.toString();
	}

	/**
	 * Chamada dos métodos
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.2
	 * @since   2020-11-19
	 * @update  2020-11-26
	 */
	@Override
	public void run() {
		while (this.distanciaPercorrida < this.distanciaTotal) {
			avancar();
			//zif (this.distanciaPercorrida < Pista.getPontoDeAtualizacao())
//				atualizacao();
//			else {
			System.out.println(atualizacao());
//				System.out.println(Pista.pontoDeAtualizacao());
//			}
			if ((this.distanciaPercorrida >= Interlagos.getGas() || this.distanciaPercorrida >= Interlagos.getPneu())
					&& Interlagos.listaCarroRemoveUm(this.nome)) {
				System.out.println(pitStop());
				Interlagos.listaCarroVerificacao();
			}
		}
		System.out.println(chegada());
	}
}
