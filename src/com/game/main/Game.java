package com.game.main;

public class Game implements Runnable {
    // Game constants
    private static final int MILLIS_PER_SECOND = Math.toIntExact(1000L);
    private static final int NANOS_PER_SECOND = Math.toIntExact(1000000000L);
    private static final double NUM_TICKS = 60.0; // The number of game updates per second

    // Game variables
    private boolean running;

    // Game components
    private Thread gameThread;

    public Game(){
        initialise();
    }


    private void initialise(){
        start();
    }

    private synchronized void start(){
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    private synchronized void stop() {
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = NUM_TICKS;
        double ns = NANOS_PER_SECOND / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            if (running) {
                render();
                frames++;
            }

            if (System.currentTimeMillis() - timer > MILLIS_PER_SECOND) {
                timer += MILLIS_PER_SECOND;
                System.out.println("FPS: " + frames + " TPS: " +  updates);
            }
        }

        stop();
    }

    // updating game state
    private void tick(){

    }

    // generating UI
    private void render(){

    }
}
