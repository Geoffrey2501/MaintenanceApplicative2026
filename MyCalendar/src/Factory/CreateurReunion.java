package Factory;

import Event.*;
import Service.InputHandler;
import ValueObject.*;


public class CreateurReunion implements CreateurEvenement {
    @Override
    public Event saisirEtCreer(InputHandler input, String utilisateur) {
        String titre = input.lireLigne("Titre : ");
        DateEvenement date = SaisieHelper.saisirDate(input);
        int duree = input.lireEntier("Durée (min) : ");
        String lieu = input.lireLigne("Lieu : ");
        String participants = input.lireLigne("Participants : ");

        return new Reunion(new TitreEvenement(titre), new Proprietaire(utilisateur),
                date, new DureeMinutes(duree), new Lieu(lieu), new Participant(participants));
    }
}