import java.util.Collections;
import java.util.Stack;

/**
 * The Deck class represents a stack containing the paths to each respective card image.
 * Card objects are initialized when the draw function is called.
 */

public class Deck {
    private Stack<String> cards = new Stack<>();

    public Deck() {
        //Populate the deck
        for (int count = 1; count <= 52; count++) {
            cards.push(("card/" + count + ".png"));
        }

        //Shuffle the deck
        Collections.shuffle(cards);
    }

    public Card Draw() { return new Card(cards.pop()); }

    //Refills the deck and shuffles it
    public void Reload() { 
        cards.clear();
        for (int count = 1; count <= 52; count++) {
            cards.push(("card/" + count + ".png"));
        }

        Collections.shuffle(cards);
    }

    public int getSize() { return cards.size(); }
}
