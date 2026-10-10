public class DamageItem extends Item {

    public DamageItem(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
        y += speed;
    }

    @Override
    public int applyEffect() {
        return 3;
    }
}