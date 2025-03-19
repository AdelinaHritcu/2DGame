package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_donut extends Entity{
    public Obj_donut(GamePanel gp) {
        super(gp);
        name="Donut";
        down1 = setup("/objects/donut", gp.tileSize, gp.tileSize);
    }
}
