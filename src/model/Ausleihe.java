package model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ausleihe")
public class Ausleihe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ausleiheid")
    private Integer ausleiheID;

    // N:1 zu Exemplar
    @ManyToOne
    @JoinColumn(name = "exemplarid")
    private Exemplar exemplar;

    // N:1 zu Mitglied
    @ManyToOne
    @JoinColumn(name = "mitgliedid")
    private Mitglied mitglied;

    @Column(name = "ausleihdatum", nullable = false)
    private LocalDate ausleihdatum;

    @Column(name = "rueckgabedatum")
    private LocalDate rueckgabedatum;  // null = noch ausgeliehen

    // ── Konstruktoren ─────────────────────────────────────
    public Ausleihe() {}

    public Ausleihe(Exemplar exemplar, Mitglied mitglied, LocalDate ausleihdatum) {
        this.exemplar     = exemplar;
        this.mitglied     = mitglied;
        this.ausleihdatum = ausleihdatum;
    }

    // ── Getter & Setter ───────────────────────────────────
    public Integer   getAusleiheID()      { return ausleiheID; }
    public Exemplar  getExemplar()        { return exemplar; }
    public Mitglied  getMitglied()        { return mitglied; }
    public LocalDate getAusleihdatum()    { return ausleihdatum; }
    public LocalDate getRueckgabedatum()  { return rueckgabedatum; }

    public void setAusleiheID(Integer id)            { this.ausleiheID     = id; }
    public void setExemplar(Exemplar exemplar)        { this.exemplar       = exemplar; }
    public void setMitglied(Mitglied mitglied)        { this.mitglied       = mitglied; }
    public void setAusleihdatum(LocalDate datum)      { this.ausleihdatum   = datum; }
    public void setRueckgabedatum(LocalDate datum)    { this.rueckgabedatum = datum; }

    @Override
    public String toString() {
        return String.format("Ausleihe[ID=%d, Buch=%s, Mitglied=%s, seit=%s, zurück=%s]",
                ausleiheID,
                exemplar != null ? exemplar.getBuch().getTitel() : "null",
                mitglied != null ? mitglied.getName()            : "null",
                ausleihdatum,
                rueckgabedatum != null ? rueckgabedatum : "noch offen");
    }
}