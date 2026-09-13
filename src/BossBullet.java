public class BossBullet {
    int x;
    int y;

    int width = 8;
    int height = 15;

    int speed = 5;
    int direction;

    public BossBullet(int x, int y,int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void move() {
        x += direction*2;
        y += speed;

    }
}
