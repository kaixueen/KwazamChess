// Todo:
// 1. Implement the PieceSelectionListener class to handle the selection of a piece on the game board.
//    - Highlight the selected piece on the game board.
//    - Highlight the possible moves for the selected piece.
// 2. Implement the MoveListener class to handle the movement of a piece on the game board.
// 3. Implement the transformPieceAt method to transform a piece at a given position.
// 4. Update the game after a player perform a move (check if valid, move piece, remove piece, flip screen etc)
// 5. Implement load and store game state methods
// 6. Handle the menu things

package Controller;

import Model.*;
import View.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Process user input and manage game states
public class GameController {
    private GameView gameView;
    private GameBoard gameModel;

    public GameController(GameView gameView, GameBoard gameModel) {
        this.gameView = gameView;
        this.gameModel = gameModel;

        this.gameView.addPieceSelectionListener(new PieceSelectionListener());
        this.gameView.addMoveListener(new MoveListener());
    }

    private class PieceSelectionListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int row = gameView.getRowFromY(y);
            int col = gameView.getColFromX(x);

            gameModel.selectPiece(row, col);

            gameView.highlightSelectedPiece(row, col);
        }
    }

    private class MoveListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            boolean moveSuccessful = gameModel.moveSelectedPiece(x, y);

            if (moveSuccessful) {
                gameView.updateBoard(gameModel.getGameBoard());
            } else {
                gameView.showInvalidMoveMessage();
            }
        }
    }

    private void transformPieceAt(int x, int y) {
        gameModel.transformPieceAt(x, y);
        gameView.updateBoard(gameModel.getGameBoard());
    }
}

