package kakao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author manki.kim
 **/
public class MatchingPoint {

	private static final Pattern FIND_PAGE_PATTERN = Pattern.compile("<meta property=\"og:url\" content=\"https://(.+?)\"/>");
	private static final Pattern FIND_EXTERNAL_LINKS_PATTERN = Pattern.compile("<a href=\"https://(.+?)\">");
	private Map<String, List<String>> referenceLinkMap = new HashMap<>();

	public int solution(String word, String[] pages) {
		Map<String, Integer> indexMap = new HashMap<>();
		Map<String, Double> externalLinkCountMap = new HashMap<>();
		Map<String, Double> basicPointMap = new HashMap<>();
		Map<Double, Queue<Integer>> matchPointMap = new TreeMap<>(Comparator.reverseOrder());

		for (int i = 0; i < pages.length; i++) {
			String currentPage = findPage(pages[i]);
			double externalLinkCount = externalLinkProcess(pages[i]);
			double basicPoint = findBasicPoint(word, pages[i]);

			externalLinkCountMap.put(currentPage, externalLinkCount);
			basicPointMap.put(currentPage, basicPoint);
			indexMap.put(currentPage, i);
		}

		for (String s : pages) {
			String currentPage = findPage(s);
			double linkPoint = 0.0;
			if (referenceLinkMap.containsKey(currentPage)) {
				for (String referenceLink : referenceLinkMap.get(currentPage)) {
					linkPoint += (basicPointMap.get(referenceLink) / externalLinkCountMap.get(referenceLink));
				}
			}

			double matchPoint = linkPoint + basicPointMap.get(currentPage);
			if (matchPointMap.containsKey(matchPoint)) {
				matchPointMap.get(matchPoint).add(indexMap.get(currentPage));
			} else {
				Queue<Integer> keys = matchPointMap.getOrDefault(matchPoint, new PriorityQueue<>());
				keys.add(indexMap.get(currentPage));
				matchPointMap.put(matchPoint, keys);
			}
		}


		return matchPointMap.values().stream()
			.findFirst()
			.get()
			.peek();
	}

	private String findPage(String s) {
		Matcher matcher = FIND_PAGE_PATTERN.matcher(s);
		matcher.find();
		return matcher.group(1);
	}

	private double externalLinkProcess(String s) {
		Matcher matcher = FIND_EXTERNAL_LINKS_PATTERN.matcher(s);
		String currentPage = findPage(s);
		double count = 0.0;
		while (matcher.find()) {
			count++;
			String targetLink = matcher.group(1);
			if (referenceLinkMap.containsKey(targetLink)) {
				referenceLinkMap.get(targetLink).add(currentPage);
			} else {
				List<String> references = new ArrayList<>();
				references.add(currentPage);
				referenceLinkMap.put(targetLink, references);
			}
		}
		return count;
	}

	private double findBasicPoint(String word, String html) {
		String upperWord = word.toUpperCase();
		String upperHtml = html.toUpperCase();
		double basicPoint = 0;
		int fromIndex = 0;
		int index = upperHtml.indexOf(upperWord, fromIndex);
		char[] arr = upperHtml.toCharArray();

		while (index != -1) {
			if (!Character.isAlphabetic(arr[index - 1]) && !Character.isAlphabetic(arr[index + upperWord.length()])) {
				basicPoint++;
			}

			index = upperHtml.indexOf(upperWord, index + upperWord.length());
		}

		return basicPoint;
	}
}
