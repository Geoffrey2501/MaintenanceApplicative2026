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
    
}