public class Shd implements Guard {
    public boolean fight(Adventurer adventurer) {
        return adventurer.getComprehensiveCE() > 1000;
    }

    public String getType() {
        return "Shd";
    }
}
