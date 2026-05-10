class Solution {
    func groupAnagrams(_ strs: [String]) -> [[String]] {
        var dict = [String: [String]]()

        for i in 0..<strs.count {
            let key = String(strs[i].sorted())
            dict[key, default: []].append(strs[i])
        }

        return Array(dict.values)
    }
}