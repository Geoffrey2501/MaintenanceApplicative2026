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
    public void ajouterEvent(String type, String title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes,
                             String lieu, String participants, int frequenceJours) {
        Event e = new Event(
                type,
                new TitreEvenement(title),
                new Proprietaire(proprietaire),
                new DateEvenement(dateDebut),
                new DureeMinutes(dureeMinutes),
                new Lieu(lieu),
                new Participant(participants),
                new Frequence(frequenceJours)
        );
        events.add(e);
    }

    public List<Event> eventsDansPeriode(DateEvenement debut, DateEvenement fin) {
        List<Event> result = new ArrayList<>();

        for (Event e : events) {
            // Attention : on doit utiliser .valeur() ou .getValeur() pour accéder aux dates
            if (e.type.equals("PERIODIQUE")) {
                var temp = e.dateDebut.valeur(); // LocalDateTime interne
                while (temp.isBefore(fin.valeur())) {
                    if (!temp.isBefore(debut.valeur())) {
                        result.add(e);
                        break;
                    }
                    // Accès à la valeur du record Frequence
                    temp = temp.plusDays(e.frequenceJours.valeur());
                }
            } else if (!e.dateDebut.valeur().isBefore(debut.valeur())
                    && !e.dateDebut.valeur().isAfter(fin.valeur())) {
                result.add(e);
            }
        }
        return result;
    }

    public boolean conflit(Event e1, Event e2) {
        // Calcul des fins en utilisant les valeurs internes
        var fin1 = e1.dateDebut.valeur().plusMinutes(e1.dureeMinutes.valeur());
        var fin2 = e2.dateDebut.valeur().plusMinutes(e2.dureeMinutes.valeur());

        if (e1.type.equals("PERIODIQUE") || e2.type.equals("PERIODIQUE")) {
            return false;
        }

        if (e1.dateDebut.valeur().isBefore(fin2) && fin1.isAfter(e2.dateDebut.valeur())) {
            return true;
        }
        return false;
    }

    public void afficherEvenements() {
        for (Event e : events) {
            System.out.println(e.description());
        }
    }
}