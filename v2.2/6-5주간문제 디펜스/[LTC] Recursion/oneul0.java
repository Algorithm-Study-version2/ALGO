//Q1. Merge Two Sorted Lists
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null) return list2;
        if(list2 == null) return list1;

        if(list1.val <= list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        }
        else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }
}


//Q2. Find Kth Bit in Nth Binary String

class Solution {
    public char findKthBit(int n, int k) {
        if (n == 1) return '0';

        int len = (1<<n)-1;
        int mid = len/2+1;

        if (k==mid) return '1';

        if (k<mid) return findKthBit(n-1, k);

        char bit = findKthBit(n-1, len-k+1);
        return bit=='0' ? '1' : '0';
    }
}

//Q3. Decode String
class Solution {
    private int index = 0;

    public String decodeString(String s) {
        index = 0;
        return decode(s);
    }

    private String decode(String s) {
        StringBuilder result = new StringBuilder();
        int number = 0;

        while(index<s.length()) {
            char ch = s.charAt(index);

            if(Character.isDigit(ch)) {
                number = number * 10+(ch-'0');
                index++;
            }
            else if(ch=='[') {
                // [ pass
                index++;

                String decoded = decode(s);

                for (int i = 0; i < number; i++) {
                    result.append(decoded);
                }

                number = 0;
            }
            else if(ch==']') {
                // ] pass
                index++;
                return result.toString();
            }
            else {
                result.append(ch);
                index++;
            }
        }

        return result.toString();
    }
}
