package com.spaceshooter.repository;

import com.spaceshooter.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerRepository {

    public PlayerRepository() {

        createTable();
    }

    private void createTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS players (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    player_name TEXT NOT NULL UNIQUE
                )
                """;

        try (
                Connection connection =
                        DatabaseConnection
                                .getConnection();

                Statement statement =
                        connection.createStatement()
        ) {

            statement.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void savePlayer(
            String playerName) {

        String sql = """
                INSERT OR IGNORE INTO players
                (player_name)
                VALUES (?)
                """;

        try (
                Connection connection =
                        DatabaseConnection
                                .getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    playerName
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public boolean playerExists(
            String playerName) {

        String sql = """
                SELECT id
                FROM players
                WHERE player_name = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection
                                .getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    playerName
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                return resultSet.next();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
}