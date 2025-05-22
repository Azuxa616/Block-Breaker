// 方块类
import java.awt.Color;
import java.awt.Graphics;

public class Block {
    //所在行、列
    private int col;
    private int row;

    private int size; //边长
    private boolean isDestroyed; //是否被摧毁
    private Color color; //颜色
    private int HP; //生命值

    public Block(int row, int col, int size, int hp) {
        this.row = row;
        this.col = col;
        this.size = size;
        this.isDestroyed = false;
        this.color = Color.RED;
        this.HP = hp;
    }

    // 随机生成方块
    public Block(int row, int col, int size, boolean isRandom ) {
        this.row = row;
        this.col = col;
        this.size = size;
        this.isDestroyed = false;
        if(isRandom){
            int hp = (int)(Math.random()*10)+1;
            this.color = setColor(hp);
            this.HP = hp;
        }else{
            this.color = Color.RED;
            this.HP = 10;
        }
    }

    // 根据HP设置颜色
    private Color setColor(int hp) {
        if (hp >= 10) {
            return Color.RED;
        } else if (hp >= 5) {
            return Color.ORANGE;
        } else if (hp > 3) {
            return Color.YELLOW;
        } else {
            return Color.GRAY;
        }
    }

    public void draw(Graphics g, int panel_X, int panel_Y) {
        if(isDestroyed){
            return;
        }
        // 绘制方块
        g.setColor(color);
        g.fillRect(panel_X + col * size, panel_Y + row * size, size, size);
        // 绘制边框
        g.setColor(Color.WHITE);
        g.drawRect(panel_X + col * size, panel_Y + row * size, size, size);
        // 绘制HP数字
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(HP), panel_X + col * size + size/2 - 5, panel_Y + row * size + size/2 + 5);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public int getSize() {
        return size;
    }

    // 被击中
    public int HitHappen(){
        HP--;
        if(HP<=0){
            isDestroyed = true;
        }
        return HP;
    }

    public int getHP() {
        return HP;
    }
}