public class Boss {
    int x;
    int y;

    int width = 150;
    int height = 100;

    int speed = 2;

    int hp = 50;

    public Boss(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {

        if (y < 50) {
            y += speed;
        }
    }

    public void takeDamage(int damage) {
        hp -= damage;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}
