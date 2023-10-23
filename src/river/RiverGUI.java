package river;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Graphical interface for the River application
 *
 * @author Gregory Kulczycki
 */
public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Fields (hotspots)
    // ==========================================================
    private final int leftBaseX = 20;
    private final int leftBaseY = 275;
    private final int rightBaseX = 670;
    private final int rightBaseY = 275;
    private final int boatLeftX = 140;
    private final int boatLeftY = 275;
    private final int boatRightX = 550;
    private final int boatRightY = 275;

    private final int boatWidth = 110;
    private final int boatHeight = 50;
    private final int leftBoatX1 = 140;
    private final int leftBoatX2 = 200;
    private final int leftBoatY = 220;
    private final int rightBoatX1 = 550;
    private final int rightBoatX2 = 610;
    private final int rightBoatY = 220;

    int[] dx = { 0, 60, 0, 60, 0, 60 };
    int[] dy = { 0, 0, -60, -60, -120, -120 };
    private final Rectangle farmerGameRect = new Rectangle(270, 120, 100, 30);
    private final Rectangle monsterGameRect = new Rectangle(420, 120, 100, 30);
    // ==========================================================
    // Private Fields
    // ==========================================================

    private GameEngine engine; // Model
    private HashMap<Item,Rectangle> itemRectangleMap;
    private Rectangle rectForBoat;

    private boolean restart = false;

    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {
        engine = new FarmerGameEngine();
        createRects();
        addMouseListener(this);
    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics g) {
        refreshItemRectangles(); // based on model
        refreshBoatRectangle(); // based on model

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for(Item item: engine.getItems()){
            paintItem(g,item);
        }
        paintBoat(g);

        String message = "";
        if (engine.gameIsLost()) {
            message = "You Lost!";
            restart = true;
        }
        if (engine.gameIsWon()) {
            message = "You Won!";
            restart = true;
        }
        paintMessage(message, g);
        if (restart) {
            paintRestartButton(g);
        }

    }
    public void refreshItemRectangles() {
        for (Item Id : engine.getItems()) {
            Rectangle r = itemRectangleMap.get(Id);
            int x = 0, y = 0, w = r.width, h = r.height;

            if (engine.getItemLocation(Id).isAtStart()) {
                x = leftBaseX + dx[Id.ordinal()];
                y = leftBaseY + dy[Id.ordinal()];
            } else if (engine.getItemLocation(Id).isAtFinish()) {
                x = rightBaseX + dx[Id.ordinal()];
                y = rightBaseY + dy[Id.ordinal()];
            } else if (engine.getItemLocation(Id).isOnBoat()) {
                boolean boatIsAtStart = engine.getBoatLocation().isAtStart();
                Point boatPoint = getBoatPositionForItem(Id, boatIsAtStart);
                x = boatPoint.x;
                y = boatPoint.y;
            }

            itemRectangleMap.put(Id, new Rectangle(x, y, w, h));
        }
    }


    public void createRects(){
        itemRectangleMap = new HashMap<>();
        for(Item item: engine.getItems()){
            itemRectangleMap.put(item,new Rectangle(leftBaseX+dx[item.ordinal()],leftBaseY+dy[item.ordinal()],50,50));
        }
    }


    private Point getBoatPositionForItem(Item Id, boolean boatIsAtStart) {
        int boatX1 = boatIsAtStart ? leftBoatX1 : rightBoatX1;
        int boatX2 = boatIsAtStart ? leftBoatX2 : rightBoatX2;
        int boatY = boatIsAtStart ? leftBoatY : rightBoatY;

        for (Map.Entry<Item, Rectangle> entry : itemRectangleMap.entrySet()) {
            Rectangle rect = entry.getValue();
            if (entry.getKey() == Id && (rect.x == boatX1 || rect.x == boatX2)) {
                return new Point(rect.x, rect.y);  // Item already placed
            }
            if (rect.x == boatX1 && rect.y == boatY) {
                return new Point(boatX2, boatY);  // First position filled
            }
        }

        return new Point(boatX1, boatY);
    }

    public void refreshBoatRectangle(){
        if(engine.getBoatLocation() == Location.START){
            rectForBoat = new Rectangle(boatLeftX, boatLeftY, boatWidth, boatHeight);
        }else{
            rectForBoat = new Rectangle(boatRightX, boatRightY, boatWidth, boatHeight);
        }
    }
    public void paintItem(Graphics g,Item Id){
        Rectangle r = itemRectangleMap.get(Id);
        g.setColor(engine.getItemColor(Id));
        g.fillRect(r.x,r.y,r.width,r.height);
        paintStringInRectangle(engine.getItemLabel(Id), r.x, r.y, r.width, r.height, g);
    }

    public void paintBoat(Graphics g){
        g.setColor(Color.ORANGE);
        g.fillRect(rectForBoat.x,rectForBoat.y,rectForBoat.width,rectForBoat.height);
    }

    public void paintStringInRectangle(String str, int x, int y, int width, int height, Graphics g) {
        g.setColor(Color.BLACK);
        int fontSize = (height >= 40) ? 36 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = x + width / 2 - fm.stringWidth(str) / 2;
        int strYCoord = y + height / 2 + fontSize / 2 - 4;
        g.drawString(str, strXCoord, strYCoord);
    }

    public void paintMessage(String message, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    public void paintRestartButton(Graphics g) {
        g.setColor(Color.BLACK);
        paintBorder(farmerGameRect, 3, g);
        paintBorder(monsterGameRect, 3, g);
        g.setColor(Color.PINK);
        paintRectangle(farmerGameRect, g);
        paintRectangle(monsterGameRect, g);
        paintStringInRectangle("Farmer", farmerGameRect.x, farmerGameRect.y, farmerGameRect.width,
                farmerGameRect.height, g);
        paintStringInRectangle("Monster", monsterGameRect.x, monsterGameRect.y, monsterGameRect.width,
                monsterGameRect.height, g);

    }

    public void paintBorder(Rectangle r, int thickness, Graphics g) {
        g.fillRect(r.x - thickness, r.y - thickness, r.width + (2 * thickness), r.height + (2 * thickness));
    }

    public void paintRectangle(Rectangle r, Graphics g) {
        g.fillRect(r.x, r.y, r.width, r.height);
    }

    // ==========================================================
    // Startup Methods
    // ==========================================================

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window
        JFrame frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        RiverGUI newContentPane = new RiverGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(RiverGUI::createAndShowGUI);
    }

    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================

    @Override
    public void mouseClicked(MouseEvent e) {

        if (restart) {
            if (this.farmerGameRect.contains(e.getPoint())) {
                engine = new FarmerGameEngine();
                engine.resetGame();
                createRects();
                restart = false;
                repaint();
            }else if(this.monsterGameRect.contains((e.getPoint()))){
                engine = new MonsterGameEngine();
                engine.resetGame();
                createRects();
                restart = false;
                repaint();
            }

            return;
        }

        Rectangle leftBoatRect1 = new Rectangle(boatLeftX,boatLeftY,boatWidth,boatHeight);
        Rectangle rightBoatRect1 = new Rectangle(boatRightX,boatRightY,boatWidth,boatHeight);

        if (leftBoatRect1.contains(e.getPoint()) || rightBoatRect1.contains(e.getPoint())) {
            for (Item i : engine.getItems()) {
                if (engine.getItemLocation(i).isOnBoat() && engine.getItemIsDriver(i)) {
                    engine.rowBoat();
                    break;
                }
            }
        } else {
            Iterator hmIterator = itemRectangleMap.entrySet().iterator();
            while(hmIterator.hasNext()) {
                Map.Entry mapEle = (Map.Entry) hmIterator.next();
                Rectangle t = (Rectangle) mapEle.getValue();
                Item id = (Item) mapEle.getKey();

                if(t.contains(e.getPoint())){
                    if(engine.getItemLocation(id).isAtStart() && t.x < leftBoatX1){
                        engine.loadBoat(id);
                    } else if(engine.getItemLocation(id).isOnBoat() && t.x >= leftBoatX1 && t.x <= rightBoatX2){
                        engine.unloadBoat(id);
                    } else if(engine.getItemLocation(id).isAtFinish() && t.x > rightBoatX2){
                        engine.loadBoat(id);
                    } else {
                        return;
                    }
                }
            }
        }
        repaint();
    }

    // ----------------------------------------------------------
    // None of these methods will be used
    // ----------------------------------------------------------

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
