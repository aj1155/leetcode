import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Sum_4 {
	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> result = new ArrayList<>();
		HashMap<Integer, ArrayList<int[]>> map = new HashMap<>();
		HashSet<String> set = new HashSet<>();
		StringBuffer sb = new StringBuffer();
		Arrays.sort(nums);
		for (int i = 0; i < nums.length - 1; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				int sum = nums[i] + nums[j];
				ArrayList list = map.getOrDefault(sum, new ArrayList<>());
				list.add(new int[] {i, j});
				map.put(sum, list);
			}
		}

		for (Integer sum : map.keySet()) {
			int companion = target - sum;
			if (map.containsKey(companion)) {
				ArrayList<int[]> l1 = map.get(sum);
				ArrayList<int[]> l2 = map.get(companion);
				for (int[] arr1 : l1) {
					for (int[] arr2 : l2) {
						if (arr2[0] > arr1[1]) {
							String str = sb.append(nums[arr1[0]]).append(nums[arr1[1]])
								.append(nums[arr2[0]]).append(nums[arr2[1]]).toString();
							if (!set.contains(str)) {
								set.add(str);
								result.add(Arrays.asList(nums[arr1[0]], nums[arr1[1]], nums[arr2[0]], nums[arr2[1]]));
							}
							sb.delete(0, sb.length());
						}
					}
				}
			}
		}
		return result;
	}
}