import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FrequentElements {
	private static final int DEFAULT_COUNT_VAL = 0;

	public List<Integer> topKFrequent(int[] nums, int k) {
		Map<Integer, Integer> frequentMap = new HashMap<>();

		for (int n : nums) {
			frequentMap.put(n, frequentMap.getOrDefault(n, DEFAULT_COUNT_VAL) + 1);
		}

		Map<Integer, List<Integer>> sortedByFrequentMap = new TreeMap<>(Collections.reverseOrder());
		for (int key : frequentMap.keySet()) {
			int sortedMapKey = frequentMap.get(key);
			if (!sortedByFrequentMap.containsKey(sortedMapKey)) {
				List<Integer> keySet = new ArrayList<>();
				keySet.add(key);
				sortedByFrequentMap.put(sortedMapKey, keySet);
			} else {
				sortedByFrequentMap.get(sortedMapKey).add(key);
			}
		}

		List<Integer> result = new ArrayList<>();

		for (int key : sortedByFrequentMap.keySet()) {
			result.addAll(sortedByFrequentMap.get(key));

			if (result.size() == k) {
				break;
			}
		}

		return result;
	}
}
