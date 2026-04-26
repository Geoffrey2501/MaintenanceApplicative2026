import Event.*;
import Factory.*;
import Service.*;
import ValueObject.*;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class CreationEvenementTest {

    private InputHandler simulerEntree(String donnees) {
        // Simule les entrées clavier pour le InputHandler
        return new InputHandler(new AffichageConsoleService(), new Scanner(donnees));
    }

    @Test
    void createurRendezVousGenereUnObjetValide() {
        // Simulation : Titre, Année, Mois, Jour, Heure, Minute, Durée
        InputHandler input = simulerEntree("Dentiste\n2026\n03\n23\n10\n15\n45\n");
        CreateurRendezVous factory = new CreateurRendezVous();

        Event ev = factory.saisirEtCreer(input, "Geoffrey");

        assertTrue(ev instanceof RendezVousPersonnel);
        assertEquals("Dentiste", ev.getTitle().valeur());
        assertEquals("RDV : Dentiste à 2026-03-23T10:15", ev.description());
    }

    @Test
    void createurReunionGenereUnObjetValide() {
        // Simulation : Titre, Année, Mois, Jour, Heure, Minute, Durée, Lieu, Participants
        InputHandler input = simulerEntree("Sprint\n2026\n03\n23\n14\n00\n60\nSalle A\nAlice, Bob\n");
        CreateurReunion factory = new CreateurReunion();

        Event ev = factory.saisirEtCreer(input, "Geoffrey");

        assertTrue(ev instanceof Reunion);
        assertEquals("Réunion : Sprint à Salle A avec Alice, Bob", ev.description());
    }

    @Test
    void createurPeriodiqueGenereUnObjetValide() {
        // Simulation : Titre, Année, Mois, Jour, Heure, Minute, Fréquence
        InputHandler input = simulerEntree("Sport\n2026\n03\n01\n09\n00\n7\n");
        CreateurPeriodique factory = new CreateurPeriodique();

        Event ev = factory.saisirEtCreer(input, "Geoffrey");

        assertTrue(ev instanceof EvenementPeriodique);
        assertEquals("Événement périodique : Sport tous les 7 jours", ev.description());
    }

    @Test
    void createurCoursValide() {
        String simulationInput = "TP Refactoring\nGenie Logiciel\n2026\n04\n27\n14\n00\n120\nAmphi A\n";

        // Initialisation de l'InputHandler avec le flux simulé
        InputHandler input = new InputHandler(new AffichageConsoleService(), new Scanner(simulationInput));
        CreateurCours factory = new CreateurCours();

        // Création de l'événement via la factory
        Event ev = factory.saisirEtCreer(input, "Geoffrey");

        // Vérifications (Assertions)
        assertAll("Vérification des propriétés du cours créé",
                () -> assertTrue(ev instanceof Cours, "L'objet doit être une instance de Cours"),
                () -> assertEquals("Cours : Genie Logiciel [TP Refactoring] en Amphi A", ev.description(),
                        "La description doit correspondre au format polymorphe")
        );
    }
}