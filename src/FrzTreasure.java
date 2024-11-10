public class FrzTreasure implements Treasure {
    @Override
    public void showInfo() {
        System.out.println("Frostbite Staff");
    }

    @Override
    public void useBy(Adventurer adv) {
        adv.useTreasure(this);// TODO : 实现你的逻辑
    }
}
