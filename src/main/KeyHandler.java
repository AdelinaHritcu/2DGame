package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
    GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp=gp;
    }

    boolean showDebugtext = false;
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // TITLE STATE
        if(gp.gameState == gp.titleState){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;

                }
            }
            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    //gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1){
                    gp.gameState = gp.loadGame;
                }
                if(gp.ui.commandNum == 2){
                    System.exit(0);
                }



                if(code == KeyEvent.VK_T){
                    if (showDebugtext == false) {
                        showDebugtext = true;
                    }
                    else {
                        showDebugtext = false;
                    }
                }

            }
        }
        if(gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_O) {
                gp.save.insertData(gp.player.hasFood, gp.player.life, gp.player.nivel, gp.player.worldX/ gp.tileSize, gp.player.worldY/ gp.tileSize);
                System.out.println("Score saved to database");
            }
        }
        // PLAY STATE
        if(gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_SPACE) {
                spacePressed = true;
            }
            if (code == KeyEvent.VK_P) {
                    gp.gameState = gp.pauseState;
            }
            // DEBUG
            if(code == KeyEvent.VK_T){
                if (showDebugtext == false) {
                    showDebugtext = true;
                }
                else {
                    showDebugtext = false;
                }
            }
        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
        // GAME OVER STATE
        if(gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
    }
    public void gameOverState(int code) {

        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum =1;
            }
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
               gp.gameState = gp.playState;
               gp.retry();
            }
            else if(gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.restart();;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed =  false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }

    }
}
