import ValueObject.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Assure-toi que les Value Objects sont accessibles (importés si nécessaire)
// import ValueObject.*;

class EventTest {

    @Test
    void descriptionRendezVousPersonnel() {
        LocalDateTime debutBrute = LocalDateTime.of(2026, 3, 23, 10, 15);

        // On enveloppe les primitives dans leurs Value Objects respectifs
        Event event = new Event(
                "RDV_PERSONNEL",
                new TitreEvenement("Dentiste"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(debutBrute),
                new DureeMinutes(45),
                new Lieu("truc"),
                new Participant("t"),
                new Frequence(0)
        );

        assertEquals("RDV : Dentiste à 2026-03-23T10:15", event.description());
    }

    @Test
    void descriptionReunion() {
        Event event = new Event(
                "REUNION",
                new TitreEvenement("Sprint"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 14, 0)),
                new DureeMinutes(60),
                new Lieu("Salle A"),
                new Participant("Geoffrey, Alice"),
                new Frequence(0)
        );

        assertEquals("Réunion : Sprint à Salle A avec Geoffrey, Alice", event.description());
    }

    @Test
    void descriptionPeriodique() {
        Event event = new Event(
                "PERIODIQUE",
                new TitreEvenement("Standup"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 9, 0)),
                new DureeMinutes(0),
                new Lieu("Planete"),
                new Participant("Maurice"),
                new Frequence(7)
        );

        assertEquals("Événement périodique : Standup tous les 7 jours", event.description());
    }

    @Test
    void descriptionTypeInconnuRetourneVide() {
        Event event = new Event(
                "AUTRE",
                new TitreEvenement("X"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 9, 0)),
                new DureeMinutes(0),
                new Lieu("truc"),
                new Participant("Maurice"),
                new Frequence(0)
        );

        assertEquals("", event.description());
    }
}