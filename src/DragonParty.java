
import java.util.*;


public class DragonParty {

    public static void main(String[] args) {

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

        for (int i = 0; i < couplesNumber; i++) {
            String[] pairRawArray = in.nextLine().split(" ");

            dragonPairs.add(new DragonPair(
                    dragonIdToDragonMap.get(Integer.parseInt(pairRawArray[0])),
                    dragonIdToDragonMap.get(Integer.parseInt(pairRawArray[1]))
            ));

        }


        List<DragonGroup> sortedDragonGroups = findDragonFriends(dragonPairs).stream()
                .map(dragonIds -> toDragonGroup(dragonIds, dragonIdToDragonMap))
                .filter(dragonGroup -> dragonGroup.gluttonySum <= maxGluttonySum)
                .sorted(DESCENDING_COMPARATOR)
                .toList();

        if (sortedDragonGroups.isEmpty()) {
            System.out.println(0);
        }

        int maxInteresting = calculateMaxInterestingRecursively(sortedDragonGroups, maxGluttonySum);
        System.out.println(maxInteresting);
    }

    // самый простой вариант, работает правильно только для малого количества кейсов
    private static int calculateMaxInteresting(final List<DragonGroup> sortedDragonGroups,
                                               int maxGluttonySum) {
        int interestingAccumulator = 0;
        int gluttonyAccumulator = 0;

        for (DragonGroup dragonGroup : sortedDragonGroups) {
            int newGluttony = gluttonyAccumulator + dragonGroup.gluttonySum;

            if (newGluttony == maxGluttonySum) {
                return interestingAccumulator + dragonGroup.interestingSum();
            } else if (newGluttony > maxGluttonySum) {
                continue;
            } else {
                gluttonyAccumulator = newGluttony;
                interestingAccumulator = interestingAccumulator + dragonGroup.interestingSum();
            }
        }

        return interestingAccumulator;
    }

    // for testing
    public static void test(String[] args) {
        ArrayList<DragonGroup> dragonGroups = new ArrayList<>();

        dragonGroups.add(new DragonGroup(10, 100));

        dragonGroups.add(new DragonGroup(3, 29));
        dragonGroups.add(new DragonGroup(3, 29));
        dragonGroups.add(new DragonGroup(3, 29));
        dragonGroups.add(new DragonGroup(3, 29));

        dragonGroups.add(new DragonGroup(2, 1));


//        int result = calculateMaxInteresting(
//                dragonGroups.stream().sorted(DESCENDING_COMPARATOR).toList(), 12
//        );
//        System.out.println("Common result: " + result);

        int resultRecursively = calculateMaxInterestingRecursively(
                dragonGroups.stream().sorted(DESCENDING_COMPARATOR).toList(), 12
        );
        System.out.println("Recursion result: " + resultRecursively);
    }

    private static int calculateMaxInterestingRecursively(final List<DragonGroup> sortedDragonGroups,
                                                          int maxGluttonySum) {
        int maxInteresting = 0;

        for (int i = 0; i < sortedDragonGroups.size(); i++) {
            int possibleMaxInteresting = findAllPossibleOptions(
                    sortedDragonGroups, maxGluttonySum, new HashSet<>(), i, 0, 0
            );

            if (possibleMaxInteresting > maxInteresting) {
                maxInteresting = possibleMaxInteresting;
            }
        }

        return maxInteresting;
    }

    // returns interest
    private static int findAllPossibleOptions(final List<DragonGroup> sortedDragonGroups,
                                              final int maxGluttonySum,
                                              final Set<Integer> visitedIndexes,
                                              final int startPosition,
                                              int currentGluttony,
                                              int currentInteresting) {

        System.out.println("Start position: " + startPosition);
        System.out.println("Visited indexes: " + visitedIndexes);
        visitedIndexes.add(startPosition);
        currentGluttony = currentGluttony + sortedDragonGroups.get(startPosition).gluttonySum();

        if (currentGluttony > maxGluttonySum) {
            System.out.printf("currentGluttony > maxGluttonySum - Visited indexes: %s. Current gluttony: %d. Current interesting: %d%n",
                    visitedIndexes, currentGluttony, currentInteresting);
            return -1;
        }

        currentInteresting = currentInteresting + sortedDragonGroups.get(startPosition).interestingSum();
        if (currentGluttony == maxGluttonySum) {
            System.out.printf("currentGluttony == maxGluttonySum - Visited indexes: %s. Current gluttony: %d. Current interesting: %d%n",
                    visitedIndexes, currentGluttony, currentInteresting);
            return currentInteresting;
        }

        for (int i = 0; i < sortedDragonGroups.size(); i++) {
            if (visitedIndexes.contains(i)) continue;

            int result = findAllPossibleOptions(
                    sortedDragonGroups, maxGluttonySum, visitedIndexes, i, currentGluttony, currentInteresting
            );
            if (result != -1) return result;
        }

        System.out.printf("FINISHED FOR - Visited indexes: %s. Current gluttony: %d. Current interesting: %d%n",
                visitedIndexes, currentGluttony, currentInteresting);
        return currentInteresting;
    }

    private static final Comparator<DragonGroup> DESCENDING_COMPARATOR = Comparator
            .comparing(DragonGroup::coefficient)
            .reversed();

    private static DragonGroup toDragonGroup(final Set<Integer> dragonIds,
                                             final Map<Integer, Dragon> dragonIdToDragonMap) {
        int interestingAccumulator = 0;
        int gluttonyAccumulator = 0;

        for (Integer dragonId : dragonIds) {
            Dragon dragon = dragonIdToDragonMap.get(dragonId);
            interestingAccumulator = interestingAccumulator + dragon.getInteresting();
            gluttonyAccumulator = gluttonyAccumulator + dragon.getGluttony();
        }

        return new DragonGroup(
                gluttonyAccumulator,
                interestingAccumulator
        );
    }

    private record DragonGroup(int gluttonySum,
                               int interestingSum) {

        public double coefficient() {
            return (double) interestingSum / gluttonySum;
        }
    }


    public static List<Set<Integer>> findDragonFriends(List<DragonPair> dragonPairs) {

        Map<Integer, Set<Dragon>> dragonIdToSetWithDragons = new HashMap<>();

        dragonPairs.forEach(dragonPair -> {
            dragonIdToSetWithDragons.computeIfAbsent(
                    dragonPair.getLeft().getId(),
                    key -> new HashSet<>()
            ).add(dragonPair.getRight());
            dragonIdToSetWithDragons.computeIfAbsent(
                    dragonPair.getRight().getId(),
                    key -> new HashSet<>()
            ).add(dragonPair.getLeft());
        });

        Set<Integer> visitedDragon = new HashSet<>();
        List<Set<Integer>> connectedDragonFriendsList = new ArrayList<>();

        for (Integer dragonId : dragonIdToSetWithDragons.keySet()) {
            if (!(visitedDragon.contains(dragonId))) {
                Set<Integer> connectedDragonFriendsSet = new HashSet<>();
                findDragonFriendsRecursively(dragonId, dragonIdToSetWithDragons, visitedDragon, connectedDragonFriendsSet);
                connectedDragonFriendsList.add(connectedDragonFriendsSet);

            }
        }
        return connectedDragonFriendsList;
    }

    public static void findDragonFriendsRecursively(int dragonId,
                                                    Map<Integer, Set<Dragon>> dragonIdToSetWithDragons,
                                                    Set<Integer> visitedDragon,
                                                    Set<Integer> connectedDragonFriendsSet) {

        visitedDragon.add(dragonId);

        connectedDragonFriendsSet.add(dragonId);

        Set<Dragon> dragonFriends = dragonIdToSetWithDragons.getOrDefault(dragonId, Collections.emptySet());

        for (Dragon dragonFriend : dragonFriends) {
            if (!(visitedDragon.contains(dragonFriend.getId()))) {
                findDragonFriendsRecursively(dragonFriend.getId(), dragonIdToSetWithDragons, visitedDragon, connectedDragonFriendsSet);
            }
        }

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

class DragonPair {
    private final Dragon left;
    private final Dragon right;

    public DragonPair(Dragon first, Dragon second) {
        if (first.getId() > second.getId()) {
            this.left = second;
            this.right = first;
        } else {
            this.left = first;
            this.right = second;
        }
    }

    public Dragon getLeft() {
        return left;
    }

    public Dragon getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "DragonPair{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}





