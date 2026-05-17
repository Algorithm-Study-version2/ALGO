final class RandomizedSet {

    private var nums: [Int]
    private var indexMap: [Int: Int]

    init() {
        nums = []
        indexMap = [:]
    }

    func insert(_ val: Int) -> Bool {

        if indexMap[val] != nil {
            return false
        }

        nums.append(val)
        indexMap[val] = nums.count - 1

        return true
    }

    func remove(_ val: Int) -> Bool {

        guard let idx = indexMap[val] else {
            return false
        }

        let lastVal = nums.last!

        nums[idx] = lastVal
        indexMap[lastVal] = idx

        nums.removeLast()
        indexMap[val] = nil

        return true
    }

    func getRandom() -> Int {
        nums.randomElement()!
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * let obj = RandomizedSet()
 * let ret_1: Bool = obj.insert(val)
 * let ret_2: Bool = obj.remove(val)
 * let ret_3: Int = obj.getRandom()
 */