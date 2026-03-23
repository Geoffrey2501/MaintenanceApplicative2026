import java.util.List;

public interface AffichageService {
    void afficherListe(List<Event> evenements);

    void afficherTitre(String titre);

    void afficherMessage(String message);

    void afficherInvite(String message);

    void afficherSeparation();
}

