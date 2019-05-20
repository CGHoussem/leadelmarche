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
public class Produit {

    private int id;
    private String nom;
    private String description;
    private TypeProduit type;
    private float prix;
    private String pays;
    private float tva;
    private int qteStock;
    private String nomFournisseur;

    public Produit(int id, String nom, String description, TypeProduit type, float prix, String pays, float tva, int qteStock, String nomFournisseur) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.type = type;
        this.prix = prix;
        this.pays = pays;
        this.tva = tva;
        this.qteStock = qteStock;
        this.nomFournisseur = nomFournisseur;
    }

    public Produit(String nom, String description, TypeProduit type, float prix, String pays, float tva, int qteStock, String nomFournisseur) {
        this.nom = nom;
        this.description = description;
        this.type = type;
        this.prix = prix;
        this.pays = pays;
        this.tva = tva;
        this.qteStock = qteStock;
        this.nomFournisseur = nomFournisseur;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeProduit getType() {
        return type;
    }

    public void setType(TypeProduit type) {
        this.type = type;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public float getTva() {
        return tva;
    }

    public void setTva(float tva) {
        this.tva = tva;
    }

    public int getQteStock() {
        return qteStock;
    }

    public void setQteStock(int qteStock) {
        this.qteStock = qteStock;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

    @Override
    public String toString(){
        return nom;
    }
    
}
