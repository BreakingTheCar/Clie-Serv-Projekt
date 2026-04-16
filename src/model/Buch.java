package model;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "buch")
public class Buch {

    @Id
    @Column(name = "isbn")
    private Long isbn;

    @Column(name = "titel")
    private String titel;

    @Column(name = "autor")
    private String autor;

    @Column(name = "kategorie")
    private String kategorie;

    // Ein Buch kann mehrere Exemplare haben (1:N)
    @OneToMany(mappedBy = "buch", cascade = CascadeType.ALL)
    private List<Exemplar> exemplare;

    // ── Konstruktoren ─────────────────────────────────────
    public Buch() {}

    public Buch(Long isbn, String titel, String autor, String kategorie) {
        this.isbn      = isbn;
        this.titel     = titel;
        this.autor     = autor;
        this.kategorie = kategorie;
    }

    // ── Getter & Setter ───────────────────────────────────
    public Long   getIsbn()      { return isbn; }
    public String getTitel()     { return titel; }
    public String getAutor()     { return autor; }
    public String getKategorie() { return kategorie; }

    public void setIsbn(Long isbn)           { this.isbn      = isbn; }
    public void setTitel(String titel)       { this.titel     = titel; }
    public void setAutor(String autor)       { this.autor     = autor; }
    public void setKategorie(String kat)     { this.kategorie = kat; }

    @Override
    public String toString() {
        return String.format("Buch[ISBN=%d, Titel=%s, Autor=%s, Kategorie=%s]",
                isbn, titel, autor, kategorie);
    }
}