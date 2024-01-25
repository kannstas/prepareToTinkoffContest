import java.util.*;
import java.util.stream.Collectors;


public class RabbitBooks {
    public static void main(String[] args) {
        Map<Integer, Integer> heightOfBookToNumberOfSuchBooks = new HashMap<>();

        Scanner in = new Scanner(System.in);
        int numberOfBooks = Integer.parseInt(in.nextLine());

        String[] booksHeights = in.nextLine().split(" ");


        for (int i = 0; i < numberOfBooks; i++) {
            int counter = 1;
            for (int j = i + 1; j < numberOfBooks; j++) {
                if (booksHeights[i].equals(booksHeights[j])) {
                    counter++;
                }
            }
            if (!(heightOfBookToNumberOfSuchBooks.containsKey(Integer.valueOf(booksHeights[i])))) {
                heightOfBookToNumberOfSuchBooks.put(
                        Integer.valueOf(booksHeights[i]),
                        counter
                );
            }

        }

        int minNumbersOfStacks = heightOfBookToNumberOfSuchBooks.keySet().size();

        String sortedHeightsOfBooks = heightOfBookToNumberOfSuchBooks.values().stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));


        System.out.println(minNumbersOfStacks);
        System.out.println(sortedHeightsOfBooks);


    }
}
