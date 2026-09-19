public class NormalEnemy extends Enemy {

    public NormalEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
        y += speed;
    }
}