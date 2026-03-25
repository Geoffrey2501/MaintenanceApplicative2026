import ValueObject.*;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CalendarManager calendar = new CalendarManager();
        AffichageService affichageService = new AffichageConsoleService();
        Scanner scanner = new Scanner(System.in);
        String utilisateur = null;
        boolean continuer = true;

        String utilisateurs[] = new String[99];
        String motsDePasses[] = new String[99];
        int nbUtilisateurs = 0;

        while (true) {

            if (utilisateur == null) {
                affichageService.afficherMessage("  _____         _                   _                __  __");
                affichageService.afficherMessage(" / ____|       | |                 | |              |  \\/  |");
                affichageService.afficherMessage(
                        "| |       __ _ | |  ___  _ __    __| |  __ _  _ __  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __");
                affichageService.afficherMessage(
                        "| |      / _` || | / _ \\| '_ \\  / _` | / _` || '__| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|");
                affichageService.afficherMessage(
                        "| |____ | (_| || ||  __/| | | || (_| || (_| || |    | |  | || (_| || | | || (_| || (_| ||  __/| |");
                affichageService.afficherMessage(
                        " \\_____| \\__,_||_| \\___||_| |_| \\__,_| \\__,_||_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|");
                affichageService.afficherMessage(
                        "                                                                                   __/ |");
                affichageService.afficherMessage(
                        "                                                                                  |___/");

                affichageService.afficherMessage("1 - Se connecter");
                affichageService.afficherMessage("2 - Creer un compte");
                affichageService.afficherMessage("Choix : ");

                switch (scanner.nextLine()) {
                    case "1":
                        affichageService.afficherInvite("Nom d'utilisateur: ");
                        utilisateur = scanner.nextLine();

                        if (utilisateur.equals("Roger")) {
                            String motDePasse = scanner.nextLine();
                            if (!motDePasse.equals("Chat")) {
                                utilisateur = null;
                            }
                        } else {
                            if (utilisateur.equals("Pierre")) {
                                String motDePasse = scanner.nextLine();
                                if (!motDePasse.equals("KiRouhl")) {
                                    utilisateur = null;
                                }
                            } else {
                                affichageService.afficherInvite("Mot de passe: ");
                                String motDePasse = scanner.nextLine();

                                for (int i = 0; i < nbUtilisateurs; i = i + 1) {
                                    if (utilisateurs[i].equals(utilisateur) && motsDePasses[i].equals(motDePasse)) {
                                        utilisateur = utilisateurs[i];
                                    }
                                }
                            }
                        }
                        break;

                    case "2":
                        affichageService.afficherInvite("Nom d'utilisateur: ");
                        utilisateur = scanner.nextLine();
                        affichageService.afficherInvite("Mot de passe: ");
                        String motDePasse = scanner.nextLine();
                        affichageService.afficherInvite("Repeter mot de passe: ");
                        if (scanner.nextLine().equals(motDePasse)) {
                            utilisateurs[nbUtilisateurs] = utilisateur;
                            motsDePasses[nbUtilisateurs] = motDePasse;
                            nbUtilisateurs = nbUtilisateurs + 1;
                        } else {
                            affichageService.afficherMessage("Les mots de passes ne correspondent pas...");
                            utilisateur = null;
                        }
                        break;
                }
            }

            while (continuer && utilisateur != null) {
                affichageService.afficherSeparation();
                affichageService.afficherMessage("Bonjour, " + utilisateur);
                affichageService.afficherTitre("Menu Gestionnaire d'Evenements");
                affichageService.afficherMessage("1 - Voir les evenements");
                affichageService.afficherMessage("2 - Ajouter un rendez-vous perso");
                affichageService.afficherMessage("3 - Ajouter une reunion");
                affichageService.afficherMessage("4 - Ajouter un evenement periodique");
                affichageService.afficherMessage("5 - Se deconnecter");
                affichageService.afficherInvite("Votre choix : ");

                String choix = scanner.nextLine();

                switch (choix) {
                    case "1":
                        affichageService.afficherTitre("Menu de visualisation d'Evenements");
                        affichageService.afficherMessage("1 - Afficher TOUS les evenements");
                        affichageService.afficherMessage("2 - Afficher les evenements d'un MOIS precis");
                        affichageService.afficherMessage("3 - Afficher les evenements d'une SEMAINE precise");
                        affichageService.afficherMessage("4 - Afficher les evenements d'un JOUR precis");
                        affichageService.afficherMessage("5 - Retour");
                        affichageService.afficherInvite("Votre choix : ");

                        choix = scanner.nextLine();

                        switch (choix) {
                            case "1":
                                affichageService.afficherListe(calendar.events);
                                break;

                            case "2":
                                affichageService.afficherInvite("Entrez l'annee (AAAA) : ");
                                int anneeMois = Integer.parseInt(scanner.nextLine());
                                affichageService.afficherInvite("Entrez le mois (1-12) : ");
                                int mois = Integer.parseInt(scanner.nextLine());

                                LocalDateTime debutMois = LocalDateTime.of(anneeMois, mois, 1, 0, 0);
                                LocalDateTime finMois = debutMois.plusMonths(1).minusSeconds(1);
                                DateEvenement debutVO = new DateEvenement(debutMois);
                                DateEvenement finVO = new DateEvenement(finMois);
                                affichageService.afficherListe(calendar.eventsDansPeriode(debutVO, finVO));
                                break;

                            case "3":
                                affichageService.afficherInvite("Entrez l'annee (AAAA) : ");
                                int anneeSemaine = Integer.parseInt(scanner.nextLine());
                                affichageService.afficherInvite("Entrez le numero de semaine (1-52) : ");
                                int semaine = Integer.parseInt(scanner.nextLine());

                                LocalDateTime debutSemaine = LocalDateTime.now()
                                        .withYear(anneeSemaine)
                                        .with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
                                        .with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
                                        .withHour(0).withMinute(0);
                                LocalDateTime finSemaine = debutSemaine.plusDays(7).minusSeconds(1);

                                DateEvenement d = new DateEvenement(debutSemaine);
                                DateEvenement f = new DateEvenement(finSemaine);

                                affichageService.afficherListe(calendar.eventsDansPeriode(d, f));
                                break;

                            case "4":
                                affichageService.afficherInvite("Entrez l'annee (AAAA) : ");
                                int anneeJour = Integer.parseInt(scanner.nextLine());
                                affichageService.afficherInvite("Entrez le mois (1-12) : ");
                                int moisJour = Integer.parseInt(scanner.nextLine());
                                affichageService.afficherInvite("Entrez le jour (1-31) : ");
                                int jour = Integer.parseInt(scanner.nextLine());

                                LocalDateTime debutJour = LocalDateTime.of(anneeJour, moisJour, jour, 0, 0);
                                LocalDateTime finJour = debutJour.plusDays(1).minusSeconds(1);

                                DateEvenement dj = new DateEvenement(debutJour);
                                DateEvenement fj = new DateEvenement(finJour);

                                affichageService.afficherListe(calendar.eventsDansPeriode(dj, fj));
                                break;
                        }
                        break;

                    case "2":
                        // Ajout simplifié d'un RDV personnel
                        affichageService.afficherInvite("Titre de l'evenement : ");
                        String titre = scanner.nextLine();
                        affichageService.afficherInvite("Annee (AAAA) : ");
                        int annee = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Mois (1-12) : ");
                        int moisRdv = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Jour (1-31) : ");
                        int jourRdv = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Heure debut (0-23) : ");
                        int heure = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Minute debut (0-59) : ");
                        int minute = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Duree (en minutes) : ");
                        int duree = Integer.parseInt(scanner.nextLine());

                        Event rdv = new RendezVousPersonnel(
                                new TitreEvenement(titre),
                                new Proprietaire(utilisateur),
                                new DateEvenement(LocalDateTime.of(annee, moisRdv, jourRdv, heure, minute)),
                                new DureeMinutes(duree)
                        );
                        calendar.ajouterEvent(rdv);

                        affichageService.afficherMessage("Evenement ajoute.");
                        break;

                    case "3":
                        // Ajout simplifié d'une réunion
                        affichageService.afficherInvite("Titre de l'evenement : ");
                        String titre2 = scanner.nextLine();
                        affichageService.afficherInvite("Annee (AAAA) : ");
                        int annee2 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Mois (1-12) : ");
                        int moisRdv2 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Jour (1-31) : ");
                        int jourRdv2 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Heure debut (0-23) : ");
                        int heure2 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Minute debut (0-59) : ");
                        int minute2 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Duree (en minutes) : ");
                        int duree2 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherMessage("Lieu :");
                        String lieu = scanner.nextLine();

                        String participants = utilisateur;

                        boolean encore = true;
                        affichageService.afficherMessage("Ajouter un participant ? (oui / non)");
                        while (scanner.nextLine().equals("oui")) {
                            affichageService.afficherInvite("Participants : " + participants);
                            participants += ", " + scanner.nextLine();
                        }

                        Event reunion = new Reunion(
                                new TitreEvenement(titre2),
                                new Proprietaire(utilisateur),
                                new DateEvenement(LocalDateTime.of(annee2, moisRdv2, jourRdv2, heure2, minute2)),
                                new DureeMinutes(duree2),
                                new Lieu(lieu),
                                new Participant(participants)
                        );
                        calendar.ajouterEvent(reunion);

                        affichageService.afficherMessage("Evenement ajoute.");
                        break;

                    case "4":
                        // Ajout simplifié d'une réunion
                        affichageService.afficherInvite("Titre de l'evenement : ");
                        String titre3 = scanner.nextLine();
                        affichageService.afficherInvite("Annee (AAAA) : ");
                        int annee3 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Mois (1-12) : ");
                        int moisRdv3 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Jour (1-31) : ");
                        int jourRdv3 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Heure debut (0-23) : ");
                        int heure3 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Minute debut (0-59) : ");
                        int minute3 = Integer.parseInt(scanner.nextLine());
                        affichageService.afficherInvite("Frequence (en jours) : ");
                        int frequence = Integer.parseInt(scanner.nextLine());

                        Event periodique = new EvenementPeriodique(
                                new TitreEvenement(titre3),
                                new Proprietaire(utilisateur),
                                new DateEvenement(LocalDateTime.of(annee3, moisRdv3, jourRdv3, heure3, minute3)),
                                new Frequence(frequence)
                        );
                        calendar.ajouterEvent(periodique);

                        affichageService.afficherMessage("Evenement ajoute.");
                        break;

                    default:
                        affichageService.afficherMessage("Deconnexion ! Voulez-vous continuer ? (O/N)");
                        continuer = scanner.nextLine().trim().equalsIgnoreCase("oui");

                        utilisateur = null;
                }
            }
        }
    }

}
