import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author manki.kim
 **/
public class FindLoadTree {

	public static void main(String... args) {
		int[][] arr = {
			{5, 3}, {11, 5}, {13, 3}, {3, 5}, {6, 1}, {1, 3}, {8, 6}
			, {7, 2}, {2, 2}
		};

		int[][] result = solution(arr);

		for (int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
		}
	}

	public static int[][] solution(int[][] nodeinfo) {
		int level = nodeinfo.length;
		Queue<Node> queue = new PriorityQueue<>();

		for (int i = 0; i < nodeinfo.length; i++) {
			queue.add(new Node(nodeinfo[i][0], nodeinfo[i][1], i + 1));
		}

		Tree tree = new Tree();

		for (Node n : queue) {
			tree.add(n);
		}

		tree.preOrder(tree.root);
		List<Integer> preSearch = tree.preSearch;

		int[][] answer = new int[2][preSearch.size()];
		for (int i = 0; i < preSearch.size(); i++) {
			answer[0][i] = preSearch.get(i);
		}

		tree.postOrder(tree.root);
		List<Integer> postSearch = tree.postSearch;

		for (int i = 0; i < postSearch.size(); i++) {
			answer[1][i] = postSearch.get(i);
		}
		return answer;

	}
}

class Node implements Comparable {
	int index;
	int x;
	int y;
	Node left;
	Node right;

	public Node(int x, int y, int index) {
		this.x = x;
		this.y = y;
		this.index = index;
	}

	@Override
	public int compareTo(Object o) {
		Node that = (Node)o;
		return this.y != that.y ? that.y - this.y : that.x - this.x;
	}
}

class Tree {
	Node root;
	List<Integer> preSearch = new ArrayList<>();
	List<Integer> postSearch = new ArrayList<>();

	public void add(Node node) {
		if (root == null) {
			root = node;
		} else {
			Node temp = root;
			while (true) {
				if (temp.x > node.x) {
					if (temp.left != null) {
						temp = temp.left;
					} else {
						temp.left = node;
						break;
					}
				} else {
					if (temp.right != null) {
						temp = temp.right;
					} else {
						temp.right = node;
						break;
					}
				}
			}
		}

	}

	public void preOrder(Node root) {
		if (root == null) {
			return;
		}

		preSearch.add(root.index);
		preOrder(root.left);
		preOrder(root.right);
	}

	public void postOrder(Node root) {
		if (root == null) {
			return;
		}

		postOrder(root.left);
		postOrder(root.right);
		postSearch.add(root.index);
	}
}
