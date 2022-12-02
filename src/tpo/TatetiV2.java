package tpo;

public class TatetiV2 extends Tateti implements TatetiTDA {

	@Override
	protected void juegaLaMaquina() {

		if (!isPartidaFinalizada()) {
			Move move = findBestMove();
			tablero[move.row][move.col] = turnoMaquina.equals(TurnoJugador.PRIMERO) ? 0 : 1;
		}
		estado = getEstadoPartida();
	}

	private int evaluate() {
		// Checking for Rows for X or O victory.
		for (int row = 0; row < 3; row++) {
			if (tablero[row][0] == tablero[row][1] && tablero[row][1] == tablero[row][2]) {
				if (tablero[row][0] == 0)
					return +10;
				else if (tablero[row][0] == 1)
					return -10;
			}
		}

		// Checking for Columns for X or O victory.
		for (int col = 0; col < 3; col++) {
			if (tablero[0][col] == tablero[1][col] && tablero[1][col] == tablero[2][col]) {
				if (tablero[0][col] == 0)
					return +10;

				else if (tablero[0][col] == 1)
					return -10;
			}
		}

		// Checking for Diagonals for X or O victory.
		if (tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2]) {
			if (tablero[0][0] == 0)
				return +10;
			else if (tablero[0][0] == 1)
				return -10;
		}

		if (tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0]) {
			if (tablero[0][2] == 0)
				return +10;
			else if (tablero[0][2] == 1)
				return -10;
		}

		// Else if none of them have won then return 0
		return 0;
	}

	class Move {
		int row, col;
	};

	// there are no moves left to play.
	private boolean isMovesLeft() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (tablero[i][j] == -1)
					return true;
		return false;
	}

	// This is the minimax function. It considers all
	// the possible ways the game can go and returns
	// the value of the board
	private int minimax(int depth, Boolean isMax) {
		int score = evaluate();

		// If Maximizer has won the game
		// return his/her evaluated score
		if (score == 10)
			return score;

		// If Minimizer has won the game
		// return his/her evaluated score
		if (score == -10)
			return score;

		// If there are no more moves and
		// no winner then it is a tie
		if (isMovesLeft() == false)
			return 0;

		// If this maximizer's move
		if (isMax) {
			int best = -1000;

			// Traverse all cells
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Check if cell is empty
					if (tablero[i][j] == -1) {
						// Make the move
						tablero[i][j] = 0;

						// Call minimax recursively and choose
						// the maximum value
						best = Math.max(best, minimax(depth + 1, !isMax));

						// Undo the move
						tablero[i][j] = -1;
					}
				}
			}
			return best;
		}

		// If this minimizer's move
		else {
			int best = 1000;

			// Traverse all cells
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Check if cell is empty
					if (tablero[i][j] == -1) {
						// Make the move
						tablero[i][j] = 1;

						// Call minimax recursively and choose
						// the minimum value
						best = Math.min(best, minimax(depth + 1, !isMax));

						// Undo the move
						tablero[i][j] = -1;
					}
				}
			}
			return best;
		}
	}

	// This will return the best possible
	// move for the player
	private Move findBestMove() {
		int bestVal = -1000;
		Move bestMove = new Move();
		bestMove.row = -1;
		bestMove.col = -1;

		// Traverse all cells, evaluate minimax function
		// for all empty cells. And return the cell
		// with optimal value.
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// Check if cell is empty
				if (tablero[i][j] == -1) {
					// Make the move
					tablero[i][j] = 0;

					// compute evaluation function for this
					// move.
					int moveVal = minimax(0, false);

					// Undo the move
					tablero[i][j] = -1;

					// If the value of the current move is
					// more than the best value, then update
					// best/
					if (moveVal > bestVal) {
						bestMove.row = i;
						bestMove.col = j;
						bestVal = moveVal;
					}
				}
			}
		}

		//System.out.printf("The value of the best Move " + "is : %d\n\n", bestVal);

		return bestMove;
	}

}
