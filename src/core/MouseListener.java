package core;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;                        // For scrolling
    private double xPos, yPos, lastY, lastX;                // For the dx and dy values
    private boolean mouseButtonPress[] = new boolean[3];    // For all three Mouse buttons
    private boolean isDragging;                             // Checks if Mouse is Dragging

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static MouseListener get() {
        if (MouseListener.instance == null) {
            instance = new MouseListener();
        }
        return instance;
    }

    /**
     * Callback function for the position of the mouse cursor on the screen
     * @param window    the long for the window, lol
     * @param xpos      xPos relative to lastX
     * @param ypos      yPos relative to lastY
     */

    public static void mousePosCallback(long window, double xpos, double ypos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
    }

    /**
     * Callback function for the Mouse Buttons. Ignores if other than three main Mouse Buttons
     * @param window    long for Window
     * @param button    the array for the mouse buttons
     * @param action
     * @param mods
     */

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < get().mouseButtonPress.length) {
                get().mouseButtonPress[button] = true;
            }
            get().mouseButtonPress[button] = true;
        } else if (action == GLFW_RELEASE) {
            if (button < get().mouseButtonPress.length) {
                get().mouseButtonPress[button] = true;
                get().mouseButtonPress[button] = false;
                get().isDragging = false;
            }
        }
    }

    /**
     * Callback function for the Mouse Scrolling
     * @param window        The long for the Window
     * @param xOffset       
     * @param yOffset
     */

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float)get().xPos;
    }

    public static float getY() {
        return (float)get().yPos;
    }

    public static float getDx() {
        return (float)(get().lastX - get().xPos);
    }

    public static float getDy() {
        return (float)(get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float)get().scrollX;
    }

    public static float getScrollY() {
        return (float)get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }
}
