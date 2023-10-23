package river;
import river.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import river.Item;
import java.awt.*;
import static river.GameEngine.*;


public class FarmerGameEngineTest {
    private FarmerGameEngine engine;
    private final Item BEANS = Item.ITEM_0;
    private final Item GOOSE = Item.ITEM_1;
    private final Item WOLF = Item.ITEM_2;
    private final Item FARMER = Item.ITEM_3;
    @Before
    public void setUp() throws Exception {
        engine = new FarmerGameEngine();
    }
    private void transportItem(Item Id){
        engine.loadBoat(Id);
        engine.rowBoat();
        engine.unloadBoat(Id);
    }


    @Test
    public void testObjectCallThroughs() {
        Assert.assertEquals("", engine.getItemLabel(FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(FARMER));

        // Check getters for wolf
        Assert.assertEquals("W", engine.getItemLabel(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertFalse(engine.getItemIsDriver(WOLF));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(WOLF));

        // Check getters for goose
        Assert.assertEquals("G", engine.getItemLabel(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertFalse(engine.getItemIsDriver(GOOSE));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(GOOSE));

        // Check getters for beans
        Assert.assertEquals("B", engine.getItemLabel(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertFalse(engine.getItemIsDriver(GOOSE));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(BEANS));
    }


    @Test
    public void testGooseTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        engine.loadBoat(FARMER);
        transportItem(GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(GOOSE));

    }


    @Test
    public void testWinningGame() {

        // transport the goose
        engine.loadBoat(FARMER);
        transportItem(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
        // transport the BEANS
        transportItem(BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back with the GOOSE
        transportItem(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
        // transport the WOLF
        transportItem(WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the GOOSE

        transportItem(GOOSE);
        engine.unloadBoat(FARMER);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());

    }


    @Test
    public void testLosingGame() {
        engine.loadBoat(FARMER);
        transportItem(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Transport the Wolf this time
        transportItem(WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());


    }


    @Test
    public void testError() {
        engine.loadBoat(FARMER);
        transportItem(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
        Location topLoc = engine.getItemLocation(WOLF);
        Location midLoc = engine.getItemLocation(GOOSE);
        Location bottomLoc = engine.getItemLocation(BEANS);
        Location playerLoc = engine.getItemLocation(FARMER);
        engine.loadBoat(WOLF);
        Assert.assertEquals(topLoc, engine.getItemLocation(WOLF));
        Assert.assertEquals(midLoc, engine.getItemLocation(GOOSE));
        Assert.assertEquals(bottomLoc, engine.getItemLocation(BEANS));
        Assert.assertEquals(playerLoc, engine.getItemLocation(FARMER));

    }
    @Test
    public void GetItems(){
        Assert.assertEquals(engine.getItems().size(),4);
    }
    @Test
    public void testLocation(){
        Assert.assertTrue(engine.getItemLocation(BEANS).isAtStart());
        engine.loadBoat(FARMER);
        transportItem(GOOSE);
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        transportItem(BEANS);
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());

    }

}