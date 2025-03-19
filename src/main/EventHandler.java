package main;
import java.awt.*;
public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    public EventHandler(GamePanel gp){
        this.gp=gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col=0;
        int row=0;
        // initializare obiecte eveniment pentru fiecare harta, coloana si rand
        while(map < gp.maxMap && col < gp.maxWorldCol &&row< gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            // trecere la urmatorul rand sau harta si resetarea coloanei la 0
            if(col == gp.maxWorldCol) {
                col =0;
                row++;
                if( row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }
    // verifica daca trebuie sa aiba loc un eveniment
    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX-previousEventX);
        int yDistance = Math.abs(gp.player.worldY-previousEventY);
        int distance = Math.max(xDistance,yDistance);
        // verifica daca jucatorul s-a deplasat suficient de mult pentru a activa un eveniment nou
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }

        // verifica evenimentele disponibile daca jucatorul poate atinge un eveniment nou
        if(canTouchEvent == true) {

            if (gp.player.nivel == 1) {
                if (hit(0, 23,8, "any") == true && gp.player.hasFood >= 5) {
                        gp.ui.showMessage("Level up!");
                        teleport(1, 8, 27);
                        gp.player.nivel++;
                        gp.tileM.update();
                }
                else  if (hit(0, 23,8, "any") == true && gp.player.hasFood <5) {
                    gp.ui.showMessage("You need to collect 5 items to level up!");
                }
            } else if (gp.player.nivel == 2) {
                if (hit(1, 36, 14, "any") == true && gp.player.hasFood >= 5) {
                        gp.ui.showMessage("Level up!");
                        teleport(2, 8, 27);
                        gp.player.nivel++;
                        gp.tileM.update();
                }
                else if (hit(1, 36, 14, "any") == true && gp.player.hasFood <5) {
                    gp.ui.showMessage("You need to collect 5 items to level up!");
                }
            }
            else if(gp.player.nivel == 3) {
                if(hit(2, 34, 10, "any") == true && gp.monster[2][5] == null) {
                    gp.gameState = gp.finishedGame;
                }
                else if (hit(2, 34, 10, "any") == true) {
                    gp.ui.showMessage("You need to kill the witch to win the game!");
                }
            }
        }
        //else if(hit(1,33,10,"any") == true)
        //   gp.ui.showMessage("To level up you need to collect 20 coins!");
    }

    // teleporteaza jucatorul pe harta specificata la coloana si randul specificate
    public void teleport(int map, int col, int row) {
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;

        gp.player.hasFood=0;
    }
    /*
    private void damage(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.player.life -= 1;
        eventRect[col][row].eventDone= true;
    }
    */
    // verifica daca jucatorul a lovit un eveniment specificat pe harta
    public boolean hit ( int map, int col, int row, String reqDirection){

        boolean hit = false;

        if(gp.player.nivel == 3 && gp.currentMap == 0) {
            gp.currentMap = 2;
        }
        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX=gp.player.worldX;
                    previousEventY=gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

}
