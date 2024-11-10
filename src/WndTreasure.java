public class WndTreasure implements Treasure {
    @Override
    public void showInfo() {
        System.out.println("Windrunner Boots");
    }

    @Override
    public void useBy(Adventurer adv) {
        adv.useTreasure(this);
    }
}
