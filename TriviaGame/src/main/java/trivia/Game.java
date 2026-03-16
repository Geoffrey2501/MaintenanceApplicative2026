package trivia;

import java.util.ArrayList;
import java.util.List;

// REFACTOR ME
public class Game implements IGame {

    private static final int STARTING_PLACE = 1;
    private static final int BOARD_SIZE = 12;
    private static final int WINNING_COINS = 6;

    private final LogService logService;
    private final GestionQuestion gestionQuestion;

    private final List<Player> players = new ArrayList<>();
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
        this(new LogService(), new GestionQuestion());
    }

    public Game(LogService logService) {
        this(logService, new GestionQuestion());
    }

    Game(LogService logService, GestionQuestion gestionQuestion) {
        this.logService = logService;
        this.gestionQuestion = gestionQuestion;
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName, STARTING_PLACE));
        logService.log(playerName + " was added");
        logService.log("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        Player player = players.get(currentPlayer);
        logService.log(player.getName() + " is the current player");
        logService.log("They have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                logService.log(player.getName() + " is getting out of the penalty box");
                player.move(roll, BOARD_SIZE);
                logService.log(player.getName() + "'s new location is " + player.getPlace());
                logService.log("The category is " + currentCategory().getName());
                askQuestion();
            } else {
                logService.log(player.getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            player.move(roll, BOARD_SIZE);
            logService.log(player.getName() + "'s new location is " + player.getPlace());
            logService.log("The category is " + currentCategory().getName());
            askQuestion();
        }
    }


    private void askQuestion() {
        logService.log(gestionQuestion.nextQuestionFor(currentCategory()));
    }

    private Categorie currentCategory() {
        return Categorie.fromPlace(players.get(currentPlayer).getPlace());
    }

    public boolean handleCorrectAnswer() {
        Player player = players.get(currentPlayer);
        if (player.isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
            advancePlayer();
            return true;
        }

        logService.log("Answer was corrent!!!!");
        player.addCoin();
        logService.log(player.getName() + " now has " + player.getPurse() + " Gold Coins.");

        boolean gameStillRunning = isGameStillRunning();
        advancePlayer();
        return gameStillRunning;
    }

    public boolean wrongAnswer() {
        Player player = players.get(currentPlayer);
        logService.log("Question was incorrectly answered");
        logService.log(player.getName() + " was sent to the penalty box");
        player.sendToPenaltyBox();
        advancePlayer();
        return true;
    }

    private void advancePlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }

    private boolean isGameStillRunning() {
        return players.get(currentPlayer).getPurse() != WINNING_COINS;
    }
}
