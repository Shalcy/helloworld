public class TreasureFactory {
    public TreasureFactory() {
    }

    public Treasure createTreasure(String guard) {
        switch (guard) {
            case "Shd" :
                return new ShdTreasure();
            case "Flm" :
                return new FlmTreasure();
            case "Stn" :
                return new StnTreasure();
            case "Wnd" :
                return new WndTreasure();
            case "Frz" :
                return new FrzTreasure();
            default :
                return null;
        }
    }
}
