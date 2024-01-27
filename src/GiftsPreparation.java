
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
                Comparator.comparing((gift) -> gift.getTheLastDayOfSendingTheGift() -
                        (gift.getDayTheMaterialsArrived() + gift.getTimeToPrepareAGift())
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
            int giftSendDay = gift.getDayTheMaterialsArrived() + gift.getTimeToPrepareAGift();

            if (scheduledDaysToSendGiftsSet.contains(giftSendDay)) {
                while (scheduledDaysToSendGiftsSet.contains(giftSendDay)) {
                    giftSendDay++;
                    if (giftSendDay > gift.getTheLastDayOfSendingTheGift()) {
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
    private int dayTheMaterialsArrived; // materialsArrivingDay
    private int timeToPrepareAGift; // daysToPrepare
    private int theLastDayOfSendingTheGift; // milestoneToSendGift

    public Gift(int dayTheMaterialsArrived, int timeToPrepareAGift, int theLastDayOfSendingTheGift) {
        this.dayTheMaterialsArrived = dayTheMaterialsArrived;
        this.timeToPrepareAGift = timeToPrepareAGift;
        this.theLastDayOfSendingTheGift = theLastDayOfSendingTheGift;
    }

    public int getDayTheMaterialsArrived() {
        return dayTheMaterialsArrived;
    }

    public int getTimeToPrepareAGift() {
        return timeToPrepareAGift;
    }

    public int getTheLastDayOfSendingTheGift() {
        return theLastDayOfSendingTheGift;
    }

}



