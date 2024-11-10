public class StnTreasure implements Treasure {
    @Override
    public void showInfo() {
        System.out.println("Stoneheart Amulet");
    }

    @Override
    public void useBy(Adventurer adv) {
        adv.useTreasure(this);
    }
}
