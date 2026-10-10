public class HealthItem extends Item {

    public HealthItem(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
        y += speed;
    }

    @Override
    public int applyEffect() {
        // เพิ่ม HP
        return 2;
    }
}