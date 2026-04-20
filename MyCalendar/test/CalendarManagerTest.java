import ValueObject.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CalendarManagerTest {

    @Test
    void supprimerEventParIdRetireLEvenement() {
        CalendarManager calendar = new CalendarManager();

        Event rdv = new RendezVousPersonnel(
                new TitreEvenement("Course"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 11, 0)),
                new DureeMinutes(30)
        );

        calendar.ajouterEvent(rdv);

        boolean supprime = calendar.supprimerEvent(rdv.getId());

        assertTrue(supprime);
        assertEquals(0, calendar.events.size());
    }

    @Test
    void supprimerEventParIdInconnuNeChangeRien() {
        CalendarManager calendar = new CalendarManager();

        Event rdv = new RendezVousPersonnel(
                new TitreEvenement("Course"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 11, 0)),
                new DureeMinutes(30)
        );

        calendar.ajouterEvent(rdv);

        boolean supprime = calendar.supprimerEvent(new EventId("event-inexistant"));

        assertFalse(supprime);
        assertEquals(1, calendar.events.size());
    }

    @Test
    void ajouterEventAjouteDansLaListe() {
        CalendarManager calendar = new CalendarManager();
        Event rdv = new RendezVousPersonnel(
                new TitreEvenement("Course"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 11, 0)),
                new DureeMinutes(30)
        );

        calendar.ajouterEvent(rdv); // Suppose que ajouterEvent prend maintenant un objet Event

        assertEquals(1, calendar.events.size());
        assertEquals("Course", calendar.events.get(0).getTitle().valeur());
    }

    @Test
    void eventsDansPeriodeInclusLesBornes() {
        CalendarManager calendar = new CalendarManager();
        LocalDateTime debut = LocalDateTime.of(2026, 3, 23, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 3, 23, 11, 0);

        calendar.ajouterEvent(new RendezVousPersonnel(new TitreEvenement("Debut"), new Proprietaire("G"), new DateEvenement(debut), new DureeMinutes(30)));
        calendar.ajouterEvent(new RendezVousPersonnel(new TitreEvenement("Fin"), new Proprietaire("G"), new DateEvenement(fin), new DureeMinutes(30)));

        List<Event> resultats = calendar.eventsDansPeriode(new DateEvenement(debut), new DateEvenement(fin));

        assertEquals(2, resultats.size());
    }

    @Test
    void eventsDansPeriodeInclutUnPeriodiqueSiOccurrenceDansLaPeriode() {
        CalendarManager calendar = new CalendarManager();
        calendar.ajouterEvent(new EvenementPeriodique(
                new TitreEvenement("Sport"),
                new Proprietaire("Geoffrey"),
                new DateEvenement(LocalDateTime.of(2026, 3, 1, 8, 0)),
                new Frequence(7)));

        DateEvenement debutVO = new DateEvenement(LocalDateTime.of(2026, 3, 15, 0, 0));
        DateEvenement finVO = new DateEvenement(LocalDateTime.of(2026, 3, 16, 0, 0));

        List<Event> resultats = calendar.eventsDansPeriode(debutVO, finVO);

        assertEquals(1, resultats.size());
        assertEquals("Sport", resultats.get(0).getTitle().valeur());
    }

    @Test
    void detecterConflitEntreDeuxEventsConcrets() {
        CalendarManager manager = new CalendarManager();

        Event reunion = new Reunion(
                new TitreEvenement("Sprint"), new Proprietaire("P"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 10, 0)),
                new DureeMinutes(60), new Lieu("Salle 1"), new Participant("A, B")
        );

        Event rdvPerso = new RendezVousPersonnel(
                new TitreEvenement("Pause"), new Proprietaire("P"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 10, 30)),
                new DureeMinutes(15)
        );

        // La méthode conflit utilise e1.occupeLeCreneau(e2.getIntervalle())
        assertTrue(manager.conflit(reunion, rdvPerso), "Il devrait y avoir un conflit");
    }

    @Test
    void aucunConflitSiLesEventsSontEspaces() {
        CalendarManager manager = new CalendarManager();

        Event e1 = new RendezVousPersonnel(
                new TitreEvenement("Matin"), new Proprietaire("G"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 8, 0)), new DureeMinutes(30)
        );

        Event e2 = new RendezVousPersonnel(
                new TitreEvenement("Aprem"), new Proprietaire("G"),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 14, 0)), new DureeMinutes(30)
        );

        assertFalse(manager.conflit(e1, e2));
    }
}

