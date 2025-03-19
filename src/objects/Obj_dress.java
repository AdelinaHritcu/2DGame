package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_dress extends Entity{
    public Obj_dress(GamePanel gp) {
        super(gp);
        name="Dress";
        down1 = setup("/objects/dress", gp.tileSize, gp.tileSize);
    }
}
