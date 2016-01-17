package com.codes.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 汉语拼音工具类
 * 
 * @author zhangguangyong
 *
 *         2015年11月27日 下午6:23:38
 */
public class Pinyins {
	/** 中文正则匹配 */ 
	static String chineseCharacteRegex = "[u4E00-u9FA5]"; 

	/** 大写格式 */ 
	public static enum PinyinUpperFormat {
		UPPER_FIRST_LETTER, UPPER_LAST_LETTER, UPPER_MIDDLE_LETTER, UPPER_FIRST_LAST_LETTER, UPPER_ALL
	}

	/** 截取格式 */ 
	public static enum PinyinCUTFormat {
		CUT_FIRST_LETTER, CUT_LAST_LETTER, CUT_MIDDLE_LETTER, CUT_FIRST_LAST_LETTER
	}

	/** 声调格式 */ 
	public static enum PinyinTONEFormat {
		TONE_MARK, TONE_NUMBER, TONE_NONE,
	}

	/** U字母格式 */ 
	public static enum PinyinULetterFormat {
		U_AND_COLON, U_UNICODE, U_V
	}
	
	/**
	 * 获取单个字符拼音
	 * @param c
	 * @return
	 */
	public static String getPinyin(char c) {
		return getPinyin(c, null);
	}
	
	/**
	 * 获取字符串拼音
	 * @param text
	 * @return
	 */
	public static String getPinyin(String text) {
		PinyinFormat pf = new PinyinFormat();
		pf.setToneFormat(PinyinTONEFormat.TONE_NONE);
		return getPinyin(text, pf);
	}

	/**
	 * 获取字符串拼音，指定拼音的格式 
	 * @param text
	 * @param format
	 * @return
	 */
	public static String getPinyin(String text, PinyinFormat format) {
		char[] array = text.toCharArray();
		StringBuffer sb = new StringBuffer();
		String sep = ""; // 分隔符
		if ($.notNull(format)) {
			String separator = format.getSeparator();
			sep = $.notNull(separator) ? separator : sep;
		}
		for (int i = 0; i < array.length; i++) {
			char c = array[i];
			if (!String.valueOf(c).matches(chineseCharacteRegex)) {
				if (!sb.toString().endsWith(sep) && sb.length() > 0) {
					sb.append(sep);
				}
				sb.append(getPinyin(c, format));
				if (i != array.length - 1) {
					sb.append(sep);
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 获取字符串首字母小写 
	 * @param text
	 * @return
	 */
	public static String getFirstLetter(String text) {
		return getFirstLetter(text, null);
	}
	
	/**
	 * 获取字符串首字母小写,指定分隔符
	 * @param text
	 * @param separator
	 * @return
	 */
	public static String getFirstLetter(String text, String separator) {
		try {
			PinyinFormat format = new PinyinFormat();
			format.setCutFormat(PinyinCUTFormat.CUT_FIRST_LETTER);
			format.setToneFormat(PinyinTONEFormat.TONE_NONE);
			if ($.notNull(separator)) {
				format.setSeparator(separator);
			}
			return getPinyin(text, format);
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
		return null;
	}

	/**
	 * 获取字符串首字母大写
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String getFirstUpperLetter(String text) throws Exception {
		return getFirstUpperLetter(text, null);
	}
	
	/**
	 * 获取字符串首字母大写, 指定分隔符
	 * @param text
	 * @param separator
	 * @return
	 * @throws Exception
	 */
	public static String getFirstUpperLetter(String text, String separator) throws Exception {
		PinyinFormat format = new PinyinFormat();
		format.setUpperFormat(PinyinUpperFormat.UPPER_FIRST_LETTER);
		format.setToneFormat(PinyinTONEFormat.TONE_NONE);
		if ($.notNull(separator)) {
			format.setSeparator(separator);
		}
		return getPinyin(text, format);
	}

	// 获字符拼音
	public static String getPinyin(char c, PinyinFormat pinyinFormat) {
		HanyuPinyinOutputFormat outputFormat = null;
		boolean formatNotNull = $.notNull(pinyinFormat);
		String ret = null;
		if (formatNotNull) {
			PinyinTONEFormat toneFormat = pinyinFormat.getToneFormat();
			PinyinULetterFormat uLetterFormat = pinyinFormat.getuLetterFormat();
			if ($.notNull(toneFormat)) {
				outputFormat = new HanyuPinyinOutputFormat();
				switch (toneFormat) {
				case TONE_MARK:
					outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
					break;
				case TONE_NUMBER:
					outputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
					break;
				case TONE_NONE:
					outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
					break;
				}
			}
			if ($.notNull(uLetterFormat)) {
				outputFormat = $.isNull(outputFormat) ? new HanyuPinyinOutputFormat() : outputFormat;
				switch (uLetterFormat) {
				case U_AND_COLON:
					outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
					break;
				case U_UNICODE:
					outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
					break;
				case U_V:
					outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
					break;
				}
			}
		}
		String[] pys = null;
		try {
			pys = $.notNull(outputFormat) ? PinyinHelper.toHanyuPinyinStringArray(c, outputFormat)
					: PinyinHelper.toHanyuPinyinStringArray(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ($.notEmpty(pys)) {
			ret = pys[0];
			int tempLen = ret.length() - 1;

			if (formatNotNull) {
				PinyinUpperFormat upperFormat = pinyinFormat.getUpperFormat();
				PinyinCUTFormat cutFormat = pinyinFormat.getCutFormat();

				if ($.notNull(upperFormat)) {
					switch (upperFormat) {
					case UPPER_ALL:
						ret = ret.toUpperCase();
						break;
					case UPPER_FIRST_LETTER:
						ret = String.valueOf(ret.charAt(0)).toUpperCase() + ret.substring(1);
						break;
					case UPPER_LAST_LETTER:
						ret = ret.substring(0, tempLen) + String.valueOf(ret.charAt(tempLen)).toUpperCase();
						break;
					case UPPER_FIRST_LAST_LETTER:
						ret = ret.length() > 2 ? String.valueOf(ret.charAt(0)).toUpperCase() + ret.substring(1, tempLen)
								+ String.valueOf(ret.charAt(tempLen)).toUpperCase() : ret.toUpperCase();
						break;
					case UPPER_MIDDLE_LETTER:
						ret = ret.length() > 2 ? String.valueOf(ret.charAt(0)) + ret.substring(1, tempLen).toUpperCase()
								+ String.valueOf(ret.charAt(tempLen)) : ret.toLowerCase();
						break;
					}
				}

				if ($.notNull(cutFormat)) {
					switch (cutFormat) {
					case CUT_FIRST_LETTER:
						ret = String.valueOf(ret.charAt(0));
						break;
					case CUT_LAST_LETTER:
						ret = String.valueOf(ret.charAt(tempLen));
						break;
					case CUT_MIDDLE_LETTER:
						ret = ret.length() > 2 ? ret.substring(1, tempLen) : ret;
						break;
					case CUT_FIRST_LAST_LETTER:
						ret = ret.length() > 2 ? String.valueOf(ret.charAt(0)) + String.valueOf(ret.charAt(tempLen))
								: ret;
						break;
					}
				}

			}
		}
		return $.notNull(ret) ? ret : String.valueOf(c);
	}

	static class PinyinFormat {
		// 大写格式
		PinyinUpperFormat upperFormat;
		// 截取格式
		PinyinCUTFormat cutFormat;
		// 声调格式
		PinyinTONEFormat toneFormat;
		// U字母格式
		PinyinULetterFormat uLetterFormat;
		// 每个拼音之间的分隔符
		String separator;

		public PinyinFormat() {
		}

		public PinyinFormat(PinyinUpperFormat upperFormat) {
			this.upperFormat = upperFormat;
		}

		public PinyinFormat(PinyinUpperFormat upperFormat, PinyinCUTFormat cutFormat) {
			this.upperFormat = upperFormat;
			this.cutFormat = cutFormat;
		}

		public PinyinFormat(PinyinUpperFormat upperFormat, PinyinCUTFormat cutFormat, PinyinTONEFormat toneFormat) {
			this.upperFormat = upperFormat;
			this.cutFormat = cutFormat;
			this.toneFormat = toneFormat;
		}

		public PinyinFormat(PinyinUpperFormat upperFormat, PinyinCUTFormat cutFormat, PinyinTONEFormat toneFormat,
				PinyinULetterFormat uLetterFormat) {
			this.upperFormat = upperFormat;
			this.cutFormat = cutFormat;
			this.toneFormat = toneFormat;
			this.uLetterFormat = uLetterFormat;
		}

		public PinyinUpperFormat getUpperFormat() {
			return upperFormat;
		}

		public PinyinCUTFormat getCutFormat() {
			return cutFormat;
		}

		public PinyinTONEFormat getToneFormat() {
			return toneFormat;
		}

		public PinyinULetterFormat getuLetterFormat() {
			return uLetterFormat;
		}

		public void setUpperFormat(PinyinUpperFormat upperFormat) {
			this.upperFormat = upperFormat;
		}

		public void setCutFormat(PinyinCUTFormat cutFormat) {
			this.cutFormat = cutFormat;
		}

		public void setToneFormat(PinyinTONEFormat toneFormat) {
			this.toneFormat = toneFormat;
		}

		public void setuLetterFormat(PinyinULetterFormat uLetterFormat) {
			this.uLetterFormat = uLetterFormat;
		}

		public String getSeparator() {
			return separator;
		}

		public void setSeparator(String separator) {
			this.separator = separator;
		}

	}

}
