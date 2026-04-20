import ValueObject.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ValueObjectsTest {

    @Test
    void eventIdNePeutPasEtreVide() {
        assertThrows(IllegalArgumentException.class, () -> new EventId(""));
        assertThrows(IllegalArgumentException.class, () -> new EventId("   "));
        assertThrows(IllegalArgumentException.class, () -> new EventId(null));
    }

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
        assertThrows(IllegalArgumentException.class, () -> new ValueObject.Frequence(-1));
    }

    @Test
    void ParticipantNotNull() {
        assertThrows(IllegalArgumentException.class, () -> new Participant(null));
        assertThrows(IllegalArgumentException.class, () -> new Participant(""));
    }

    @Test
    void LieuNotNull() {
        assertThrows(IllegalArgumentException.class, () -> new Lieu(null));
        assertThrows(IllegalArgumentException.class, () -> new Lieu(""));
    }

    @Test
    void ProprietaireNotNull() {
        assertThrows(IllegalArgumentException.class, () -> new Proprietaire(null));
        assertThrows(IllegalArgumentException.class, () -> new Proprietaire(""));
    }

    @Test
    void dateComparaison() {
        DateEvenement matin = new DateEvenement(LocalDateTime.of(2026, 3, 23, 8, 0));
        DateEvenement midi = new DateEvenement(LocalDateTime.of(2026, 3, 23, 12, 0));

        assertTrue(matin.estAvant(midi));
        assertFalse(midi.estAvant(matin));
    }

    @Test
    void intervalleChevauchement() {
        DateEvenement d1 = new DateEvenement(LocalDateTime.of(2026, 3, 23, 10, 0));
        DateEvenement f1 = new DateEvenement(LocalDateTime.of(2026, 3, 23, 11, 0));
        IntervalleTemps i1 = new IntervalleTemps(d1, f1);

        // Intervalle qui empiète (10h30 - 11h30)
        IntervalleTemps i2 = new IntervalleTemps(
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 10, 30)),
                new DateEvenement(LocalDateTime.of(2026, 3, 23, 11, 30))
        );

        assertTrue(i1.chevauche(i2), "Les intervalles devraient se chevaucher");
    }
    
}