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
            pstmt.setInt(0, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                TypeProduit type = TypeProduit.ALIMENT;
                switch (rs.getInt("type")) {
                    case 1:
                        type = TypeProduit.MEDECINE;
                        break;
                    case 2:
                        type = TypeProduit.APPAREIL_ELECTRONIQUE;
                        break;
                    case 3:
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
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Produit");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TypeProduit type = TypeProduit.ALIMENT;
                switch (rs.getInt("type")) {
                    case 1:
                        type = TypeProduit.MEDECINE;
                        break;
                    case 2:
                        type = TypeProduit.APPAREIL_ELECTRONIQUE;
                        break;
                    case 3:
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
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Produit VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?)");
            int type = 0;
            switch (t.getType()) {
                case MEDECINE:
                    type = 1;
                    break;
                case APPAREIL_ELECTRONIQUE:
                    type = 2;
                    break;
                case AUTRE:
                    type = 3;
                    break;
            }
            pstmt.setString(0, t.getNom());
            pstmt.setString(1, t.getDescription());
            pstmt.setInt(2, type);
            pstmt.setFloat(3, t.getPrix());
            pstmt.setString(4, t.getPays());
            pstmt.setFloat(5, t.getTva());
            pstmt.setInt(6, t.getQteStock());
            pstmt.setString(7, t.getNomFournisseur());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Produit t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Produit SET nom=?, description=?, type=?, prix=?, pays=?, tva=?, qteStock=?, nomFournisseur=? WHERE id=?");
            int type = 0;
            switch (t.getType()) {
                case MEDECINE:
                    type = 1;
                    break;
                case APPAREIL_ELECTRONIQUE:
                    type = 2;
                    break;
                case AUTRE:
                    type = 3;
                    break;
            }
            pstmt.setString(0, t.getNom());
            pstmt.setString(1, t.getDescription());
            pstmt.setInt(2, type);
            pstmt.setFloat(3, t.getPrix());
            pstmt.setString(4, t.getPays());
            pstmt.setFloat(5, t.getTva());
            pstmt.setInt(6, t.getQteStock());
            pstmt.setString(7, t.getNomFournisseur());
            pstmt.setInt(8, t.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Produit t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Produit WHERE id=?");
            pstmt.setInt(0, t.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
