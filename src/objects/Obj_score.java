package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Obj_score extends Entity {
    public BufferedImage image;
    public String name;

    public Obj_score(GamePanel gp) {
        super(gp);
        name="score";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/score.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }



}
