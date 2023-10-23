package river;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class AbstractGameEngine implements GameEngine {

    protected HashMap<Item,GameObject> gameObjects;
    protected Location boatLocation;
    private int boatSize;

    public AbstractGameEngine(int bSize){
        gameObjects = new HashMap<Item,GameObject>();
        boatLocation = Location.START;
        boatSize = bSize;
    }

    public String getItemLabel(Item id) {
        return gameObjects.get(id).getLabel();
    }

    public Location getItemLocation(Item id) {
        return gameObjects.get(id).getLocation();
    }

    public Color getItemColor(Item id) {
        return gameObjects.get(id).getColor();
    }

    public Location getBoatLocation() {
        return boatLocation;
    }

    public boolean getItemIsDriver(Item item){
        return gameObjects.get(item).getDriverStatus();
    }

    public void setItemLocation(Item item,Location location){
        gameObjects.get(item).setLocation(location);
    }

    public void loadBoat(Item id) {
        int n = 0;
        Iterator hmIterator = gameObjects.entrySet().iterator();
        while(hmIterator.hasNext()) {
            Map.Entry mapEle = (Map.Entry) hmIterator.next();
            GameObject g = (GameObject) mapEle.getValue();
            if(g.getLocation().isOnBoat()) n++;
        }

        if (n<boatSize && gameObjects.get(id).getLocation()==boatLocation){
            gameObjects.get(id).setLocation(Location.BOAT);
        }
    }

    public void unloadBoat(Item id){
        if (gameObjects.get(id).getLocation() == Location.BOAT) {
            gameObjects.get(id).setLocation(boatLocation);
        }
    }

    public void rowBoat() {
        int n = 0;
        Iterator hmIterator = gameObjects.entrySet().iterator();
        while(hmIterator.hasNext()) {
            Map.Entry mapEle = (Map.Entry) hmIterator.next();
            GameObject g = (GameObject) mapEle.getValue();
            if(g.getLocation().isOnBoat()) n++;
        }
        if(n>0){
            if (boatLocation == Location.START) {
                boatLocation = Location.FINISH;
            } else {
                boatLocation = Location.START;
            }
        }
    }

    public boolean gameIsWon() {

        Iterator hmIterator = gameObjects.entrySet().iterator();
        while(hmIterator.hasNext()) {
            Map.Entry mapEle = (Map.Entry) hmIterator.next();
            GameObject g = (GameObject) mapEle.getValue();
            if(! g.getLocation().isAtFinish()) return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    abstract public boolean gameIsLost();

    public void resetGame() {
        Iterator hmIterator = gameObjects.entrySet().iterator();
        while(hmIterator.hasNext()) {
            Map.Entry mapEle = (Map.Entry) hmIterator.next();
            GameObject g = (GameObject) mapEle.getValue();
            g.setLocation(Location.START);
        }
        boatLocation = Location.START;
    }

}
