package river;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonsterGameEngine extends  AbstractGameEngine{

    private final int no_of_items=6;
    MonsterGameEngine(){
        super(2);
        for(Item id : Item.values()){
            if((id.ordinal())%2 == 0){
                gameObjects.put(id,new GameObject("M",Location.START,Color.CYAN,true));
                gameObjects.put(id,new GameObject("M",Location.START,Color.CYAN,true));
                gameObjects.put(id,new GameObject("M",Location.START,Color.CYAN,true));
            } else {
                gameObjects.put(id,new GameObject("K",Location.START,Color.GREEN,true));
            }
        }
    }

    @Override
    public boolean gameIsLost(){
        int ssCount = 0, fsCount=0, shCount = 0, fhCount = 0;
        for(Item id : Item.values()){
            if(gameObjects.get(id).getLabel() == "M"){
                if (getItemLocation(id) == Location.START || (getItemLocation(id) == Location.BOAT && getBoatLocation() == Location.START)){
                    ssCount++;
                }else{
                    fsCount++;
                }
            } else{
                if (getItemLocation(id) == Location.START || (getItemLocation(id) == Location.BOAT && getBoatLocation() == Location.START))
                    shCount++;
                else
                    fhCount++;
            }
        }
        return (ssCount > shCount && shCount > 0) || (fsCount > fhCount && fhCount > 0);
    }
    @Override
    public boolean getItemIsDriver(Item item) {
        return true;
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