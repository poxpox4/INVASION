public class BossBullet {
    int x;
    int y;

    int width = 8;
    int height = 15;

    int speed = 5;

    public BossBullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += speed;
    }
}
