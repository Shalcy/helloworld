public class FlmTreasure implements Treasure {
    @Override
    public void showInfo() {
        System.out.println("Flamebrand Sword");
    }

    @Override
    public void useBy(Adventurer adv) {
        adv.useTreasure(this);// TODO : 实现你的逻辑
    }
}
