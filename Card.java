import java.util.HashMap;

public class Card implements Comparable<Card>{
    private int rank;
    public Suit suit;

    //convert suit of each card from char to Suit
    public enum Suit {DIAMONDS, HEARTS, CLUBS, SPADES;
        public static Suit fromChar(char c) {
            switch (c) {
                case 'h': return Suit.HEARTS;
                case 'd': return Suit.DIAMONDS;
                case 's': return Suit.SPADES;
                case 'c': return Suit.CLUBS;
                default: throw new IllegalArgumentException("Invalid card suit");
            }
        }
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(char suit) {
        this.suit = Suit.fromChar(suit);
    }

    //use a map to interpret face value of each card from string to int
    static HashMap<Character, Integer> faceValue = new HashMap<Character, Integer>(){{
        put('2', 2);
        put('3', 3);
        put('4', 4);
        put('5', 5);
        put('6', 6);
        put('7', 7);
        put('8', 8);
        put('9', 9);
        put('T', 10);
        put('J', 11);
        put('Q', 12);
        put('K', 13);
        put('A', 14);
    }};

    public Card(char rank, char suit) {
        this.rank = getFaceValue(rank);
        this.suit = Suit.fromChar(suit);
    }

    public Card(String cardString) {
        if(cardString.length() != 2){
            throw new IllegalArgumentException("Invalid card format");
        }
        this.rank = getFaceValue(cardString.charAt(0));
        this.suit = Suit.fromChar(cardString.charAt(1));
    }

    //Returns the face value of the card from the map
    public static int getFaceValue(char rank){
        if(!faceValue.containsKey(rank)){
            throw new IllegalArgumentException("Invalid rank value");
        }
        int value = faceValue.get(rank);
        return value;
    }
    @Override
    public int compareTo(Card anotherCard) {
        if (this.getRank() < anotherCard.getRank())
            return -1;
        else if (this.getRank() > anotherCard.getRank())
            return 1;
        else
            return 0;
    }

    @Override
    public boolean equals(Object o) {
        Card anotherCard = (Card) o;
        return this.getRank() == anotherCard.getRank();
    }

    @Override
    public int hashCode() {
        return this.rank + this.suit.ordinal();
    }
}