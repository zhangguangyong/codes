package com.codes.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 随机数据工具类
 * 
 * @author zhangguangyong
 *
 *         2015年11月27日 下午6:28:22
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Randoms {

	static final Random RANDOM = new Random();

	// 字符类型
	static enum CharType {
		Lower, Upper, Char, Num, CharAndNum
	};

	// 中文字符界限
	static Character firstChineseChar = '\u4e00';
	static Character lastChineseChar = '\u9fa5';
	static int firstChineseNum = firstChineseChar;
	static int lastChineseNum = lastChineseChar;

	static final char[] LOWERCHAR = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	static final char[] UPPERCHAR = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	static final char[] CHARS = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	static final char[] NUMS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	static char[] CHARANDNUM = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9' };

	public static String random(final int count, final boolean letters, final boolean numbers) {
		return random(count, 0, 0, letters, numbers);
	}

	public static String random(final int count, final int start, final int end, final boolean letters,
			final boolean numbers) {
		return random(count, start, end, letters, numbers, null, RANDOM);
	}

	public static String random(final int count, final String chars) {
		if (chars == null) {
			return random(count, 0, 0, false, false, null, RANDOM);
		}
		return random(count, chars.toCharArray());
	}

	public static String random(final int count, final char... chars) {
		if (chars == null) {
			return random(count, 0, 0, false, false, null, RANDOM);
		}
		return random(count, 0, chars.length, false, false, chars, RANDOM);
	}

	public static String random(int count, int start, int end, final boolean letters, final boolean numbers,
			final char[] chars, final Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		if (chars != null && chars.length == 0) {
			throw new IllegalArgumentException("The chars array must not be empty");
		}

		if (start == 0 && end == 0) {
			if (chars != null) {
				end = chars.length;
			} else {
				if (!letters && !numbers) {
					end = Integer.MAX_VALUE;
				} else {
					end = 'z' + 1;
					start = ' ';
				}
			}
		} else {
			if (end <= start) {
				throw new IllegalArgumentException(
						"Parameter end (" + end + ") must be greater than start (" + start + ")");
			}
		}

		final char[] buffer = new char[count];
		final int gap = end - start;

		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if (letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters && !numbers) {
				if (ch >= 56320 && ch <= 57343) {
					if (count == 0) {
						count++;
					} else {
						// low surrogate, insert high surrogate after putting it
						// in
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + random.nextInt(128));
					}
				} else if (ch >= 55296 && ch <= 56191) {
					if (count == 0) {
						count++;
					} else {
						// high surrogate, insert low surrogate before putting
						// it in
						buffer[count] = (char) (56320 + random.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if (ch >= 56192 && ch <= 56319) {
					// private high surrogate, no effing clue, so skip it
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}

	/**
	 * 随机指定范围的一个数字
	 * 
	 * @param min
	 *            范围最小值
	 * @param max
	 *            范围最大值
	 * @return
	 */
	public static Number random(Number min, Number max) {
		double minVal = min.doubleValue() - 1;
		double maxVal = max.doubleValue() + 1;
		double ret = Math.random() * (maxVal - minVal) + minVal;
		ret = Math.min(ret, max.doubleValue());
		ret = Math.max(ret, min.doubleValue());
		return ret;
	}

	/**
	 * 随机指定范围和长度的数字
	 * 
	 * @param min
	 * @param max
	 * @param len
	 * @return
	 */
	public static List random(Number min, Number max, long len) {
		return random(min, max, len, len);
	}

	/**
	 * 随机指定大小区间和长度区间的数字
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @param lenMin
	 *            最小长度
	 * @param lenMax
	 *            最大长度
	 * @return
	 */
	public static List random(Number min, Number max, long lenMin, long lenMax) {
		long len = random(lenMin, lenMax).longValue();
		if (len > 0) {
			List ret = new ArrayList();
			for (int i = 0; i < len; i++) {
				ret.add(random(min, max));
			}
			return ret;
		}
		return null;
	}

	/**
	 * 随机小写字符
	 * 
	 * @param len
	 *            随机的个数
	 * @return
	 */
	public static String randomLowerChar(int len) {
		return random(CharType.Lower, len);
	}

	/**
	 * 随机大写字母
	 * 
	 * @param len
	 *            随机的个数
	 * @return
	 */
	public static String randomUpperChar(int len) {
		return random(CharType.Upper, len);
	}

	/**
	 * 随机字符
	 * 
	 * @param len
	 *            随机的个数
	 * @return
	 */
	public static String randomChar(int len) {
		return random(CharType.Char, len);
	}

	/**
	 * 随机数字
	 * 
	 * @param len
	 *            随机的个数
	 * @return
	 */
	public static String randomNum(int len) {
		return random(CharType.Num, len);
	}

	/**
	 * 随机字符和数字
	 * 
	 * @param len
	 *            随机的个数
	 * @return
	 */
	public static String randomCharAndNum(int len) {
		return random(CharType.CharAndNum, len);
	}

	/**
	 * 随机数组里面的一个值
	 * 
	 * @param values
	 * @return
	 */
	public static <T> T randomOne(T... values) {
		return (T) values[RANDOM.nextInt(values.length)];
	}

	/**
	 * 随机指定长度来数据来源的数据
	 * 
	 * @param minLen
	 * @param maxLen
	 * @param values
	 * @return
	 */
	public static List random(long minLen, long maxLen, Object... values) {
		long len = randomLong(minLen, maxLen);
		if (len > 0) {
			List ret = new ArrayList();
			for (int i = 0; i < len; i++) {
				ret.add(randomOne(values));
			}
			return ret;
		}
		return null;
	}

	/**
	 * 随机中文
	 * 
	 * @param length
	 * @return
	 */
	public static String randomChinese(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append((char) random(firstChineseNum, lastChineseNum).intValue());
		}
		return sb.toString();
	}

	/**
	 * 随机指定范围的字符
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static char randomChar(int min, int max) {
		return (char) randomInteger(min, max);
	}

	/**
	 * 随机指定范围的整数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomInteger(int min, int max) {
		return random(min, max).intValue();
	}

	/**
	 * 随机指定范围的长整型数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static long randomLong(long min, long max) {
		return random(min, max).longValue();
	}

	public static double randomDouble(double min, double max) {
		return random(min, max).doubleValue();
	}

	private static String random(CharType type, long len) {
		return random(type, len, len);
	}

	private static String random(CharType type, long minLen, long maxLen) {
		long len = random(minLen, maxLen).longValue();
		if (len <= 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		char[] cs = null;
		switch (type) {
		case Lower:
			cs = LOWERCHAR;
			break;
		case Upper:
			cs = UPPERCHAR;
			break;
		case Char:
			cs = CHARS;
			break;
		case Num:
			cs = NUMS;
			break;
		case CharAndNum:
			cs = CHARANDNUM;
			break;
		}
		for (int i = 0; i < len; i++) {
			sb.append(cs[RANDOM.nextInt(cs.length)]);
		}
		return sb.toString();
	}

	public static Date randomDate(Date min, Date max) {
		return randomDate(min.getTime(), max.getTime());
	}

	public static Date randomDate(long min, long max) {
		return new Date(randomLong(min, max));
	}

	public static Date randomDate() {
		return randomDate(System.currentTimeMillis() - Integer.MAX_VALUE,
				System.currentTimeMillis() + Integer.MAX_VALUE);
	}
}
