package tpo;

import java.util.Scanner;

public class Main {

	private static final int POSICION_INICIO_MAQUINA = 0;

	public static void main(String[] args) {

		TatetiTDA tateti = new Tateti();
		
		tateti.Inicializar();
		
		TurnoJugador maquina = tateti.Turno(TurnoJugador.PRIMERO);

		int position;

		if (TurnoJugador.PRIMERO.equals(maquina)) {
			position = POSICION_INICIO_MAQUINA;
		} else {
			position = realizarJugada();
		}

		while (!tateti.Jugar(position)) {
			position = realizarJugada();
		}

	}

	private static int realizarJugada() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese posicion:");
		
		try {
			int valor = Integer.parseInt(scanner.nextLine());
			if (valor < 0 || valor > 9) { //LUQUI
				printMensaje("Posicion invalida, vuelva a intentar.");
				return realizarJugada();
			}
			return valor;

		} catch (Exception e) {

			printMensaje("Jugada invalida, vuelva a intentar.");
			return realizarJugada();
		}
	}
	
	private static void printMensaje(String msj) {
		System.out.println("");
		System.out.println(msj);
		System.out.println("");
	}

}
