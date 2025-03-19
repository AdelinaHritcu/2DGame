package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_pretzel extends Entity{
    public Obj_pretzel(GamePanel gp) {
        super(gp);
        name="Pretzel";
        down1 = setup("/objects/pretzel", gp.tileSize, gp.tileSize);
    }
}
