import ValueObject.*;

import java.time.LocalDateTime;

public class Event {
    public String type; // "RDV_PERSONNEL", "REUNION", "PERIODIQUE"
    public TitreEvenement title;
    public Proprietaire proprietaire;
    public DateEvenement dateDebut;
    public DureeMinutes dureeMinutes;
    public Lieu lieu;
    public Participant participants;
    public Frequence frequenceJours;// uniquement pour PERIODIQUE

    public Event(String type, TitreEvenement title, Proprietaire proprietaire,
                 DateEvenement dateDebut, DureeMinutes dureeMinutes,
                 Lieu lieu, Participant participants, Frequence frequenceJours) {
        this.type = type;
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.dureeMinutes = dureeMinutes;
        this.lieu = lieu;
        this.participants = participants;
        this.frequenceJours = frequenceJours;
    }

    public String description() {
        String desc = "";
        if (type.equals("RDV_PERSONNEL")) {
            desc = "RDV : " + title.valeur() + " à " + dateDebut.valeur();
        } else if (type.equals("REUNION")) {
            desc = "Réunion : " + title.valeur() + " à " + lieu.valeur() + " avec " + participants.participants();
        } else if (type.equals("PERIODIQUE")) {
            desc = "Événement périodique : " + title.valeur() + " tous les " + frequenceJours.valeur() + " jours";
        }
        return desc;
    }
}