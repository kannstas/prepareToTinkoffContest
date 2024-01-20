import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class GiftPreparation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Чтение входных данных
        int n = scanner.nextInt();
        Gift[] gifts = new Gift[n];
        for (int i = 0; i < n; i++) {
            int d = scanner.nextInt();
            int c = scanner.nextInt();
            int s = scanner.nextInt();
            gifts[i] = new Gift(d, c, s);
        }

        // Сортировка подарков по дате отправки
        Arrays.sort(gifts, Comparator.comparingInt(g -> g.s));

        // Проверка возможности отправки всех подарков
        int currentDay = 1;
        for (Gift gift : gifts) {
            if (currentDay <= gift.s) {
                currentDay += gift.c;
            } else {
                System.out.println("NO");
                return;
            }
        }

        System.out.println("YES");
    }

    static class Gift {
        int d;
        int c;
        int s;

        public Gift(int d, int c, int s) {
            this.d = d;
            this.c = c;
            this.s = s;
        }
    }
}