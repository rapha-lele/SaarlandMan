package core;


import core.Window;

import static org.lwjgl.opengl.GL11C.*;

public class Game {
    private Window window;

    public void init() {
        // Initialize the window
        window = new Window(1920, 1080, "My Game", true);
        window.init();
    }

    public void run() {
        init();

        // Main game loop
        while (!window.shouldClose()) {
            update();
            render();

            window.swapBuffers();
            window.pollEvents();
        }

        cleanup();
    }

    private void update() {
        // Game logic updates
    }

    private void render() {
        // Clear screen
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private void cleanup() {
        window.cleanup();
    }

    public static void main(String[] args) {
        new Game().run();
    }
}
