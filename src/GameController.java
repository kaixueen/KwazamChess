// Process user input and manage game states
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

