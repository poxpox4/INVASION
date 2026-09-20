public abstract class Item implements Movable{

    protected int x;
    protected int y;
    protected int width = 30;
    protected int height = 30;
    protected int speed = 2;

    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void move();

    public abstract int applyEffect();
}