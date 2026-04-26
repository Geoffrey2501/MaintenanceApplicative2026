public class ActionVoirEvenements implements ActionMenu {
    @Override
    public void executer(Contexte contexte) {
        contexte.affichage.afficherListe(contexte.calendar.events);
    }
}