import ValueObject.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    @Test
    void descriptionRendezVousPersonnel() {
        LocalDateTime debutBrute = LocalDateTime.of(2026, 3, 23, 10, 15);

        Event event = new RendezVousPersonnel(
                new TitreEvenement("Dentiste"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(debutBrute),
                new DureeMinutes(45)
        );

        assertEquals("RDV : Dentiste à 2026-03-23T10:15", event.description());
    }

    @Test
    void descriptionReunion() {
        Event event = new Reunion(
                new TitreEvenement("Sprint"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 14, 0)),
                new DureeMinutes(60),
                new Lieu("Salle A"),
                new Participant("Geoffrey, Alice")
        );

        assertEquals("Réunion : Sprint à Salle A avec Geoffrey, Alice", event.description());
    }

    @Test
    void descriptionPeriodique() {
        Event event = new EvenementPeriodique(
                new TitreEvenement("Standup"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 9, 0)),
                new Frequence(7)
        );

        assertEquals("Événement périodique : Standup tous les 7 jours", event.description());
    }
}