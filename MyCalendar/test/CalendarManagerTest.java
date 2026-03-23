import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalendarManagerTest {

    @Test
    void ajouterEventAjouteDansLaListe() {
        CalendarManager calendar = new CalendarManager();

        calendar.ajouterEvent("RDV_PERSONNEL", "Course", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 11, 0),
                30,
                "",
                "",
                0);

        assertEquals(1, calendar.events.size());
        assertEquals("Course", calendar.events.get(0).title);
    }

    @Test
    void eventsDansPeriodeInclusLesBornesPourNonPeriodique() {
        CalendarManager calendar = new CalendarManager();
        LocalDateTime debut = LocalDateTime.of(2026, 3, 23, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 3, 23, 11, 0);

        calendar.ajouterEvent("RDV_PERSONNEL", "Debut", "Geoffrey", debut, 30, "", "", 0);
        calendar.ajouterEvent("RDV_PERSONNEL", "Fin", "Geoffrey", fin, 30, "", "", 0);

        List<Event> resultats = calendar.eventsDansPeriode(debut, fin);

        assertEquals(2, resultats.size());
    }

    @Test
    void eventsDansPeriodeExclutHorsPeriodePourNonPeriodique() {
        CalendarManager calendar = new CalendarManager();
        LocalDateTime debut = LocalDateTime.of(2026, 3, 23, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 3, 23, 11, 0);

        calendar.ajouterEvent("RDV_PERSONNEL", "Avant", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 9, 59), 30, "", "", 0);
        calendar.ajouterEvent("RDV_PERSONNEL", "Apres", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 11, 1), 30, "", "", 0);

        List<Event> resultats = calendar.eventsDansPeriode(debut, fin);

        assertTrue(resultats.isEmpty());
    }

    @Test
    void eventsDansPeriodeInclutUnPeriodiqueSiUneOccurrenceTombeDansLaPeriode() {
        CalendarManager calendar = new CalendarManager();
        calendar.ajouterEvent("PERIODIQUE", "Sport", "Geoffrey",
                LocalDateTime.of(2026, 3, 1, 8, 0),
                0,
                "",
                "",
                7);

        LocalDateTime debut = LocalDateTime.of(2026, 3, 15, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 3, 16, 0, 0);

        List<Event> resultats = calendar.eventsDansPeriode(debut, fin);

        assertEquals(1, resultats.size());
        assertEquals("Sport", resultats.get(0).title);
    }

    @Test
    void eventsDansPeriodePeriodiqueExclutOccurrenceExactementALaBorneFin() {
        CalendarManager calendar = new CalendarManager();
        calendar.ajouterEvent("PERIODIQUE", "Standup", "Geoffrey",
                LocalDateTime.of(2026, 3, 1, 9, 0),
                0,
                "",
                "",
                7);

        LocalDateTime debut = LocalDateTime.of(2026, 3, 7, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 3, 8, 9, 0);

        List<Event> resultats = calendar.eventsDansPeriode(debut, fin);

        assertTrue(resultats.isEmpty());
    }

    @Test
    void conflitRetourneVraiQuandChevauchement() {
        CalendarManager calendar = new CalendarManager();
        Event e1 = new Event("RDV_PERSONNEL", "A", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 10, 0), 60, "", "", 0);
        Event e2 = new Event("REUNION", "B", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 10, 30), 60, "Salle", "X", 0);

        assertTrue(calendar.conflit(e1, e2));
    }

    @Test
    void conflitRetourneFauxQuandJusteAdjacents() {
        CalendarManager calendar = new CalendarManager();
        Event e1 = new Event("RDV_PERSONNEL", "A", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 10, 0), 60, "", "", 0);
        Event e2 = new Event("REUNION", "B", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 11, 0), 30, "Salle", "X", 0);

        assertFalse(calendar.conflit(e1, e2));
    }

    @Test
    void conflitRetourneToujoursFauxSiPeriodique() {
        CalendarManager calendar = new CalendarManager();
        Event e1 = new Event("PERIODIQUE", "A", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 10, 0), 30, "", "", 7);
        Event e2 = new Event("REUNION", "B", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 10, 15), 30, "Salle", "X", 0);

        assertFalse(calendar.conflit(e1, e2));
    }
}

