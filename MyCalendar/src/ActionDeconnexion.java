import Service.AffichageService;

/**
 * Action gérant la déconnexion de l'utilisateur.
 * En remettant l'utilisateur à null, le système bascule automatiquement
 * vers les stratégies d'affichage et de menu de l'accueil.
 */
public class ActionDeconnexion implements ActionMenu {

    @Override
    public void executer(Contexte contexte) {
        // Notification à l'utilisateur
        contexte.affichage.afficherSeparation();
        contexte.affichage.afficherMessage("Déconnexion en cours...");

        // Mise à jour de l'état du contexte via le setter
        // Cela change le retour de contexte.estConnecte() vers false
        contexte.setUtilisateur(null);

        contexte.affichage.afficherMessage("Vous avez été déconnecté avec succès.");
        contexte.affichage.afficherSeparation();
    }
}