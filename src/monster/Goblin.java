package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Goblin extends Entity {
    GamePanel gp;
    public Goblin(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Goblin";
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
    // metoda pentru încărcarea imaginilor goblinului
    public void getImage() {
        down1 = setup("/monster/downn1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/downn2", gp.tileSize, gp.tileSize);
        down3 = setup("/monster/downn3", gp.tileSize, gp.tileSize);
        down4 = setup("/monster/downn4", gp.tileSize, gp.tileSize);

        up1 = setup("/monster/upp1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/upp2", gp.tileSize, gp.tileSize);
        up3 = setup("/monster/upp3", gp.tileSize, gp.tileSize);
        up4 = setup("/monster/upp4", gp.tileSize, gp.tileSize);

        right1 = setup("/monster/rightt1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/rightt2", gp.tileSize, gp.tileSize);
        right3 = setup("/monster/rightt3", gp.tileSize, gp.tileSize);
        right4 = setup("/monster/rightt4", gp.tileSize,gp.tileSize);

        left1 = setup("/monster/leftt1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/leftt2", gp.tileSize, gp.tileSize);
        left3 = setup("/monster/leftt3", gp.tileSize, gp.tileSize);
        left4 = setup("/monster/leftt4", gp.tileSize, gp.tileSize);
    }

    // metoda pentru actualizarea stării goblinului
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

    // metoda pentru gestionarea acțiunilor goblinului.
    // daca goblinul este pe un traseu (onPath = true), acesta va cauta sa atace jucătorul.
    // in caz contrar, goblinul va genera aleator un nou direcție de mișcare la intervale regulate.
    public void setAction() {

        if (onPath) {

            // calculează coloana si randul la care se afla jucătorul
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            // cauta un traseu catre jucator
            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;

            // la fiecare 120 de unitați de timp, goblinul va alege aleator o directie de miscare.
            if (actionLockCounter == 120) {
                Random random = new Random();

                // generează un numar aleator intre 1 și 100
                int i = random.nextInt(100) + 1;

                // distribuie aleator directiile de miscare
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

                actionLockCounter = 0; // resetarea contorului de actiuni
            }
        }
    }
    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }
};
