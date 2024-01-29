
import java.util.*;

public class GiftsPreparation {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int numberOfGifts = in.nextInt();

        List<Gift> listOfGifts = new ArrayList<>();

        for (int i = 0; i < numberOfGifts; i++) {
            listOfGifts.add(new Gift(in.nextInt(), in.nextInt(), in.nextInt()));
        }

        listOfGifts.sort(
                Comparator.comparing((gift) -> gift.getMilestoneToSendGift() -
                        (gift.getMaterialsArrivingDay() + gift.getDaysToPrepare())
                )
//                Comparator.comparing(Gift::getTheLastDayOfSendingTheGift)
//                        .thenComparing(Gift::getTimeToPrepareAGift)
        );

        String readyAnswer = checkTheReadinessOfTheGifts(listOfGifts);

        System.out.println(readyAnswer);
    }

    public static String checkTheReadinessOfTheGifts(List<Gift> giftList) {

        Set<Integer> scheduledDaysToSendGiftsSet = new HashSet<>();

        for (Gift gift : giftList) {
            int giftSendDay = gift.getMaterialsArrivingDay() + gift.getDaysToPrepare();

            if (giftSendDay > gift.getMilestoneToSendGift()) {
                return "NO";
            }
            if (scheduledDaysToSendGiftsSet.contains(giftSendDay)) {
                while (scheduledDaysToSendGiftsSet.contains(giftSendDay)) {
                    giftSendDay++;
                    if (giftSendDay > gift.getMilestoneToSendGift()) {
                        return "NO";
                    }
                }
            }

            scheduledDaysToSendGiftsSet.add(giftSendDay);
        }

        return "YES";
    }
}

class Gift {
    private int materialsArrivingDay;
    private int daysToPrepare;
    private int milestoneToSendGift;

    public Gift(int materialsArrivingDay, int daysToPrepare, int milestoneToSendGift) {
        this.materialsArrivingDay = materialsArrivingDay;
        this.daysToPrepare = daysToPrepare;
        this.milestoneToSendGift = milestoneToSendGift;
    }

    public int getMaterialsArrivingDay() {
        return materialsArrivingDay;
    }

    public int getDaysToPrepare() {
        return daysToPrepare;
    }

    public int getMilestoneToSendGift() {
        return milestoneToSendGift;
    }

}



