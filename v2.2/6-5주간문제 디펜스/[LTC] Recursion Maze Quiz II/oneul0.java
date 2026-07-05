//Q1. Reorder List
//반 나눠서 뒷쪽 뒤집고 번갈아 붙이기
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
    public void reorderList(ListNode head) {
        if(head == null || head.next == null) return;

        //1. 중간 찾아서
        ListNode slow = head;
        ListNode fast = head;

        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        //2. 뒤쪽 절반 분리해서 뒤집기
        ListNode second = slow.next;
        slow.next= null;

        ListNode prev = null;
        while(second != null){
            ListNode next = second.next;
            second.next = prev;
            prev = second;
            second = next;
        }

        second = prev;

        ListNode first = head;

        while(second != null){
            ListNode tmp1 = first.next;
            ListNode tmp2 = second.next;

            first.next = second;
            second.next = tmp1;

            first = tmp1;
            second = tmp2;
        }
    }
}

//Q2. All Paths From Source to Target
//dfs + 백트
class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> answer = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        path.add(0);
        dfs(graph, 0, path, answer);

        return answer;
    }

    private void dfs(int[][] gr, int current, List<Integer> path, List<List<Integer>> answer) {
        int target = gr.length-1;

        if (current == target) {
            answer.add(new ArrayList<>(path));
            return;
        }

        for (int next : gr[current]) {
            path.add(next);
            dfs(gr, next, path, answer);
            path.remove(path.size() - 1);
        }
    }
}