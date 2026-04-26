import ValueObject.Identifiant;
import ValueObject.MotDePasse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthService {
    private final Map<Identifiant, MotDePasse> comptes = new HashMap<>();

    public AuthService() {
        comptes.put(new Identifiant("Roger"), new MotDePasse("Chat"));
        comptes.put(new Identifiant("Pierre"), new MotDePasse("KiRouhl"));
    }

    public String connecter(String nom, String mdp) {
        Identifiant id = new Identifiant(nom);
        MotDePasse challenge = new MotDePasse(mdp);

        return Optional.ofNullable(comptes.get(id))
                .filter(motDePasseStocke -> motDePasseStocke.correspond(challenge))
                .map(ok -> id.valeur())
                .orElse(null);
    }

    public boolean creerCompte(String nom, String mdp, String confirmation) {
        Identifiant id = new Identifiant(nom);
        MotDePasse secret = new MotDePasse(mdp);
        MotDePasse conf = new MotDePasse(confirmation);

        return enregistrerSiValide(id, secret, conf);
    }

    private boolean enregistrerSiValide(Identifiant id, MotDePasse mdp, MotDePasse conf) {
        if (mdp.correspond(conf)) {
            comptes.put(id, mdp);
            return true;
        }
        return false;
    }
}