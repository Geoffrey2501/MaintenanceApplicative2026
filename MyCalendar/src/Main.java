import Service.*;
import ValueObject.*;

public class Main {
    public static void main(String[] args) {
        // Initialisation du contexte (état de l'application)
        Contexte ctx = new Contexte(
                new CalendarManager(),
                new AffichageConsoleService(),
                new AuthService()
        );
        ctx.setInput(new InputHandler(ctx.affichage));

        Menu router = new Menu();

        // Boucle infinie sans "if" pour le contrôle de flux
        while (ctx.isApplicationEnCours()) {
            afficherMenuAdequat(ctx);
            String choix = ctx.input.lireLigne("Votre choix : ");

            // Le routeur s'occupe de tout sans aucun switch
            router.router(ctx, choix);
        }
    }

    private static void afficherMenuAdequat(Contexte ctx) {
        // On peut utiliser une Map d'affichage ou le polymorphisme ici aussi
        ctx.affichage.afficherSeparation();
        // Logique d'affichage déléguée au contexte ou à un service
        ctx.afficherMenuSelonEtat();
    }
}