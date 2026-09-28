package com.spaceshooter.repository;

import com.spaceshooter.database.DatabaseConnection;
import com.spaceshooter.model.Score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScoreRepository {

    public ScoreRepository() {

        createTable();
    }

    private void createTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS scores (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    player_name TEXT NOT NULL,
                    score INTEGER NOT NULL,
                    level INTEGER NOT NULL,
                    wave INTEGER NOT NULL,
                    date TEXT NOT NULL
                )
                """;

        try (
                Connection connection =
                        DatabaseConnection
                                .getConnection();

                Statement statement =
                        connection
                                .createStatement()
        ) {

            statement.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void save(Score score) {

        String sql = """
                INSERT INTO scores
                (
                    player_name,
                    score,
                    level,
                    wave,
                    date
                )
                VALUES (?, ?, ?, ?, ?)
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
                    score.getPlayerName()
            );

            statement.setInt(
                    2,
                    score.getScore()
            );

            statement.setInt(
                    3,
                    score.getLevel()
            );

            statement.setInt(
                    4,
                    score.getWave()
            );

            statement.setString(
                    5,
                    score.getDate().toString()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public List<Score> findTopScores() {

        List<Score> scores =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM scores
                ORDER BY score DESC
                LIMIT 10
                """;

        try (
                Connection connection =
                        DatabaseConnection
                                .getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Score score =
                        new Score();

                score.setId(
                        resultSet.getInt(
                                "id"
                        )
                );

                score.setPlayerName(
                        resultSet.getString(
                                "player_name"
                        )
                );

                score.setScore(
                        resultSet.getInt(
                                "score"
                        )
                );

                score.setLevel(
                        resultSet.getInt(
                                "level"
                        )
                );

                score.setWave(
                        resultSet.getInt(
                                "wave"
                        )
                );

                String dateString =
                        resultSet.getString(
                                "date"
                        );

                try {

                    score.setDate(
                            LocalDateTime
                                    .parse(
                                            dateString
                                    )
                    );

                } catch (Exception e) {

                    score.setDate(
                            LocalDateTime.now()
                    );
                }

                scores.add(score);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return scores;
    }
}