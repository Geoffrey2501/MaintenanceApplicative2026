import ValueObject.DateEvenement;
import ValueObject.TitreEvenement;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ValueObjectsTest {

    @Test
    void titreNePeutPasEtreVide() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement(""));
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement(null));
    }

   @Test
    void DateEvenementNePeutPasEtreNull() {
        assertThrows(IllegalArgumentException.class, () -> new ValueObject.DateEvenement(null));
    }

    @Test
    void DureeMinutesNePeutPasEtreNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ValueObject.DureeMinutes(-1));
    }

    @Test
    void FrequenceDoitEtrePositiveNonNull() {
        assertThrows(IllegalArgumentException.class, () -> new ValueObject.Frequence(0));
        assertThrows(IllegalArgumentException.class, () -> new ValueObject.Frequence(-1));
    }
    
}