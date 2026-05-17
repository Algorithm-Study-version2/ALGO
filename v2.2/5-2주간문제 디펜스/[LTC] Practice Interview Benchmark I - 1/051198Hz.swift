class Solution {
    func isAnagram(_ s: String, _ t: String) -> Bool {
        var sDict = [Character: Int]()
        for c in s {
            sDict[c, default: 0] += 1
        }
        var tDict = [Character: Int]()
        for c in t {
            tDict[c, default: 0] += 1
        }
        return sDict == tDict
    }
}