package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Monster extends Entity {
    GamePanel gp;
    public Monster(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Monster";
        speed = 1;
        maxLife = 2;
        life = maxLife;
        type=2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 45;
        solidArea.height = 45;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setAction();
    }
    public void getImage() {
        down1 = setup("/monster/monster_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/monster_down1", gp.tileSize, gp.tileSize);
        down3 = setup("/monster/monster_down1", gp.tileSize, gp.tileSize);
        down4 = setup("/monster/monster_down1", gp.tileSize, gp.tileSize);

        up1 = setup("/monster/monster_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/monster_up1", gp.tileSize, gp.tileSize);
        up3 = setup("/monster/monster_up1", gp.tileSize, gp.tileSize);
        up4 = setup("/monster/monster_up1", gp.tileSize, gp.tileSize);

        right1 = setup("/monster/monster_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/monster_right1", gp.tileSize, gp.tileSize);
        right3 = setup("/monster/monster_right1", gp.tileSize, gp.tileSize);
        right4 = setup("/monster/monster_right1", gp.tileSize,gp.tileSize);

        left1 = setup("/monster/monster_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/monster_left1", gp.tileSize, gp.tileSize);
        left3 = setup("/monster/monster_left1", gp.tileSize, gp.tileSize);
        left4 = setup("/monster/monster_left1", gp.tileSize, gp.tileSize);
    }

    public void setAction() {

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

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
};
