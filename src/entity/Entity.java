package entity;
import main.GamePanel;
import main.UtilityTool;
import monster.Goblin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public BufferedImage up1, up2, up3, up4, up5, left1, left2, left3, left4, left5, right1, right2, right3, right4, right5, down1, down2, down3, down4, down5;
   public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
           attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
   // STATE
    public String name;
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;

    public boolean onPath;

    public boolean collision = false;
    public boolean collisionOn = false;
    public boolean alive = true;
    public boolean dying = false;
    public int type;

    boolean attacking = false;
    // COUNTER
    public int spriteCounter = 0;
    public int solidAreaDefaultX, solidAreaDefaultY;
    int dyingCounter = 0;

    // CHARACTER ATTRIBUTES
    public int maxLife;
    public int life;
    public int speed;

    // MONSTER
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter =0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public void setAction() {}
    public void damageReaction() {
    }

    // verificarea coliziunilor entitatii cu alte entitati din joc
    public void checkCollisionMonster() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        gp.cChecker.checkEntity(this,gp.monster);
        gp.cChecker.checkPlayer(this);

        if(!collisionOn){

            switch(direction){
                case "up":
                    worldY-= speed;
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
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(this.type == 2 && contactPlayer) {
            if(!gp.player.invincible) {
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

    }
    // scalarea imaginii citite din fisier
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, width, height);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    // cauta un traseu catre o pozitie data
    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);

        if (gp.pFinder.search()) {
            int nextX = gp.pFinder.pathList.getFirst().col * gp.tileSize;
            int nextY = gp.pFinder.pathList.getFirst().row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "right";
                }
            }
        }
    }
    // afiseaza/deseneaza entitatile pe ecran
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                    if (spriteNum == 3) image = up3;
                    if (spriteNum == 4) image = up4;
                    break;
                case "down":
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                    if (spriteNum == 3) image = down3;
                    if (spriteNum == 4) image = down4;
                    break;
                case "left":
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    if (spriteNum == 3) image = left3;
                    if (spriteNum == 4) image = left4;
                    break;
                case "right":
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    if (spriteNum == 3) image = right3;
                    if (spriteNum == 4) image = right4;
                    break;
            }

            // monster HP bar
            if(type == 2 && hpBarOn == true) {

                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;

                if(hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }

            }
            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2,0.4f);
            }
            if(dying == true) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i=5;
        if(dyingCounter <= i) {changeAlpha(g2, 0f);}
        if(dyingCounter > i && dyingCounter <=i*2) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*2 && dyingCounter <=i*3) {changeAlpha(g2, 0f);}
        if(dyingCounter > i*3 && dyingCounter <=i*4) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*4 && dyingCounter <=i*5) {changeAlpha(g2, 0f);}
        if(dyingCounter > i*5 && dyingCounter <=i*6) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*6 && dyingCounter <=i*7) {changeAlpha(g2, 0f);}
        if(dyingCounter > i*7 && dyingCounter <=i*8) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*8){
            dying = false;
            alive = false;
        }
    }
    // modificarea gradului de transparenta al entitatii
    public void changeAlpha(Graphics2D g2, float alphaValue) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    // actualizarea starii monstrului
    public void updateMonster(){
        setAction();
        checkCollisionMonster();
    }

}


