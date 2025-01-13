package Controller;

import Model.*;
import Util.Position;
import View.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

import static Util.Consts.*;

// Process user input and manage game states
public class GameController {
    private GameView gameView;
    private GameBoard gameBoard;

    private ArrayList<Position> possibleMoves;

    public GameController(GameView gameView, GameBoard gameBoard) {
        this.gameView = gameView;
        this.gameBoard = gameBoard;

        gameView.addPieceListener(new PieceListener(this));
        gameView.addMenuListener(new MenuListener());
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
            gameView.pieceOnHover(position, gameBoard.getCurrentPlayer());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton exitedButton = (JButton) e.getSource();
            Position position = gameView.findButtonPosition(exitedButton);
            gameView.pieceOffHover(position);
        }
    }

    public void handlePieceSelection(Position position) {
        Piece selectedPiece = gameBoard.getPieceAt(position);
        if (selectedPiece == null || !selectedPiece.getColor().equals(gameBoard.getCurrentPlayer())) {
            return;
        }
        // Unselect the first piece if the second piece is not the same as the first piece
        if (!gameBoard.getSelectedPieces().isEmpty() && gameBoard.getSelectedPieces().get(0) != selectedPiece) {
            gameView.pieceOnClick(gameBoard.getSelectedPieces().get(0).getPosition());
            gameView.unhighlightPossibleMoves(possibleMoves);
            gameBoard.getSelectedPieces().clear();
        }

        // If the selected piece is already selected, unselect it
        if(!gameBoard.getSelectedPieces().isEmpty() && gameBoard.getSelectedPieces().get(0) == selectedPiece) {
            gameBoard.getSelectedPieces().remove(selectedPiece);
            gameView.pieceOnClick(position);
            possibleMoves = gameBoard.getPossibleMoves(selectedPiece);
            gameView.removeMoveListener(possibleMoves, new PieceListener(this));
            gameView.unhighlightPossibleMoves(possibleMoves);
        } else {
            // Select the piece
            gameBoard.getSelectedPieces().add(selectedPiece);
            gameView.pieceOnClick(position);
            possibleMoves = gameBoard.getPossibleMoves(selectedPiece);
            gameView.addMoveListener(possibleMoves, new MoveListener(this));
            gameView.highlightPossibleMoves(possibleMoves, gameBoard.getCurrentPlayer());
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
        Piece selectedPiece = gameBoard.getSelectedPieces().get(0);
        gameView.pieceOnClick(selectedPiece.getPosition());
        gameView.movePiece(selectedPiece.getPosition(), position);
        if (gameBoard.isEmpty(position.getX(), position.getY())) {
            gameBoard.movePieceTo(selectedPiece.getPosition(), position);
        } else {
           gameBoard.capturePiece(selectedPiece.getPosition(), position);
        }

        if (selectedPiece.isIconNeedToFlip()) {
            gameView.rotateIcon(selectedPiece.getPosition());
            selectedPiece.setIconNeedToFlip(false);
        }

        gameBoard.getSelectedPieces().clear();
        clearMoveListeners();
        String winner = gameBoard.determineWinConditions();
        if (gameBoard.isGameOver()) {
            gameView.removePieceListener();
            new GameOverView(winner, new RestartListener());
            return;
        }

        // Add a delay before flipping the board
        Timer timer = new Timer(800, e -> {
            gameView.flipBoard(); // Flip the board after 1 second
            if (gameBoard.getCurrentTurn() % 4 == 0) {
                ArrayList<Position> transformedPieces = gameBoard.getTorXorPosition();
                for (Position pos : transformedPieces) {
                    gameBoard.transformPieceAt(pos);
                    gameView.transformPieceAt(pos);
                }
            }
            gameBoard.switchTurn();
            gameView.updateTurn(gameBoard.getCurrentPlayer(), (gameBoard.getCurrentTurn() + 1) / 2);

        });
        timer.setRepeats(false); // Configure the timer
        timer.start();           // Start the timer
    }

    public void clearMoveListeners() {
        gameView.removeMoveListener(possibleMoves, new PieceListener(this));
        gameView.unhighlightPossibleMoves(possibleMoves);
    }


    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameView.clickMenu(new SaveListener(), new LoadListener(), new RestartListener());
        }
    }

    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    ArrayList<String> gameState = gameBoard.saveBoardState(); // Assume gameBoard has this method
                    String gameStateStr = String.join("", gameState);
                    writer.write(gameStateStr);
                    JOptionPane.showMessageDialog(null, "Game saved successfully!");
                } catch (IOException ie) {
                    JOptionPane.showMessageDialog(null, "Error saving game: " + ie.getMessage());
                }
            }
            SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
        }
    }

    private class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    if (!gameBoard.getSelectedPieces().isEmpty()) {
                        handlePieceSelection(gameBoard.getSelectedPieces().get(0).getPosition());
                        gameBoard.getSelectedPieces().clear();
                    }

                    gameView.removePieceListener();

                    int count = 0;
                    String[][] gameState = new String[ROWS+2][COLUMNS];
                    String line;
                    String pieces[];
                    while ((line = reader.readLine()) != null) {
                        // read next line
                        pieces = line.split(" ");
                        gameState[count] = pieces;
                        count++;
                    }
                    gameView.loadGame(gameState);
                    gameView.addPieceListener(new PieceListener(GameController.this));
                    gameBoard.loadBoardState(gameState);
                    JOptionPane.showMessageDialog(null, "Game loaded successfully!");
                } catch (IOException ie) {
                    JOptionPane.showMessageDialog(null, "Error loading game: " + ie.getMessage());
                }
            }
            SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
        }
    }

    private class RestartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameBoard.getSelectedPieces().isEmpty()) {
                handlePieceSelection(gameBoard.getSelectedPieces().get(0).getPosition());
                gameBoard.getSelectedPieces().clear();
            }

            gameView.clearBoard();
            gameView.removePieceListener();
            if (gameView.isFlipped())
                gameView.flipBoard();

            gameBoard.initializeBoard();
            gameView.initPosition();
            gameView.addPieceListener(new PieceListener(GameController.this));
            SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
        }
    }
}