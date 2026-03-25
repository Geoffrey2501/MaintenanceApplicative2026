package ValueObject;

// Dans ValueObject/IntervalleTemps.java
public record IntervalleTemps(DateEvenement debut, DateEvenement fin) {
    public boolean chevauche(IntervalleTemps autre) {
        // Formule standard : (D1 < F2) ET (D2 < F1)
        return this.debut.estAvant(autre.fin) && autre.debut.estAvant(this.fin);
    }
}
