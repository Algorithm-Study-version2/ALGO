class Solution {
    func findMedianSortedArrays(_ nums1: [Int], _ nums2: [Int]) -> Double {
        if nums1.count > nums2.count {
            return findMedianSortedArrays(nums2, nums1)
        }

        let m = nums1.count
        let n = nums2.count
        let half = (m + n + 1) / 2

        var left = 0
        var right = m

        while left <= right {
            let i = (left + right) / 2
            let j = half - i

            let Aleft = i == 0 ? Int.min : nums1[i - 1]
            let Aright = i == m ? Int.max : nums1[i]
            let Bleft = j == 0 ? Int.min : nums2[j - 1]
            let Bright = j == n ? Int.max : nums2[j]

            if Aleft <= Bright && Bleft <= Aright {

                if (m + n) % 2 == 1 {
                    return Double(max(Aleft, Bleft))
                } else {
                    return Double(max(Aleft, Bleft) + min(Aright, Bright)) / 2.0
                }
            } else if Aleft > Bright {
                right = i - 1
            } else {
                left = i + 1
            }
        }

        return 0.0
    }
}