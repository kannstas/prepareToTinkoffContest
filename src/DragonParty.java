import java.util.*;

public class DragonParty {
    public static void main(String[] args) throws NoSuchFieldException {

        Scanner in = new Scanner(System.in);
        String[] params = in.nextLine().split(" ");
        int numberOfFriends = Integer.parseInt(params[0]);
        int couplesNumber = Integer.parseInt(params[1]);
        int maxGluttonySum = Integer.parseInt(params[2]);

        String[] interestingArray = in.nextLine().split(" ");
        String[] gluttonyArray = in.nextLine().split(" ");

        final Map<Integer, Dragon> dragonIdToDragonMap = new HashMap<>();
        final List<DragonPair> dragonPairs = new ArrayList<>();

        for (int i = 0; i < numberOfFriends; i++) {
            dragonIdToDragonMap.put(
                    i + 1,
                    new Dragon(
                            i + 1,
                            Integer.parseInt(interestingArray[i]),
                            Integer.parseInt(gluttonyArray[i])
                    ));
        }

        for (int i = 1; i <= couplesNumber; i++) {
            List<Integer> dragonIdPair = Arrays.stream(in.nextLine().split(" "))
                    .map(Integer::parseInt)
                    .sorted(Comparator.reverseOrder()).toList();

            dragonPairs.add(new DragonPair(
                    dragonIdToDragonMap.get(dragonIdPair.get(0)),
                    dragonIdToDragonMap.get(dragonIdPair.get(1))));
        }


        // TODO надо пройтись по списку dragonPairs рекурсивно.
        //  Для каждого участника пары, требуется найти все прочие упоминания этого участника в других парах.
        //  (в каждом таком упоминании будет один новый участник)
        //  Затем таким же образом нужно найти упоминания для новых участников, пропуская повторы.
        //  Повторять это до тех пока не закончат встречаться новые участники.
        //  Весь список собранных таким образом драконов - будет первым деревом связанных друзей.
        //  Затем нужно будет сделать тоже самое для всех прочих пар, которые не попали в предыдущие выборки.

        func(dragonPairs, couplesNumber);

        System.out.println("asa");

    }

    public static int func(List<DragonPair> dragonPairList, int couples) throws NoSuchFieldException {

        DragonTree dragonTree = new DragonTree(new HashSet<>());
        Map<Integer, Set<Dragon>> dragonIdToDragonSetMap = new HashMap<>();

        for (int i = 0; i < dragonPairList.size(); i++) {

            DragonPair firstDragonPair = dragonPairList.get(i);
            Set<Dragon> currentSet = new HashSet<>();

            for (int j = i + 1; j < dragonPairList.size(); j++) {
                DragonPair secondDragonPair = dragonPairList.get(j);

                if (firstDragonPair.intersects(secondDragonPair)) {
                    if (firstDragonPair.getLeft() == secondDragonPair.getLeft()) {
                        currentSet.add(firstDragonPair.getRight());
                        currentSet.add(secondDragonPair.getRight());
                        dragonIdToDragonSetMap.put(
                                firstDragonPair.getLeft().getId(),
                                currentSet);

                    } else {
                        currentSet.add(firstDragonPair.getLeft());
                        currentSet.add(secondDragonPair.getLeft());
                        dragonIdToDragonSetMap.put(
                                firstDragonPair.getRight().getId(),
                                currentSet);
                    }

                }

//                System.out.println(currentSet);

//                dragonIdToDragonSetMap.put(
//                        firstDragonPair.getLeft().getId(),
//                        currentSet
//                );
//
//                dragonIdToDragonSetMap.put(
//                        firstDragonPair.getRight().getId(),
//                        currentSet
//                );


            }


        }
        // TODO сделать так, чтобы цифра игнорировала сама себя.
        //  И чтобы видела своего соседа и тоже записывала его в хеш сет
        //   сделать так, чтобы оно не повторялось


        System.out.println(dragonIdToDragonSetMap);


        return 0;
    }
}

class DragonTree {
    private final Set<Dragon> dragons; // опять создать мапу с ключом id и значением сет драгонс

    DragonTree(Set<Dragon> dragons) {
        this.dragons = dragons;
    }

    public Set<Dragon> getDragons() {
        return dragons;
    }
}


class DragonPair {
    private final Dragon left;
    private final Dragon right;

    public DragonPair(Dragon left, Dragon right) {
        this.left = left;
        this.right = right;
    }

    public Dragon getLeft() {
        return left;
    }

    public Dragon getRight() {
        return right;
    }

    public boolean intersects(DragonPair other) {
        if (this.left != null && this.right != null && other.left != null && other.right != null) {
            return this.left == other.left || this.left == other.right ||
                    this.right == other.left || this.right == other.right;
        }
        return false;

    }
}

class Dragon {
    private final int id;
    private final int interesting;
    private final int gluttony;

    public Dragon(int id, int interesting, int gluttony) {
        this.id = id;
        this.interesting = interesting;
        this.gluttony = gluttony;
    }

    public int getId() {
        return id;
    }

    public int getInteresting() {
        return interesting;
    }

    public int getGluttony() {
        return gluttony;
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "id=" + id +
                ", interesting=" + interesting +
                ", gluttony=" + gluttony +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragon = (Dragon) o;
        return id == dragon.id && interesting == dragon.interesting && gluttony == dragon.gluttony;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, interesting, gluttony);
    }
}