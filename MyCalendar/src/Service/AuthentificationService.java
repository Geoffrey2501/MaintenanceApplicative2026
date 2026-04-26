package Service;

import ValueObject.Identifiant;
import ValueObject.MotDePasse;

public interface AuthentificationService {
    String connecter(String nom, String mdp);
    boolean creerCompte(String nom, String mdp, String confirmation);
}
