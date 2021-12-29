import java.util.Arrays;


public class Hand implements Comparable<Hand> {
    public enum Type {
        HIGH_CARD,
        PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        STRAIGHT_FLUSH
    }

    private final static int HAND_SIZE = 3;
    private Card[] cards;
    private String id;
    private Type rankType;
    private int score;

    //Hand contains an array of cards sorted by rank
    public Hand(String string) throws Exception {
        try{
            id = string.substring(0, 2);
            String handstr = string.substring(2);
            String[] hand = handstr.split(" ");
            if (hand.length != HAND_SIZE) {
                throw new IllegalArgumentException("Exactly " + HAND_SIZE + " cards are required.");
            }
            cards = new Card[hand.length];
            for (int i = 0; i < cards.length; i++) {
                if (hand[i].length() != 2) {
                    throw new RuntimeException("Invalid card size");
                }
                cards[i] = new Card(hand[i]);
            }
            Arrays.sort(cards);
            calScore();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    Hand(String id, String[] handStr) {
        this.id = id;
        if (handStr.length != HAND_SIZE) {
            throw new IllegalArgumentException("Exactly " + HAND_SIZE + " cards are required.");
        }
        cards = new Card[HAND_SIZE];
        for (int i = 0; i < handStr.length; i++) {
            cards[i] = new Card(handStr[i]);
        }
        Arrays.sort(cards);
        calScore();
    }

    Hand() {
        id = "";
        cards = new Card[HAND_SIZE];
        score = 0;
    }

    public Card[] getCards() {
        return this.cards;
    }

    public String getId() {
        return this.id;
    }

    public Type getType() {
        return this.rankType;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(Hand o) {
        return o.score - score;
    }

/*
    determines the types of rank of the hand and
    get the highCard of each rankType
*/
    private int evaluateHand() {
        if (isStraightFlush()) {
            return getHighCard();
        } else if (isThreeOfAKind()) {
            return cards[0].getRank();
        } else if (isStraight()) {
            return getHighCard();
        } else if (isFlush()) {
            return getHighCard();
        } else if (isPair()) {
            return cards[1].getRank();
        } else if (isHighCard()) {
            return getHighCard();
        }
        return -1;
    }

    // checks for straight flush
    public boolean isStraightFlush() {
        if(isStraight() && isFlush()){
            rankType = Type.STRAIGHT_FLUSH;
            return true;
        }
        return false;
    }

    // checks for three of a kind
    public boolean isThreeOfAKind() {
        if (cards[0].getRank() == cards[1].getRank() && cards[1].getRank() == cards[2].getRank()) {
            rankType = Type.THREE_OF_A_KIND;
            return true;
        }
        return false;
    }


    // check for straight
    public boolean isStraight() {
        for (int counter2 = 1; counter2 < HAND_SIZE; counter2++) {
            if (counter2 == HAND_SIZE - 1 && cards[counter2].getRank() == 14) {
                if (cards[counter2 - 1].getRank() == 13 || cards[0].getRank() == 2) {
                    continue;
                }
            }
            if (cards[counter2 - 1].getRank() != (cards[counter2].getRank() - 1)) {
                return false;
            }
        }
        rankType = Type.STRAIGHT;
        return true;
    }

    // checks for flush
    public boolean isFlush() {
        for (int counter = 1; counter < HAND_SIZE; counter++) {
            if (!cards[0].getSuit().equals(cards[counter].getSuit())) {
                return false;
            }
        }
        rankType = Type.FLUSH;
        return true;
    }
    // checks for pair
    public boolean isPair() {
        if (cards[0].getRank() == cards[1].getRank() || cards[1].getRank() == cards[2].getRank()) {
            rankType = Type.PAIR;
            return true;
        }
        return false;
    }
    // checks for highcard
    public boolean isHighCard() {
        if (!isPair() && !isFlush() && !isStraight() && !isThreeOfAKind()) {
            rankType = Type.HIGH_CARD;
            return true;
        }
        return false;
    }
    //  find highest card
    public int getHighCard() {
        int highCard = 0;
        for (int counter = 0; counter < HAND_SIZE; counter++) {
            if (cards[counter].getRank() > highCard) {
                highCard = cards[counter].getRank();
            }
        }
        if (isStraight() || isStraightFlush()) {
            if (cards[HAND_SIZE - 1].getRank() == 14 && cards[0].getRank() == 2) {
                highCard = cards[HAND_SIZE - 2].getRank();
            }
        }
        return highCard;
    }
    //  calculate the score for the hand
    private int calScore() {
        int keyValue = evaluateHand();
        switch (getType()) {
            case STRAIGHT_FLUSH:
                score = keyValue + 5000000;
                break;
            case THREE_OF_A_KIND:
                score = keyValue + 1000000;
                break;
            case STRAIGHT:
                score = keyValue * 10000;
                break;
            case FLUSH:
                score += cards[HAND_SIZE - 1].getRank() * 1000 + 10000;
                score += cards[HAND_SIZE - 2].getRank() * 100;
                score += cards[HAND_SIZE - 3].getRank() * 10;
                break;
            case PAIR:
                if(keyValue == cards[0].getRank()){
                    score = keyValue * 200 +  cards[2].getRank();
                } else {
                    score = keyValue * 200 +  cards[0].getRank();
                }
                break;
            case HIGH_CARD:
                score = keyValue;
                break;
            default:
                throw new IllegalArgumentException("Illegal card rank");
        }
        return score;
    }
}

