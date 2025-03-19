package monster;
import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Witch extends Entity {
    GamePanel gp;
    public Witch(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Witch";
        speed = 3;
        maxLife = 2;
        life = maxLife;
        type=2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 40;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setAction();
    }
    public void getImage() {
        down1 = setup("/monster/witch_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/witch_down2", gp.tileSize, gp.tileSize);
        down3 = setup("/monster/witch_down3", gp.tileSize, gp.tileSize);
        down4 = setup("/monster/witch_down4", gp.tileSize, gp.tileSize);

        up1 = setup("/monster/witch_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/witch_up2", gp.tileSize, gp.tileSize);
        up3 = setup("/monster/witch_up3", gp.tileSize, gp.tileSize);
        up4 = setup("/monster/witch_up4", gp.tileSize, gp.tileSize);

        right1 = setup("/monster/witch_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/witch_right2", gp.tileSize, gp.tileSize);
        right3 = setup("/monster/witch_right3", gp.tileSize, gp.tileSize);
        right4 = setup("/monster/witch_right4", gp.tileSize,gp.tileSize);

        left1 = setup("/monster/witch_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/witch_left2", gp.tileSize, gp.tileSize);
        left3 = setup("/monster/witch_left3", gp.tileSize, gp.tileSize);
        left4 = setup("/monster/witch_left4", gp.tileSize, gp.tileSize);
    }

    public void updateMonster() {
        super.updateMonster();
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance < 10) {
            int i = new Random().nextInt(100) + 1;
            if (i > 50) {
                onPath = true;
            }
        }
        if (onPath && tileDistance > 20) {
            onPath = false;
        }
    }

    public void setAction() {

        if (onPath) {

            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();

                int i = random.nextInt(100) + 1;

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }
};
