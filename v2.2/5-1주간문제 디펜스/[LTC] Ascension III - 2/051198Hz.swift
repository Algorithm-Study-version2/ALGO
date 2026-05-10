class Solution {
    func maxSlidingWindow(_ nums: [Int], _ k: Int) -> [Int] {
        var deque = [Int]()
        var result = [Int]()

        for i in 0..<nums.count {
            if let first = deque.first, first <= i - k {
                deque.removeFirst()
            }
            while let last = deque.last, nums[last] <= nums[i] {
                deque.removeLast()
            }

            deque.append(i)

            if i >= k - 1 {
                result.append(nums[deque.first!])
            }
        }

        return result
    }
}