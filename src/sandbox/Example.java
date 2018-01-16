package sandbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer;

class BouncingBall {
    // Overview: A sandbox.BouncingBall is a mutable data type. It simulates a
    // rubber ball bouncing inside a two dimensional box. It also
    // provides methods that are useful for creating animations of the
    // ball as it moves.

    private int x = (int) ((Math.random() * 100.0) + 100.0);

    private int y = (int) ((Math.random() * 100.0) + 100.0);

    private int vx = (int) ((Math.random() * 10.0) + 10.0);

    private int vy = (int) ((Math.random() * 10.0) + 10.0);

    private int radius = 6;

    private Color color = new Color(255, 0, 0);

    public void move() {
        // modifies: this
        // effects: Move the ball according to its velocity. Reflections off
        // walls cause the ball to change direction.

        x += vx;
        if (x <= radius) {
            x = radius;
            vx = -vx;
        }
        if (x >= 500 - radius) {
            x = 500 - radius;
            vx = -vx;
        }

        y += vy;
        if (y <= radius) {
            y = radius;
            vy = -vy;
        }
        if (y >= 500 - radius) {
            y = 500 - radius;
            vy = -vy;
        }
    }

    public void randomBump() {
        // modifies: this
        // effects: Changes the velocity of the ball by a random amount
        vx += (int) ((Math.random() * 10.0) - 5.0);
        vx = -vx;
        vy += (int) ((Math.random() * 10.0) - 5.0);
        vy = -vy;
    }

    public void paint(Graphics g) {
        // modifies: the Graphics object <g>.
        // effects: paints a circle on <g> reflecting the current position
        // of the ball.

        // the "clip rectangle" is the area of the screen that needs to be
        // modified
        Rectangle clipRect = g.getClipBounds();

        // For this tiny program, testing whether we need to redraw is
        // kind of silly. But when there are lots of objects all over the
        // screen this is a very important performance optimization
        if (clipRect.intersects(this.boundingBox())) {
            g.setColor(color);
            g
                    .fillOval(x - radius, y - radius, radius + radius, radius
                            + radius);
        }
    }

    public Rectangle boundingBox() {
        // effect: Returns the smallest rectangle that completely covers the
        //         current position of the ball.

        // a Rectangle is the x,y for the upper left corner and then the
        // width and height
        return new Rectangle(x - radius, y - radius, radius + radius + 1,
                radius + radius + 1);
    }
}

// Note the very indirect way control flow works during an animation:
//
// (1) We set up an eventListener with a reference to the animationWindow.
// (2) We set up a timer with a reference to the eventListener.
// (3) We call timer.start().
// (4) Every 20 milliseconds the timer calls eventListener.actionPerformed()
// (5) eventListener.actionPerformed() modifies the logical
//     datastructure (e.g. changes the coordinates of the ball).
// (6) eventListener.actionPerformed() calls myWindow.repaint.
// (7) Swing schedules, at some point in the future, a call to
//      myWindow.paint()
// (8) myWindow.paint() tells various objects to paint
//     themselves on the provided Graphics context.
//
// This may seem very complicated, but it makes the coordination of
// all the various different kinds of user input much easier. For
// example here is how control flow works when the user presses the
// mouse button:
//
// (1) We set up an eventListener (actually we just use the same
//     eventListener that is being used by the timer.)
// (2) We register the eventListener with the window using the
//     addMouseListener() method.
// (3) Every time the mouse button is pressed inside the window the
//     window calls eventListener.mouseClicked().
// (4) eventListener.mouseClicked() modifies the logical
//     datastructures. (In this example it calls ball.randomBump(), but
//     in other programs it might do something else, including request a
//     repaint operation).
//

class AnimationWindow extends JPanel {
    // overview: an sandbox.AnimationWindow is an area on the screen in which a
    // bouncing ball animation occurs. AnimationWindows have two modes:
    // on and off. During the on mode the ball moves, during the off
    // mode the ball doesn't move.

    private AnimationEventListener eventListener;

    private BouncingBall ball;

    private Timer timer;

    private boolean mode;

    public AnimationWindow() {
        // effects: initializes this to be in the off mode.

        super(); // do the standard JPanel setup stuff
        ball = new BouncingBall();

        // this only initializes the timer, we actually start and stop the
        // timer in the setMode() method
        eventListener = new AnimationEventListener();
        // The first parameter is how often (in milliseconds) the timer
        // should call us back. 50 milliseconds = 20 frames/second
        timer = new Timer(50, eventListener);

        mode = false;
    }

    // This is just here so that we can accept the keyboard focus
    public boolean isFocusTraversable() {
        return true;
    }

    public void paint(Graphics g) {
        // modifies: <g>
        // effects: Repaints the Graphics area <g>. Swing will then send the
        //          newly painted g to the screen.

        // first repaint the proper background color (controlled by
        // the windowing system)
        super.paint(g);

        ball.paint(g);
    }

    public void setMode(boolean m) {
        // modifies: this
        // effects: changes the mode to <m>.

        if (mode == true) {
            // we're about to change mode: turn off all the old listeners
            removeMouseListener(eventListener);
            removeMouseMotionListener(eventListener);
            removeKeyListener(eventListener);
        }

        mode = m;

        if (mode == true) {
            // the mode is true: turn on the listeners
            addMouseListener(eventListener);
            addMouseMotionListener(eventListener);
            addKeyListener(eventListener);
            requestFocus(); // make sure keyboard is directed to us
            timer.start();
        } else {
            timer.stop();
        }
    }

    class AnimationEventListener extends MouseAdapter implements
            MouseMotionListener, KeyListener, ActionListener {
        // overview: AnimationEventListener is an inner class that
        // responds to all sorts of external events, and provides the
        // required semantic operations for our particular program. It
        // owns, and sends semantic actions to the ball and window of the
        // outer class

        // MouseAdapter gives us empty methods for the MouseListener
        // interface: mouseClicked, mouseEntered, mouseExited, mousePressed,
        // and mouseReleased.

        // for this example we only need to override mouseClicked
        public void mouseClicked(MouseEvent e) {
            // modifes: the ball that this listener owns
            // effects: causes the ball to be bumped in a random direction
            ball.randomBump();
        }

        // Here's the MouseMotionListener interface
        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }

        // Here's the KeyListener interface
        public void keyPressed(KeyEvent e) {
            // modifes: the ball that this listener owns
            // effects: causes the ball to be bumped in a random direction but
            //          only if one of the keys A-J is pressed.
            int keynum = e.getKeyCode();

            if ((keynum >= 65) && (keynum <= 74)) {
                System.out.println("keypress " + e.getKeyCode());
                ball.randomBump();
            }
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }

        // this is the callback for the timer
        public void actionPerformed(ActionEvent e) {
            // modifes: both the ball and the window that this listener owns
            // effects: causes the ball to move and the window to be updated
            //          to show the new position of the ball.

            Rectangle oldPos = ball.boundingBox();

            ball.move(); // make changes to the logical animation state

            Rectangle repaintArea = oldPos.union(ball.boundingBox());

            // Have Swing tell the sandbox.AnimationWindow to run its paint()
            // method. One could also call repaint(), but this would
            // repaint the entire window as opposed to only the portion that
            // has changed.
            repaint(repaintArea.x, repaintArea.y, repaintArea.width,
                    repaintArea.height);
        }
    }
}

class ApplicationWindow extends JFrame {
    // overview: An sandbox.ApplicationWindow is a top level program window that
    // contains a toolbar and an animation window.

    protected AnimationWindow animationWindow;

    public ApplicationWindow() {
        // effects: Initializes the application window so that it contains
        //          a toolbar and an animation window.

        // Title bar
        super("Swing Demonstration Program");

        // respond to the window system asking us to quit
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //Create the toolbar.
        JToolBar toolBar = new JToolBar();
        addButtons(toolBar);

        //Create the animation area used for output.
        animationWindow = new AnimationWindow();
        // Put it in a scrollPane, (this makes a border)
        JScrollPane scrollPane = new JScrollPane(animationWindow);

        //Lay out the content pane.
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(510, 530));
        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        setContentPane(contentPane);
    }

    protected void addButtons(JToolBar toolBar) {
        // modifies: toolBar
        // effects: adds Run, Stop and Quit buttons to toolBar

        JButton button = null;

        button = new JButton("Run");
        button.setToolTipText("Start the animation");
        // when this button is pushed it calls animationWindow.setMode(true)
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                animationWindow.setMode(true);
            }
        });
        toolBar.add(button);

        button = new JButton("Stop");
        button.setToolTipText("Stop the animation");
        // when this button is pushed it calls animationWindow.setMode(false)
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                animationWindow.setMode(false);
            }
        });
        toolBar.add(button);

        button = new JButton("Quit");
        button.setToolTipText("Quit the program");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        toolBar.add(button);
    }
}

public class Example {
    public static void main(String[] args) {
        ApplicationWindow frame = new ApplicationWindow();

        // the following code realizes the top level application window
        frame.pack();
        frame.setVisible(true);
    }
}