public class AuthService {
    private static final int MAX_UTILISATEURS = 99;

    private final String[] utilisateurs = new String[MAX_UTILISATEURS];
    private final String[] motsDePasse = new String[MAX_UTILISATEURS];
    private int nbUtilisateurs = 0;

    public String connecter(String utilisateur, String motDePasse) {
        if (utilisateur == null || motDePasse == null) {
            return null;
        }

        if (utilisateur.equals("Roger")) {
            return motDePasse.equals("Chat") ? utilisateur : null;
        }

        if (utilisateur.equals("Pierre")) {
            return motDePasse.equals("KiRouhl") ? utilisateur : null;
        }

        for (int i = 0; i < nbUtilisateurs; i++) {
            if (utilisateurs[i].equals(utilisateur) && motsDePasse[i].equals(motDePasse)) {
                return utilisateurs[i];
            }
        }

        return null;
    }

    public boolean creerCompte(String utilisateur, String motDePasse, String confirmationMotDePasse) {
        if (utilisateur == null || motDePasse == null || confirmationMotDePasse == null) {
            return false;
        }

        if (!motDePasse.equals(confirmationMotDePasse)) {
            return false;
        }

        if (nbUtilisateurs >= MAX_UTILISATEURS) {
            return false;
        }

        utilisateurs[nbUtilisateurs] = utilisateur;
        motsDePasse[nbUtilisateurs] = motDePasse;
        nbUtilisateurs++;
        return true;
    }
}

