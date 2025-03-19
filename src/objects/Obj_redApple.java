package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_redApple extends Entity{
    public Obj_redApple(GamePanel gp) {
        super(gp);
        name="redApple";
        down1 = setup("/objects/redApple", gp.tileSize, gp.tileSize);
    }
}
