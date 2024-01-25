import java.util.*;

public class GiftsPreparation {
    public static void main(String[] args) {

        List<Gift> listOfGifts = new ArrayList<>();

        Scanner in = new Scanner(System.in);
        int numberOfGifts = in.nextInt();

        for (int i = 0; i < numberOfGifts; i++) {
            listOfGifts.add(new Gift(in.nextInt(), in.nextInt(), in.nextInt()));
        }

        listOfGifts.sort(Comparator.comparing(Gift::getTheLastDayOfSendingTheGift).thenComparing(Gift::getTimeToPrepareAGift));

        String readyAnswer = checkTheReadinessOfTheGifts(listOfGifts);

        System.out.println(readyAnswer);

    }


    public static String checkTheReadinessOfTheGifts(List<Gift> giftList) {
        List<Integer> listWithDaysOfSendingGifts = new ArrayList<>();

        for (Gift gift : giftList) {
            int giftSendDay = gift.getDayTheMaterialsArrived() + gift.getTimeToPrepareAGift();

            if (listWithDaysOfSendingGifts.contains(giftSendDay)) {
                while (listWithDaysOfSendingGifts.contains(giftSendDay) || giftSendDay > gift.getTheLastDayOfSendingTheGift()) {
                    giftSendDay++;
                }
                if (giftSendDay > gift.getTheLastDayOfSendingTheGift()) {
                    return "NO";
                }
            }

            listWithDaysOfSendingGifts.add(giftSendDay);
        }

        return "YES";
    }
}

class Gift {
    private int dayTheMaterialsArrived;
    private int timeToPrepareAGift;
    private int theLastDayOfSendingTheGift;

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



