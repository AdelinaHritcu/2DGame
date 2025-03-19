package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_candy extends Entity{
    public Obj_candy(GamePanel gp) {
        super(gp);

        name="Donut";
        down1 = setup("/objects/candy", gp.tileSize, gp.tileSize);
    }
}
