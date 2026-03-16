package trivia;

public enum Categorie {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    private static final Categorie[] ORDERED_CATEGORIES = values();
    private final String name;

    Categorie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Categorie fromPlace(int place) {
        return ORDERED_CATEGORIES[Math.floorMod(place - 1, ORDERED_CATEGORIES.length)];
    }
}
