public class ReverseInteger {
	public int reverse(int x) {
		boolean sign = false;
		if (x < 0) {
			sign = true;
			x *= -1;
		}
		String num = String.valueOf(x);
		String ans = "";
		for (int i = 0; i < num.length(); i++) {
			ans = num.substring(i, i + 1) + ans;
		}
		try {
			int val = Integer.parseInt(ans);
			return sign ? -val : val;
		} catch (Exception e) {
			return 0;
		}
	}
}
