package Factory;

import Event.*;
import Service.InputHandler;
import ValueObject.*;

public class CreateurRendezVous implements CreateurEvenement {
    @Override
    public Event saisirEtCreer(InputHandler input, String utilisateur) {
        String titre = input.lireLigne("Titre de l'événement : ");

        // Appel au helper pour récupérer le Value Object DateEvenement
        DateEvenement debut = SaisieHelper.saisirDate(input);

        int duree = input.lireEntier("Durée (en minutes) : ");

        return new RendezVousPersonnel(
                new TitreEvenement(titre),
                new Proprietaire(utilisateur),
                debut,
                new DureeMinutes(duree)
        );
    }
}