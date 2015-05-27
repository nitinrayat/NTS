/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 *
 * @author nitin
 */

public class NTSTB extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    // Height and Width of our game
    static final int WIDTH = 1200;
    static final int HEIGHT = 800;

    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;

    //keys
    boolean space = false;
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    boolean g = false;
    boolean h = false;
    //boolean values
    boolean grid = false;
    boolean menu = true;
    boolean gameon = false;

    ///if mouse is hovering over a certain place 
    int mouseX = 0;
    int mouseY = 0;
    
    //for if mouse is clicked 
    int mx = 0;
    int my = 0;

    //drawing
    Rectangle fs = new Rectangle(450, 250, 300, 300); //drawing the front side of cube
    Rectangle rs = new Rectangle(500, 200, 300, 300); //drawing rear square
    Rectangle start = new Rectangle( fs.x + 100 , fs.y + 100, 100, 100); //drawing rear square
    //drawing squares where the mouse is 
    Rectangle mouses = new Rectangle(mx,my,1,1);

    //speed of moving cube
    int cspeed = 5;

    //declaring variable to grid
    int gridb = 1;

    //declaring width of how far to extend the cube for 3d effect
    int threed = 100;
    
    //colours
    Color grassbk = new Color(0,150,0);

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 
        
        if (menu) {
            
        
        //drawing grid
        if (grid) {
            for (int i = 0; i < 800; i += 50) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, i, 1200, i);
            }
            for (int a = 0; a < 1200; a += 50) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(a, 0, a, 800);
            }
        }

        //drawing frontside of cube
        g.setColor(Color.black);
        g.drawRect(fs.x, fs.y, fs.width, fs.height);

        //drawing rear side of cube
        g.setColor(Color.black);
        g.drawRect(rs.x, rs.y, rs.width, rs.height);

        //***drawing line to cover up line that should not show
        //bottom line of rear cube from left side horizontal
        if (rs.x < fs.x && (rs.y + rs.height) < (fs.y + fs.height)) {
            g.setColor(Color.white);
            g.drawLine(rs.x, rs.y + rs.height, fs.x, rs.y + rs.height);
        }

        //top right line of rear cube verticaly
        if (rs.y < fs.y && rs.x + rs.width < fs.x + fs.width) {
            g.setColor(Color.white);
            g.drawLine(rs.x + rs.width, rs.y, rs.x + rs.width, rs.y + rs.height);
        }

        //bottom line of rear cube from righ side horizontal
        if (rs.x > fs.x && rs.y + rs.height < fs.y + fs.height) {
            g.setColor(Color.white);
            g.drawLine(rs.x + rs.width, rs.y + rs.height, fs.x + fs.width, rs.y + rs.height);
        }

        //top left line of rear cube verticaly
        if (rs.y < fs.y && rs.x > fs.x) {
            g.setColor(Color.white);
            g.drawLine(rs.x, rs.y, rs.x, rs.y + rs.height);
        }

        //top line of rear square hrizontal left side
        if (rs.x < fs.x && rs.y > fs.y) {
            g.setColor(Color.white);
            g.drawLine(rs.x, rs.y, rs.x + rs.width, rs.y);
        }

        //top line of rear square horizontal right side
        if (rs.x > fs.x && rs.y > fs.y) {
            g.setColor(Color.white);
            g.drawLine(rs.x + rs.width, rs.y, rs.x, rs.y);
        }

        //bottom line of rear square vertical left
        if (rs.x > fs.x && rs.y > fs.y) {
            g.setColor(Color.white);
            g.drawLine(rs.x, rs.y + rs.height, rs.x, rs.y);

        }

        //bottom line of rear suqare vetical right
        if (rs.x + rs.width < fs.x + fs.width && rs.y > fs.y) {
            g.setColor(Color.white);
            g.drawLine(rs.x + rs.width, rs.y + rs.height, rs.x + rs.width, rs.y);
        }

        //***to stop cube from extendin forever***
        
        //left
        if (fs.x - rs.x >= threed) {
            rs.x = fs.x - threed;
        }
        //top
        if (fs.y - rs.y >= 100) {
            rs.y = fs.y - threed;
        }
        //right
        if ((fs.x + fs.width) - (rs.x + rs.width) <= -threed) {
            rs.x = (fs.x + fs.width) + threed - rs.width;
        }
        //bottom
        if ((fs.y + fs.height) - (rs.y + rs.height) <= -threed) {
            rs.y = (fs.y + fs.height) + threed - rs.height;
        }

        //*** colouring the cube ***
        
        //top side of cube
        if (rs.y < fs.y) {
            for (int i = 0; i < rs.width; i++) {
                g.setColor(Color.gray);
                g.drawLine(rs.x + i, rs.y, fs.x + i, fs.y);
            }
        }

        //right side of cube
        if (rs.x - fs.x <= threed) {
            for (int i = 0; i < rs.width; i++) {
                g.setColor(Color.gray);
                g.drawLine(fs.x + fs.width, fs.y + i, rs.x + rs.width, rs.y + i);
            }
        }

        //left side of cube
        if (fs.x - rs.x <= threed) {
            for (int i = 0; i < rs.width; i++) {
                g.setColor(Color.gray);
                g.drawLine(rs.x, rs.y + i, fs.x, fs.y + i);
            }
        }

        //bottom side of cube
        if (rs.y > fs.y) {
            for (int i = 0; i < rs.width; i++) {
                g.setColor(Color.gray);
                g.drawLine(fs.x + i, fs.y + fs.height, rs.x + i, rs.y + rs.height);
                
            }
        }

        // ***connect edges of square***
        
        //top left
        g.setColor(Color.black);
        g.drawLine(fs.x, fs.y, rs.x, rs.y);
        //top right
        g.setColor(Color.black);
        g.drawLine(fs.x + fs.width, fs.y, rs.x + rs.width, rs.y);
        //bottom left
        g.setColor(Color.black);
        g.drawLine(fs.x, fs.y + rs.height, rs.x, rs.y + rs.height);
        //bottom right
        g.setColor(Color.black);
        g.drawLine(fs.x + fs.width, fs.y + fs.height, rs.x + rs.width, rs.y + rs.height);

        //***fillling inside of front cube and drawng it again***
        
        g.setColor(Color.lightGray);
        g.fillRect(fs.x, fs.y, fs.width, fs.height);
        g.setColor(Color.black);
        g.drawRect(fs.x, fs.y, fs.width, fs.height);
        
        
        //***drawing menu***
        
        //start button 
        g.setColor(Color.GRAY);
        g.fillRect(start.x, start.y, start.width, start.height);
        
        //check to see if start is pressed
        
        if (mx >= start.x && mx < start.x + start.width && my > start.y && my <= start.y + start.height) {
            g.clearRect(0, 0, WIDTH, HEIGHT);
            System.out.println("Reset");
            menu = false;
            gameon = true;
            }     
//        if (mouses.intersects(start)) {
//            g.clearRect(0, 0, WIDTH, HEIGHT);
//        }        
        
        }
        

        //****** game is drawn here ******
        if (gameon) {
            

         
                    
            
            
            //draw game background
            //left grass backkground
            g.setColor(grassbk);
            g.fillRect(0, 0, 200, HEIGHT);
            
            //right grass background
            g.setColor(grassbk);
            g.fillRect(WIDTH - 200, 0, 200, HEIGHT);
            
            //left side lines
            g.setColor(Color.black);
            g.fillRect(200, 0, 50, HEIGHT);
            
            //right side line
            g.setColor(Color.black);
            g.fillRect(WIDTH - 250, 0, 50, HEIGHT);       
            
            //drawing the road to race on
            g.setColor(Color.darkGray);
            g.fillRect(250, 0, 700, HEIGHT);

            //draw escape/back button 
            g.setColor(Color.DARK_GRAY);
            g.fillRect(10, 10, 50, 25);
            g.setColor(Color.black);
            g.drawRect(10, 10, 50, 25);
            
            if (space) {
                
            }
           
        }
        
            
        
        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            
            
            
            //move the cube depending on what key is pressed
            if (right) {
                fs.x += cspeed;
                rs.x -= cspeed;
                start.x += cspeed;
            }
            if (left) {
                fs.x -= cspeed;
                rs.x += cspeed;
                start.x -=  cspeed;
            }
            if (up) {
                fs.y -= cspeed;
                rs.y += cspeed;
                start.y -= cspeed;
            }
            if (down) {
                fs.y += cspeed;
                rs.y -= cspeed;
                start.y += cspeed;
            }
            
            //enablig or disbling grid
            if (g) {
                grid = true;
            }
            if (h) {
                grid = false;
            }
            

            

            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("Need To Speed: The Begining");

        // creates an instance of my game
        NTSTB game = new NTSTB();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        game.addMouseListener(game);
        game.addMouseMotionListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            up = true;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            down = true;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            left = true;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (keyCode == KeyEvent.VK_SPACE) {
            space = true;
        } else if (keyCode == KeyEvent.VK_G) {
            grid = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            up = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            down = false;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            left = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (keyCode == KeyEvent.VK_SPACE) {
            space = false;
        } else if (keyCode == KeyEvent.VK_H) {
            grid = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        mx = me.getX();
        my = me.getY();
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    @Override
    public void mouseDragged(MouseEvent me) {

    }

    @Override
    public void mouseMoved(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
    }
}
