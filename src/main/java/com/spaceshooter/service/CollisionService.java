package com.spaceshooter.service;

import com.spaceshooter.model.Bullet;
import com.spaceshooter.model.Enemy;
import com.spaceshooter.model.EnemyBullet;
import com.spaceshooter.model.Player;

public class CollisionService {

    public boolean bulletHitsEnemy(
            Bullet bullet,
            Enemy enemy) {

        return bullet.intersects(enemy);
    }

    public boolean enemyBulletHitsPlayer(
            EnemyBullet bullet,
            Player player) {

        return bullet.intersects(player);
    }

    public boolean enemyHitsPlayer(
            Enemy enemy,
            Player player) {

        return enemy.intersects(player);
    }
}