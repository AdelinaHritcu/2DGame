package tile;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    public TileManager(GamePanel gp){
        this.gp=gp;
        tile = new Tile[100];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/worldMap.txt",0);
        loadMap("/maps/worldMap2.txt",1);
        loadMap("/maps/worldMap3.txt",2);
    }
    public void getTileImage() {
            setup(0,"grass",true);
            setup(1,"terminal_mijloc",false);
            setup(2,"terminal_stanga",false);
            setup(3,"colt_dreapta",false);
            setup(4,"grass2",true);
            setup(5,"middle_carare",false);
            setup(6,"carare_sus",false);
            setup(7,"colt_stanga",false);
            setup(8,"tree1",true);
            setup(9,"colt_jos_stanga",false);
            setup(10,"tufa_roz",true);
            setup(11,"colt_jos_dreapta",false);
            setup(12,"tufa_galben",true);
            setup(13,"terminal_dreapta",false);
            setup(14,"terminal_sus",false);
            setup(15,"terminal_jos",false);
            setup(16,"middle_up",false);
            setup(17,"rose",true);
            setup(18,"middle_down",false);
            setup(19,"mm",false);
            setup(20,"apa_stg_sus",false);
            setup(21,"apa_drt_sus",false);
            setup(22,"apa_drt_jos",false);
            setup(23,"apa_stg_jos",false);
            setup(24,"mij_sus",false);
            setup(25,"mij_jos",false);
            setup(26,"rose2",true);
            setup(27,"rose3",true);
            setup(28,"tulips",true);
            setup(29,"flowers",true);
            setup(30,"flowers2",true);
            setup(31,"flowers3",true);
            setup(32,"p1",false);
            setup(33,"p2",true);
            setup(34,"p3",true);
            setup(35,"p4",true);
            setup(36,"balta_mica",false);
            setup(37,"balta_mare",false);
            setup(38,"p5",false);
            setup(39,"p6",false);
            setup(40,"c1",false);
            setup(41,"c2",false);
            setup(42,"c3",false);
            setup(43,"c4",false);
            setup(44,"c5",false);
            setup(45,"c6",false);
            setup(46,"ceva",true);
            setup(47,"t",true);
            setup(48,"t1",true);
            setup(49,"t2",true);
            setup(50,"t3",true);
            setup(51,"t4",true);
            setup(52,"t5",true);
            setup(53,"pietre",false);
            setup(54,"grass3",false);
            setup(55,"grass4",false);
            setup(56,"pp1",false);
            setup(57,"pp2",false);
            setup(58,"pp3",false);
            setup(59,"pp4",false);
            setup(60,"pp5",false);
            setup(61,"pp6",false);
            setup(62,"grass6",true);
            setup(63,"sand",false);
            setup(64,"water",true);
            setup(65,"water1",true);
            setup(66,"water2",true);
            setup(67,"palmier",true);
            setup(68,"copac11",true);
            setup(69,"copac2",true);
            setup(70,"cactus1",true);
            setup(71,"cactus2",true);
            setup(72,"cactus3",true);
            setup(73,"water_left",true);
            setup(74,"water_right",true);
            setup(75,"wat",true);
            setup(76,"water3",true);
            setup(77,"wat_stj",true);
            setup(78,"wat_drj",true);
            setup(79,"wat_sts",true);
            setup(80,"wat_colt_dr",true);
            setup(81,"pal1",true);
            setup(82,"pal2",true);
            setup(83,"scoici",false);
            setup(84,"ppp1",false);
            setup(85,"ppp2",false);
            setup(86,"ppp3",false);
            setup(87,"ppp4",false);
            setup(88,"ppp5",false);
            setup(89,"ppp6",false);
    }
    public void setup(int index, String imagePath, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath+".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath, int map) {
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                        String line = br.readLine();

                        while(col < gp.maxWorldCol){
                            String numbers[] = line.split(" ");

                            int num = Integer.parseInt(numbers[col]);
                            mapTileNum[map][col][row] = num;
                            col++;
                        }
                        if(col == gp.maxWorldCol){
                            col = 0;
                            row++;
                        }
            }
            br.close();
        }catch(Exception e) {
        }
    }
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
    public void update() {
        if(gp.player.nivel == 1){
            loadMap("/maps/worldMap.txt",0);
        }
        if(gp.player.nivel == 2) {
            loadMap("/maps/worldMap2.txt",0);
        }
        if(gp.player.nivel == 3) {
            loadMap("/maps/worldMap3.txt",0);
        }
    }
}
