package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_waffle extends Entity{
    public Obj_waffle(GamePanel gp) {
        super(gp);
        name="Waffle";
        down1 = setup("/objects/waffle", gp.tileSize, gp.tileSize);
    }
}
