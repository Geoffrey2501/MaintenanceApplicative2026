import ValueObject.*;
import java.time.LocalDateTime;

public class EvenementPeriodique extends Event {
    private final Frequence frequence;

    public EvenementPeriodique(TitreEvenement title, Proprietaire proprietaire,
                               DateEvenement dateDebut, Frequence frequence) {
        // Durée à 0 par défaut pour les périodiques selon l'ancien code
        super(title, proprietaire, dateDebut, new DureeMinutes(0));
        this.frequence = frequence;
    }

    @Override
    public String description() {
        return "Événement périodique : " + title.valeur() + " tous les " + frequence.valeur() + " jours";
    }

    @Override
    public boolean isWithinRange(DateEvenement debut, DateEvenement fin) {
        LocalDateTime temp = dateDebut.valeur();
        while (temp.isBefore(fin.valeur())) {
            if (!temp.isBefore(debut.valeur())) {
                return true;
            }
            temp = temp.plusDays(frequence.valeur());
        }
        return false;
    }
}
