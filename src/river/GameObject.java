package river;
import java.awt.Color;

public class GameObject {

    final String label;
    final Color color;
    private Location location;
    final Boolean isDriver;

    public GameObject(String n, Location l,Color c, Boolean d){
        this.label = n;
        this.location = l;
        this.isDriver = d;
        this.color = c;

    }

    public String getLabel() {
        return label;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public Color getColor() {
        return color;
    }

    public boolean getDriverStatus(){
        return isDriver;
    }

}
