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
import model.Personnel;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class PersonnelDAO implements DAO<Personnel> {

    @Override
    public Personnel get(int id) {
        Personnel p = null;
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM personnel WHERE id = ?");
            pstmt.setInt(0, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                p = new Personnel(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adressePerso"),
                        rs.getString("adresseTravail"),
                        rs.getString("poste"),
                        this.get(rs.getInt("superieur")),
                        rs.getInt("numBadge")
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
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM personnel");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                personnels.add(new Personnel(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("adressePerso"),
                        rs.getString("adresseTravail"),
                        rs.getString("poste"),
                        this.get(rs.getInt("superieur")),
                        rs.getInt("numBadge")
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
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Personnel VALUES(NULL, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(0, t.getNom());
            pstmt.setString(1, t.getAdressePerso());
            pstmt.setString(2, t.getAdresseTravail());
            pstmt.setString(3, t.getPoste());
            pstmt.setInt(4, t.getSuperieur().getId());
            pstmt.setInt(5, t.getNumBadge());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Personnel t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Personnel SET nom=?, adressePerso=?, adresseTravail=?, poste=?, superieur=?, numBadge=? WHERE id=?");
            pstmt.setString(0, t.getNom());
            pstmt.setString(1, t.getAdressePerso());
            pstmt.setString(2, t.getAdresseTravail());
            pstmt.setString(3, t.getPoste());
            pstmt.setInt(4, t.getSuperieur().getId());
            pstmt.setInt(5, t.getNumBadge());
            pstmt.setInt(6, t.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Personnel t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Personnel WHERE id=?");
            pstmt.setInt(0, t.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
