package ar.com.fran150.game;

import ar.com.fran150.gfx.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 3;
    public static final String NAME = "Game";

    private JFrame frame;

    private SpriteSheet spriteSheet;
    private Handler handler;

    public boolean running = false;

    public Game() {
        Dimension dimension = new Dimension(WIDTH * SCALE, WIDTH * SCALE);

        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);

        frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        spriteSheet = new SpriteSheet("/SpriteSheet.png");

        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
    }

    public synchronized void start() {
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
    }


    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60;
        int frames = 0;
        int ticks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;

                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;

                System.out.println(frames + " frames," + ticks + " ticks");

                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick() {
        handler.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
        }

        Graphics g = bs.getDrawGraphics();

        handler.render(g);

        g.dispose();

        bs.show();
    }

    public static void main(String[] args) {
        new Game().start();
    }

}
