package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_ring extends Entity{
    public Obj_ring(GamePanel gp) {
        super(gp);
        name="Ring";
        down1 = setup("/objects/ring", gp.tileSize, gp.tileSize);
    }
}
