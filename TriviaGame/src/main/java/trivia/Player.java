package trivia;

public class Player {
    private final String name;
    private int place;
    private int purse;
    private boolean inPenaltyBox;

    public Player(String name, int startingPlace) {
        this.name = name;
        this.place = startingPlace;
        this.purse = 0;
        this.inPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public int getPurse() {
        return purse;
    }

    public void addCoin() {
        this.purse++;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void sendToPenaltyBox() {
        this.inPenaltyBox = true;
    }

    public void move(int roll, int boardSize) {
        int newPlace = this.place + roll;
        if (newPlace > boardSize) newPlace -= boardSize;
        this.place = newPlace;
    }
}

