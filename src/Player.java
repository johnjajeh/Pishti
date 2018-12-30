import java.util.ArrayList;

/**
 * The Player class keeps track of each player's total points, the card in their hand,
 * and whether the Player is a computer or not.
 */

public class Player {
    private int score = 0, turn = 0;
    private ArrayList<Card> hand = new ArrayList<>();
    private boolean computer;
    
    //Important for determining who gets the 3 points given to whoever has the majority of won cards
    private int numberOfCardsWon = 0;

    public Player(Deck deck, boolean computer) {
        //Determine whether the player is human and populate their initial hand
        this.computer = computer;
        Populate(deck);
    }

    public boolean isComputer() { return computer; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public int getNumberOfCardsWon() { return numberOfCardsWon; }

    public void setNumberOfCardsWon(int numberOfCardsWon) { this.numberOfCardsWon = numberOfCardsWon; }

    public void addPoints(int points){ this.score += points; }

    public ArrayList<Card> getHand(){ return hand; }

    /*
    Play_Card is an overloaded function that represents the playing of a card from the player's hand.
    This function is called in relation to the add function of the Pile class.
    When an index is specified, the function represents a real player playing a card.
    When no index is specified, a computer is making a move.
     */
    public Card Play_Card(Deck deck, int index) {
        //User playing a card
        Card temp = hand.get(index);
        hand.remove(index);
        
        //If hand is empty draw four cards from the deck
        if (hand.size() < 1 && deck.getSize() > 0) {
            Populate(deck);
        }
        return temp;
    }

    public Card Play_Card(Deck deck, Pile pile) {
        //Computer playing a card
        //The computer checks whether it can make a score-able play and if it can't
        //it will randomly play a card from its hand.
        int index = 0;
        boolean validplay = false;
        int rand = getValidRandom(hand.size() - 1);
        
        //Random card is chosen
        Card temp = hand.get(rand);
        if (pile.getSize() > 0) {
            //Check to see if computer can make a score-able play
            for (Card card: hand) {
                if (card.Compare((Card)pile.getTop()) || card.getCard_type() == 11) {
                    temp = hand.get(index);
                    hand.remove(index);
                    validplay = true;
                    break;
                }
                index++;
            }
        }

        if (!validplay) {
            hand.remove(rand);
        }

        if (hand.size() < 1 && deck.getSize() > 0) {
            Populate(deck);
        } 
        return temp;
    }

    private void Populate(Deck deck) {
        for (int cnt = 0; cnt < 4; cnt++) {
            hand.add(deck.Draw());
        }
    }

    public void Reset(Deck deck) {
        hand.clear();
        Populate(deck);
    }

    private int getValidRandom(int max) {
        double test = Math.random();
        return (int) (test * max);
    }

    public String toString() {
        String out = "";
        for (Card card: hand) {
            out += card.toString() + "\n";
        }
        return out;
    }
}