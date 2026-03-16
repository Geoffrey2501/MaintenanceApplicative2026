package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import static trivia.Categorie.*;

// REFACTOR ME
public class Game implements IGame {

    private static final int MAX_PLAYERS = 6;
    private static final int QUESTION_COUNT = 50;
    private static final int STARTING_PLACE = 1;
    private static final int STARTING_PURSE = 0;
    private static final int BOARD_SIZE = 12;
    private static final int WINNING_COINS = 6;

    private final LogService logService;

    ArrayList<String> players = new ArrayList<>();
    int[] places = new int[MAX_PLAYERS];
    int[] purses = new int[MAX_PLAYERS];
    boolean[] inPenaltyBox = new boolean[MAX_PLAYERS];

    Map<Categorie, LinkedList<String>> questions = Map.of(
            POP, new LinkedList<>(),
            SCIENCE, new LinkedList<>(),
            SPORTS, new LinkedList<>(),
            ROCK, new LinkedList<>()
    );


    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        this(new LogService());
    }

    public Game(LogService logService) {
        this.logService = logService;
        for (int i = 0; i < QUESTION_COUNT; i++) {
            questions.get(POP).addLast("Pop Question " + i);
            questions.get(SCIENCE).addLast(("Science Question " + i));
            questions.get(SPORTS).addLast(("Sports Question " + i));
            questions.get(ROCK).addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        places[howManyPlayers()] = STARTING_PLACE;
        purses[howManyPlayers()] = STARTING_PURSE;
        inPenaltyBox[howManyPlayers()] = false;
        players.add(playerName);

        logService.log(playerName + " was added");
        logService.log("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        logService.log(players.get(currentPlayer) + " is the current player");
        logService.log("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                logService.log(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > BOARD_SIZE) places[currentPlayer] = places[currentPlayer] - BOARD_SIZE;

                logService.log(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                logService.log("The category is " + currentCategory().getName());
                askQuestion();
            } else {
                logService.log(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > BOARD_SIZE) places[currentPlayer] = places[currentPlayer] - BOARD_SIZE;

            logService.log(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            logService.log("The category is " + currentCategory().getName());
            askQuestion();
        }

    }

    private void askQuestion() {
        logService.log(questions.get(currentCategory()).removeFirst());
    }


    private Categorie currentCategory() {
        return Categorie.fromPlace(places[currentPlayer]);
    }

    public boolean handleCorrectAnswer() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                logService.log("Answer was corrent!!!!");
                purses[currentPlayer]++;
                logService.log(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }


        } else {

            logService.log("Answer was corrent!!!!");
            purses[currentPlayer]++;
            logService.log(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        logService.log("Question was incorrectly answered");
        logService.log(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return purses[currentPlayer] != WINNING_COINS;
    }
}
