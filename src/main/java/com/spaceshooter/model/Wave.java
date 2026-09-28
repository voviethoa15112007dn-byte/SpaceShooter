package com.spaceshooter.model;

public class Wave {

    private int currentWave;

    private final int totalWaves;

    private int enemiesPerWave;

    public Wave() {

        currentWave = 1;

        totalWaves = 6;

        enemiesPerWave = 5;
    }

    public int getCurrentWave() {

        return currentWave;
    }

    public int getTotalWaves() {

        return totalWaves;
    }

    public int getEnemiesPerWave() {

        return enemiesPerWave;
    }

    public void nextWave() {

        if (currentWave < totalWaves) {

            currentWave++;

            enemiesPerWave += 2;
        }
    }

    public boolean isFinalWave() {

        return currentWave >= totalWaves;
    }

    public void reset() {

        currentWave = 1;

        enemiesPerWave = 5;
    }
}