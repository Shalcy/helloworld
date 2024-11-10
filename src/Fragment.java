public class Fragment {
    private final int id;
    private final String name;

    public Fragment(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Fragment f) {
        return id == f.id && name.equals(f.name);
    }
}
