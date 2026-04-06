package Mapping;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "regal")
public class Regal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regalid")
    private Integer regalID;

    @Column(name = "standort")
    private String standort;

    // Ein Regal enthält mehrere Exemplare (1:N)
    @OneToMany(mappedBy = "regal", cascade = CascadeType.ALL)
    private List<Exemplar> exemplare;

    // ── Konstruktoren ─────────────────────────────────────
    public Regal() {}

    public Regal(String standort) {
        this.standort = standort;
    }

    // ── Getter & Setter ───────────────────────────────────
    public Integer getRegalID()  { return regalID; }
    public String  getStandort() { return standort; }

    public void setRegalID(Integer id)     { this.regalID  = id; }
    public void setStandort(String standort) { this.standort = standort; }

    @Override
    public String toString() {
        return String.format("Regal[ID=%d, Standort=%s]", regalID, standort);
    }
}