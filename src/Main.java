import java.util.*;

// ПОДУМАТЬ ЕЩЕ НЕ КОРРЕКТНО РАБОТАЕТ
// 4
//1 1 4
//1 1 4
//1 1 4
//1 1 4
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
        List<Integer> listWithDays = new ArrayList<>();

        int counterOfDay = 1;
        int dayOfSendingTheLastGift= 0;
        for (Gift gift : giftList) {
            if (counterOfDay <= gift.getTheLastDayOfSendingTheGift()) {
               int currentSendDay =  Math.max(dayOfSendingTheLastGift, gift.getTheLastDayOfSendingTheGift() - (gift.getDayTheMaterialsArrived()+ gift.getTimeToPrepareAGift()));
                listWithDays.add(currentSendDay);
                counterOfDay = currentSendDay + gift.getTimeToPrepareAGift();
                if (counterOfDay == currentSendDay) {
                    return "NO";
                }
//
//               if (currentSendDay > dayOfSendingTheLastGift) {
//                   dayOfSendingTheLastGift = currentSendDay;
//                   counterOfDay = currentSendDay + gift.getTimeToPrepareAGift();
//               }
//               else {
//                   counterOfDay += gift.getTimeToPrepareAGift();
//               }



                System.out.println(listWithDays);
            } else {
                return "NO";
            }

        }
        return "YES";
    }


}
// нужен цикл и массив, в который будет записываться дни


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


