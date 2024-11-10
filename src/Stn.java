public class Stn implements Guard {
    public boolean fight(Adventurer adventurer) {
        return adventurer.getComprehensiveCE() > 3000;
    }

    public String getType() {
        return "Stn";
    }
}
