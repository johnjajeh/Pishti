import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {
    public void start(Stage primaryStage) {
        
        int fontSize = 18;
        
        //Declare Panes and Nodes
        HBox mainBox = new HBox();
        VBox cardsBox = new VBox();
        HBox buttons = new HBox();
        VBox scoreBox = new VBox();
        HBox computerCardsBox = new HBox();
        HBox centerCardsBox = new HBox();
        HBox userCardsBox = new HBox();
        VBox gameOverBox = new VBox();
        Button restart = new Button("Restart");

        Button[] cards = new Button[4];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Button();
        }

        Label computerLabel = new Label("Computer");
        computerLabel.setUnderline(true);
        computerLabel.setFont(new Font(fontSize));

        Label computerScore = new Label("Score: 0");
        computerScore.setFont(new Font(fontSize));

        Text end = new Text();

        Label centerLabel = new Label("Center Pile.");
        centerLabel.setFont(new Font(fontSize));

        Label userLabel = new Label("User");
        userLabel.setFont(new Font(fontSize));
        userLabel.setUnderline(true);

        Label userScore = new Label("Score: 0");
        userScore.setFont(new Font(fontSize));
        Scene primary = new Scene(mainBox);

        //Declare Game Objects
        Deck main = new Deck();

        //VERY IMPORTANT INITIALIZE PILE FIRST BEFORE PLAYERS
        //(needed to correctly handle having no Jacks on the top of the initial pile)
        Pile pile = new Pile(main);
        Player user = new Player(main, false);
        Player computer = new Player(main, true);

        //Populate Panes
        gameOverBox.getChildren().addAll(end, restart);
        buttons.getChildren().addAll(cards[0], cards[1], cards[2], cards[3]);
        mainBox.getChildren().addAll(cardsBox, scoreBox);
        cardsBox.getChildren().addAll(computerCardsBox, centerCardsBox, userCardsBox,  buttons);
        scoreBox.getChildren().addAll(computerLabel, computerScore, userLabel, userScore);
        for (int i = 0; i < user.getHand().size(); i++) {
            userCardsBox.getChildren().add(user.getHand().get(i));
        }

        for (int i = 0; i < computer.getHand().size(); i++) {
            computerCardsBox.getChildren().addAll(new ImageView ("card/b1fv.png"));
        }

        centerCardsBox.getChildren().add(pile.getTop());

        //Pane Modifications
        mainBox.setAlignment(Pos.CENTER);
        gameOverBox.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        centerCardsBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < 4; i++) {
            cards[i].setMinWidth(72);
            cards[i].setMaxWidth(80);
            cards[i].setText(user.getHand().get(i).getName());
        }

        //Staging of Scene
        primaryStage.setScene(primary);
        primaryStage.setTitle("Pishti!");
        primaryStage.setResizable(false);
        primaryStage.show();

        // Event Handlers
        cards[0].setOnAction(e ->{
            eventHandling(pile, computer, user, mainBox,
                    main, userCardsBox, computerCardsBox,
                    computerScore, userScore, end,
                    scoreBox, centerCardsBox, cards, gameOverBox,restart, 0);
        });
        cards[1].setOnAction(e ->{
            eventHandling(pile, computer, user, mainBox,
                    main, userCardsBox, computerCardsBox,
                    computerScore, userScore, end,
                    scoreBox, centerCardsBox, cards, gameOverBox,restart, 1);
        });
        cards[2].setOnAction(e ->{
            eventHandling(pile, computer, user, mainBox,
                    main, userCardsBox, computerCardsBox,
                    computerScore, userScore, end,
                    scoreBox, centerCardsBox, cards, gameOverBox, restart, 2);
        });
        cards[3].setOnAction(e ->{
            eventHandling(pile, computer, user, mainBox,
                    main, userCardsBox, computerCardsBox,
                    computerScore, userScore, end,
                    scoreBox, centerCardsBox, cards, gameOverBox, restart, 3);
        });

        //Resets all game objects and updates the playing field and score.
        restart.setOnAction(e ->{
            main.Reload();
            pile.Reset(main);
            user.setScore(0);
            computer.setScore(0);
            user.Reset(main);
            computer.Reset(main);
            centerCardsBox.getChildren().clear();
            centerCardsBox.getChildren().add(pile.getTop());
            userScore.setText("Score: " + user.getScore());
            computerScore.setText("Score: " + computer.getScore());
            mainBox.getChildren().clear();
            mainBox.getChildren().addAll(cardsBox, scoreBox);
            computerCardsBox.getChildren().clear();
            centerCardsBox.getChildren().clear();
            userCardsBox.getChildren().clear();

            for (int i = 0; i < computer.getHand().size(); i++) {
                computerCardsBox.getChildren().addAll(new ImageView ("card/b1fv.png"));
            }

            centerCardsBox.getChildren().add(pile.getTop());
            
            for (int i = 0; i < user.getHand().size(); i++) {
                userCardsBox.getChildren().addAll(user.getHand().get(i));
                cards[i].setText(user.getHand().get(i).getName());
                cards[i].setVisible(true);
            }
        });
    }

    public void movementComputerCard(HBox computerCardsBox, Player computer)
    {
        if (computerCardsBox.getChildren().size() > 1) {
            computerCardsBox.getChildren().remove(0);
        }
        else {
            for (int i = 0; i < computer.getHand().size() - 1; i++) {
                computerCardsBox.getChildren().addAll(new ImageView ("card/b1fv.png"));
            }
        }
    }

    /*
    When a button is clicked an event is fired that "plays" the card represented by the button, and
    conducts the computers turn as well. This event also checks for when the game is over
    (Deck empty and no cards in the players hand)
     */
    public void eventHandling(Pile pile, Player computer, Player user, HBox mainBox,
                              Deck main, HBox userCardsBox, HBox computerCardsBox,
                              Label computerScore, Label userScore, Text end,
                              VBox scoreBox, HBox centerCardsBox, Button[] cards,
                              VBox gameOverBox, Button restart, int index) {
        // Play user's chosen card
        pile.add(user.Play_Card(main, index), user);

        // Conduct computers turn
        pile.add(computer.Play_Card(main, pile), computer);

        // Update the players hand (remove button from used card and re-display the user's new hand)
        userCardsBox.getChildren().clear();
        for (int i = 0; i < 4; i++) {
            cards[i].setVisible(false);
        }

        for (int i = 0; i < user.getHand().size(); i++) {
            userCardsBox.getChildren().addAll(user.getHand().get(i));
            cards[i].setText(user.getHand().get(i).getName());
            cards[i].setVisible(true);
        }

        //Update playing space
        centerCardsBox.getChildren().clear();
        centerCardsBox.getChildren().add(pile.getTop());
        userScore.setText("Score: " + user.getScore());
        computerScore.setText("Score: " + computer.getScore());
        movementComputerCard(computerCardsBox, computer);
        
        //End of Game Check
        if (main.getSize() == 0 && user.getHand().size() == 0) {
            if (user.getNumberOfCardsWon() > computer.getNumberOfCardsWon()) {
                user.setScore(user.getScore() + 3);
            } else if (user.getNumberOfCardsWon() < computer.getNumberOfCardsWon()) {
                computer.setScore(computer.getScore() + 3);
            }
            
            if (pile.isLast_computer()) {
                computer.setScore(computer.getScore() + pile.Calculate());
                computerScore.setText("Score: " + (computer.getScore()));
                userScore.setText("Score: " + (user.getScore()));
            } else {
                user.setScore(user.getScore() + pile.Calculate());
                userScore.setText("Score: " + (user.getScore()));
                computerScore.setText("Score: " + (computer.getScore()));
            }
            
            if (user.getScore() > computer.getScore()) {
                end.setText("You Win!");
                gameOverBox.getChildren().clear();
                gameOverBox.getChildren().addAll(end,restart,new ImageView("card/winning2.gif"));
            } else if (user.getScore() < computer.getScore()) {
                end.setText("You Lose!");
                gameOverBox.getChildren().clear();
                gameOverBox.getChildren().addAll(end, restart);
            } else {
                end.setText("You Tie!");
                gameOverBox.getChildren().clear();
                gameOverBox.getChildren().addAll(end,restart);
            }

            mainBox.getChildren().clear();
            mainBox.getChildren().addAll(gameOverBox, scoreBox);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}