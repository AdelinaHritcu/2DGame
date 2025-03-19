package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_heart extends Entity{
    public Obj_heart(GamePanel gp) {
        super(gp);
        name="Heart";
        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_empty", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);

    }
}
