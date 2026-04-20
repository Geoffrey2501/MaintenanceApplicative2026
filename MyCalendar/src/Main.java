import ValueObject.*;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        CalendarManager calendar = new CalendarManager();
        AffichageService affichageService = new AffichageConsoleService();
        InputHandler input = new InputHandler(affichageService);
        AuthService authService = new AuthService();

        String utilisateur = null;
        boolean continuer = true;

        while (true) {
            if (utilisateur == null) {
                afficherMenuAccueil(affichageService);
                String choixAccueil = input.lireLigne();

                switch (choixAccueil) {
                    case "1":
                        utilisateur = connecterUtilisateur(authService, input);
                        break;
                    case "2":
                        utilisateur = creerCompte(authService, input, affichageService);
                        break;
                    default:
                        affichageService.afficherMessage("Choix invalide.");
                }
            }

            while (continuer && utilisateur != null) {
                afficherMenuPrincipal(affichageService, utilisateur);
                String choix = input.lireLigne();

                switch (choix) {
                    case "1":
                        afficherEvenements(calendar, affichageService, input);
                        break;

                    case "2":
                        ajouterRendezVousPersonnel(calendar, utilisateur, affichageService, input);
                        break;

                    case "3":
                        ajouterReunion(calendar, utilisateur, affichageService, input);
                        break;

                    case "4":
                        ajouterEvenementPeriodique(calendar, utilisateur, affichageService, input);
                        break;

                    default:
                        affichageService.afficherMessage("Deconnexion ! Voulez-vous continuer ? (O/N)");
                        continuer = input.lireLigne().trim().equalsIgnoreCase("oui");
                        utilisateur = null;
                }
            }
        }
    }

    private static void afficherMenuAccueil(AffichageService affichageService) {
        affichageService.afficherMessage("  _____         _                   _                __  __");
        affichageService.afficherMessage(" / ____|       | |                 | |              |  \\/  |");
        affichageService.afficherMessage("| |       __ _ | |  ___  _ __    __| |  __ _  _ __  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __");
        affichageService.afficherMessage("| |      / _` || | / _ \\| '_ \\  / _` | / _` || '__| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|");
        affichageService.afficherMessage("| |____ | (_| || ||  __/| | | || (_| || (_| || |    | |  | || (_| || | | || (_| || (_| ||  __/| |");
        affichageService.afficherMessage(" \\_____| \\__,_||_| \\___||_| |_| \\__,_| \\__,_||_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|");
        affichageService.afficherMessage("                                                                                   __/ |");
        affichageService.afficherMessage("                                                                                  |___/");
        affichageService.afficherMessage("1 - Se connecter");
        affichageService.afficherMessage("2 - Creer un compte");
        affichageService.afficherMessage("Choix : ");
    }

    private static String connecterUtilisateur(AuthService authService, InputHandler input) {
        String identifiant = input.lireLigne("Nom d'utilisateur: ");

        // Conserve le comportement historique: pas d'invite explicite pour ces comptes.
        String motDePasse;
        if (identifiant.equals("Roger") || identifiant.equals("Pierre")) {
            motDePasse = input.lireLigne();
        } else {
            motDePasse = input.lireLigne("Mot de passe: ");
        }

        return authService.connecter(identifiant, motDePasse);
    }

    private static String creerCompte(AuthService authService, InputHandler input, AffichageService affichageService) {
        String identifiant = input.lireLigne("Nom d'utilisateur: ");
        String motDePasse = input.lireLigne("Mot de passe: ");
        String confirmation = input.lireLigne("Repeter mot de passe: ");

        if (authService.creerCompte(identifiant, motDePasse, confirmation)) {
            return identifiant;
        }

        affichageService.afficherMessage("Les mots de passes ne correspondent pas...");
        return null;
    }

    private static void afficherMenuPrincipal(AffichageService affichageService, String utilisateur) {
        affichageService.afficherSeparation();
        affichageService.afficherMessage("Bonjour, " + utilisateur);
        affichageService.afficherTitre("Menu Gestionnaire d'Evenements");
        affichageService.afficherMessage("1 - Voir les evenements");
        affichageService.afficherMessage("2 - Ajouter un rendez-vous perso");
        affichageService.afficherMessage("3 - Ajouter une reunion");
        affichageService.afficherMessage("4 - Ajouter un evenement periodique");
        affichageService.afficherMessage("5 - Se deconnecter");
        affichageService.afficherInvite("Votre choix : ");
    }

    private static void afficherEvenements(CalendarManager calendar, AffichageService affichageService, InputHandler input) {
        affichageService.afficherTitre("Menu de visualisation d'Evenements");
        affichageService.afficherMessage("1 - Afficher TOUS les evenements");
        affichageService.afficherMessage("2 - Afficher les evenements d'un MOIS precis");
        affichageService.afficherMessage("3 - Afficher les evenements d'une SEMAINE precise");
        affichageService.afficherMessage("4 - Afficher les evenements d'un JOUR precis");
        affichageService.afficherMessage("5 - Retour");
        affichageService.afficherInvite("Votre choix : ");

        String choix = input.lireLigne();

        switch (choix) {
            case "1":
                affichageService.afficherListe(calendar.events);
                break;

            case "2":
                int anneeMois = input.lireEntier("Entrez l'annee (AAAA) : ");
                int mois = input.lireEntier("Entrez le mois (1-12) : ");

                LocalDateTime debutMois = LocalDateTime.of(anneeMois, mois, 1, 0, 0);
                LocalDateTime finMois = debutMois.plusMonths(1).minusSeconds(1);
                affichageService.afficherListe(
                        calendar.eventsDansPeriode(new DateEvenement(debutMois), new DateEvenement(finMois))
                );
                break;

            case "3":
                int anneeSemaine = input.lireEntier("Entrez l'annee (AAAA) : ");
                int semaine = input.lireEntier("Entrez le numero de semaine (1-52) : ");

                LocalDateTime debutSemaine = LocalDateTime.now()
                        .withYear(anneeSemaine)
                        .with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
                        .with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
                        .withHour(0).withMinute(0);
                LocalDateTime finSemaine = debutSemaine.plusDays(7).minusSeconds(1);

                affichageService.afficherListe(
                        calendar.eventsDansPeriode(new DateEvenement(debutSemaine), new DateEvenement(finSemaine))
                );
                break;

            case "4":
                int anneeJour = input.lireEntier("Entrez l'annee (AAAA) : ");
                int moisJour = input.lireEntier("Entrez le mois (1-12) : ");
                int jour = input.lireEntier("Entrez le jour (1-31) : ");

                LocalDateTime debutJour = LocalDateTime.of(anneeJour, moisJour, jour, 0, 0);
                LocalDateTime finJour = debutJour.plusDays(1).minusSeconds(1);

                affichageService.afficherListe(
                        calendar.eventsDansPeriode(new DateEvenement(debutJour), new DateEvenement(finJour))
                );
                break;

            default:
                break;
        }
    }

    private static void ajouterRendezVousPersonnel(
            CalendarManager calendar,
            String utilisateur,
            AffichageService affichageService,
            InputHandler input
    ) {
        String titre = input.lireLigne("Titre de l'evenement : ");
        int annee = input.lireEntier("Annee (AAAA) : ");
        int mois = input.lireEntier("Mois (1-12) : ");
        int jour = input.lireEntier("Jour (1-31) : ");
        int heure = input.lireEntier("Heure debut (0-23) : ");
        int minute = input.lireEntier("Minute debut (0-59) : ");
        int duree = input.lireEntier("Duree (en minutes) : ");

        Event rdv = new RendezVousPersonnel(
                new TitreEvenement(titre),
                new Proprietaire(utilisateur),
                new DateEvenement(LocalDateTime.of(annee, mois, jour, heure, minute)),
                new DureeMinutes(duree)
        );
        calendar.ajouterEvent(rdv);

        affichageService.afficherMessage("Evenement ajoute.");
    }

    private static void ajouterReunion(
            CalendarManager calendar,
            String utilisateur,
            AffichageService affichageService,
            InputHandler input
    ) {
        String titre = input.lireLigne("Titre de l'evenement : ");
        int annee = input.lireEntier("Annee (AAAA) : ");
        int mois = input.lireEntier("Mois (1-12) : ");
        int jour = input.lireEntier("Jour (1-31) : ");
        int heure = input.lireEntier("Heure debut (0-23) : ");
        int minute = input.lireEntier("Minute debut (0-59) : ");
        int duree = input.lireEntier("Duree (en minutes) : ");
        String lieu = input.lireLigne("Lieu : ");

        String participants = utilisateur;
        affichageService.afficherMessage("Ajouter un participant ? (oui / non)");
        while (input.lireLigne().equals("oui")) {
            participants += ", " + input.lireLigne("Participants : " + participants);
        }

        Event reunion = new Reunion(
                new TitreEvenement(titre),
                new Proprietaire(utilisateur),
                new DateEvenement(LocalDateTime.of(annee, mois, jour, heure, minute)),
                new DureeMinutes(duree),
                new Lieu(lieu),
                new Participant(participants)
        );
        calendar.ajouterEvent(reunion);

        affichageService.afficherMessage("Evenement ajoute.");
    }

    private static void ajouterEvenementPeriodique(
            CalendarManager calendar,
            String utilisateur,
            AffichageService affichageService,
            InputHandler input
    ) {
        String titre = input.lireLigne("Titre de l'evenement : ");
        int annee = input.lireEntier("Annee (AAAA) : ");
        int mois = input.lireEntier("Mois (1-12) : ");
        int jour = input.lireEntier("Jour (1-31) : ");
        int heure = input.lireEntier("Heure debut (0-23) : ");
        int minute = input.lireEntier("Minute debut (0-59) : ");
        int frequence = input.lireEntier("Frequence (en jours) : ");

        Event periodique = new EvenementPeriodique(
                new TitreEvenement(titre),
                new Proprietaire(utilisateur),
                new DateEvenement(LocalDateTime.of(annee, mois, jour, heure, minute)),
                new Frequence(frequence)
        );
        calendar.ajouterEvent(periodique);

        affichageService.afficherMessage("Evenement ajoute.");
    }
}
