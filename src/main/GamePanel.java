package main;

import AI.Pathfinder;
import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 23; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48*48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 11;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    // WORLD SETTINGS
    public final int maxWorldCol = 47;
    public final int maxWorldRow = 36;
    public final int maxMap = 10;
    public int currentMap = 0;
    public final int gameOverState = 3;
    public final int loadGame = 4;
    public Save save;
    public final int finishedGame =5;

    public int s, h, ix,iy,ni;

    public Pathfinder pFinder = new Pathfinder(this);

    public CollisionChecker cChecker = new CollisionChecker(this);
    // FPS
    int FPS = 60;
    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cCheker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    public Player player = new Player(this, keyH);
    public UI ui = new UI(this);
    // ENTITY AND OBJECT
    public Entity obj[][] = new Entity[maxMap][50];
    public Entity[][] monster = new Entity[maxMap][20];
    ArrayList <Entity> entityList = new ArrayList<>();
    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState =1;
    public final int pauseState=2;
    public Score scor;
    private int score=100;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        save = new Save();
        save.bazaDeDate();
        scor = new Score();
        scor.bazaDeDate();
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void setupGame() {
        aSetter.setObject();
        aSetter.setMonster();
        playMusic(0);
        gameState = playState;
        gameState = titleState;
    }
    public void retry() {
        player.setDefaultPositions();;
        player.restoreLife();
        aSetter.setMonster();
        aSetter.setObject();
    }
    public void restart() {
        player.setDefaultPositions();
        aSetter.setObject();
        player.setDefaultPositions();;
        player.restoreLife();
        aSetter.setMonster();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // 0.1666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update(){
        if(gameState == playState) {
            player.update();

            for (int i=0; i<monster[1].length; i++){
                if(monster[currentMap][i] != null) {
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying)
                          monster[currentMap][i].updateMonster();
                    if(!monster[currentMap][i].alive)
                        monster[currentMap][i] = null;
                }
            }
        }
        if(gameState == pauseState){
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        // DEBUG
        long drawStart = 0;
        if(keyH.showDebugtext) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        // OTHERS
        else{

            // TILE
            tileM.draw(g2);

            // ADD ENTITIES TO THE LIST
            entityList.add(player);
            for(int i = 0; i<obj[1].length;i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }
            for(int i = 0; i<monster[1].length;i++){
                if(monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]);
                }
            }
            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                        @Override
                        public int compare(Entity e1, Entity e2) {
                            int result = Integer.compare(e1.worldY, e2.worldY);
                            return result;
                        }
            });
            // DRAW ENTITIES
            for(int i=0; i< entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            // EMPTY ENTITY LIST
            entityList.clear();
            //UI
            ui.draw(g2);

            //DEBUG
            if(keyH.showDebugtext) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial", Font.PLAIN,20));
                g2.setColor(Color.white);

                int x = 10;
                int y = 400;
                int lineHeight = 20;

                g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
                g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
                g2.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
                g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;

                g2.drawString("Draw Time: " + passed, x, y);
            }
        }
        g2.dispose();
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSE (int i){
        se.setFile(i);
        se.play();
    }
    public void getDateDB() {
        List<String[]> data = Save.getData();

        // Check if there's data to avoid ArrayIndexOutOfBoundsException
        if (data.isEmpty()) {
            System.out.println("No data available in the database.");
            return;
        }
        // Assuming you want to get the last entry in the list
        String[] lastEntry = data.get(data.size() - 1);
        System.out.println("Last entry: " + Arrays.toString(lastEntry)); // Log the last entry

        if (lastEntry.length != 5) {
            System.out.println("Data entry does not have the expected number of columns.");
            return;
        }
        s = Integer.parseInt(lastEntry[0]);
        h = Integer.parseInt(lastEntry[1]);
        ni = Integer.parseInt(lastEntry[2]);
        ix = Integer.parseInt(lastEntry[3]);
        iy = Integer.parseInt(lastEntry[4]);
    }
/*
    public void endGame() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(50, 50));

        JButton button = new JButton("Introdu numele");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nume = textField.getText();
                scor.insertData(nume, score);
                System.out.println("Score saved to database");
            }
        });

        // Setează layout-ul Border pentru a poziționa componentele în partea de jos a panoului
        this.setLayout(new BorderLayout());

        // Adaugă componentele în partea de jos a panoului
        this.add(textField, BorderLayout.SOUTH);
        this.add(button, BorderLayout.SOUTH);

        // Asigură că panoul este redesenat pentru a afișa noile componente
        this.revalidate();
        this.repaint();
    }

*/
    public void showDatabaseContents() {
        List<String[]> data = Score.getData();

        // Column names for the table
        String[] columnNames = {"Nume", "Scor"};

        // Convert list to a 2D array for JTable
        String[][] dataArray = new String[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i);
        }

        // Create the table with the data
        JTable table = new JTable(dataArray, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Create a new panel to hold the table
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create a new frame to display the panel
        JFrame frame = new JFrame("Database Contents");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 700);
        frame.add(panel);
        frame.setVisible(true);
    }


}
