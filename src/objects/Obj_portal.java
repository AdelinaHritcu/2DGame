package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_portal extends Entity{
    public Obj_portal(GamePanel gp) {
        super(gp);
        name="Portal";
        down1 = setup("/objects/portal", gp.tileSize, gp.tileSize);
    }
}
