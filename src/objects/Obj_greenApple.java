package objects;

import main.GamePanel;
import entity.Entity;

public class Obj_greenApple extends Entity{
    public Obj_greenApple(GamePanel gp) {
        super(gp);
        name="greenApple";
        down1=setup("/objects/greenApple", gp.tileSize, gp.tileSize);
        }
}
