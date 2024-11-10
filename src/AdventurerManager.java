import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class AdventurerManager {
    private int opsNumber;
    private final String[] lines;
    private final HashMap<Integer, Adventurer> adventurers;
    private ArrayList<Adventurer> attackedAdventurers;

    public AdventurerManager() {
        adventurers = new HashMap<>();
        attackedAdventurers = new ArrayList<>();
        lines = new String[999999];
    }

    public void processInput() {
        Scanner scanner = new Scanner(System.in);
        opsNumber = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < opsNumber; i++) {
            this.lines[i] = scanner.nextLine();
        }
        scanner.close();
    }

    public void operate() {
        for (int i = 0; i < opsNumber; i++) {
            String[] op = lines[i].split(" ");
            switch (Integer.parseInt(op[0])) {
                case 1 :
                    addAdventurer(new Adventurer(adventurers, Integer.parseInt(op[1]), op[2]));
                    break;
                case 2 :
                    adventurers.get(Integer.parseInt(op[1])).addItem(2, Integer.parseInt(op[2]),
                            op[3], Integer.parseInt(op[4]), op[5], Integer.parseInt(op[6]));
                    break;
                case 3 :
                    adventurers.get(Integer.parseInt(op[1])).addItem(3, Integer.parseInt(op[2]),
                            op[3], Integer.parseInt(op[4]), op[5], Integer.parseInt(op[6]));
                    break;
                case 4 :
                    adventurers.get(Integer.parseInt(op[1])).addDurability(Integer.parseInt(op[2]));
                    break;
                case 5 :
                    adventurers.get(Integer.parseInt(op[1])).removeItem(Integer.parseInt(op[2]));
                    break;
                case 6 :
                    adventurers.get(Integer.parseInt(op[1])).takeItem(Integer.parseInt(op[2]));
                    break;
                case 7 :
                    adventurers.get(Integer.parseInt(op[1])).useBottle(Integer.parseInt(op[2]));
                    break;
                case 8 :
                    adventurers.get(Integer.parseInt(op[1])).addFragment(Integer.parseInt(op[2]),
                            op[3]);
                    break;
                case 9 :
                    adventurers.get(Integer.parseInt(op[1])).redeem(op[2], Integer.parseInt(op[3]));
                    break;
                case 10 :
                    int[] atkIds = new int[Integer.parseInt(op[4])];
                    int chain;
                    for (int j = 0, k = 5; j < Integer.parseInt(op[4]); j++, k++) {
                        atkIds[j] = Integer.parseInt(op[k]);
                    }
                    if (op[3].equals("chain")) {
                        chainAttack(atkIds);
                        chain = 2;
                    } else {
                        normalAttack(atkIds);
                        chain = 1;
                    }
                    if (!attackedAdventurers.isEmpty()) {
                        adventurers.get(Integer.parseInt(op[1])).attack(op[2],
                                attackedAdventurers, chain);
                    }
                    attackedAdventurers.clear();
                    break;
                case 11 :
                    adventurers.get(Integer.parseInt(op[1])).employ(Integer.parseInt(op[2]));
                    break;
                case 12 :
                    adventurers.get(Integer.parseInt(op[1])).challenge();
                    break;
                default :
                    break;
            }
        }
    }

    private void addAdventurer(Adventurer adventurer) {
        this.adventurers.put(adventurer.getId(), adventurer);
    }

    private void normalAttack(int[] atkIds) {
        for (Integer atkId : atkIds) {
            attackedAdventurers.add(adventurers.get(atkId));
        }
    }

    private void chainAttack(int[] atkIds) {
        recursiveAttack(0, atkIds);
        LinkedHashSet<Adventurer> tmpList = new LinkedHashSet<>(attackedAdventurers);
        attackedAdventurers = new ArrayList<>(tmpList);
    }

    private void recursiveAttack(int depth, int... targetIds) {
        if (depth < 5) {
            for (Integer targetId : targetIds) {
                attackedAdventurers.add(adventurers.get(targetId));
                recursiveAttack(depth + 1, adventurers.get(targetId).getEmployeeIds());
            }
        }
    }

}
