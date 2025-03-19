package main;
import entity.Entity;
import objects.Obj_heart;
import objects.Obj_score;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

public class UI {
    public boolean gameFinished = false;
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage heart_full, heart_half, heart_empty;
    BufferedImage scoreImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    Graphics2D g2;
    public boolean gameIsOver = false;

    public int commandNum = 0;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Times New Roman", Font.BOLD, 40);
        arial_80B = new Font("Times New Roman", Font.BOLD, 80);
        Obj_score score = new Obj_score(gp);
        scoreImage = score.image;

        // CREATE HUD OBJECT
        Entity heart = new Obj_heart(gp);
        heart_full = heart.image;
        heart_empty = heart.image2;
        heart_half = heart.image3;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void drawPauseScreen(){
        String text ="PAUSE";
        int x= getXforCenterText(text);
        int y= gp.screenHeight/2;
        g2.drawString(text,x,y);
    }
    public int getXforCenterText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2-length/2;
        return x;
    }
    public void drawTitleScreen() {
        //g2.setColor(new Color(120,0,250));
        //g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        BufferedImage image;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/fundal.jpeg"));
            g2.drawImage(image, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } catch(IOException e) {
            e.printStackTrace();
        }

        String text = "NEW GAME";
        int x = getXforCenterText(text);
        int y = gp.tileSize*3;
        y += gp.tileSize*2.5;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.setColor(Color.black);
            g2.drawString(">", x-gp.tileSize,y);
        }


        text = "LOAD GAME";
        x = getXforCenterText(text);
        y += gp.tileSize;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize,y);
        }

        text = "QUIT";
        x = getXforCenterText(text);
        y += gp.tileSize;
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize,y);
        }
    }
    public void draw (Graphics2D g2){

        this.g2=g2;
        g2.setFont(arial_40);
        g2.setColor(Color.yellow);
        // TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        // PLAY STATE
        if(gp.gameState == gp.playState){
            drawPlayerLife();
        }
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        // GAME OVER STATE
        if(gp.gameState  == gp.gameOverState) {
            drawGameOverScreen();
        }

        if(gp.gameState == gp.loadGame){
            drawLoadGame();
        }
        if(gp.gameState == gp.finishedGame){
            drawFinishedGame();
        }
        if(gp.gameState == gp.finishedGame &&  gameIsOver == false){
          //  gp.endGame();
            gameIsOver=true;
        }
        if(gameFinished) {

            g2.setFont(arial_40);
            g2.setColor(Color.blue);

            String text;
            int textLength,x,y;

            text = "YOU WIN!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text,x,y);

            g2.setFont(arial_80B);
            g2.setColor(Color.cyan);
            text = "Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text,x,y);

            g2.setFont(arial_40);
            g2.setColor(Color.blue);
            text = "Your score: " + gp.player.hasScore;
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*4);
            g2.drawString(text,x,y);

            gp.gameThread = null;
        }
        else{
            g2.setFont(arial_40);
            g2.setColor(Color.black);
            g2.drawImage(scoreImage, gp.tileSize/2-7, gp.tileSize/2,gp.tileSize, gp.tileSize,null);
            g2.drawString(": " + gp.player.hasFood, 88, 78);

            //MESSAGE
            if(messageOn){
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);

                messageCounter++;

                if(messageCounter > 120){
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }

    public void drawFinishedGame() {
        String text;
        int textLength,x,y;

        text = "YOU WIN!";
        textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        x = gp.screenWidth/2 - textLength/2;
        y = gp.screenHeight/2 - (gp.tileSize*3);
        g2.drawString(text,x,y);

        g2.setFont(arial_80B);
        g2.setColor(Color.cyan);
        text = "Congratulations!";
        textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        x = gp.screenWidth/2 - textLength/2;
        y = gp.screenHeight/2 + (gp.tileSize*2);
        g2.drawString(text,x,y);

        g2.setFont(arial_40);
        g2.setColor(Color.blue);
        text = "Your score: " + gp.player.hasScore;
        textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        x = gp.screenWidth/2 - textLength/2;
        y = gp.screenHeight/2 + (gp.tileSize*4);
        g2.drawString(text,x,y);
        if (!gameIsOver) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introdu numele: ");
            String nume = scanner.nextLine();
            gp.scor.insertData(nume, gp.player.hasScore);
            System.out.println("Score saved to database");

            // Afișează conținutul bazei de date
            gp.showDatabaseContents();

            gameIsOver = true;
        }
        gp.gameThread = null;
    }
    public void drawLoadGame(){
        gp.getDateDB();
       // gp.player.restart0();
        gp.gameState = gp.playState;
        gp.player.nivel=gp.ni;
        if(gp.player.nivel == 2) {
            gp.currentMap++;
        }
        gp.eHandler.checkEvent();
        gp.player.worldX =gp.ix;
        gp.player.worldY=gp.iy;
       //gp.player.worldX =10*gp.tileSize;
       //gp.player.worldY =26*gp.tileSize;
        gp.player.hasFood =gp.s;
        gp.player.life=gp.h;
        gp.tileM.update();
    }
    public void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        int x, y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        text = "Game Over";
        // SHADOW
        g2.setColor(Color.black);
        x = getXforCenterText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        // MAIN
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        // RETRY
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenterText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-40, y);
        }

        // Back to the title screen
        text = "Quit";
        x = getXforCenterText(text);
        y += 70;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-40, y);
        }
    }
    public void drawPlayerLife() {
        //gp.player.life = 5;
        //
        int x = gp.getWidth() - gp.tileSize*2;
        int y = gp.tileSize-6;
        int i=0;
        // DRAW MAX LIFE
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_empty,x,y,null);
            i++;
            x -= gp.tileSize;
        }
        //
        x = gp.getWidth() - gp.tileSize*2;
        y = gp.tileSize-6;
        i=0;
        // DRAW CURRENT LIFE
        while(i<gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x -= gp.tileSize;
        }
    }
}
