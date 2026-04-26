package Event;

import ValueObject.*;

public class Cours extends Event {
    private final Matiere matiere;
    private final Lieu salle;
    private final IntervalleTemps intervalleTemps;

    public Cours(TitreEvenement title, Proprietaire proprietaire, DateEvenement dateDebut,
                 DureeMinutes dureeMinutes, Matiere matiere, Lieu salle) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.matiere = matiere;
        this.salle = salle;
        // Encapsulation de l'intervalle pour la gestion des conflits
        this.intervalleTemps = new IntervalleTemps(dateDebut, dateDebut.getDateFin(dureeMinutes));
    }

    @Override
    public String description() {
        return "Cours : " + matiere.valeur() + " [" + title.valeur() + "] en " + salle.valeur();
    }

    @Override
    public boolean isWithinRange(DateEvenement debut, DateEvenement fin) {
        return !dateDebut.valeur().isBefore(debut.valeur()) && !dateDebut.valeur().isAfter(fin.valeur());
    }

    @Override
    public boolean occupeLeCreneau(IntervalleTemps autre) {
        return intervalleTemps.chevauche(autre);
    }
}