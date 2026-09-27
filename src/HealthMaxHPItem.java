public class HealthMaxHPItem extends Item {

    public HealthMaxHPItem(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
        y += speed;
    }

    @Override
    public int applyEffect() {
        return 0;
    }
}