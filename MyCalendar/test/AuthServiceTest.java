import Service.AuthService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void connecteUnComptePreconfigureRoger() {
        AuthService authService = new AuthService();

        String utilisateur = authService.connecter("Roger", "Chat");

        assertEquals("Roger", utilisateur);
    }

    @Test
    void rejetteMotDePasseInvalidePourPierre() {
        AuthService authService = new AuthService();

        String utilisateur = authService.connecter("Pierre", "mauvais");

        assertNull(utilisateur);
    }

    @Test
    void creePuisConnecteUnCompteUtilisateur() {
        AuthService authService = new AuthService();

        boolean cree = authService.creerCompte("Alice", "secret", "secret");
        String utilisateur = authService.connecter("Alice", "secret");

        assertTrue(cree);
        assertEquals("Alice", utilisateur);
    }
}

