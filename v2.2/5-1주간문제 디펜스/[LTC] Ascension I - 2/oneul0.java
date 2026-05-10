class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if(m > n){
            int[] tmp;
            tmp = nums1;
            nums1 = nums2;
            nums2 = tmp;
            int t = n;
            n = m;
            m = t;
        }

        final int INF = 987654321;
        int low = 0;
        int high = m;
        while(low <= high){
            int i = (low + high)/2;
            int j = ((m+n+1)/2) -i;
            int n1_left = (i>0) ? nums1[i-1] : -INF;
            int n1_right = (i<m) ? nums1[i] : INF;
            int n2_left = (j>0) ? nums2[j-1] : -INF;
            int n2_right = (j<n) ? nums2[j] : INF;

            if(n1_left <= n2_right && n2_left <= n1_right) {
                if((m+n)%2 == 1) return Math.max(n1_left, n2_left);
                else return (Math.max(n1_left, n2_left) + Math.min(n1_right, n2_right)) / 2.0;
            } else if(n1_left > n2_right) {
                high = i-1;

            } else {
                low = i+1;
            }
        }

        return 0.0;
    }
}