package sandbox;

import javax.swing.*;
        import java.awt.event.*;

public class KeypressTest {
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.getContentPane().add(new JLabel("Make sure this window has focus, then hit some keys"));
        jf.getContentPane().add(new JLabel("CTRL-C at your console to quit"));
        jf.addKeyListener( new KeyListener() {
            public void keyPressed(KeyEvent e) {
                System.out.println("PRESSED " + e);
            }
            public void keyReleased(KeyEvent e) {
                System.out.println("RELEASED " + e);
            }
            public void keyTyped(KeyEvent e) {
                // You are probably not interested in typed events
                // System.out.println("TYPED " + e);
            }
        } );
        jf.setSize(400, 400);
        jf.setVisible(true);
    }
}
