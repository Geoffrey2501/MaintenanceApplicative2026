package Factory;

import Event.*;
import Service.InputHandler;
import ValueObject.*;

public class CreateurPeriodique implements CreateurEvenement {

    @Override
    public Event saisirEtCreer(InputHandler input, String utilisateur) {
        // Saisie du titre via le Value Object TitreEvenement
        String titre = input.lireLigne("Titre de l'événement périodique : ");

        // Utilisation du Helper pour centraliser la saisie complexe de la date de début
        DateEvenement debut = SaisieHelper.saisirDate(input);

        // Saisie de la fréquence en jours
        int jours = input.lireEntier("Fréquence (en jours) : ");

        // Construction de l'objet avec ses Value Objects respectifs
        return new EvenementPeriodique(
                new TitreEvenement(titre),
                new Proprietaire(utilisateur),
                debut,
                new Frequence(jours)
        );
    }
}