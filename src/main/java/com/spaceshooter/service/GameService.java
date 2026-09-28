package com.spaceshooter.service;

import com.spaceshooter.model.Boss;
import com.spaceshooter.model.Bullet;
import com.spaceshooter.model.Enemy;
import com.spaceshooter.model.EnemyBullet;
import com.spaceshooter.model.Player;
import com.spaceshooter.model.Wave;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameService {

    private final Player player;

    private final List<Enemy> enemies;

    private final List<Bullet> bullets;

    private final List<EnemyBullet> enemyBullets;

    private final Wave wave;

    private final CollisionService collisionService;

    private final Random random;

    private int score;

    private int lives;

    private int enemyShootTimer;

    public GameService() {

        player = new Player(
                610,
                620
        );

        enemies =
                new ArrayList<>();

        bullets =
                new ArrayList<>();

        enemyBullets =
                new ArrayList<>();

        wave =
                new Wave();

        collisionService =
                new CollisionService();

        random =
                new Random();

        score = 0;

        lives = 3;

        enemyShootTimer = 0;
    }

    public Player getPlayer() {

        return player;
    }

    public List<Enemy> getEnemies() {

        return enemies;
    }

    public List<Bullet> getBullets() {

        return bullets;
    }

    public List<EnemyBullet>
    getEnemyBullets() {

        return enemyBullets;
    }

    public Wave getWave() {

        return wave;
    }

    public int getScore() {

        return score;
    }

    public int getLives() {

        return lives;
    }

    public int getLevel() {

        return wave.getCurrentWave();
    }

    public void createWave() {

        enemies.clear();

        enemyBullets.clear();

        int numberOfEnemies =
                wave.getEnemiesPerWave();

        if (wave.isFinalWave()) {

            Boss boss =
                    new Boss(
                            600,
                            80
                    );

            enemies.add(boss);

            return;
        }

        for (int i = 0;
             i < numberOfEnemies;
             i++) {

            int column =
                    i % 8;

            int row =
                    i / 8;

            double x =
                    100 + column * 130;

            double y =
                    100 + row * 70;

            Enemy enemy =
                    new Enemy(
                            x,
                            y
                    );

            enemies.add(enemy);
        }
    }

    public Bullet playerShoot() {

        Bullet bullet =
                player.shoot();

        bullets.add(bullet);

        return bullet;
    }

    public void update() {

        player.update();

        updateEnemies();

        updateBullets();

        updateEnemyBullets();

        updateEnemyShooting();

        checkCollisions();

        removeOffScreenObjects();
    }

    private void updateEnemies() {

        for (Enemy enemy :
                enemies) {

            enemy.update();
        }
    }

    private void updateBullets() {

        for (Bullet bullet :
                bullets) {

            bullet.update();
        }
    }

    private void updateEnemyBullets() {

        for (EnemyBullet bullet :
                enemyBullets) {

            bullet.update();
        }
    }

    private void updateEnemyShooting() {

        enemyShootTimer++;

        if (enemyShootTimer < 70) {

            return;
        }

        enemyShootTimer = 0;

        if (enemies.isEmpty()) {

            return;
        }

        int index =
                random.nextInt(
                        enemies.size()
                );

        Enemy enemy =
                enemies.get(index);

        enemyBullets.add(
                enemy.shoot()
        );
    }

    private void checkCollisions() {

        checkPlayerBullets();

        checkEnemyBullets();

        checkEnemyPlayerCollision();
    }

    private void checkPlayerBullets() {

        Iterator<Bullet>
                bulletIterator =
                bullets.iterator();

        while (bulletIterator.hasNext()) {

            Bullet bullet =
                    bulletIterator.next();

            Iterator<Enemy>
                    enemyIterator =
                    enemies.iterator();

            while (enemyIterator.hasNext()) {

                Enemy enemy =
                        enemyIterator.next();

                if (collisionService
                        .bulletHitsEnemy(
                                bullet,
                                enemy
                        )) {

                    enemy.takeDamage();

                    bulletIterator.remove();

                    if (enemy.isDestroyed()) {

                        if (enemy instanceof Boss) {

                            score += 1000;

                        } else {

                            score += 100;
                        }

                        enemyIterator.remove();
                    }

                    break;
                }
            }
        }
    }

    private void checkEnemyBullets() {

        Iterator<EnemyBullet>
                iterator =
                enemyBullets.iterator();

        while (iterator.hasNext()) {

            EnemyBullet bullet =
                    iterator.next();

            if (collisionService
                    .enemyBulletHitsPlayer(
                            bullet,
                            player
                    )) {

                iterator.remove();

                loseLife();
            }
        }
    }

    private void checkEnemyPlayerCollision() {

        Iterator<Enemy>
                iterator =
                enemies.iterator();

        while (iterator.hasNext()) {

            Enemy enemy =
                    iterator.next();

            if (collisionService
                    .enemyHitsPlayer(
                            enemy,
                            player
                    )) {

                iterator.remove();

                loseLife();
            }

            if (enemy.getY() > 670) {

                iterator.remove();

                loseLife();
            }
        }
    }

    private void removeOffScreenObjects() {

        bullets.removeIf(
                Bullet::isOffScreen
        );

        enemyBullets.removeIf(
                bullet ->
                        bullet.isOffScreen(720)
        );
    }

    private void loseLife() {

        if (lives > 0) {

            lives--;
        }
    }

    public boolean checkWaveCompleted() {

        if (!enemies.isEmpty()) {

            return false;
        }

        if (wave.isFinalWave()) {

            return false;
        }

        wave.nextWave();

        createWave();

        return true;
    }

    public void reset() {

        score = 0;

        lives = 3;

        bullets.clear();

        enemyBullets.clear();

        enemies.clear();

        wave.reset();

        player.setX(610);

        player.setY(620);

        player.update();

        enemyShootTimer = 0;

        createWave();
    }

    public boolean isGameOver() {

        return lives <= 0;
    }

    public boolean isVictory() {

        return wave.isFinalWave()
                && enemies.isEmpty();
    }
}