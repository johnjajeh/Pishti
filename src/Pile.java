import javafx.scene.image.ImageView;
import java.util.Stack;

/**
 * The Pile class represents the stack of cards that have been "played" during the current cycle.
 * This class ultimately determines the correct amount of points to distribute to a player when a score-able
 * event has occurred. The Pile is represented by a stack, where the top of the stack represents the card
 * that has most recently been played.
 */

public class Pile extends ImageView {
    private Stack<Card> pile = new Stack<>();
    private int worth;
    
    //Boolean is important to determine who gets the remaining cards in the pile after the game ends
    private boolean last_computer = false;
    
    //Turns is needed to assure that no points can be scored on the first turn
    private double turns = 0;

    public Pile(Deck deck) {
        //Initialize the first four cards that are in the pile at the start of the game.
        for (int count = 0; count < 4; count++) {
            pile.push(deck.Draw());
        }
        
        //If the top card is a jack, reload the deck and draw again.
        //(Jack can not be on the top at the start of the game)
        while (pile.peek().getCard_type() == 11) {
            pile.clear();
            deck.Reload();
            
            for (int count = 0; count < 4; count++) {
                pile.push(deck.Draw());
            }
        }
    }

    public boolean isLast_computer() { return last_computer; }

    /*
    This is where most point calculations are made.
    First the function checks to see if the pile is empty or it is the first
    turn of the game. If so the card is added to the pile. Otherwise the function
    begins to check if the card played will result in points.
    */
    public Stack<Card> add(Card card, Player player) {
        
        turns += 0.5;
        if (pile.size() == 0 || this.turns < 1) {
            pile.push(card);
            return pile;
        }

        //If card equals the last played card or a jack, points should be allotted
        if (pile.peek().Compare(card) || card.getCard_type() == 11) {
            //Determine who is playing a card
            if (player.isComputer()) {
                this.last_computer = true;
            }
            else {
                this.last_computer = false;
            }

            //Check if there is a Pishti
            if (pile.size() == 1 && (card.getCard_type() == pile.peek().getCard_type())) {
                //Check if there is a Jack Pishti
                if (card.getCard_type() == 11) {
                    this.worth = 20;
                } else {
                    this.worth = 10;
                }
            } else { 
                this.worth = Calculate() + card.getValue();
            }

            //Update the number of cards won by the Player
            player.setNumberOfCardsWon(player.getNumberOfCardsWon() + (this.getSize() + 1));
            
            //Clear out the pile and give points to the player
            pile.clear();
            player.addPoints(worth);
            worth = 0;
            return pile;
        } else {
            pile.push(card);
            return pile;
        }
    }

    public int Calculate() {
        //Standard value calculation. Adds up all point values of cards in the pile.
        int sum = 0;
        for (Card card: this.pile) {
            sum += card.getValue();
        }
        return sum;
    }

    public int getSize() { return pile.size(); }

    public double getTurns() { return turns; }

    public ImageView getTop(){
        //Returns the image of the top card of the pile
        if (pile.size() == 0) {
            //If no cards in pile return outline of a card
            ImageView temp =  new ImageView("card/card_outline.png");
            temp.setFitWidth(72);
            temp.setFitHeight(96);
            return temp;
        } else {
            return pile.peek();
        }  
    }

    public Stack<Card> getPile() { return pile; }

    public void Reset(Deck deck) {
        pile.clear();
        
        //Initialize the first four cards that are in the pile at the start of the game.
        for (int count = 0; count < 4; count++) {
            pile.push(deck.Draw());
        }

        //If the top card is a jack, reload the deck and draw again.
        //(Jack can not be on the top at the start of the game)
        while (pile.peek().getCard_type() == 11) {
            pile.clear();
            deck.Reload();
            for (int cnt = 0; cnt < 4; cnt++) {
                pile.push(deck.Draw());
            } 
        }
    }

    public String toString() {
        String out = "";
        for (Card card: pile) {
            out += card.toString() + "\n";
        } 
        return out;
    }
}