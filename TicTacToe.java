package com.example.tictactoegame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {
    private Button buttons[][] = new Button[3][3];
    private Label playerXscoreLabel, playerOscoreLabel;
    private boolean playerXturn = true;

    // Private variables to store scores
    private int playerXScore = 0;
    private int playerOScore = 0;

    private BorderPane createContent() {
        BorderPane root = new BorderPane();
        // Add padding to the root pane
        root.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Tic Tac Toe");
        // Increased font size to 35 as requested
        titleLabel.setStyle("-fx-font-size : 35pt; -fx-font-weight : bold;");
        root.setTop(titleLabel);

        // Center the title in the Top region
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        // Board
        GridPane gridPane = new GridPane();
        // Add gaps to the grid pane
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        // Center the grid pane
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setPrefSize(100, 100);
                button.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold;");
                button.setOnAction(event -> buttonClicked(button));
                buttons[i][j] = button;
                gridPane.add(button, j, i);
            }
        }
        root.setCenter(gridPane);

        // Scoreboard
        HBox scoreboard = new HBox(20);
        // Center the scoreboard content
        scoreboard.setAlignment(Pos.CENTER);

        playerXscoreLabel = new Label("Player X : 0");
        playerXscoreLabel.setStyle("-fx-font-size : 18pt; -fx-font-weight : bold;");
        playerOscoreLabel = new Label("Player O : 0");
        playerOscoreLabel.setStyle("-fx-font-size : 18pt; -fx-font-weight : bold;");
        scoreboard.getChildren().addAll(playerXscoreLabel, playerOscoreLabel);

        root.setBottom(scoreboard);
        // Center the scoreboard in the Bottom region
        BorderPane.setAlignment(scoreboard, Pos.CENTER);

        return root;
    }

    private void buttonClicked(Button button) {
        if (button.getText().equals("")) {
            if (playerXturn) {
                button.setText("X");
            } else {
                button.setText("O");
            }
            playerXturn = !playerXturn;

            checkWinner();
        }
    }

    private void checkWinner() {
        // Check Rows
        for (int row = 0; row < 3; row++) {
            if (buttons[row][0].getText().equals(buttons[row][1].getText()) &&
                    buttons[row][1].getText().equals(buttons[row][2].getText()) &&
                    !buttons[row][0].getText().isEmpty()) {

                String winner = buttons[row][0].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }

        // Check Columns
        for (int col = 0; col < 3; col++) {
            if (buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                    buttons[1][col].getText().equals(buttons[2][col].getText()) &&
                    !buttons[0][col].getText().isEmpty()) {

                String winner = buttons[0][col].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }

        // Check Diagonals
        // Top-Left to Bottom-Right
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().isEmpty()) {

            String winner = buttons[0][0].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }

        // Top-Right to Bottom-Left
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][0].getText()) &&
                !buttons[0][2].getText().isEmpty()) {

            String winner = buttons[0][2].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }

        // Check for Tie
        checkTie();
    }

    private void checkTie() {
        boolean full = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    full = false;
                    break;
                }
            }
        }

        if (full) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Game Over! It's a Tie.");
            alert.showAndWait();
            resetBoard();
        }
    }

    private void showWinnerDialog(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner!");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations " + winner + "! You won the game.");
        alert.showAndWait();
    }

    // Method to update score
    private void updateScore(String winner) {
        if (winner.equals("X")) {
            playerXScore++;
            playerXscoreLabel.setText("Player X : " + playerXScore);
        } else {
            playerOScore++;
            playerOscoreLabel.setText("Player O : " + playerOScore);
        }
    }

    // Method to reset board
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        // X usually starts first after reset
        playerXturn = true;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Tic Tac Toe Game!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}