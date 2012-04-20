package client;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

public final class Client {
    private Client() {}

    private static void logic() {
        while (Keyboard.next()) {
            int key = Keyboard.getEventKey();
            //boolean down = Keyboard.getEventKeyState();
            switch (key) {
                case Keyboard.KEY_ESCAPE:
                    System.exit(0);
                    break;
            }
        }
    }

    private static void glSetup() {
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.75f, 1, 1, 1);
    }

    private static void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(60, Display.getWidth() / (float) Display.getHeight(),
                1f, 500f);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        GLU.gluLookAt(
                5, 5, 5,
                0, 0, 0,
                0, 1, 0);

        glBegin(GL_TRIANGLES);
        glVertex3i(0, 0, 0);
        glVertex3i(1, 0, 0);
        glVertex3i(0, 0, 1);
        glEnd();
    }

    private static void mainLoop() {
        while (!Display.isCloseRequested()) {
            logic();
            render();
            Display.update();
        }
    }

    public static void main(String[] args) throws LWJGLException {
        Display.setTitle("FPS RPG");
        Display.setResizable(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.create();
        Keyboard.create();
        Mouse.setGrabbed(true);
        glSetup();
        mainLoop();
    }
}
