class Solution {
    public int longestOnes(int[] nums, int k) {
        int answer = 0;
        int zeros = 0;
        int left = 0;
        for(int right = 0; right<nums.length; right++){
            if(nums[right] == 0){
                zeros++;
            }
            while(zeros> k){
                if(nums[left]==0){
                    zeros--;
                }
                left++;
            }
            answer = Math.max(answer, right-left+1);
        }
        return answer;
    }
}
//순차적으로 순회하면서 k의 개수에 따라 분리된 가장 긴 길이의 dp
//연속적이어야하므로 k가 없는데 끊긴다면 1로 초기화
//k개수 너무 많음 10만개
//슬라이딩 윈도우