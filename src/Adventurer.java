import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class Adventurer {
    private final int id;
    private final String name;
    private final HashMap<String, List<Bottle>> bottles;
    private final HashMap<String,List<Bottle>> takenBottles;
    private final HashMap<String, List<Equipment>> equipments;
    private final HashMap<String, Equipment> takenEquipments;
    private final HashMap<Integer, Item> items;
    private final HashMap<String, List<Fragment>> fragments;
    private int atk;
    private int def;
    private int hp;
    private final HashMap<Integer, Adventurer> allAdventurers;
    private final HashMap<Adventurer, Integer> employees;
    private static final ArrayList<Guard> guards = new ArrayList<>();
    private int hpBeforeBattle;
    private final ArrayList<Treasure> treasures;
    private final TreasureFactory treasureFactory;

    public Adventurer(HashMap<Integer, Adventurer> adventurers, int id, String name) {
        this.allAdventurers = adventurers;
        this.id = id;
        this.name = name;
        this.bottles = new HashMap<>();
        this.equipments = new HashMap<>();
        this.takenEquipments = new HashMap<>();
        this.takenBottles = new HashMap<>();
        this.items = new HashMap<>();
        this.fragments = new HashMap<>();
        this.employees = new HashMap<>();
        this.treasures = new ArrayList<>();
        this.treasureFactory = new TreasureFactory();
        this.atk = 1;
        this.def = 0;
        this.hp = 500;
        this.hpBeforeBattle = this.hp;
    }

    public static void createGuard() {
        guards.add(new Shd());
        guards.add(new Flm());
        guards.add(new Stn());
        guards.add(new Wnd());
        guards.add(new Frz());
    }

    public HashMap<Integer, Adventurer> getAllAdventurers() {
        return allAdventurers;
    }

    public TreasureFactory getTreasureFactory() {
        return treasureFactory;
    }

    public int getHpBoreBattle() {
        return hpBeforeBattle;
    }

    public static ArrayList<Guard> getGuards() {
        return guards;
    }

    public HashMap<String, List<Bottle>> getTakenBottles() {
        return takenBottles;
    }

    public HashMap<String, Equipment> getTakenEquipments() {
        return this.takenEquipments;
    }

    public HashMap<Integer, Item> getItems() {
        return this.items;
    }

    public HashMap<String, List<Fragment>> getFragments() {
        return fragments;
    }

    public HashMap<String, List<Equipment>> getEquipments() {
        return equipments;
    }

    public HashMap<String, List<Bottle>> getBottles() {
        return bottles;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return this.atk;
    }

    public int getRealAtk(String eqName) {
        return this.atk + takenEquipments.get(eqName).getCe();
    }

    public void setHpBeforeBattle() {
        this.hpBeforeBattle = this.hp;
    }

    public int getDef() {
        return this.def;
    }

    public int getCe() {
        return this.atk + this.def;
    }

    public int getMaxBottles() {
        return (this.getAtk() + this.getDef()) / 5 + 1;
    }

    public int getComprehensiveCE() {
        int btlCE = takenBottles.values().stream()
                .flatMap(List::stream)
                .mapToInt(Bottle::getCe)
                .sum();
        int eqCE = takenEquipments.values().stream()
                .mapToInt(Equipment::getCe)
                .sum();
        int emCE = employees.keySet().stream()
                .mapToInt(Adventurer::getCe)
                .sum();
        return this.atk + this.def + btlCE + eqCE + emCE;
    }

    private int getHpBeforeBattle() {
        return this.hpBeforeBattle;
    }

    public HashMap<Adventurer, Integer> getEmployees() {
        return this.employees;
    }

    public int[] getEmployeeIds() {
        return employees.keySet().stream()
                .mapToInt(Adventurer::getId)
                .toArray();
    }

    public void addFragment(int id, String name) {
        this.fragments.computeIfAbsent(name, k -> new ArrayList<>()).add(new Fragment(id, name));
    }

    public void addDurability(int equipmentID) {
        Equipment targetEquipment = (Equipment) items.get(equipmentID);
        if (targetEquipment != null) {
            targetEquipment.equipmentAddDurability();
            System.out.println(targetEquipment.getName() + " " + targetEquipment.getDurability());
        }

    }

    public void addItem(int sign, int id, String name, int property, String type,int ce) {
        if (sign == 2) {
            Bottle newBtl = new Bottle(this, id, name, property, type, ce);
            this.bottles.computeIfAbsent(newBtl.getName(), k -> new ArrayList<>()).add(newBtl);
            this.items.put(id, newBtl);
        } else {
            Equipment newEq = new Equipment(this, id, name, property, type, ce);
            this.equipments.computeIfAbsent(name, k -> new ArrayList<>()).add(newEq);
            this.items.put(id, newEq);
        }
    }

    public void addItem(Item item) {
        if (item instanceof Bottle) {
            Bottle newBtl = (Bottle) item;
            this.bottles.computeIfAbsent(newBtl.getName(), k -> new ArrayList<>()).add(newBtl);
            this.items.put(item.getId(), newBtl);
        } else if (item instanceof Equipment) {
            Equipment newEq = (Equipment) item;
            this.equipments.computeIfAbsent(name, k -> new ArrayList<>()).add(newEq);
            this.items.put(item.getId(), newEq);
        }
    }

    public void removeItem(int id) {
        Item itemToRemove = items.get(id);
        if (itemToRemove != null) {
            items.remove(id);
            if (itemToRemove instanceof Bottle) {
                Bottle b = (Bottle) itemToRemove;
                switch (b.getType()) {
                    case "HpBottle" :
                        System.out.println(
                                    "HpBottle" + " " + b.getName() + " " + b.getCapacity());
                        break;
                    case "AtkBottle" :
                        System.out.println(
                                    "AtkBottle" + " " + b.getName() + " " + b.getCapacity());
                        break;
                    case "DefBottle" :
                        System.out.println(
                                    "DefBottle" + " " + b.getName() + " " + b.getCapacity());
                        break;
                    default :
                        break;
                }
                bottles.entrySet().removeIf(entry -> {
                    entry.getValue().removeIf(bottle -> bottle.equals(b));
                    return entry.getValue().isEmpty();
                });
                takenBottles.entrySet().removeIf(entry -> {
                    entry.getValue().removeIf(bottle -> bottle.equals(b));
                    return entry.getValue().isEmpty();
                });
            } else if (itemToRemove instanceof Equipment) {
                Equipment eq = (Equipment) itemToRemove;
                System.out.println("Equipment" + " " + eq.getName() + " " + eq.getDurability());
                equipments.entrySet().removeIf(entry -> {
                    entry.getValue().removeIf(equipment -> equipment.equals(eq));
                    return entry.getValue().isEmpty();
                });
                takenEquipments.entrySet().removeIf(entry -> entry.getValue().equals(eq));

            }
        }
    }

    public void breakEq(Equipment eq) {
        equipments.entrySet().removeIf(entry -> {
            entry.getValue().removeIf(equipment -> equipment.equals(eq));
            return entry.getValue().isEmpty();
        });
        takenEquipments.entrySet().removeIf(entry -> entry.getValue().equals(eq));
    }

    public void takeItem(int id) {
        Item itemToTake = items.get(id);
        if (itemToTake != null) {
            if (itemToTake instanceof Bottle) {
                Bottle b = (Bottle) itemToTake;
                b.take();
                takenBottles.computeIfAbsent(b.getName(), k -> new ArrayList<>()).add(b);
                if (takenBottles.get(b.getName()).size() > getMaxBottles()) {
                    takenBottles.get(b.getName()).remove(b);
                }
            } else if (itemToTake instanceof Equipment) {
                Equipment eq = (Equipment) itemToTake;
                takenEquipments.put(eq.getName(), eq);
            }
        }
    }

    public void useBottle(int id) {
        Bottle bottle = (Bottle) items.get(id);
        if (takenBottles.get(bottle.getName()).contains(bottle)) {
            switch (bottle.getType()) {
                case "HpBottle" :
                    this.hp += bottle.getCapacity();
                    break;
                case "AtkBottle" :
                    this.atk += bottle.getCe() + bottle.getCapacity() / 100;
                    break;
                case "DefBottle" :
                    this.def += bottle.getCe() + bottle.getCapacity() / 100;
                    break;
                default :
                    break;
            }
            removeItem(id);
            System.out.println(this);
        } else {
            System.out.println(this.getName() + " fail to use " + bottle.getName());
        }
    }

    public void useTreasure(Treasure treasure) {
        if (treasure instanceof ShdTreasure) {
            this.def += 40;
            for (Adventurer adventurer : employees.keySet()) {
                adventurer.def += 40;
            }
        } else if (treasure instanceof FlmTreasure) {
            this.atk += 40;
            for (Adventurer adventurer : employees.keySet()) {
                adventurer.atk += 40;
            }
        } else if (treasure instanceof StnTreasure) {
            this.def += 40;
            for (Adventurer adventurer : employees.keySet()) {
                adventurer.def += 40;
            }
        } else if (treasure instanceof WndTreasure) {
            this.def += 30;
            for (Adventurer adventurer : employees.keySet()) {
                adventurer.def += 30;
            }
        } else if (treasure instanceof FrzTreasure) {
            this.atk += 50;
            for (Adventurer adventurer : employees.keySet()) {
                adventurer.atk += 50;
            }
        }
    }

    public void redeem(String name, int welfareId) {
        Item itemToRedeem = items.get(welfareId);
        if (fragments.get(name) != null) {
            if (fragments.get(name).size() >= 5) {
                if (itemToRedeem != null) {
                    if (itemToRedeem instanceof Bottle) {
                        Bottle b = (Bottle) itemToRedeem;
                        if (b.ifEmpty()) {
                            b.restock();
                            System.out.println(b.getName() + " " + b.getCapacity());
                        }
                    } else if (itemToRedeem instanceof Equipment) {
                        Equipment eq = (Equipment) itemToRedeem;
                        eq.equipmentAddDurability();
                        System.out.println(eq.getName() + " " + eq.getDurability());
                    }
                } else {
                    bottles.computeIfAbsent(name, k -> new ArrayList<>())
                            .add(new Bottle(this, welfareId, name, 100, "HpBottle", 0));
                    System.out.println("Congratulations! HpBottle " + name + " acquired");
                }

                Iterator<Fragment> iterator = fragments.get(name).iterator();
                for (int i = 0; i < 5; i++) {
                    iterator.next();
                    iterator.remove();
                }
            } else {
                System.out.println(fragments.get(name).size()
                        + ": Not enough fragments collected yet");
            }
        } else {
            System.out.println(0
                    + ": Not enough fragments collected yet");
        }
    }

    public void attack(String eqName, ArrayList<Adventurer> targetAdventurers, int sign) {

        int defMax = 0;
        boolean success = false;
        for (Adventurer adventurer : targetAdventurers) {
            adventurer.setHpBeforeBattle();
        }

        for (Adventurer targetAdventurer : targetAdventurers) {
            int defTmp = targetAdventurer.getDef();
            if (defTmp > defMax) {
                defMax = defTmp;
            }
        }
        Equipment atkEq = takenEquipments.get(eqName);
        if (atkEq != null) {
            if (getRealAtk(eqName) > defMax) {
                finishAttacking(atkEq, targetAdventurers, sign);
                success = true;
            }
        }
        if (success) {
            atkEq.equipmentSubDurability();
        }
        if (!success) {
            System.out.println("Adventurer " + getId() + " defeated");
        }
    }

    public void employ(int id) {
        Adventurer adventurer = allAdventurers.get(id);
        employees.putIfAbsent(adventurer, 4);
    }

    private void help(Adventurer adventurer) {
        AtomicBoolean dismiss = new AtomicBoolean(false);
        if (adventurer.getHp() <= adventurer.getHpBeforeBattle() / 2) {
            if (!takenEquipments.isEmpty()) {
                for (Equipment equipment : takenEquipments.values()) {
                    equipment.transfer(adventurer);
                }
                takenEquipments.clear();
            }
            adventurer.getEmployees().compute(this, (key, oldValue) -> {
                int newValue = Optional.ofNullable(oldValue).orElse(0) - 1;
                if (newValue == 0) {
                    dismiss.set(true);
                    return null;
                }
                return newValue;
            });
        }
        if (dismiss.get()) {
            dismissedBy(adventurer);
        }
    }

    public void dismissedBy(Adventurer adventurer) {
        adventurer.getEmployees().remove(this);
    }

    public void challenge() {
        createGuard();
        for (Guard guard : guards) {
            if (guard.fight(this)) {
                Treasure treasure = treasureFactory.createTreasure(guard.getType());
                treasures.add(treasure);
                useTreasure(treasure);
            } else {
                break;
            }
        }
        for (Treasure treasure : treasures) {
            treasure.showInfo();
        }
    }

    private void finishAttacking(Equipment eq, ArrayList<Adventurer> targetAdventurers, int sign) {
        switch (eq.getType()) {
            case "Axe" :
                for (Adventurer targetAdventurer : targetAdventurers) {
                    targetAdventurer.setHp(targetAdventurer.getHp() / 10);
                }
                break;
            case "Sword" :
                for (Adventurer targetAdventurer : targetAdventurers) {
                    int decreaseHp = eq.getCe() + this.getAtk() - targetAdventurer.getDef();
                    targetAdventurer.setHp(targetAdventurer.getHp() - decreaseHp);
                }
                break;
            case "Blade" :
                for (Adventurer targetAdventurer : targetAdventurers) {
                    int decreaseHp = eq.getCe() + this.getAtk();
                    targetAdventurer.setHp(targetAdventurer.getHp() - decreaseHp);
                }
                break;
            default :
                break;

        }
        for (Adventurer targetAdventurer : targetAdventurers) {
            for (Adventurer helper : targetAdventurer.getEmployees().keySet()) {
                helper.help(targetAdventurer);
            }
        }
        if (sign == 1) {
            for (Adventurer targetAdventurer : targetAdventurers) {
                System.out.println(targetAdventurer.getName() + " " + targetAdventurer.getHp());
            }
        } else if (sign == 2) {
            switch (eq.getType()) {
                case "Axe" :
                    int decrease = 0;
                    for (Adventurer targetAdventurer : targetAdventurers) {
                        decrease += targetAdventurer.getHpBeforeBattle() - targetAdventurer.getHp();
                    }
                    System.out.println(decrease);
                    break;
                case "Sword" :
                    int a = 0;
                    for (Adventurer targetAdventurer : targetAdventurers) {
                        a += targetAdventurer.getHpBeforeBattle() - targetAdventurer.getHp();
                    }
                    System.out.println(a);
                    break;
                case "Blade" :
                    System.out.println((eq.getCe() + this.getAtk()) * targetAdventurers.size());
                    break;
                default :
                    break;
            }
        }
    }

    public String toString() {
        return name + " " + hp + " " + atk + " " + def;
    }

    public void setAtk(int i) {
        this.atk = i;
    }

    public void setDef(int i) {
        this.def = i;
    }
}
