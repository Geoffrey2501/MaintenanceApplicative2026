import ValueObject.DateEvenement;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CalendarManagerTest {

    @Test
    void ajouterEventAjouteDansLaListe() {
        CalendarManager calendar = new CalendarManager();

        // Correction : Utilisation de "N/A" pour Lieu/Participant et 1 pour Frequence
        calendar.ajouterEvent("RDV_PERSONNEL", "Course", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 11, 0),
                30,
                "N/A",
                "N/A",
                1);

        assertEquals(1, calendar.events.size());
        // On utilise getValeur() car 'title' est maintenant un Value Object
        assertEquals("Course", calendar.events.get(0).title.valeur());
    }

    @Test
    void eventsDansPeriodeInclusLesBornesPourNonPeriodique() {
        CalendarManager calendar = new CalendarManager();
        LocalDateTime debut = LocalDateTime.of(2026, 3, 23, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 3, 23, 11, 0);
        DateEvenement debutVO = new DateEvenement(debut);
        DateEvenement finVO = new DateEvenement(fin);

        calendar.ajouterEvent("RDV_PERSONNEL", "Debut", "Geoffrey", debut, 30, "N/A", "N/A", 1);
        calendar.ajouterEvent("RDV_PERSONNEL", "Fin", "Geoffrey", fin, 30, "N/A", "N/A", 1);

        List<Event> resultats = calendar.eventsDansPeriode(debutVO, finVO);

        assertEquals(2, resultats.size());
    }

    @Test
    void eventsDansPeriodeExclutHorsPeriodePourNonPeriodique() {
        CalendarManager calendar = new CalendarManager();
        LocalDateTime debut = LocalDateTime.of(2026, 3, 23, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 3, 23, 11, 0);
        DateEvenement debutVO = new DateEvenement(debut);
        DateEvenement finVO = new DateEvenement(fin);

        calendar.ajouterEvent("RDV_PERSONNEL", "Avant", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 9, 59), 30, "N/A", "N/A", 1);
        calendar.ajouterEvent("RDV_PERSONNEL", "Apres", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 11, 1), 30, "N/A", "N/A", 1);

        List<Event> resultats = calendar.eventsDansPeriode(debutVO, finVO);

        assertTrue(resultats.isEmpty());
    }

    @Test
    void eventsDansPeriodeInclutUnPeriodiqueSiUneOccurrenceTombeDansLaPeriode() {
        CalendarManager calendar = new CalendarManager();
        calendar.ajouterEvent("PERIODIQUE", "Sport", "Geoffrey",
                LocalDateTime.of(2026, 3, 1, 8, 0),
                0,
                "Gym",
                "Moi",
                7);

        LocalDateTime debut = LocalDateTime.of(2026, 3, 15, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 3, 16, 0, 0);

        DateEvenement debutVO = new DateEvenement(debut);
        DateEvenement finVO = new DateEvenement(fin);
        List<Event> resultats = calendar.eventsDansPeriode(debutVO, finVO);

        assertEquals(1, resultats.size());
        assertEquals("Sport", resultats.get(0).title.valeur());
    }

    @Test
    void conflitRetourneVraiQuandChevauchement() {
        CalendarManager calendar = new CalendarManager();
        // Important : même propriétaire et lieu pour forcer le conflit
        calendar.ajouterEvent("RDV_PERSONNEL", "A", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 10, 0), 60, "N/A", "N/A", 1);
        calendar.ajouterEvent("REUNION", "B", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 10, 30), 60, "Salle", "X", 1);

        Event e1 = calendar.events.get(0);
        Event e2 = calendar.events.get(1);

        assertTrue(calendar.conflit(e1, e2));
    }

    @Test
    void conflitRetourneFauxQuandJusteAdjacents() {
        CalendarManager calendar = new CalendarManager();
        calendar.ajouterEvent("RDV_PERSONNEL", "A", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 10, 0), 60, "N/A", "N/A", 1);
        calendar.ajouterEvent("REUNION", "B", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 11, 0), 30, "Salle", "X", 1);

        Event e1 = calendar.events.get(0);
        Event e2 = calendar.events.get(1);

        assertFalse(calendar.conflit(e1, e2));
    }

    @Test
    void conflitRetourneToujoursFauxSiPeriodique() {
        CalendarManager calendar = new CalendarManager();
        calendar.ajouterEvent("PERIODIQUE", "A", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 10, 0), 30, "N/A", "N/A", 7);
        calendar.ajouterEvent("REUNION", "B", "Geoffrey",
                LocalDateTime.of(2026, 3, 23, 10, 15), 30, "Salle", "X", 1);

        Event e1 = calendar.events.get(0);
        Event e2 = calendar.events.get(1);

        assertFalse(calendar.conflit(e1, e2));
    }
}