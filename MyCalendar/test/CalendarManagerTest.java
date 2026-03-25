import ValueObject.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CalendarManagerTest {

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
}
