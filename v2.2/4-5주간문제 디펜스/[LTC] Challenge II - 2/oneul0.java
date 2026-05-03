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
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length == 0) return null;
        if(lists.length == 1) return mergeSort(lists[0]);
        ListNode left = lists[0];
        for(int i = 1; i<lists.length; i++){
            left = merge(left, lists[i]);
        }
        return mergeSort(left);
    }
    public ListNode mergeSort(ListNode head){
        if(head == null || head.next == null){
            return head;
        }

        //divide
        ListNode mid = mid(head);

        ListNode rightHead = mid.next;

        mid.next = null;

        ListNode left = mergeSort(head);
        ListNode right = mergeSort(rightHead);

        return merge(left, right);
    }

    //mid node
    public ListNode mid(ListNode head){
        ListNode slow = head;
        ListNode fast = head.next;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next; //2칸씩
        }
        return slow;
    }

    //merge
    public ListNode merge(ListNode left, ListNode right){
        ListNode tmp = new ListNode(0);
        ListNode cur = tmp;

        while(left != null && right != null){
            if(left.val <= right.val){
                cur.next = left;
                left = left.next;
            }
            else{
                cur.next = right;
                right = right.next;
            }
            cur = cur.next;
        }

        if(left != null){
            cur.next = left;
        }
        if(right != null){
            cur.next = right;
        }
        return tmp.next;
    }
}