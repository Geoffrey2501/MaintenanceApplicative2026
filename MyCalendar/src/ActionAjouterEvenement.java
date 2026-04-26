import Event.Event;
import Factory.CreateurEvenement;

public class ActionAjouterEvenement implements ActionMenu {
    private final CreateurEvenement factory;

    public ActionAjouterEvenement(CreateurEvenement factory) {
        this.factory = factory;
    }

    @Override
    public void executer(Contexte contexte) {
        Event e = factory.saisirEtCreer(contexte.input, contexte.getUtilisateur());
        contexte.calendar.ajouterEvent(e);
        contexte.affichage.afficherMessage("Événement ajouté.");
    }
}