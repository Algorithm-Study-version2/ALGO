class Solution {
    func numOfSubarrays(_ arr: [Int], _ k: Int, _ threshold: Int) -> Int {
        let target = k * threshold
        var sum = 0
        var answer = 0

        for i in 0..<k {
            sum += arr[i]
        }

        if sum >= target {
            answer += 1
        }

        for right in k..<arr.count {
            sum += arr[right]
            sum -= arr[right - k]

            if sum >= target {
                answer += 1
            }
        }

        return answer
    }

    func maxSatisfied(_ customers: [Int], _ grumpy: [Int], _ minutes: Int) -> Int {
        let n = customers.count

        var base = 0
        var addition = 0

        for i in 0..<n {
            if grumpy[i] == 0 {
                base += customers[i]
            }
        }
        
        for i in 0..<minutes {
            if grumpy[i] == 1 {
                addition += customers[i]
            }
        }

        var maxAddition = addition

        for right in minutes..<n {
            if grumpy[right] == 1 {
                addition += customers[right]
            }

            let left = right - minutes
            if grumpy[left] == 1 {
                addition -= customers[left]
            }

            maxAddition = max(maxAddition, addition)
        }

        return base + maxAddition
    }
}