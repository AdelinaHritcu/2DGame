package entity;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX, screenY;
    public int hasFood = 0;
    public int nivel = 1;
    public int hasScore = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 10;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 38;
        solidArea.height = 50;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    // seteaza valorile implicite ale jucatorului
    public void setDefaultValues() {
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 26;
        speed = 5;
        direction = "down";

        // PLAYER
        maxLife = 6;
        life = maxLife;
    }

    // seteaza pozitiile implicite ale jucatorului
    public void setDefaultPositions() {
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 26;
        direction = "down";
    }

    // restaureaza viata jucatorului
    public void restoreLife() {
        life = maxLife;
        invincible = false;
        hasFood = 0;
    }

    public BufferedImage setup(String imageName, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageName + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    // initializeaza imaginile jucatorului
    public void getPlayerImage() {
        up1 = setup("/textures/spate1", gp.tileSize, gp.tileSize);
        up2 = setup("/textures/spate2", gp.tileSize, gp.tileSize);
        up3 = setup("/textures/spate3", gp.tileSize, gp.tileSize);
        up4 = setup("/textures/spate4", gp.tileSize, gp.tileSize);
        up5 = setup("/textures/spate5", gp.tileSize, gp.tileSize);

        down1 = setup("/textures/fata1", gp.tileSize, gp.tileSize);
        down2 = setup("/textures/fata2", gp.tileSize, gp.tileSize);
        down3 = setup("/textures/fata3", gp.tileSize, gp.tileSize);
        down4 = setup("/textures/fata4", gp.tileSize, gp.tileSize);
        down5 = setup("/textures/fata5", gp.tileSize, gp.tileSize);

        left1 = setup("/textures/stanga1", gp.tileSize, gp.tileSize);
        left2 = setup("/textures/stanga2", gp.tileSize, gp.tileSize);
        left3 = setup("/textures/stanga3", gp.tileSize, gp.tileSize);
        left4 = setup("/textures/stanga4", gp.tileSize, gp.tileSize);
        left5 = setup("/textures/stanga5", gp.tileSize, gp.tileSize);

        right1 = setup("/textures/dreapta1", gp.tileSize, gp.tileSize);
        right2 = setup("/textures/dreapta2", gp.tileSize, gp.tileSize);
        right3 = setup("/textures/dreapta3", gp.tileSize, gp.tileSize);
        right4 = setup("/textures/dreapta4", gp.tileSize, gp.tileSize);
        right5 = setup("/textures/dreapta5", gp.tileSize, gp.tileSize);
    }

    // initializeaza imaginile de atac ale jucatorului
    public void getPlayerAttackImage() {
        attackUp1 = setup("/textures/up1", gp.tileSize, gp.tileSize);
        attackUp2 = setup("/textures/up2", gp.tileSize, gp.tileSize);

        attackDown1 = setup("/textures/down1", gp.tileSize, gp.tileSize);
        attackDown2 = setup("/textures/down2", gp.tileSize, gp.tileSize);

        attackLeft1 = setup("/textures/left1", gp.tileSize, gp.tileSize);
        attackLeft2 = setup("/textures/left2", gp.tileSize, gp.tileSize);

        attackRight1 = setup("/textures/right1", gp.tileSize, gp.tileSize);
        attackRight2 = setup("/textures/right2", gp.tileSize, gp.tileSize);

    }

    public void update() {
        //System.out.println("x:"+gp.player.worldX/gp.tileSize);
        //System.out.println("y:"+gp.player.worldY/gp.tileSize);
        if (keyH.spacePressed && attacking == true) {
            attacking();
        } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cCheker.checkTile(this);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cCheker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK object COLLISION
            int objIndex = gp.cCheker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK EVENT
            gp.eHandler.checkEvent();

            // IF THE COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyH.spacePressed) {
                attacking = false;
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
                gp.keyH.spacePressed = false;
            } else if (keyH.spacePressed) {
                attacking = true;
            }

            spriteCounter++;
            if (spriteCounter > 14) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 5;
                } else if (spriteNum == 5) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (life == 0 && gp.gameState == gp.playState) {
            gp.gameState = gp.gameOverState;
        }

    }

    // realzieaza interactiunea cu monstrul
    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                life -= 1;
                invincible = true;
            }
        }
    }

    // desfasoara atacul jucatorului
    public void attacking() {

        spriteNum++;
        if (spriteCounter <= 5) {
            spriteNum = 2;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int solidAreaWidth = solidArea.width;
            int currentWorldY = worldY;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            // check monster collision with the updated worldX, worldY and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > 25) {
            spriteNum = 2;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damageMonster(int i) {

        if (i != 999) {
            if (gp.monster[gp.currentMap][i].invincible == false)
            {
                gp.monster[gp.currentMap][i].life -= 1;
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                }
            }
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {
            String objectName = gp.obj[gp.currentMap][i].name;

            switch (objectName) {
                case "Waffle":
                    gp.playSE(1);
                    hasFood++;
                    hasScore++;
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "Donut":
                    gp.playSE(1);
                    hasFood++;
                    hasScore++;
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "Pretzel":
                    gp.playSE(1);
                    hasFood++;
                    hasScore++;
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "Portal":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    break;
                case "greenApple":
                    gp.playSE(1);
                    speed = speed - 2;
                    gp.obj[gp.currentMap][i] = null;
                    gp.ui.showMessage("-2 speed");
                    break;
                case "redApple":
                    gp.playSE(1);
                    speed = speed + 1;
                    gp.obj[gp.currentMap][i] = null;
                    gp.ui.showMessage("+2 speed");
                    break;
                case "Potion":
                    gp.obj[gp.currentMap][i] = null;
                    if(gp.player.life < 6)
                        gp.player.life += 1;
                    break;
                case "Dress":
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "Ring":
                    gp.obj[gp.currentMap][i] = null;
                    break;
                case "Necklace":
                    gp.obj[gp.currentMap][i] = null;
                    break;
            }
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    if (spriteNum == 3) {
                        image = up3;
                    }
                    if (spriteNum == 4) {
                        image = up4;
                    }
                    if (spriteNum == 5) {
                        image = up5;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }

                }
                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    if (spriteNum == 3) {
                        image = down3;
                    }
                    if (spriteNum == 4) {
                        image = down4;
                    }
                    if (spriteNum == 5) {
                        image = down5;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    if (spriteNum == 3) {
                        image = left3;
                    }
                    if (spriteNum == 4) {
                        image = left4;
                    }
                    if (spriteNum == 5) {
                        image = left5;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }

                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    if (spriteNum == 3) {
                        image = right3;
                    }
                    if (spriteNum == 4) {
                        image = right4;
                    }
                    if (spriteNum == 5) {
                        image = right5;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }
        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, null);

        // reset alpha
        g2.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)));
    }
/*
    public void restart0(){
        System.out.println("obj.length = "+gp.obj[0].length);
        for(int j=0;j<gp.obj[0].length;j++ ){
            //System.out.println(gp.obj[0].length);
            gp.obj[0][j]=null;
        }
        for(int i=0;i<gp.monster[0].length;i++){
            gp.monster[0][i] =null;
        }
    }
}
 */
}