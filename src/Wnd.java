public class Wnd implements Guard {
    public boolean fight(Adventurer adventurer) {
        return adventurer.getComprehensiveCE() > 4000;
    }

    public String getType() {
        return "Wnd";
    }
}
