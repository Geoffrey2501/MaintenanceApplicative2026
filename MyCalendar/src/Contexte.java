import Service.*;
import ValueObject.Identifiant;
import java.util.HashMap;
import java.util.Map;

public class Contexte {
    // Services et Manager
    public final CalendarManager calendar;
    public final AffichageService affichage;
    public final AuthentificationService auth;
    public InputHandler input;

    // État de l'application
    private String utilisateur = null;
    private boolean applicationEnCours = true;

    // Registre d'affichage pour supprimer le "if" dans la navigation
    private final Map<Boolean, Runnable> strategiesAffichage = new HashMap<>();

    public Contexte(CalendarManager calendar, AffichageService affichage, AuthService auth) {
        this.calendar = calendar;
        this.affichage = affichage;
        this.auth = auth;

        // On initialise les comportements d'affichage selon l'état de connexion
        strategiesAffichage.put(false, this::afficherMenuAccueil);
        strategiesAffichage.put(true, this::afficherMenuPrincipal);
    }

    // --- Gestion de l'état ---

    public boolean estConnecte() {
        return utilisateur != null;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean isApplicationEnCours() {
        return applicationEnCours;
    }

    public void arreter() {
        this.applicationEnCours = false;
    }

    public void setInput(InputHandler input) {
        this.input = input;
    }

    // --- Logique d'affichage (Zéro Conditionnel) ---

    public void afficherMenuSelonEtat() {
        // On récupère la fonction d'affichage correspondant à l'état de connexion (true/false)
        strategiesAffichage.get(estConnecte()).run();
    }

    private void afficherMenuAccueil() {
        affichage.afficherTitre("ACCUEIL - GESTIONNAIRE D'ÉVÉNEMENTS");
        affichage.afficherMessage("1 - Se connecter");
        affichage.afficherMessage("2 - Créer un compte");
        affichage.afficherMessage("3 - Quitter");
    }

    private void afficherMenuPrincipal() {
        affichage.afficherTitre("MENU PRINCIPAL - Connecté en tant que : " + utilisateur);
        affichage.afficherMessage("1 - Voir les événements");
        affichage.afficherMessage("2 - Ajouter un rendez-vous perso");
        affichage.afficherMessage("3 - Ajouter une réunion");
        affichage.afficherMessage("4 - Ajouter un événement périodique");
        affichage.afficherMessage("5 - Se déconnecter");
    }
}