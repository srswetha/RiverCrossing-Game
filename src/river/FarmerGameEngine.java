package river;
import java.util.*;
import java.awt.Color;

public class FarmerGameEngine extends AbstractGameEngine {

    private final Item BEANS = Item.ITEM_0;
    private final Item GOOSE = Item.ITEM_1;
    private final Item WOLF = Item.ITEM_2;
    private final Item FARMER = Item.ITEM_3;
    private final int no_of_items = 4;

    public FarmerGameEngine() {
        super(2);
        gameObjects.put(FARMER,new GameObject("",Location.START,Color.MAGENTA, true));
        gameObjects.put(WOLF,new GameObject("W",Location.START,Color.CYAN, false));
        gameObjects.put(GOOSE,new GameObject("G",Location.START,Color.CYAN,false));
        gameObjects.put(BEANS,new GameObject("B",Location.START,Color.CYAN, false));
    }

    public boolean gameIsLost() {
        if (gameObjects.get(GOOSE).getLocation() == Location.BOAT) {
            return false;
        }
        if (gameObjects.get(GOOSE).getLocation() == gameObjects.get(FARMER).getLocation()) {
            return false;
        }
        if (gameObjects.get(GOOSE).getLocation() == boatLocation) {
            return false;
        }
        if (gameObjects.get(GOOSE).getLocation() == gameObjects.get(WOLF).getLocation()) {
            return true;
        }
        if (gameObjects.get(GOOSE).getLocation() == gameObjects.get(BEANS).getLocation()) {
            return true;
        }
        return false;
    }

    public List<Item> getItems(){
        List<Item> items = new ArrayList<Item>();
        int itemCount = 0;
        for(Item item:Item.values()){
            if(itemCount>=no_of_items) break;
            items.add(item);
            itemCount++;
        }
        return items;
    }
}
