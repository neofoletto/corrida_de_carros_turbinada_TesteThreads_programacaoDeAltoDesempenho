package campeonato.pistas;

import java.util.ArrayList;

import campeonato.Main;

/**
 * Classe responsável por calcular a durabilidade/autonomia dos pneus e do tanque 
 * (cheio de combustível).
 * 	
 * @author  angelo_foletto
 * @version 1.0
 * @since   2020-11-26
 * @update  2020-12-01
 */
public class Pista {

	/**
	 /**
	 * Declaração de veriáveis estáticias 
	 * 
	 * -- multiplicado definido pelo fabricante de pneus.
	 */
	private final double MULTIPLICADOR_SECO 		   = 1.5;
	private final double MULTIPLICADOR_SECO_MOLHADO = 1.65;
	private final double MULTIPLICADOR_MOLHADO 	   = 1.8;
	// Checagem do avanço de cada veículo ao passar pelo ponto de avanço
	private final static double PONTO_DE_ATUALIZACAO_AVANCO = 25;

	/**
	 * Declaração de veriáveis dinâmicas
	 */
	private static ArrayList<String> veiculoParouPitstop;
	private static ArrayList<String> veiculoPassouPontoVerificacao;
	private static double gas;
	private static double gasFixo;
	private static double pneu;
	private static double pneuFixo;
	private static double pontoDeAtualizacao;
	private static int count;
	private static int numeroCarros;

	/**
	 * Construcor
	 * 
	 * @param key String
	 * 
	 * @author  angelo_foletto
	 * @version 1.2
	 * @since   2020-11-26
	 * @update  2020-12-02
	 */
	public Pista(String key, int numeroCarros) {
		switch (key) {
		case "seco":
			seco();
		case "seco_molhado":
			seco_molhado();
		case "molhado":
			molhado();
		default:
			seco();
		}
		this.pontoDeAtualizacao = 0;
		this.count = 0;
		this.numeroCarros = numeroCarros;
		
		listaCarroPopulacao();
		listaVerificacaoCheckpointIserir();
		fixaValoresGasPneu();
	}

	/**
	 * Retorna valor autonomia do tanque cheio de gás.
	 * 
	 * @return double
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-11-26
	 */
	public static double getGas() {
		return gas;
	}

	/**
	 * Retorna valor de durabilidade do pneu.
	 * @return double
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-11-26
	 */
	public static double getPneu() {
		return pneu;
	}
	
	/**
	 * Retorna valor do ponto de atualização da pista para notificação
	 * de qual veículo cruzou ou encontra-se neste.
	 * @return double
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-11-26
	 */
	public static double getPontoDeAtualizacao() {
		return pontoDeAtualizacao;
	}
	
	/**
	 * Retorna informações sobre as condições da pista seca.
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-11-26
	 */
	private void seco() {
		this.gas  = 150;
		this.pneu = this.gas * this.MULTIPLICADOR_SECO;
	}

	/**
	 * Retorna informações sobre as condições da pista seca/molhada.
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-11-26
	 */
	private void seco_molhado() {
		this.gas  = 120;
		this.pneu = this.gas * this.MULTIPLICADOR_SECO_MOLHADO;
	}

	/**
	 * Retorna informações sobre as condições da pista molhada.
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-11-26
	 */
	private void molhado() {
		this.gas  = 80;
		this.pneu = this.gas * this.MULTIPLICADOR_MOLHADO;
	}	
	
	/**
	 * Atribuí dados a variáveis de apoio para alimentar o contador, tanto de
	 * autonomia em quilometragem e desgaste do pneu.
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-12-01
	 */
	private void fixaValoresGasPneu() {
		//Fixa dados do pneu e gás para persistência.
		this.gasFixo = this.gas;
		this.pneuFixo = this.pneu;
	}
	
	/**
	 * Populando a lista com veículos que ainda não fizeram pitstop.
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-11-26
	 */
	private static void listaCarroPopulacao() {
		veiculoParouPitstop = new ArrayList<String>();
		for (String i : Main.NOME_CARRO)
			veiculoParouPitstop.add(i);
	}

	/**
	 * Remove um veículo da lista que realizou o pitstop.
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-12-01
	 * @update  2020-12-02
	 */
	public static boolean listaCarroRemoveUm(String nome) {
		for (int i = 0; i < veiculoParouPitstop.size(); i++)
			if (veiculoParouPitstop.get(i).equals(nome)) {
				veiculoParouPitstop.remove(i);
				return true;
			}
		return false;
	}
	
	/**
	 * Valida se todos os veículos já pararam no pitstop.
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-12-01
	 * @update  2020-12-02
	 */
	public static void listaCarroVerificacao() {
		if (veiculoParouPitstop.isEmpty()) {
			listaCarroPopulacao();
			gas += gasFixo;
			pneu += pneuFixo;
		}
	}
	
	/**
	 * Retorna informações sobre os pontos de atualização da pista, informando
	 * qual veículo cruzou ou encontra-se neste ponto (quilometro da pista).
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-11-26
	 * @update  2020-12-02
	 */
	public static String pontoDeAtualizacao(String nome) {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append("Checkpoint KM: ");
		sb.append(pontoDeAtualizacao);
		sb.append("  --> Carro ");
		sb.append(nome);
		sb.append("\n");
		return sb.toString();
	}
	
	/**
	 * Populando a lista com veículos que ainda não cruzaram o ponto de verificação.
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-12-01
	 * @update  2020-12-02
	 */
	private static void listaVerificacaoCheckpointIserir() {
		veiculoPassouPontoVerificacao = new ArrayList<String>();
		for (String i : Main.NOME_CARRO)
			veiculoPassouPontoVerificacao.add(i);
	}

	/**
	 * Remove um veículo da lista que cruzou o ponto de verificação.
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-12-01
	 * @update  2020-12-02
	 */
	public static boolean listaVerificacaoCheckpointRemoveUm(String nome) {
		for (int i = 0; i < veiculoPassouPontoVerificacao.size(); i++)
			if (veiculoPassouPontoVerificacao.get(i).equals(nome)) {
				veiculoPassouPontoVerificacao.remove(i);
				return true;
			}
		return false;
	}
	
	/**
	 * Valida se todos os veículos já cruzaram o ponto de verificação.
	 * 
	 * @return void
	 * 
	 * @author  angelo_foletto
	 * @version 1.0
	 * @since   2020-12-01
	 * @update  2020-12-02
	 */
	public static void listaVerificacaoCheckpoint() {
		if (veiculoPassouPontoVerificacao.isEmpty()) {
			listaVerificacaoCheckpointIserir();
			pontoDeAtualizacao += PONTO_DE_ATUALIZACAO_AVANCO;
		}
	}
}
