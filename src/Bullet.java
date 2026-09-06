public class Bullet {
    int x;
    int y;

    int width = 10;
    int height = 15;

    int speed = 10;

    public Bullet() {
        
    }
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        this.y -= speed;
    }
}