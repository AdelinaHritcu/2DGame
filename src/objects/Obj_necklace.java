package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_necklace extends Entity{
    public Obj_necklace(GamePanel gp) {
        super(gp);
        name="Necklace";
        down1 = setup("/objects/necklace", gp.tileSize, gp.tileSize);
    }
}
