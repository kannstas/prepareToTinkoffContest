import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


        reflectFunc(dragonPairs);


    }

    public static void reflectFunc(List<DragonPair> dragonPairs) {
        Map<Integer, Set<Dragon>> dragonIdToDragonSetMap = new HashMap<>();
        Map<Integer, Set <Dragon>> updateIdToDragonSetWithJoinSetDragonFriendsMap = new HashMap<>();
        DragonTree dragonTree = new DragonTree(new HashSet<>());
        Set <Dragon> test = new HashSet<>();

        for (int i = 0; i < dragonPairs.size(); i++) {
            func(dragonPairs.get(i), dragonPairs, dragonIdToDragonSetMap);
        }

        for (DragonPair dragonPair : dragonPairs) {

            test = dragonIdToDragonSetMap.get(dragonPair.getLeft().getId());
            test.addAll(dragonIdToDragonSetMap.get(dragonPair.getRight().getId()));

            Set<Dragon> newTest = new HashSet<>();
            for (Dragon dragon : test) {
                if (dragonIdToDragonSetMap.containsKey(dragon.getId()))
                    newTest.addAll(dragonIdToDragonSetMap.get(dragon.getId()));
            }
            test.addAll(newTest);



            if (test.contains(dragonPair.getLeft())) {
                Set <Dragon> copySet = new HashSet<>();
                copySet.addAll(test);
                copySet.remove(dragonPair.getLeft());

                updateIdToDragonSetWithJoinSetDragonFriendsMap.put(
                        dragonPair.getLeft().getId(),
                        copySet
                );

            }

            if (test.contains(dragonPair.getRight())){
                Set <Dragon> copySet = new HashSet<>();
                copySet.addAll(test);
                copySet.remove(dragonPair.getRight());

                updateIdToDragonSetWithJoinSetDragonFriendsMap.put(
                        dragonPair.getRight().getId(),
                        copySet
                );



            }


        }

        System.out.println("fpfpfp");
    }




    public static void func(DragonPair dragonPair, List<DragonPair> dragonPairList, Map<Integer, Set<Dragon>> dragonIdToDragonSetMap) {
        Set<Dragon> dragonSet = new HashSet<>();

        for (DragonPair pair : dragonPairList) {
            if (pair.intersects(dragonPair)) {
                dragonSet.add(pair.getLeft());
                dragonSet.add(pair.getRight());
            }
        }

        if (!(dragonIdToDragonSetMap.containsKey(dragonPair.getLeft().getId()))) { // проверки нужны по той причине, что в Map уже может быть такой же ключ,
            // а если не сделать проверку, то он обновит ключ, и выбросит все прошлые значения, которые там лежали
            dragonIdToDragonSetMap.put(
                    dragonPair.getLeft().getId(),
                    checkId(dragonPair.getLeft(), dragonSet)
            );


        }
        if (!(dragonIdToDragonSetMap.containsKey(dragonPair.getRight().getId()))) { // проверки разделены, потому что если использовать ||
            // то повторяющийся ключ все еще может проскочить, если && то из-за повторяющегося ключа не запишется второе значение,
            // которое стоит справа (а его еще может и не быть в Map)
            dragonIdToDragonSetMap.put(
                    dragonPair.getRight().getId(),
                    checkId(dragonPair.getRight(), dragonSet)
            );


        }


        // TODO мб сделать проверку на ключи выше, чтобы если что не так, алгоритм не прокручивался
    }

    public static Set<Dragon> checkId(Dragon dragon, Set<Dragon> dragonSet) {

        if (dragonSet.contains(dragon)) {
            Set<Dragon> updateDragonSet = new HashSet<>();
            updateDragonSet.addAll(dragonSet);
            updateDragonSet.remove(dragon);
            return updateDragonSet;
        }
        return dragonSet;
    }
// TODO сделать так, чтобы цифра игнорировала сама себя. +
//  И чтобы видела своего соседа и тоже записывала его в хеш сет ?
//   сделать так, чтобы оно не повторялось +

}

class DragonTree {
    private final Set<Dragon> dragons; // опять создать мапу с ключом id и значением сет драгонс

    public DragonTree(Set<Dragon> dragons) {
        this.dragons = dragons;
    }

    public Set<Dragon> getDragons() {
        return dragons;
    }

    @Override
    public String toString() {
        return "DragonTree{" +
                "dragons=" + dragons +
                '}';
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

    @Override
    public String toString() {
        return "DragonPair{" +
                "left=" + left +
                ", right=" + right +
                '}';
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


