

import java.util.ArrayList;

public class TryPurple {
    public static void main(String[] args) {
        ArrayList<Integer> rankList = new ArrayList<Integer>();
        ArrayList<Integer> countList = new ArrayList<>(5);
        rankList.add(7);
        rankList.add(3);
        rankList.add(3);
        rankList.add(2);
        rankList.add(2);
        int totalCount = 0;
        int index = 0;

        while(totalCount<5){
            int j = index+1;
            int count = 1;
            while(j<5){
                if(rankList.get(index).equals(rankList.get(j))){
                    count++;
                }
                j++;
            }
            index +=count;
            totalCount +=count;
            countList.add(count);
        }
        for(int i=0;i<countList.size();i++){
            System.out.println(countList.get(i));
        }

    }
}
