import Service.AuthService;

public class ActionSeConnecter implements ActionMenu {
    @Override
    public void executer(Contexte contexte) {
        String nom = contexte.input.lireLigne("Nom d'utilisateur : ");
        String mdp = contexte.input.lireLigne("Mot de passe : ");

        // On met à jour l'état du contexte. Si null, le menu accueil reviendra.
        String utilisateur = contexte.auth.connecter(nom, mdp);
        contexte.setUtilisateur(utilisateur);

        // Petit message de feedback (sans IF, on pourrait utiliser un Result Object)
        contexte.affichage.afficherMessage("Tentative de connexion effectuée.");
    }
}