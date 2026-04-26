import Event.*;
import Factory.*;
import Service.*;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class ActionAjouterEvenementTest {

    @Test
    void lActionAjouterEvenementInsereBienDansLeCalendrier() {
        // 1. Initialisation du contexte
        CalendarManager calendar = new CalendarManager();
        Contexte ctx = new Contexte(calendar, new AffichageConsoleService(), new AuthService());
        ctx.setUtilisateur("Roger");

        // 2. Simulation de saisie pour un RDV
        ctx.setInput(new InputHandler(ctx.affichage, new Scanner("Course\n2026\n03\n23\n08\n00\n30\n")));

        // 3. Exécution de l'action avec une factory de RDV
        ActionAjouterEvenement action = new ActionAjouterEvenement(new CreateurRendezVous());
        action.executer(ctx);

        // 4. Vérification
        assertEquals(1, calendar.events.size(), "L'événement devrait être ajouté au calendrier");
        assertEquals("Course", calendar.events.get(0).getTitle().valeur());
    }
}
