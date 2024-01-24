import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<Gift> listOfGifts = new ArrayList<>();

        Scanner in = new Scanner(System.in);
        int numberOfGifts = in.nextInt();

        for (int i = 0; i < numberOfGifts; i++) {
            listOfGifts.add(new Gift(in.nextInt(), in.nextInt(), in.nextInt()));
        }

        listOfGifts.sort(Comparator.comparing(Gift::getTheLastDayOfSendingTheGift).thenComparing(Gift::getTimeToPrepareAGift));

        System.out.println(checkPreparedGifts(listOfGifts));

    }

    public static String checkPreparedGifts(List<Gift> giftList) {
        List<Integer> listWithDaysOfSendingGifts = new ArrayList<>();

        int counterOfDay = 1;
        int dayOfSendingTheLastGift = 0;
        for (Gift gift : giftList) {







            if (counterOfDay <= gift.getTheLastDayOfSendingTheGift()) {

                int currentSendDay = Math.min(gift.getTheLastDayOfSendingTheGift(), (gift.getDayTheMaterialsArrived() + gift.getTimeToPrepareAGift()));
                listWithDaysOfSendingGifts.add(currentSendDay);

                if (listWithDaysOfSendingGifts.contains(currentSendDay)) {
                    // ЧЕРЕЗ WHILE
                    int difference = gift.getTheLastDayOfSendingTheGift() - (gift.getDayTheMaterialsArrived() + gift.getTimeToPrepareAGift());
                    int firstNumber = 0;
                    while (firstNumber > difference) {
                        currentSendDay++;
                        listWithDaysOfSendingGifts.add(currentSendDay);
                    }

                }

//                dayOfSendingTheLastGift = currentSendDay + gift.getTimeToPrepareAGift();
//                counterOfDay = dayOfSendingTheLastGift;
//                listWithDaysOfSendingGifts.add(dayOfSendingTheLastGift);

                if (counterOfDay == currentSendDay) {
                    return "NO";
                }

            } else {
                return "NO";
            }

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


