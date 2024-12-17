package graphics;

import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static java.lang.invoke.MethodHandles.loop;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window2 {
    private long window;
    private int width;
    private int height;
    private String title;
    private boolean fullscreen;

    /**
     * Constructor for class Window. Creates window and checks if it has to be set to fullscreen
     *
     * @param width      Width of window in Pixels
     * @param height     Height of Window in Pixels
     * @param title      Title of Window (SaarlandMan)
     * @param fullscreen Boolean to check if fullscreen is true
     */

    public Window2(int width, int height, String title, boolean fullscreen) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.fullscreen = fullscreen;
    }

    // Imported from https://www.lwjgl.org/guide

    private void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // Setup an error callback. The default implementation will print the error message in System.err
        GLFWErrorCallback.createPrint(System.err).set();

        // If this doesn't run, we are fucked!
        if (!glfwInit()) {
            throw new IllegalStateException("GLFW is not running correctly...");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Get monitor and video mode if fullscreen
        long monitor = fullscreen ? glfwGetPrimaryMonitor() : NULL;
        GLFWVidMode videoMode = fullscreen ? glfwGetVideoMode(monitor) : null;

        // Create window
        window = glfwCreateWindow(
                fullscreen ? videoMode.width() : width,
                fullscreen ? videoMode.height() : height,
                title,
                monitor,
                NULL
        );

        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Center window in windowed mode
        if (!fullscreen) {
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    window,
                    (vidMode.width() - width) / 2,
                    (vidMode.height() - height) / 2
            );
        }

        // Make OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable v-sync
        glfwSwapInterval(1);

        // Show the window
        glfwShowWindow(window);

        // Initialize OpenGL capabilities
        GL.createCapabilities();
    }

    // Poll window events
    public void pollEvents() {
        glfwPollEvents();
    }

    // Check if the window should close
    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    // Swap buffers
    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    // Cleanup resources
    public void cleanup() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    // Getters for width, height, and window handle
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindowHandle() {
        return window;
    }

    private boolean isFullscreen = true;

    private void toggleFullscreen() {
        // Destroy the current window
        glfwDestroyWindow(window);

        // Get the monitor and video mode
        long monitor = isFullscreen ? NULL : glfwGetPrimaryMonitor();
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        // Create the new window
        window = glfwCreateWindow(
                isFullscreen ? 800 : videoMode.width(),
                isFullscreen ? 600 : videoMode.height(),
                title,
                monitor,
                NULL
        );

        if (window == NULL) {
            throw new RuntimeException("Failed to recreate the GLFW window");
        }

        // Set up OpenGL context
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        // Update fullscreen flag
        isFullscreen = !isFullscreen;

        // Show the window
        glfwShowWindow(window);
    }
}