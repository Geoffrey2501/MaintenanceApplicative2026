package Factory;

import Event.*;
import Service.InputHandler;
import ValueObject.*;

public class CreateurCours implements CreateurEvenement {
    @Override
    public Event saisirEtCreer(InputHandler input, String utilisateur) {
        String titre = input.lireLigne("Nom du cours : ");
        String matiereNom = input.lireLigne("Matière : ");
        DateEvenement date = SaisieHelper.saisirDate(input);
        int minutes = input.lireEntier("Durée (min) : ");
        String salleNom = input.lireLigne("Salle : ");

        return new Cours(
                new TitreEvenement(titre),
                new Proprietaire(utilisateur),
                date,
                new DureeMinutes(minutes),
                new Matiere(matiereNom),
                new Lieu(salleNom)
        );
    }
}