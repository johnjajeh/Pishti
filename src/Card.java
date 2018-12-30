import javafx.scene.image.ImageView;

/**
 * The Card Class represents each individual card and its associated image.
 * The class extends the ImageView object, and adds values that define the cards
 * suit, card type, and point value.
 */

public class Card extends ImageView {
    
    //constants to improve readability
    private final int SPADES = 1, HEARTS = 2, DIAMONDS = 3, CLUBS = 4;
    
    //The three main definers of each card
    private int point_value, suit, card_type;

    public Card(String file) {
        
        //Initialize ImageView Constructor
        super(file);
        int string_num;
        
        //Parse number from Image File name
        string_num = Integer.parseInt(file.replaceAll("[^\\d]", ""));
        
        // Determine card's suit
        if (string_num <= 13) {
            this.suit = SPADES;
        } else if (string_num > 13 && string_num <= 26) {
            this.suit = HEARTS;
        } else if (string_num > 26 && string_num <= 39) {
            this.suit = DIAMONDS;
        } else if (string_num > 39) {
            this.suit = CLUBS;
        }
        
        /*
        Determine Card Type:
        if number is less than 13 card type is its actual file number
        if 13 the card is the king of spades and to makes sure it compares correctly
        with the rest of the values it is declared zero manually
        (since every 13th card (which are kings) will be modulo 0).
        Otherwise the file numbers remainder from being divided by 13 determines its value.
         */
        if (string_num < 13) {
            this.card_type = string_num;
        } else if (string_num == 13) {
            this.card_type = 0;
        } else {
            this.card_type = string_num % 13;
        }

        /*
         * Determine Cards point value:
         * If the card's file number is > 13 then the card object takes the remainder of
         * the card's file number divided by 13. Since there are only 13 possible card
         * types (1-10, J,Q,K,A) and the card images are in order by suit, taking the remainder
         * of this division will accurately determine the card type, and therefore its associated
         * value. If a card's file number is < 13 the same check is made, but without dividing by 13.
         */
        if (suit == SPADES) {
            switch (string_num) {
                case 1: this.point_value = 1; break;
                case 10: this.point_value = 1; break;
                case 11: this.point_value = 1; break;
                case 12: this.point_value = 1; break;
                case 13: this.point_value = 1; break;
                default: this.point_value = 0; break;
            }
        } else if (suit == DIAMONDS) {
            switch (string_num % 13) {
                case 1: this.point_value = 1; break;
                case 10: this.point_value = 3; break;
                case 11: this.point_value = 1; break;
                case 12: this.point_value = 1; break;
                case 0: this.point_value = 1; break;
                default: this.point_value = 0; break;
            }
        } else if (suit == CLUBS) {
            switch (string_num % 13) {
                case 1: this.point_value = 1; break;
                case 2: this.point_value = 2; break;
                case 10: this.point_value = 1; break;
                case 11: this.point_value = 1; break;
                case 12: this.point_value = 1; break;
                case 0: this.point_value = 1; break;
                default: this.point_value = 0; break;
            }
        } else {
            switch (string_num % 13) {
                case 1: this.point_value = 1; break;
                case 10: this.point_value = 1; break;
                case 11: this.point_value = 1; break;
                case 12: this.point_value = 1; break;
                case 0: this.point_value = 1; break;
                default: this.point_value = 0; break;
            }
        }
    }

    public int getValue() {
        return this.point_value;
    }

    public int getCard_type() {
        return card_type;
    }

    //Returns string object that states a card's type, suit, and point value.
    public String toString() {
        String name, num;
        int test = card_type % 13;

        if (suit == SPADES) {
            switch (card_type) {
                case 1: num = "Ace"; break;
                case 11: num = "Jack"; break;
                case 12: num = "Queen"; break;
                case 13: num = "King"; break;
                default: num = "" + card_type; break;
            }
        } else {
            switch (test) {
                case 1: num = "Ace"; break;
                case 11: num = "Jack"; break;
                case 12: num = "Queen"; break;
                case 0: num = "King"; break;
                default: num = "" + test; break;
            }
        }

        switch (suit) {
            case 1: name = "Spades"; break;
            case 2: name = "Hearts"; break;
            case 3: name = "Diamonds"; break;
            default: name = "Clubs"; break;
        }

        return "The " + num + " of " + name + " | Value: " + point_value;
    }

    public String getName() {

        String name, num;
        int test = card_type % 13;

        if (suit == SPADES) {
            switch (card_type) {
                case 1: num = "A"; break;
                case 11: num = "J"; break;
                case 12: num = "Q"; break;
                case 13: num = "K"; break;
                default: num = "" + card_type; break;
            }
        } else {
            switch (test) {
                case 1: num = "A"; break;
                case 11: num = "J"; break;
                case 12: num = "Q"; break;
                case 0: num = "K"; break;
                default: num = "" + test; break;
            }
        }

        switch (suit) {
            case 1: name = "S"; break;
            case 2: name = "H"; break;
            case 3: name = "D"; break;
            default: name = "C"; break;
        }

        return num + " OF " + name;
    }

    public boolean Compare(Card compare) {
        /*
        This comparison allows the program to check if two card objects
        are the same card type regardless of suit. (important for checking Pishtis)
        */
        return this.card_type == compare.card_type;
    }
}