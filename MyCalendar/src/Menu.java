import Factory.*;

import java.util.HashMap;
import java.util.Map;

public class Menu {
    // Premier niveau : "true" (connecté) ou "false" (non connecté)
    private final Map<Boolean, Map<String, ActionMenu>> menus = new HashMap<>();

    public Menu() {
        // Configuration du menu non connecté (Accueil)
        Map<String, ActionMenu> menuAccueil = new HashMap<>();
        menuAccueil.put("1", new ActionSeConnecter());
        menuAccueil.put("2", new ActionCreerCompte());

        // Configuration du menu connecté (Principal)
        Map<String, ActionMenu> menuPrincipal = new HashMap<>();
        menuPrincipal.put("1", new ActionVoirEvenements());
        menuPrincipal.put("2", new ActionAjouterEvenement(new CreateurRendezVous()));
        menuPrincipal.put("3", new ActionAjouterEvenement(new CreateurReunion()));
        menuPrincipal.put("4", new ActionAjouterEvenement(new CreateurPeriodique()));
        menuPrincipal.put("5", new ActionDeconnexion());

        menus.put(false, menuAccueil);
        menus.put(true, menuPrincipal);
    }

    public void router(Contexte ctx, String choix) {
        menus.get(ctx.estConnecte())
                .getOrDefault(choix, c -> c.affichage.afficherMessage("Choix invalide."))
                .executer(ctx);
    }
}