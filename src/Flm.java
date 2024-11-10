public class Flm implements Guard {
    public boolean fight(Adventurer adventurer) {
        return adventurer.getComprehensiveCE() > 2000;
    }

    public String getType() {
        return "Flm";
    }
}
