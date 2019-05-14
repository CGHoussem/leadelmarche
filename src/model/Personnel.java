/*
 * Copyright (C) 2019 Houssem Ben Mabrouk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package model;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class Personnel {

    private int id;
    private String nom;
    private String adressePerso;
    private String adresseTravail;
    private String poste;
    private Personnel superieur;
    private int numBadge;

    public Personnel(int id, String nom, String adressePerso, String adresseTravail, String poste, Personnel superieur, int numBadge) {
        this.id = id;
        this.nom = nom;
        this.adressePerso = adressePerso;
        this.adresseTravail = adresseTravail;
        this.poste = poste;
        this.superieur = superieur;
        this.numBadge = numBadge;
    }

    public Personnel(String nom, String adressePerso, String adresseTravail, String poste, Personnel superieur, int numBadge) {
        this.nom = nom;
        this.adressePerso = adressePerso;
        this.adresseTravail = adresseTravail;
        this.poste = poste;
        this.superieur = superieur;
        this.numBadge = numBadge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdressePerso() {
        return adressePerso;
    }

    public void setAdressePerso(String adressePerso) {
        this.adressePerso = adressePerso;
    }

    public String getAdresseTravail() {
        return adresseTravail;
    }

    public void setAdresseTravail(String adresseTravail) {
        this.adresseTravail = adresseTravail;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Personnel getSuperieur() {
        return superieur;
    }

    public void setSuperieur(Personnel superieur) {
        this.superieur = superieur;
    }

    public int getNumBadge() {
        return numBadge;
    }

    public void setNumBadge(int numBadge) {
        this.numBadge = numBadge;
    }

}