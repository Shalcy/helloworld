public class Frz implements Guard {
    public boolean fight(Adventurer adventurer) {
        return adventurer.getComprehensiveCE() > 5000;
    }

    public String getType() {
        return "Frz";
    }
}
