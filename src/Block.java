import java.awt.Color;
import java.awt.Graphics;

public class Block {
    private int col;
    private int row;
    private int size;
    private boolean isDestroyed;
    private Color color;
    private int HP;

    public Block(int row, int col, int size, int hp) {
        this.row = row;
        this.col = col;
        this.size = size;
        this.isDestroyed = false;
        this.color = Color.RED;
        this.HP = hp;
    }

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
        g.setColor(color);
        g.fillRect(panel_X + col * size, panel_Y + row * size, size, size);
        g.setColor(Color.WHITE);
        g.drawRect(panel_X + col * size, panel_Y + row * size, size, size);
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