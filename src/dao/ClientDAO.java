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
import model.Client;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class ClientDAO implements DAO<Client> {

    @Override
    public Client get(int id) {
        Client c = null;
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Client WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                c = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("numCarteFidelite"),
                        rs.getString("mail"),
                        rs.getInt("codePostal")
                );
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Client WHERE active=true");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("numCarteFidelite"),
                        rs.getString("mail"),
                        rs.getInt("codePostal")
                ));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clients;
    }

    @Override
    public void add(Client t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Client VALUES(NULL, ?, ?, ?, ?, ?, true)");
            pstmt.setString(1, t.getNom());
            pstmt.setString(2, t.getPrenom());
            pstmt.setInt(3, t.getNumCarteFidelite());
            pstmt.setString(4, t.getMail());
            pstmt.setInt(5, t.getCodePostal());
            pstmt.execute();
            pstmt.close();

            JOptionPane.showMessageDialog(null, "Le client " + t.getNom() + " a été ajouter avec succés", "Ajout d'un client", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Client t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Client SET nom=?, prenom=?, numCarteFidelite=?, mail=?, codePostal=? WHERE id=?");
            pstmt.setString(1, t.getNom());
            pstmt.setString(2, t.getPrenom());
            pstmt.setInt(3, t.getNumCarteFidelite());
            pstmt.setString(4, t.getMail());
            pstmt.setInt(5, t.getCodePostal());
            pstmt.setInt(6, t.getId());
            pstmt.executeUpdate();
            pstmt.close();

            JOptionPane.showMessageDialog(null, "Le client " + t.getNom() + " a été mis à jour avec succés", "M.A.J d'un client", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Client t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Client SET active=false WHERE id=?");
            pstmt.setInt(1, t.getId());
            pstmt.executeUpdate();
            pstmt.close();

            JOptionPane.showMessageDialog(null, "Le client " + t.getNom() + " a été supprimer avec succés", "Suppression d'un client", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
