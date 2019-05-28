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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class Vente {

    private int id;
    private String nom;
    private Client client;
    private Personnel caissier;
    private List<Produit> produitsVendus;
    private float sousTotal;
    private float total;

    public Vente(int id, String nom, Client client, Personnel caissier, List<Produit> produitsVendus, float sousTotal, float total) {
        this.id = id;
        this.nom = nom;
        this.client = client;
        this.caissier = caissier;
        this.produitsVendus = produitsVendus;
        this.sousTotal = sousTotal;
        this.total = total;
    }

    public Vente(String nom, Client client, Personnel caissier, List<Produit> produitsVendus, float sousTotal, float total) {
        this.nom = nom;
        this.client = client;
        this.caissier = caissier;
        this.produitsVendus = produitsVendus;
        this.sousTotal = sousTotal;
        this.total = total;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Personnel getCaissier() {
        return caissier;
    }

    public void setCaissier(Personnel caissier) {
        this.caissier = caissier;
    }

    public List<Produit> getProduitsVendus() {
        return produitsVendus;
    }

    public void setProduitsVendus(List<Produit> produitsVendus) {
        this.produitsVendus = produitsVendus;
    }

    public float getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(float sousTotal) {
        this.sousTotal = sousTotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getStringTotal() {
        return total + " €";
    }

    public void enregistrerVente() throws IOException {
        LocalDateTime ldt = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(ldt);
        String filename = "factures/facture" + ldt.format(DateTimeFormatter.ofPattern("yMdHms")) + ".txt";
        FileWriter write = new FileWriter(filename, true);
        PrintWriter printLine = new PrintWriter(write);

        String date = ldt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        printLine.printf("=========================\n");
        printLine.printf("Vente " + nom + "\n");
        printLine.printf("Date\t:\t" + date + "\n");
        printLine.printf("Caissier\t:\t" + caissier + "\n");
        printLine.printf("Client\t:\t" + client + "\n");
        printLine.printf("Produits Vendus :\n");
        produitsVendus.forEach((p) -> {
            printLine.printf("\t" + p + "\n");
        });
        printLine.printf("Sous Total :\t" + sousTotal + "\n");
        printLine.printf("Total :\t" + total + "\n");
        printLine.printf("=========================\n");

        printLine.close();
        write.close();
    }

    @Override
    public String toString() {
        return "Vente{" + "id=" + id + ", nom=" + nom + ", client=" + client + ", caissier=" + caissier + ", produitsVendus=" + produitsVendus + ", sousTotal=" + sousTotal + ", total=" + total + '}';
    }

}
