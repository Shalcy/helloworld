public class ShdTreasure implements Treasure {
    @Override
    public void showInfo() {
        System.out.println("Cloak of Shadows");
    }

    @Override
    public void useBy(Adventurer adv) {
        adv.useTreasure(this);// TODO : 实现你的逻辑
    }
}
