public class Equipment implements Item {
    private final int id;
    private final String name;
    private int durability;
    private final int ce;
    private final String type;
    private final Adventurer adventurer;

    public Equipment(Adventurer adventurer, int id, String name,
                     int durability, String type, int ce) {
        this.adventurer = adventurer;
        this.id = id;
        this.name = name;
        this.durability = durability;
        this.ce = ce;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getDurability() {
        return this.durability;
    }

    public void equipmentAddDurability() {
        this.durability += 1;
    }

    public void equipmentSubDurability() {
        this.durability -= 1;
        if (this.durability == 0) {
            adventurer.breakEq(this);
        }
    }

    public int getCe() {
        return this.ce;
    }

    public String toString() {
        return type + " " + name + " " + durability;
    }

    public void transfer(Adventurer adventurer) {
        adventurer.addItem(this);
    }

    public boolean equals(Equipment equipment) {
        return this.id == equipment.getId() && this.name.equals(equipment.getName())
                && this.durability == equipment.getDurability() && this.ce == equipment.getCe();
    }
}
