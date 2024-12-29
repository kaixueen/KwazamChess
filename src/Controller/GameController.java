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
import java.util.ArrayList;

import javax.swing.*;


// Process user input and manage game states
public class GameController {
    private GameView gameView;
    private GameBoard gameModel;
    private ArrayList<Piece> selectedPieces;
    private ArrayList<Position> possibleMoves;


    public GameController(GameView gameView, GameBoard gameModel) {
        this.gameView = gameView;
        this.gameModel = gameModel;
        selectedPieces = new ArrayList<>();
        gameView.addPieceListener(new PieceListener(this));
    }

    private class PieceListener extends MouseAdapter {
        private GameController controller;

        public PieceListener(GameController controller) {
            this.controller = controller;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            Position position = gameView.findButtonPosition(clickedButton);
            controller.handlePieceSelection(position);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton enteredButton = (JButton) e.getSource();
            Position position = gameView.findButtonPosition(enteredButton);
            gameView.pieceOnHover(position, gameModel.getCurrentPlayer());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton exitedButton = (JButton) e.getSource();
            Position position = gameView.findButtonPosition(exitedButton);
            gameView.pieceOffHover(position);
        }
    }

    public void handlePieceSelection(Position position) {
        Piece selectedPiece = gameModel.getPieceAt(position);
        if (selectedPiece.getColor() != gameModel.getCurrentPlayer()) {
            return;
        }
        // Unselect the first piece if the second piece is not the same as the first piece
        if (!selectedPieces.isEmpty() && selectedPieces.getFirst() != selectedPiece) {
            gameView.pieceOnClick(selectedPieces.getFirst().getPosition());
            gameView.unhighlightPossibleMoves(possibleMoves);
            selectedPieces.clear();
        }

        if (selectedPiece != null) {
            // If the selected piece is already selected, unselect it
            if(!selectedPieces.isEmpty() && selectedPieces.getFirst() == selectedPiece) {
                selectedPieces.remove(selectedPiece);
                gameView.pieceOnClick(position);
                possibleMoves = gameModel.getPossibleMoves(selectedPiece);
                gameView.removeMoveListener(possibleMoves, new MoveListener(this));
                gameView.unhighlightPossibleMoves(possibleMoves);
            } else {
                // Select the piece
                selectedPieces.add(selectedPiece);
                gameView.pieceOnClick(position);
                possibleMoves = gameModel.getPossibleMoves(selectedPiece);
                gameView.addMoveListener(possibleMoves, new MoveListener(this));
                gameView.highlightPossibleMoves(possibleMoves, gameModel.getCurrentPlayer());
            }
        }
    }

    private class MoveListener extends MouseAdapter {
        private GameController controller;

        public MoveListener(GameController controller) {
            this.controller = controller;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            Position position = gameView.findButtonPosition(clickedButton);
            controller.handleMove(position);
        }
    }

    public void handleMove(Position position) {
        Piece selectedPiece = selectedPieces.getFirst();
        gameView.pieceOnClick(selectedPiece.getPosition());
        gameView.movePiece(selectedPiece.getPosition(), position);
        if (gameModel.isEmpty(position.getX(), position.getY())) {
            gameModel.movePieceTo(selectedPiece.getPosition(), position);
        } else {
           gameModel.capturePiece(selectedPiece.getPosition(), position);
        }

        selectedPieces.clear();
        clearMoveListeners();
        gameModel.determineWinConditions();
        if (gameModel.isGameOver()) {
            System.out.println("Game Over");
        }

        // Add a delay before flipping the board
        Timer timer = new Timer(800, e -> {
            gameView.flipBoard(); // Flip the board after 1 second
            gameModel.switchTurn();
            gameView.updateTurn(gameModel.getCurrentPlayer());
        });
        timer.setRepeats(false); // Configure the timer
        timer.start();           // Start the timer

    }

    public void clearMoveListeners() {
        gameView.removeMoveListener(possibleMoves, new PieceListener(this));
        gameView.unhighlightPossibleMoves(possibleMoves);
    }


}