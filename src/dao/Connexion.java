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
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Houssem Ben Mabrouk
 */
public class Connexion {

    private final static String URL = "jdbc:mysql://localhost/leadelmarche";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";

    private static Connection instance = null;

    synchronized public static Connection getInstance() {
        if (instance == null) {
            try {
                instance = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de donnée!", "Erreur", JOptionPane.ERROR_MESSAGE);

                System.out.println(ex.getMessage());
                System.exit(1);
            }
        }
        return instance;
    }

    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Connection is out!");
    }
}
