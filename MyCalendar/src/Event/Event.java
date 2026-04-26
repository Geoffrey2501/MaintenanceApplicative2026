package Event;

import ValueObject.*;

public abstract class Event {
    protected final EventId id;
    protected final TitreEvenement title;
    protected final Proprietaire proprietaire;
    protected final DateEvenement dateDebut;
    protected final DureeMinutes dureeMinutes;

    protected Event(TitreEvenement title, Proprietaire proprietaire, DateEvenement dateDebut, DureeMinutes dureeMinutes) {
        this.id = EventId.generer();
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.dureeMinutes = dureeMinutes;
    }

    // Méthode polymorphe pour la description
    public abstract String description();

    // Méthode pour vérifier si l'événement est dans une période donnée
    public abstract boolean isWithinRange(DateEvenement debut, DateEvenement fin);
    //Méthode pour
    public abstract boolean occupeLeCreneau(IntervalleTemps creneau);

    // Accesseurs communs
    public EventId getId() { return id; }
    public TitreEvenement getTitle() { return title; }
    public DateEvenement getDateDebut() { return dateDebut; }
    public DureeMinutes getDureeMinutes() { return dureeMinutes; }
}