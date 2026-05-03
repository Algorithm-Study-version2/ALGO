class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        left[0] = 1;
        left[1] = nums[0];
        for(int i = 2; i<n; i++){
            left[i] = left[i-1]*nums[i-1];
        }
        int right = 1;
        for(int i = n-1; i>=0; i--){
            left[i] *= right;
            right *= nums[i];
        }
        return left;
    }
}