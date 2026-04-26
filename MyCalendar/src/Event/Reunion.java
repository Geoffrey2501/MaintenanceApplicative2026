package Event;

import ValueObject.*;

public class Reunion extends Event {
    private final Lieu lieu;
    private final Participant participants;

    private IntervalleTemps intervalleTemps;

    public Reunion(TitreEvenement title, Proprietaire proprietaire, DateEvenement dateDebut,
                   DureeMinutes dureeMinutes, Lieu lieu, Participant participants) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.lieu = lieu;
        this.participants = participants;
        intervalleTemps =new IntervalleTemps(dateDebut, dateDebut.getDateFin(dureeMinutes));
    }

    @Override
    public String description() {
        return "Réunion : " + title.valeur() + " à " + lieu.valeur() + " avec " + participants.participants();
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
