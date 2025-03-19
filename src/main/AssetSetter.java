package main;

import monster.Goblin;
import monster.Monster;
import monster.Witch;
import objects.*;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject() {

        int mapNum=0;
        gp.obj[mapNum][0] = new Obj_waffle(gp);
        gp.obj[mapNum][0].worldX = 17 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 23 * gp.tileSize;

        gp.obj[mapNum][1] = new Obj_donut(gp);
        gp.obj[mapNum][1].worldX = 16 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 20 * gp.tileSize;

        gp.obj[mapNum][2] = new Obj_pretzel(gp);
        gp.obj[mapNum][2].worldX = 11 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 20 * gp.tileSize;

        gp.obj[mapNum][3] = new Obj_donut(gp);
        gp.obj[mapNum][3].worldX = 10 * gp.tileSize;
        gp.obj[mapNum][3].worldY = 24 * gp.tileSize;

        gp.obj[mapNum][4] = new Obj_donut(gp);
        gp.obj[mapNum][4].worldX = 17* gp.tileSize;
        gp.obj[mapNum][4].worldY = 9* gp.tileSize;

        gp.obj[mapNum][26] = new Obj_donut(gp);
        gp.obj[mapNum][26].worldX = 30* gp.tileSize;
        gp.obj[mapNum][26].worldY = 20* gp.tileSize;

        gp.obj[mapNum][27] = new Obj_waffle(gp);
        gp.obj[mapNum][27].worldX = 35* gp.tileSize;
        gp.obj[mapNum][27].worldY = 13* gp.tileSize;

        gp.obj[mapNum][5] = new Obj_greenApple(gp);
        gp.obj[mapNum][5].worldX = 17 * gp.tileSize;
        gp.obj[mapNum][5].worldY = 26 * gp.tileSize;

        gp.obj[mapNum][6] = new Obj_redApple(gp);
        gp.obj[mapNum][6].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][6].worldY = 17 * gp.tileSize;

        gp.obj[mapNum][7] = new Obj_potion(gp);
        gp.obj[mapNum][7].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][7].worldY = 25 * gp.tileSize;

        gp.obj[mapNum][19] = new Obj_potion(gp);
        gp.obj[mapNum][19].worldX = 33 * gp.tileSize;
        gp.obj[mapNum][19].worldY = 25 * gp.tileSize;

        gp.obj[mapNum][20] = new Obj_dress(gp);
        gp.obj[mapNum][20].worldX = 23 * gp.tileSize;
        gp.obj[mapNum][20].worldY = 9 * gp.tileSize;

        mapNum++;
        gp.obj[mapNum][8] = new Obj_candy(gp);
        gp.obj[mapNum][8].worldX = 14 * gp.tileSize;
        gp.obj[mapNum][8].worldY = 26 * gp.tileSize;

        gp.obj[mapNum][9] = new Obj_candy(gp);
        gp.obj[mapNum][9].worldX = 13 * gp.tileSize;
        gp.obj[mapNum][9].worldY = 10 * gp.tileSize;

        gp.obj[mapNum][10] = new Obj_candy(gp);
        gp.obj[mapNum][10].worldX = 34 * gp.tileSize;
        gp.obj[mapNum][10].worldY = 12 * gp.tileSize;

        gp.obj[mapNum][11] = new Obj_candy(gp);
        gp.obj[mapNum][11].worldX = 27 * gp.tileSize;
        gp.obj[mapNum][11].worldY = 26 * gp.tileSize;

        gp.obj[mapNum][12] = new Obj_candy(gp);
        gp.obj[mapNum][12].worldX = 32 * gp.tileSize;
        gp.obj[mapNum][12].worldY = 18 * gp.tileSize;

        gp.obj[mapNum][21] = new Obj_ring(gp);
        gp.obj[mapNum][21].worldX = 36 * gp.tileSize;
        gp.obj[mapNum][21].worldY = 15 * gp.tileSize;

        gp.obj[mapNum][23] = new Obj_candy(gp);
        gp.obj[mapNum][23].worldX = 15 * gp.tileSize;
        gp.obj[mapNum][23].worldY = 19 * gp.tileSize;

        gp.obj[mapNum][24] = new Obj_candy(gp);
        gp.obj[mapNum][24].worldX = 27 * gp.tileSize;
        gp.obj[mapNum][24].worldY = 7 * gp.tileSize;

        gp.obj[mapNum][25] = new Obj_candy(gp);
        gp.obj[mapNum][25].worldX = 36 * gp.tileSize;
        gp.obj[mapNum][25].worldY = 19 * gp.tileSize;

        mapNum++;
        gp.obj[mapNum][13] = new Obj_tool(gp);
        gp.obj[mapNum][13].worldX = 27 * gp.tileSize;
        gp.obj[mapNum][13].worldY = 14 * gp.tileSize;

        gp.obj[mapNum][14] = new Obj_tool(gp);
        gp.obj[mapNum][14].worldX = 13 * gp.tileSize;
        gp.obj[mapNum][14].worldY = 27 * gp.tileSize;

        gp.obj[mapNum][15] = new Obj_tool(gp);
        gp.obj[mapNum][15].worldX = 15 * gp.tileSize;
        gp.obj[mapNum][15].worldY = 7 * gp.tileSize;

        gp.obj[mapNum][16] = new Obj_tool(gp);
        gp.obj[mapNum][16].worldX = 30 * gp.tileSize;
        gp.obj[mapNum][16].worldY = 12 * gp.tileSize;

        gp.obj[mapNum][17] = new Obj_tool(gp);
        gp.obj[mapNum][17].worldX = 20 * gp.tileSize;
        gp.obj[mapNum][17].worldY = 18 * gp.tileSize;

        gp.obj[mapNum][18] = new Obj_tool(gp);
        gp.obj[mapNum][18].worldX = 20 * gp.tileSize;
        gp.obj[mapNum][18].worldY = 18 * gp.tileSize;

        gp.obj[mapNum][22] = new Obj_necklace(gp);
        gp.obj[mapNum][22].worldX = 34 * gp.tileSize;
        gp.obj[mapNum][22].worldY = 12 * gp.tileSize;
    }

    public void setMonster() {
        int mapNum=0;
        gp.monster[mapNum][0] = new Monster(gp);
        gp.monster[mapNum][0].worldX = gp.tileSize*29;
        gp.monster[mapNum][0].worldY = gp.tileSize*9;

        gp.monster[mapNum][6] = new Monster(gp);
        gp.monster[mapNum][6].worldX = gp.tileSize*19;
        gp.monster[mapNum][6].worldY = gp.tileSize*17;

        mapNum++;
        gp.monster[mapNum][1] = new Goblin(gp);
        gp.monster[mapNum][1].worldX = gp.tileSize*26;
        gp.monster[mapNum][1].worldY = gp.tileSize*25;

        gp.monster[mapNum][2] = new Goblin(gp);
        gp.monster[mapNum][2].worldX = gp.tileSize*9;
        gp.monster[mapNum][2].worldY = gp.tileSize*8;

        gp.monster[mapNum][3] = new Goblin(gp);
        gp.monster[mapNum][3].worldX = gp.tileSize*34;
        gp.monster[mapNum][3].worldY = gp.tileSize*19;

        gp.monster[mapNum][4] = new Goblin(gp);
        gp.monster[mapNum][4].worldX = gp.tileSize*33;
        gp.monster[mapNum][4].worldY = gp.tileSize*24;

        gp.monster[mapNum][5] = new Goblin(gp);
        gp.monster[mapNum][5].worldX = gp.tileSize*31;
        gp.monster[mapNum][5].worldY = gp.tileSize*28;

        mapNum++;
        gp.monster[mapNum][5] = new Witch(gp);
        gp.monster[mapNum][5].worldX = gp.tileSize*31;
        gp.monster[mapNum][5].worldY = gp.tileSize*28;

        gp.monster[mapNum][7] = new Monster(gp);
        gp.monster[mapNum][7].worldX = gp.tileSize*27;
        gp.monster[mapNum][7].worldY = gp.tileSize*28;

        gp.monster[mapNum][8] = new Monster(gp);
        gp.monster[mapNum][8].worldX = gp.tileSize*23;
        gp.monster[mapNum][8].worldY = gp.tileSize*13;

        gp.monster[mapNum][8] = new Goblin(gp);
        gp.monster[mapNum][8].worldX = gp.tileSize*11;
        gp.monster[mapNum][8].worldY = gp.tileSize*9;

        gp.monster[mapNum][8] = new Goblin(gp);
        gp.monster[mapNum][8].worldX = gp.tileSize*14;
        gp.monster[mapNum][8].worldY = gp.tileSize*21;
    }
}
