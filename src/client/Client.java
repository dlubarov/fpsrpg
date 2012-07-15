package client;

import java.io.IOException;
import java.net.*;

import net.NetConfig;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

public final class Client {
    private Client() {}

    private static final DatagramSocket sock;

    private static final int targetFPS = 80;
    private static long lastTime = System.nanoTime() - 1;
    private static double averageFPS;
    private static final double averageFPSDuration = 30;

    static {
        try {
            sock = new DatagramSocket(NetConfig.PORT_S2C, NetConfig.serverAddr);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendToServer(byte[] data) {
        try {
            sock.send(new DatagramPacket(data, data.length,
                    NetConfig.serverAddr, NetConfig.PORT_C2S));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logic() {
        while (Keyboard.next()) {
            int key = Keyboard.getEventKey();
            boolean down = Keyboard.getEventKeyState();
            switch (key) {
                case Keyboard.KEY_ESCAPE:
                    if (down)
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
        GLU.gluPerspective(60,
                Display.getWidth() / (float) Display.getHeight(),
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
            long time = System.nanoTime();
            double currentFPS = 1e9 / (time - lastTime);
            averageFPS = averageFPS * (averageFPSDuration - 1) / averageFPSDuration
                       + currentFPS / averageFPSDuration;
            lastTime = time;
            if (Math.random() < 0.1)
                Display.setTitle(String.format("RPG (%.0f FPS)", averageFPS));
            logic();
            render();
            Display.update();
            Display.sync(targetFPS);
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
