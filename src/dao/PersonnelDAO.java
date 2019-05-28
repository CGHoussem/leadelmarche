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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Personnel;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class PersonnelDAO implements DAO<Personnel> {

    @Override
    public Personnel get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Personnel getByNumBadge(int numBadge) {
        Personnel p = null;
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM personnel WHERE numBadge = ?");
            pstmt.setInt(1, numBadge);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                p = new Personnel(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adressePerso"),
                        rs.getString("adresseTravail"),
                        rs.getString("poste"),
                        getById(rs.getInt("superieur")),
                        rs.getInt("numBadge"),
                        rs.getString("mdp")
                );
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public Personnel getById(int id) {
        Personnel p = null;
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM personnel WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                p = new Personnel(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adressePerso"),
                        rs.getString("adresseTravail"),
                        rs.getString("poste"),
                        getById(rs.getInt("superieur")),
                        rs.getInt("numBadge"),
                        rs.getString("mdp")
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
    public List<Personnel> getAll() {
        List<Personnel> personnels = new ArrayList<>();
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM personnel WHERE active=true");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                personnels.add(new Personnel(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adressePerso"),
                        rs.getString("adresseTravail"),
                        rs.getString("poste"),
                        getById(rs.getInt("superieur")),
                        rs.getInt("numBadge"),
                        rs.getString("mdp")
                ));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return personnels;
    }

    public List<Personnel> getAllCaissier() {
        List<Personnel> personnels = new ArrayList<>();
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM personnel WHERE poste='caissier' AND active=true");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                personnels.add(new Personnel(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adressePerso"),
                        rs.getString("adresseTravail"),
                        rs.getString("poste"),
                        getById(rs.getInt("superieur")),
                        rs.getInt("numBadge"),
                        rs.getString("mdp")
                ));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return personnels;
    }

    @Override
    public void add(Personnel t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Personnel VALUES(NULL, ?, ?, ?, ?, ?, ?, true, ?, MD5(?))");
            pstmt.setString(1, t.getNom());
            pstmt.setString(2, t.getPrenom());
            pstmt.setString(3, t.getAdressePerso());
            pstmt.setString(4, t.getAdresseTravail());
            pstmt.setString(5, t.getPoste());
            if (t.getSuperieur() == null) {
                pstmt.setNull(6, Types.INTEGER);
            } else {
                pstmt.setInt(6, t.getSuperieur().getId());
            }
            pstmt.setInt(7, t.getNumBadge());
            pstmt.setString(8, t.getMdp());
            pstmt.execute();
            pstmt.close();

            JOptionPane.showMessageDialog(null, "Le personnel " + t.getNom() + " a été ajouté avec succés", "Ajout d'un personnel", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Personnel t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Personnel SET nom=?, prenom=?, adressePerso=?, adresseTravail=?, poste=?, superieur=?, numBadge=? WHERE id=?");
            pstmt.setString(1, t.getNom());
            pstmt.setString(2, t.getPrenom());
            pstmt.setString(3, t.getAdressePerso());
            pstmt.setString(4, t.getAdresseTravail());
            pstmt.setString(5, t.getPoste());
            if (t.getSuperieur() == null) {
                pstmt.setNull(6, Types.INTEGER);
            } else {
                pstmt.setInt(6, t.getSuperieur().getId());
            }
            pstmt.setInt(7, t.getNumBadge());
            pstmt.setInt(8, t.getId());
            pstmt.executeUpdate();
            pstmt.close();

            JOptionPane.showMessageDialog(null, "Le personnel " + t.getNom() + " a été mis à jour avec succés", "M.A.J du personnel", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Personnel t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Personnel SET active=false WHERE id=?");
            pstmt.setInt(1, t.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
