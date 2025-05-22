// 方块网格类
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

public class BlockGridPanel {
    private int rows; //行数
    private int cols; //列数
    private int blockSize; //方块大小
    private Block[][] blocks; //方块数组

    // 网格起始坐标在左上角
    private int panel_X; //面板X坐标
    private int panel_Y; //面板Y坐标


    public BlockGridPanel(int rows, int cols, int blockSize, int panelWidth) {
        this.rows = rows;
        this.cols = cols;
        this.blockSize = blockSize;
        this.blocks = new Block[rows][cols];
        initBlocks();//构造方块
        this.panel_X = (panelWidth - cols * blockSize) / 2; //设置网格居中
        this.panel_Y = 100;

    }

    private void initBlocks() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                
                // 有10%的几率不生成方块
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
        // 绘制网格边框
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
