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
            pstmt.setInt(0, id);
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
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Client");
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
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Client VALUES(NULL, ?, ?, ?, ?, ?)");
            pstmt.setString(0, t.getNom());
            pstmt.setString(1, t.getPrenom());
            pstmt.setInt(2, t.getNumCardeFidelite());
            pstmt.setString(3, t.getMail());
            pstmt.setInt(4, t.getCodePostal());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Client t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Client SET nom=?, prenom=?, numCarteFidelite=?, mail=?, codePostal=? WHERE id=?");
            pstmt.setString(0, t.getNom());
            pstmt.setString(1, t.getPrenom());
            pstmt.setInt(2, t.getNumCardeFidelite());
            pstmt.setString(3, t.getMail());
            pstmt.setInt(4, t.getCodePostal());
            pstmt.setInt(5, t.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Client t) {
        try {
            Connection con = Connexion.getInstance();
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Client WHERE id=?");
            pstmt.setInt(0, t.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}