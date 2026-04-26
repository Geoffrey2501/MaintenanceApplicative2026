package Factory;


import Event.Event;
import Service.InputHandler;

public interface CreateurEvenement {
    Event saisirEtCreer(InputHandler input, String utilisateur);
}