import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        CustomIndexedList<String> indexedList = new CustomIndexedList<>();
        indexedList.add("One");
        indexedList.add("Two");
        indexedList.add("Three");

        System.out.println(indexedList.get(1));  // Вывод: One
        System.out.println(indexedList.get(2));  // Вывод: Two
        System.out.println(indexedList.get(3));  // Вывод: Three
    }

    public static class CustomIndexedList<T> {
        private List<T> internalList;

        public CustomIndexedList() {
            this.internalList = new ArrayList<>();
        }

        public T get(int index) {
            // Вернуть элемент по индексу, скорректированному на 1
            return internalList.get(index - 1);
        }

        public void add(T element) {
            // Добавить элемент в список
            internalList.add(element);
        }


    }
}