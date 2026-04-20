import java.util.Scanner;

public class InputHandler {
    private final AffichageService affichageService;
    private final Scanner scanner;

    public InputHandler(AffichageService affichageService) {
        this(affichageService, new Scanner(System.in));
    }

    public InputHandler(AffichageService affichageService, Scanner scanner) {
        this.affichageService = affichageService;
        this.scanner = scanner;
    }

    public String lireLigne() {
        return scanner.nextLine();
    }

    public String lireLigne(String invite) {
        affichageService.afficherInvite(invite);
        return scanner.nextLine();
    }

    public int lireEntier(String invite) {
        return Integer.parseInt(lireLigne(invite));
    }
}

