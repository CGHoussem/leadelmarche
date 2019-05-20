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
import model.Vente;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class VenteDAO implements DAO<Vente> {

    private List<Produit> getProduits(int idVente) {
        List<Produit> produits = new ArrayList<>();
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM lignevente WHERE produit=?");
            pstmt.setInt(1, idVente);
            ResultSet rs = pstmt.executeQuery();
            ProduitDAO pdao = new ProduitDAO();
            while (rs.next()) {
                produits.add(pdao.get(rs.getInt("produit")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return produits;
    }

    private void addLigneVente(int idVente, int idProduit) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO lignevente VALUES(NULL, ?, ?)");
            pstmt.setInt(1, idVente);
            pstmt.setInt(2, idProduit);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Vente get(int id) {
        Vente v = null;
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Vente WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ClientDAO cdao = new ClientDAO();
                PersonnelDAO pdao = new PersonnelDAO();
                v = new Vente(
                        id,
                        rs.getString("nom"),
                        cdao.get(rs.getInt("client")),
                        pdao.get(rs.getInt("caissier")),
                        getProduits(id),
                        rs.getFloat("sous_total"),
                        rs.getFloat("tva"),
                        rs.getFloat("total")
                );
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return v;
    }

    @Override
    public List<Vente> getAll() {
        List<Vente> ventes = new ArrayList<>();
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Vente WHERE active=true");
            ResultSet rs = pstmt.executeQuery();
            ClientDAO cdao = new ClientDAO();
            PersonnelDAO pdao = new PersonnelDAO();
            while (rs.next()) {
                ventes.add(new Vente(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        cdao.get(rs.getInt("client")),
                        pdao.get(rs.getInt("caissier")),
                        getProduits(rs.getInt("id")),
                        rs.getFloat("sous_total"),
                        rs.getFloat("tva"),
                        rs.getFloat("total")
                ));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ventes;
    }

    @Override
    public void add(Vente t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Vente VALUES(NULL, ?, ?, ?, ?, ?, ?, true)");
            pstmt.setString(1, t.getNom());
            pstmt.setInt(2, t.getCaissier().getId());
            pstmt.setInt(3, t.getClient().getId());
            pstmt.setFloat(4, t.getSousTotal());
            pstmt.setFloat(5, t.getTva());
            pstmt.setFloat(6, t.getTotal());
            pstmt.execute();
            pstmt.close();

            t.getProduitsVendus().forEach((p) -> {
                addLigneVente(t.getId(), p.getId());
            });

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Vente t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Vente SET nom=?, caissier=?, client=?, sous_total=?, tva=?, total=? WHERE id=?");
            pstmt.setString(1, t.getNom());
            pstmt.setInt(2, t.getCaissier().getId());
            pstmt.setInt(3, t.getClient().getId());
            pstmt.setFloat(4, t.getSousTotal());
            pstmt.setFloat(5, t.getTva());
            pstmt.setFloat(6, t.getTotal());
            pstmt.setInt(7, t.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Vente t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Vente SET active=false WHERE id=?");
            pstmt.setInt(1, t.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
