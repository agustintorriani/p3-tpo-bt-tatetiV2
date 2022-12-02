package tpo;

public class Tateti implements TatetiTDA {

	private TurnoJugador turnoMaquina;
	private int[][] tablero;
	private EstadoPartida estado;
	private String X = "X";
	private String O = "O";
	private int TAM = 3;

	public void Inicializar() {
		tablero = new int[TAM][TAM];
		estado = EstadoPartida.SIN_GANADOR;

		// limpiar tablero
		for (int i = 0; i < TAM; i++) {
			for (int j = 0; j < TAM; j++) {
				tablero[i][j] = -1;
			}
		}

		printInstrucciones();
	}

	public TurnoJugador Turno(TurnoJugador turno) {
		this.turnoMaquina = turno;
		return this.turnoMaquina;
	}

	public boolean Jugar(int posicionJugada) {

		if (posicionJugada > 0) {

			int fila = getFila(posicionJugada);
			int col = getColumna(posicionJugada, fila);
			boolean isPosicionValida = fila >= 0 && col >= 0 && fila < TAM && col < TAM;

			if (isPosicionValida) {

				boolean isPosicionLibre = tablero[fila][col] == -1;

				if (isPosicionLibre) {
					if (EstadoPartida.SIN_GANADOR.equals(estado)) {
						tablero[fila][col] = this.turnoMaquina.equals(TurnoJugador.SEGUNDO) ? 0 : 1;
						estado = getEstadoPartida();
						juegaLaMaquina();
					}
				} else {
					System.out.println("Posicion ocupada, seleccione otra");
				}
			}

		} else {
			// juega primero la maquina
			juegaLaMaquina();
		}

		printTableroActual();
		
		if (isPartidaFinalizada()) {
			printResultadoFinal();
			return true;
		}

		return false;
	}

	private void printInstrucciones() {
		System.out.println("Ingrese un numero que coincida con la posicion del tablero");
		System.out.println("");
		System.out.println("| - | - | - |");
		System.out.println("| 1 | 2 | 3 |");
		System.out.println("| - | - | - |");
		System.out.println("| 4 | 5 | 6 |");
		System.out.println("| - | - | - |");
		System.out.println("| 7 | 8 | 9 |");
		System.out.println("| - | - | - |");
		System.out.println("");
	}

	private void printTableroActual() {
		for (int i = 0; i < TAM; i++) {

			System.out.println("| - | - | - |");

			StringBuilder rowBuilder = new StringBuilder("| ");

			for (int j = 0; j < TAM; j++) {

				if (tablero[i][j] == -1) {
					rowBuilder.append(" ");
				}

				if (tablero[i][j] == 0) {
					rowBuilder.append(X);
				}

				if (tablero[i][j] == 1) {
					rowBuilder.append(O);
				}

				rowBuilder.append(" | ");
			}

			System.out.println(rowBuilder.toString());
		}

		System.out.println("");
	}

	private int getFila(int posicionJugada) {
		return (posicionJugada - 1) / 3;
	}

	private int getColumna(int posicionJugada, int fila) {
		return (posicionJugada - (fila * 3)) - 1;
	}

	private EstadoPartida getEstadoPartida() {
		if (tablero[0][0] != -1 && tablero[0][0] == tablero[1][1] && tablero[0][0] == tablero[2][2]) {
			return resolverEstado(tablero[0][0]);
		}

		if (tablero[0][2] != -1 && tablero[0][2] == tablero[1][1] && tablero[0][2] == tablero[2][0]) {
			return resolverEstado(tablero[0][2]);
		}

		for (int n = 0; n < TAM; n++) {

			if (tablero[n][0] != -1 && tablero[n][0] == tablero[n][1] && tablero[n][0] == tablero[n][2]) {
				return resolverEstado(tablero[n][0]);
			}

			if (tablero[0][n] != -1 && tablero[0][n] == tablero[1][n] && tablero[0][n] == tablero[2][n]) {
				return resolverEstado(tablero[0][n]);
			}
		}

		return EstadoPartida.SIN_GANADOR;
	}

	// Algoritmo minimax
	private boolean isTableroCompleto() {

		for (int i = 0; i < TAM; i++) {
			for (int j = 0; j < TAM; j++) {
				if (tablero[i][j] == -1) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean isPartidaFinalizada() {
		return isTableroCompleto() || !getEstadoPartida().equals(EstadoPartida.SIN_GANADOR);
	}

	private void juegaLaMaquina() {
		if (!isPartidaFinalizada()) {
			int fila = 0;
			int columna = 0;
			int v = Integer.MIN_VALUE;
			int aux;
			for (int i = 0; i < TAM; i++) {
				for (int j = 0; j < TAM; j++) {
					if (tablero[i][j] == -1) {
						tablero[i][j] = 1;
						aux = min();
						if (aux > v) {
							v = aux;
							fila = i;
							columna = j;
						}
						tablero[i][j] = -1;
					}
				}
			}
			tablero[fila][columna] = this.turnoMaquina.equals(TurnoJugador.PRIMERO) ? 0 : 1;
		}
		estado = getEstadoPartida();
	}

	private int max() {
		if (isPartidaFinalizada()) {
			if (!getEstadoPartida().equals(EstadoPartida.SIN_GANADOR))
				return -1;
			else
				return 0;
		}

		int v = Integer.MIN_VALUE;
		int aux;

		for (int i = 0; i < TAM; i++) {
			for (int j = 0; j < TAM; j++) {
				if (tablero[i][j] == -1) {
					tablero[i][j] = 1;
					aux = min();
					if (aux > v)
						v = aux;
					tablero[i][j] = -1;

				}
			}
		}
		return v;
	}

	private int min() {
		if (isPartidaFinalizada()) {

			if (!getEstadoPartida().equals(EstadoPartida.SIN_GANADOR)) {
				return 1;
			} else {
				return 0;
			}
		}

		int v = Integer.MAX_VALUE;
		int aux;

		for (int n = 0; n < TAM; n++) {
			for (int m = 0; m < TAM; m++) {
				if (tablero[n][m] == -1) {
					tablero[n][m] = 0;
					aux = max();
					if (aux < v)
						v = aux;
					tablero[n][m] = -1;
				}
			}
		}
		return v;
	}

	private EstadoPartida resolverEstado(int valor) {

		if (valor == -1) {
			return EstadoPartida.SIN_GANADOR;
		}

		if (TurnoJugador.PRIMERO.equals(turnoMaquina)) {
			if (valor == 0) {
				return EstadoPartida.GANO_LA_MAQUINA;
			} else {
				return EstadoPartida.GANO_EL_HUMANO;
			}
		} else {
			if (valor == 0) {
				return EstadoPartida.GANO_EL_HUMANO;
			} else {
				return EstadoPartida.GANO_LA_MAQUINA;
			}
		}
	}

	private void printResultadoFinal() {
		
		switch (estado) {
			case SIN_GANADOR:
				System.out.print("No hubo ganador 😑");
				break;
				
			case GANO_EL_HUMANO:
				System.out.print("Ganaste! 🤩");
				break;
	
			case GANO_LA_MAQUINA:
				System.out.print("La maquina te gano 😱");
				break;
		}
	}

}
