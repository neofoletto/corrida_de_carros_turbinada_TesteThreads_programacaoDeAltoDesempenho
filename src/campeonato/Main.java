package campeonato;

import campeonato.equipes.Carro;
import campeonato.pistas.Pista;

/**
 * Aqui é onde a corrida acontece!
 * 	
 * @author  angelo_foletto
 * @version 4.0
 * @since   2020-11-19
 * @update  2020-12-01
 * */
public class Main {

	public static final String[] NOME_CARRO = {
			  "A"
			, "B"
			, "C"
			, "D"
			, "E"
			//, 'F'
			//, 'G'
			//, 'H'
			};
	
	//Distância do percurso em quilometros
	public static final int DISTANCIA_DA_CORRIDA = 150; 

	/**
	 * Responsável por executar o programa, criando e chamando o objeto para
	 * que a corrida inicie.
	 * 
	 * OBS: clima_pista = "seco" || "seco_molhado" || "molhado".
	 * 
	 * OBS02: Observe que os veículos possuem uma altonomia (com o tanque cheio) 
	 * 150 quilometros nesta pista, com ela estando SECA. Consequentemente, alterando 
	 * as condições desta, reduzirá a autonomia dos veículos, juntamente com a 
	 * durabilidade dos pneus.
	 * 
	 * @param args
	 * 
	 * void
	 * 
	 * @author 	angelo_foletto
	 * @version 2.5
	 * @since 	2020-11-19
	 * @update  2020-12-01
	 */
	public static void main(String[] args) {
		
		final int quantidade_carro = NOME_CARRO.length;
		Carro[] corrida = new Carro[quantidade_carro];
		String clima_pista = 
				"seco";
				//"seco_molhado";
				//"molhado";

		new Pista(clima_pista, quantidade_carro); //Definição do clima (condições) da pista.
				
		System.out.println(" * * * LARGADA * * *");
		System.out.printf("CONDIÇÕES DA PISTA: %s \n", clima_pista);
		System.out.printf("HOJE TEREMOS %d VEÍCULOS NA PISTA!!!\n", quantidade_carro);
		System.out.printf("! ! ! FAÇAM SUAS APOSTAS, LET's ROCK ! ! !");
		//sleep(5000);
		
		for (int x = 0; x < quantidade_carro; x++) {
			corrida[x] = new Carro(NOME_CARRO[x], DISTANCIA_DA_CORRIDA);
			corrida[x].start();
		}
	}

	private static void sleep(int milisegundos) {
		try {
			Thread.sleep(milisegundos);
		} catch (InterruptedException e) {
			System.out.println("Thread is interrupted");
			Thread.currentThread().interrupt();
		}
	}
}
