package Mapping;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "mitglied")
public class Mitglied {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mitgliedid")
    private Integer mitgliedID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    // Ein Mitglied kann mehrere Ausleihen haben (1:N)
    @OneToMany(mappedBy = "mitglied", cascade = CascadeType.ALL)
    private List<Ausleihe> ausleihen;

    // ── Konstruktoren ─────────────────────────────────────
    public Mitglied() {}

    public Mitglied(String name, String email) {
        this.name  = name;
        this.email = email;
    }

    // ── Getter & Setter ───────────────────────────────────
    public Integer getMitgliedID() { return mitgliedID; }
    public String  getName()       { return name; }
    public String  getEmail()      { return email; }

    public void setMitgliedID(Integer id)  { this.mitgliedID = id; }
    public void setName(String name)       { this.name       = name; }
    public void setEmail(String email)     { this.email      = email; }

    @Override
    public String toString() {
        return String.format("Mitglied[ID=%d, Name=%s, Email=%s]",
                mitgliedID, name, email);
    }
}