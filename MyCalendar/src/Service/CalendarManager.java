package Service;

import Event.Event;
import ValueObject.*;

import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    // On garde la liste d'Event.Event
    public List<Event> events;

    public CalendarManager() {
        this.events = new ArrayList<>();
    }

    // Mise à jour de la signature avec les Value Objects
    public void ajouterEvent(Event e) {
        events.add(e);
    }

    public boolean supprimerEvent(EventId id) {
        return events.removeIf(event -> event.getId().equals(id));
    }

    public List<Event> eventsDansPeriode(DateEvenement debut, DateEvenement fin) {
        return events.stream()
                .filter(event -> event.isWithinRange(debut, fin))
                .toList();
    }


    public boolean conflit(Event e1, Event e2) {
      return e1.occupeLeCreneau(new IntervalleTemps(e2.getDateDebut(), e2.getDateDebut().getDateFin(e2.getDureeMinutes())));
    }
}
