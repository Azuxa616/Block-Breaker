import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

public class BlockGridPanel {
    private int rows;
    private int cols;
    private int blockSize;
    private Block[][] blocks;

    private int panel_X;
    private int panel_Y;


    public BlockGridPanel(int rows, int cols, int blockSize, int panelWidth) {
        this.rows = rows;
        this.cols = cols;
        this.blockSize = blockSize;
        this.blocks = new Block[rows][cols];
        initBlocks();
        this.panel_X = (panelWidth - cols * blockSize) / 2;
        this.panel_Y = 100;

    }

    private void initBlocks() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int random = (int)(Math.random()*10)+1;
                if(random>9){
                    continue;
                }else{
                    blocks[row][col] = new Block(row, col, blockSize, true);
                }
                
            }
        }
    }

    public void addBlock(int row, int col, Block block) {
        blocks[row][col] = block;
    }

    public void removeBlock(int row, int col) {
        blocks[row][col] = null;
    }

    

    public void draw(Graphics g) {
       
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (blocks[row][col] != null&&!blocks[row][col].isDestroyed()) {
                    blocks[row][col].draw(g, panel_X, panel_Y);
                }
            }
        }
        g.setColor(java.awt.Color.WHITE);
        int gridWidth = cols * blockSize;
        int gridHeight = rows * blockSize;
        g.drawRect(panel_X, panel_Y, gridWidth, gridHeight);
    }
    public List<Block> getBlocks() {
        List<Block> blocksList = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (blocks[row][col] != null) {
                    blocksList.add(blocks[row][col]);
                }
            }
        }
        return blocksList;
    }
    
    public int getPanel_X() {
        return panel_X;
    }
    public int getPanel_Y() {
        return panel_Y;
    }
}
