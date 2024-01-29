import java.util.*;
import java.util.stream.Collectors;

public class RabbitBooks {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int numberOfBooks = Integer.parseInt(in.nextLine());

        Map<Integer, Integer> result = Arrays.stream(in.nextLine().split(" "))
                .map(Integer::parseInt)
                .collect(
                        Collectors.toMap(
                                (height) -> height,
                                (height) -> 1,
                                (oldNumber, newNumber) -> oldNumber + newNumber
                        )
                );

        int minNumbersOfStacks = result.keySet().size();

        String sortedHeightsOfBooks = result.values().stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));


        System.out.println(minNumbersOfStacks);
        System.out.println(sortedHeightsOfBooks);
    }
}
