class Solution {
    public int minOperations(int n) {
        int count = 0;

        while(n>0){
            //is even 
            if((n & 1)== 0){
                n >>=1;
            }

            //is odd
            else{
                count++;
                //last two bit
                //01 || 11
                if(n == 1 || (n&3) == 1){
                    n--;
                }
                else{
                    n++;
                }
            }
        }
        return count;
    }
}