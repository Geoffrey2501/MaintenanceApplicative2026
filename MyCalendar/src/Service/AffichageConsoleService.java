package Service;

import Event.Event;

import java.util.List;

public class AffichageConsoleService implements AffichageService {
    @Override
    public void afficherListe(List<Event> evenements) {
        afficherSeparation();
        afficherTitre("Resultats de recherche");
        if (evenements.isEmpty()) {
            afficherMessage("Aucun evenement trouve pour cette periode.");
            afficherSeparation();
            return;
        }

        afficherMessage("Nombre d'evenements: " + evenements.size());
        afficherMessage("Evenements trouves:");
        for (int i = 0; i < evenements.size(); i++) {
            Event e = evenements.get(i);
            afficherMessage((i + 1) + ". " + e.description());
        }
        afficherSeparation();
    }

    @Override
    public void afficherTitre(String titre) {
        System.out.println("=== " + titre + " ===");
    }

    @Override
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void afficherInvite(String message) {
        System.out.print(message);
    }

    @Override
    public void afficherSeparation() {
        System.out.println("----------------------------------------");
    }
}

