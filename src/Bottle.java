public class Bottle implements Item {
    private final int id;
    private final String name;
    private final int capacity;
    private final int ce;
    private final String type;
    private boolean empty;
    private boolean taken;

    public Bottle(Adventurer adventurer, int id, String name, int capacity, String type, int ce) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.ce = ce;
        this.type = type;
        this.empty = false;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void restock() {
        this.empty = false;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getId() {
        return id;
    }

    public int getCe() {
        return ce;
    }

    public boolean ifEmpty() {
        return empty;
    }

    @Override
    public String toString() {
        return type + " " + name + " " + capacity;
    }

    public boolean equals(Bottle bottle) {
        return id == bottle.id
                && name.equals(bottle.name)
                && capacity == bottle.capacity
                && ce == bottle.ce
                && type.equals(bottle.type);
    }

    public void take() {
        taken = true;
    }

    public boolean ifTaken() {
        return taken;
    }

}
