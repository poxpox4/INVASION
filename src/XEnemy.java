public class XEnemy extends Enemy {

    private int direction;

    public XEnemy(int x, int y, int direction) {
        super(x, y);
        this.direction = direction;
    }

    @Override
    public void move() {
        y += speed;
        x += direction * speed;
    }
}