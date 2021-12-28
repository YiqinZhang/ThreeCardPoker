import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PokerHandEvaluator {
    private static final int MIN_PLAYER_COUNT = 1;
    private static final int MAX_PLAYER_COUNT = 8;
    public static void main(String[] args) throws Exception {
        BufferedReader reader;
        try {
            if (args.length != 0) {
                FileReader in = new FileReader(args[0]);
                reader = new BufferedReader(in);
            } else {
                InputStreamReader in = new InputStreamReader(System.in);
                reader = new BufferedReader(in);
            }

            int playersCount = Integer.parseInt(reader.readLine());
            if (playersCount < MIN_PLAYER_COUNT || playersCount > MAX_PLAYER_COUNT) {
                throw new IllegalArgumentException("Invalid player count");
            }

            String line;
            Hand[] hands = new Hand[playersCount];
            Set<String> idSet = new HashSet<String>();
            for (int i = 0; i < playersCount; i++) {
                line = reader.readLine();
                if(line.length() > 10){
                    throw new IllegalArgumentException("Wrong input format");
                }
                String id = line.substring(0, 2);
                if(!idSet.isEmpty()){
                    if(idSet.contains(id)){
                        id = String.valueOf(i);
                        hands[i] = new Hand(id, line.substring(2).split(" "));
                        throw new IllegalArgumentException("Duplicate id");
                    }
                }
                idSet.add(id);
                hands[i] = new Hand(line);
            }
            reader.close();
            List<Hand> winners = selectWinners(hands);
        } catch (Exception e) {
            System.out.println("Input error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static List<Hand> selectWinners(Hand[] hands) {
        List<Hand> winners = new ArrayList<>();
        if(hands == null || hands.length == 0){
            return winners;
        }
        winners.add(hands[0]);
        int max = hands[0].getRankValue();

        for (int i = 1; i < hands.length; i++) {
            if (hands[i].getRankValue() > max) {
                max = hands[i].getRankValue();
                winners.clear();
                winners.add(hands[i]);
            } else if (hands[i].getRankValue() == max) {
                winners.add(hands[i]);
            }
        }
        print(winners);
        return winners;
    }

    private static void print(List<Hand> winner) {
        if (winner == null || winner.size() == 0) {
            return;
        }
        for (Hand hand : winner) {
            System.out.print(hand.getId());
        }
    }
}
