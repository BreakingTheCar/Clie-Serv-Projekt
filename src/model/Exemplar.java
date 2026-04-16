package model;

import jakarta.persistence.*;

@Entity
@Table(name = "exemplar")
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exemplarid")
    private Integer exemplarID;

    // N:1 zu Buch — viele Exemplare gehören zu einem Buch
    @ManyToOne
    @JoinColumn(name = "isbn")
    private Buch buch;

    // N:1 zu Regal — viele Exemplare stehen in einem Regal
    @ManyToOne
    @JoinColumn(name = "regalid")
    private Regal regal;

    // ── Konstruktoren ─────────────────────────────────────
    public Exemplar() {}

    public Exemplar(Buch buch, Regal regal) {
        this.buch  = buch;
        this.regal = regal;
    }

    // ── Getter & Setter ───────────────────────────────────
    public Integer getExemplarID() { return exemplarID; }
    public Buch    getBuch()       { return buch; }
    public Regal   getRegal()      { return regal; }

    public void setExemplarID(Integer id) { this.exemplarID = id; }
    public void setBuch(Buch buch)        { this.buch       = buch; }
    public void setRegal(Regal regal)     { this.regal      = regal; }

    @Override
    public String toString() {
        return String.format("Exemplar[ID=%d, Buch=%s, Regal=%s]",
                exemplarID,
                buch  != null ? buch.getTitel()     : "null",
                regal != null ? regal.getStandort()  : "null");
    }
}