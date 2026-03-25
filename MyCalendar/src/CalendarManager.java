import ValueObject.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarManager {
    // On garde la liste d'Event
    public List<Event> events;

    public CalendarManager() {
        this.events = new ArrayList<>();
    }

    // Mise à jour de la signature avec les Value Objects
    public void ajouterEvent(Event e) {
        events.add(e);
    }

    public List<Event> eventsDansPeriode(DateEvenement debut, DateEvenement fin) {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            // Le polymorphisme remplace le "if (type.equals("PERIODIQUE"))"
            if (e.isWithinRange(debut, fin)) {
                result.add(e);
            }
        }
        return result;
    }


}