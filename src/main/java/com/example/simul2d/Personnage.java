package com.example.simul2d;

import java.util.Objects;

/**
 * Classe représentant un personnage simple.
 *
 * Un personnage a des propriétés comme :
 * - Son nom
 * - Sa vie (HP)
 * - Sa position
 * etc.
 *
 * Pour cet exemple, on commence avec juste le nom !
 */
public class Personnage {
    // Les attributs = les données du personnage
    private String nom;
    private int vie;
    private int x;
    private int y;

    // Constructeur = méthode spéciale pour créer un personnage
    // On doit toujours créer un personnage avec au moins un nom
    public Personnage(String nom) {
        this.nom = nom;
        this.vie = 100; // Au départ, le personnage a 100 points de vie
        this.x = 0;     // Position initiale : 0, 0
        this.y = 0;
    }

    // Getters = méthodes pour RÉCUPÉRER les données
    public String getNom() {
        return this.nom;
    }

    public int getVie() {
        return this.vie;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    // Setters = méthodes pour MODIFIER les données
    public void setNom(String nouveauNom) {
        this.nom = nouveauNom;
    }

    public void setVie(int nouvelleVie) {
        this.vie = nouvelleVie;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Méthode de présentation
    @Override
    public String toString() {
        return "Personnage{" +
                "nom='" + nom + '\'' +
                ", vie=" + vie +
                ", position=(" + x + ", " + y + ")" +
                '}';
    }

    // Exemple de méthode : faire perdre de la vie
    public void prenderDegats(int degats) {
        this.vie -= degats;
        if (this.vie < 0) {
            this.vie = 0; // La vie ne peut pas être négative
        }
    }

    // Exemple de méthode : soigner le personnage
    public void soigner(int soin) {
        this.vie += soin;
        if (this.vie > 100) {
            this.vie = 100; // La vie ne peut pas dépasser 100
        }
    }
    
    @Override
    public boolean equals(Object object){
        // conditions * 50
        if (object instanceof Personnage) return false;
        
        return Objects.equals(((Personnage) object).getNom(), this.getNom());
        
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(getNom());
    }
}



