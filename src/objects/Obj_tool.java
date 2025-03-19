package objects;

import entity.Entity;
import main.GamePanel;

public class Obj_tool extends Entity{
    public Obj_tool(GamePanel gp) {
        super(gp);

        name="Donut";
        down1 = setup("/objects/tool", gp.tileSize, gp.tileSize);
    }
}
