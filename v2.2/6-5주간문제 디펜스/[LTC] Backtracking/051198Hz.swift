class Solution {
    func combine(_ n: Int, _ k: Int) -> [[Int]] {
        if k == 0 {
            return [[]]
        }

        if n < k {
            return []
        }

        if n == k {
            return [Array(1...n)]
        }

        var result = combine(n - 1, k)

        for var combination in combine(n - 1, k - 1) {
            combination.append(n)
            result.append(combination)
        }

        return result
    }

    func restoreIpAddresses(_ s: String) -> [String] {
        let chars = Array(s)
        var result = [String]()
        var currentAddres = [String]()

        func dfs(_ index: Int, _ part: Int) {
            if part == 4 {
                if index == chars.count {
                    result.append(currentAddres.joined(separator: "."))
                }
                return
            }

            let remain = chars.count - index
            let need = 4 - part

            guard remain >= need && remain <= need * 3 else {
                return
            }

            var value = 0

            for end in index..<min(index + 3, chars.count) {
                value = value * 10 + chars[end].wholeNumberValue!

                if value > 255 {
                    break
                }

                // 01, 010, 011 같은 애들은 패스
                if end > index && chars[index] == "0" {
                    break
                }

                currentAddres.append(String(chars[index...end]))
                dfs(end + 1, part + 1)
                currentAddres.removeLast()
            }
        }

        dfs(0, 0)
        return result
    }

    private let alphas = ["a", "b", "c"]
    private var count = 0
    private var currentString = [String]()
    private var answer = ""

    func getHappyString(_ n: Int, _ k: Int) -> String {
        if n == 0 {
            count += 1
            if count == k {
                answer = currentString.joined()
            }
            return answer
        }
        
        for alpha in alphas {
            if currentString.last == alpha {
                continue
            }            
            currentString.append(alpha)
            if !getHappyString(n-1, k).isEmpty {
                return answer
            }
            currentString.removeLast()
        }
        return answer
    }
}