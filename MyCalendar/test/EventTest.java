import ValueObject.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void eventExposeUnEventId() {
        Event event = new RendezVousPersonnel(
                new TitreEvenement("Dentiste"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 10, 15)),
                new DureeMinutes(45)
        );

        assertNotNull(event.getId());
        assertFalse(event.getId().valeur().isBlank());
    }

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

    @Test
    void rdvEnConflitAvecUnAutreRdv() {
        Event rdv1 = new RendezVousPersonnel(
                new TitreEvenement("RDV 1"), new Proprietaire("G"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 14, 0)),
                new DureeMinutes(60)
        );

        IntervalleTemps creneauConflit = new IntervalleTemps(
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 14, 30)),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 15, 30))
        );

        assertTrue(rdv1.occupeLeCreneau(creneauConflit));
    }

    @Test
    void eventPeriodiqueNeProvoqueJamaisDeConflit() {
        // Selon ta règle métier : un périodique retourne toujours false pour les conflits
        Event periodique = new EvenementPeriodique(
                new TitreEvenement("Sport"), new Proprietaire("G"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 18, 0)),
                new Frequence(1)
        );

        IntervalleTemps nimporteQuelCreneau = new IntervalleTemps(
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 18, 0)),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 19, 0))
        );

        assertFalse(periodique.occupeLeCreneau(nimporteQuelCreneau),
                "Un événement périodique ne devrait pas bloquer le calendrier");
    }

}