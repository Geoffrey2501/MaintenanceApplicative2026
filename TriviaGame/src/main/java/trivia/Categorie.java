package trivia;

public enum Categorie {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    private final String name;

    Categorie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
