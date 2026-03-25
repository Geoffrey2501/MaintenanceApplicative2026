import ValueObject.*;

public class RendezVousPersonnel extends Event {
    public RendezVousPersonnel(TitreEvenement title, Proprietaire proprietaire, DateEvenement dateDebut, DureeMinutes dureeMinutes) {
        super(title, proprietaire, dateDebut, dureeMinutes);
    }

    @Override
    public String description() {
        return "RDV : " + title.valeur() + " à " + dateDebut.valeur();
    }

    @Override
    public boolean isWithinRange(DateEvenement debut, DateEvenement fin) {
        return !dateDebut.valeur().isBefore(debut.valeur()) && !dateDebut.valeur().isAfter(fin.valeur());
    }
}
