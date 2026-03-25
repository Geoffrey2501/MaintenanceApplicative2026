import ValueObject.*;

public class RendezVousPersonnel extends Event {
    private IntervalleTemps intervalleTemps;
    public RendezVousPersonnel(TitreEvenement title, Proprietaire proprietaire, DateEvenement dateDebut, DureeMinutes dureeMinutes) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        intervalleTemps = new IntervalleTemps(dateDebut, dateDebut.getDateFin(dureeMinutes));
    }

    @Override
    public String description() {
        return "RDV : " + title.valeur() + " à " + dateDebut.valeur();
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
