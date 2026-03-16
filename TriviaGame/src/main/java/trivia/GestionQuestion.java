package trivia;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;

public class GestionQuestion {
    private static final int QUESTION_COUNT = 50;
    private static final String QUESTION_SUFFIX = " Question ";

    private final Map<Categorie, LinkedList<String>> questions = new EnumMap<>(Categorie.class);

    public GestionQuestion() {
        for (Categorie categorie : Categorie.values()) {
            questions.put(categorie, new LinkedList<>());
        }

        for (int i = 0; i < QUESTION_COUNT; i++) {
            for (Categorie categorie : Categorie.values()) {
                questions.get(categorie).addLast(createQuestion(categorie, i));
            }
        }
    }

    public String nextQuestionFor(Categorie categorie) {
        return questions.get(categorie).removeFirst();
    }

    private String createQuestion(Categorie categorie, int index) {
        return categorie.getName() + QUESTION_SUFFIX + index;
    }
}
