//Q1. Number of Sub-arrays of Size K and Average Greater than or Equal to Threshold
//정수로 관리하는 윈도우 이용해서 슬라이딩 윈도우
class Solution {
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int target = k*threshold;
        int window = 0;
        int count = 0;

        for(int i = 0; i<k; i++){
            window += arr[i];
        }

        if(window >= target){
            count++;
        }

        for(int i = k; i<arr.length; i++){
            window += arr[i];
            window -= arr[i-k];

            if(window >= target){
                count++;
            }

        }

        return count;
    }
}

//Q2. Grumpy Bookstore Owner
//새로운 추가 배열 만들어서 슬라이딩 윈도우
class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
        int base = 0;

        for (int i=0; i<customers.length; i++) {
            if (grumpy[i]==0) {
                base += customers[i];
            }
        }

        int extra = 0;

        for (int i=0; i<minutes; i++) {
            if (grumpy[i]==1) {
                extra += customers[i];
            }
        }

        int maxVal = extra;

        for (int i=minutes; i<customers.length; i++) {
            if (grumpy[i]==1) {
                extra += customers[i];
            }

            if (grumpy[i-minutes]==1) {
                extra -= customers[i-minutes];
            }

            maxVal = Math.max(maxVal, extra);
        }

        return base + maxVal;
    }
}