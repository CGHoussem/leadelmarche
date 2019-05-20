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
public class Client {

    private int id;
    private String nom;
    private String prenom;
    private int numCarteFidelite;
    private String mail;
    private int codePostal;

    public Client() {
    }

    public Client(int id, String nom, String prenom, int numCarteFidelite, String mail, int codePostal) {
        this.id = id;
        this.nom = nom.toUpperCase();
        this.prenom = prenom;
        this.numCarteFidelite = numCarteFidelite;
        this.mail = mail;
        this.codePostal = codePostal;
    }

    public Client(String nom, String prenom, int numCarteFidelite, String mail, int codePostal) {
        this.nom = nom.toUpperCase();
        this.prenom = prenom;
        this.numCarteFidelite = numCarteFidelite;
        this.mail = mail;
        this.codePostal = codePostal;
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
        this.nom = nom.toUpperCase();
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNumCarteFidelite() {
        return numCarteFidelite;
    }

    public void setNumCarteFidelite(int numCarteFidelite) {
        this.numCarteFidelite = numCarteFidelite;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public String toString() {
        return numCarteFidelite + " | " + nom + " " + prenom;
    }

}
