import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    @Test
    void descriptionRendezVousPersonnel() {
        LocalDateTime debut = LocalDateTime.of(2026, 3, 23, 10, 15);
        Event event = new Event("RDV_PERSONNEL", "Dentiste", "Geoffrey", debut, 45, "", "", 0);

        assertEquals("RDV : Dentiste à 2026-03-23T10:15", event.description());
    }

    @Test
    void descriptionReunion() {
        Event event = new Event("REUNION", "Sprint", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 14, 0),
                60,
                "Salle A",
                "Geoffrey, Alice",
                0);

        assertEquals("Réunion : Sprint à Salle A avec Geoffrey, Alice", event.description());
    }

    @Test
    void descriptionPeriodique() {
        Event event = new Event("PERIODIQUE", "Standup", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 9, 0),
                0,
                "",
                "",
                7);

        assertEquals("Événement périodique : Standup tous les 7 jours", event.description());
    }

    @Test
    void descriptionTypeInconnuRetourneVide() {
        Event event = new Event("AUTRE", "X", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 9, 0),
                0,
                "",
                "",
                0);

        assertEquals("", event.description());
    }
}

