class Solution {
    func longestOnes(_ nums: [Int], _ k: Int) -> Int {
        var left = 0
        var zeroCount = 0
        var maxLen = 0

        for right in 0..<nums.count {
            if nums[right] == 0 {
                zeroCount += 1
            }

            while zeroCount > k {
                if nums[left] == 0 {
                    zeroCount -= 1
                }
                left += 1
            }

            maxLen = max(maxLen, right - left + 1)
        }

        return maxLen
    }
}