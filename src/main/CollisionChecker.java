package main;
import entity.Entity;
public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp=gp;
    }

    // verifica coliziunile cu dalele hărții
    public void checkTile(Entity entity){

        // calculeaza coordonatele colturilor dreptunghiului solid al entitatii în lumea jocului
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // calculează numarul de coloane si randuri ale tile-urilor cu care entitatea se intersecteaza
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;
        // verifica coliziunile în functie de directia entitatii
        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2=gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY - entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2=gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2=gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX - entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2=gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
        }
    }
    // verifica coliziunile cu obiectele de pe harta
    public int checkObject(Entity entity, boolean player) {
        // indexul obiectului cu care s-a produs coliziunea (999 dacă nu există coliziune)
        int index = 999;

        // parcurge toate obiectele de pe hartă
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {
                // actualizeaza pozitia solid area a entitatii si a obiectului
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                // actualizează pozitia solid area a entitatii in functie de directia de mers
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                // verifica dacă solid area a entitatii se intersecteaza cu solid area obiectului
                if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                    // Verifică dacă obiectul are coliziune
                    if (gp.obj[gp.currentMap][i].collision) {
                        entity.collisionOn = true;
                    }
                    // Verifica dacă entitatea este jucator si returnează indexul obiectului
                    if (player) {
                        index = i;
                    }
                }
                // resetarea pozitiilor solid area ale entitatii si obiectului
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }

        // returnează indexul obiectului cu care s-a produs coliziunea
        return index;

}

    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;

        for(int i=0;i<target[1].length;i++)
        {
            if(target[gp.currentMap][i]!=null){
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed; break;
                    case "down":
                        entity.solidArea.y += entity.speed; break;
                    case "left":
                        entity.solidArea.x -= entity.speed; break;
                    case "right":
                        entity.solidArea.x += entity.speed; break;
                }
                if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                    if(target[gp.currentMap][i] != entity) {
                    entity.collisionOn = true;
                    index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    // verifica dacă entitatea se intersecteaza cu jucatorul
    public boolean checkPlayer(Entity entity) {
        // variabila pentru a indica dacă s-a produs contactul cu jucatorul
        boolean contactPlayer = false;

        // actualizeaza pozitia solid area a entitatii si a jucatorului
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        // actualizeaza pozitia solid area a entitatii in functie de directia de mers
        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }

        // verifica dacă solid area a entitatii se intersecteaza cu solid area jucatorului
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            // marcheaza coliziunea pentru entitate
            entity.collisionOn = true;
            // indica că s-a produs contact cu jucatorul
            contactPlayer = true;
        }

        // reseteaza pozitiile solid area ale entitatii si jucatorului
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        // returneaza true daca s-a produs contact cu jucatorul, altfel false
        return contactPlayer;
    }

}
