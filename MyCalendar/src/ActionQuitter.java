public class ActionQuitter implements ActionMenu {
    @Override
    public void executer(Contexte contexte) {
        contexte.affichage.afficherMessage("Merci d'avoir utilisé MyCalendar. À bientôt !");
        contexte.arreter();
    }
}