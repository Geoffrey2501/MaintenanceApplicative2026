public class ActionCreerCompte implements ActionMenu {
    @Override
    public void executer(Contexte contexte) {
        String nom = contexte.input.lireLigne("Nouvel identifiant : ");
        String mdp = contexte.input.lireLigne("Mot de passe : ");
        String conf = contexte.input.lireLigne("Confirmation : ");

        contexte.auth.creerCompte(nom, mdp, conf);
        contexte.affichage.afficherMessage("Compte créé (si les mots de passe correspondent).");
    }
}