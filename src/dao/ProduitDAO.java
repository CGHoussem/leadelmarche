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
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Produit;
import model.TypeProduit;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class ProduitDAO implements DAO<Produit> {

    @Override
    public Produit get(int id) {
        Produit p = null;
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Produit WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                TypeProduit type = TypeProduit.ALIMENT;
                switch (rs.getString("type")) {
                    case "MEDECINE":
                        type = TypeProduit.MEDECINE;
                        break;
                    case "APPAREIL_ELECTRONIQUE":
                        type = TypeProduit.APPAREIL_ELECTRONIQUE;
                        break;
                    case "AUTRE":
                        type = TypeProduit.AUTRE;
                        break;
                }
                p = new Produit(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        type,
                        rs.getFloat("prix"),
                        rs.getString("pays"),
                        rs.getFloat("tva"),
                        rs.getInt("qteStock"),
                        rs.getString("nomFournisseur")
                );
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    @Override
    public List<Produit> getAll() {
        List<Produit> prodtuis = new ArrayList<>();
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Produit WHERE active=true");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TypeProduit type = TypeProduit.ALIMENT;
                switch (rs.getString("type")) {
                    case "MEDECINE":
                        type = TypeProduit.MEDECINE;
                        break;
                    case "APPAREIL_ELECTRONIQUE":
                        type = TypeProduit.APPAREIL_ELECTRONIQUE;
                        break;
                    case "AUTRE":
                        type = TypeProduit.AUTRE;
                        break;
                }
                prodtuis.add(new Produit(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        type,
                        rs.getFloat("prix"),
                        rs.getString("pays"),
                        rs.getFloat("tva"),
                        rs.getInt("qteStock"),
                        rs.getString("nomFournisseur")
                ));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prodtuis;
    }

    @Override
    public void add(Produit t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Produit VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, true)");
            pstmt.setString(1, t.getNom());
            pstmt.setString(2, t.getDescription());
            pstmt.setString(3, t.getType().name());
            pstmt.setFloat(4, t.getPrixHorsTax());
            pstmt.setString(5, t.getPays());
            pstmt.setFloat(6, t.getTva());
            pstmt.setInt(7, t.getQteStock());
            pstmt.setString(8, t.getNomFournisseur());
            pstmt.execute();
            pstmt.close();

            JOptionPane.showMessageDialog(null, "Le produit " + t.getNom() + " a été ajouté avec succés", "Ajout d'un produit", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Produit t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Produit SET nom=?, description=?, type=?, prix=?, pays=?, tva=?, qteStock=?, nomFournisseur=? WHERE id=?");
            pstmt.setString(1, t.getNom());
            pstmt.setString(2, t.getDescription());
            pstmt.setString(3, t.getType().name());
            pstmt.setFloat(4, t.getPrixHorsTax());
            pstmt.setString(5, t.getPays());
            pstmt.setFloat(6, t.getTva());
            pstmt.setInt(7, t.getQteStock());
            pstmt.setString(8, t.getNomFournisseur());
            pstmt.setInt(9, t.getId());
            pstmt.executeUpdate();
            pstmt.close();

            JOptionPane.showMessageDialog(null, "Le produit " + t.getNom() + " a été mis à jour avec succés", "M.A.J du produit", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Produit t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Produit SET active=false WHERE id=?");
            pstmt.setInt(1, t.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
