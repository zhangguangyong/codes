package com.codes.platform.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.codes.platform.util.ClassFinder;

public class ResourceLoadTest {
	
	@Test
	public void test(){
		String packageName = "com.codes.platform.**.domain";
		List<String> names = ClassFinder.getInstance(packageName).findClassNames();
		System.out.println(names);
		
	}
	
	@Test
	public void test1(){
		// System.out.println( 10000 * Math.pow(1.1, 6) );
		// 17715.610000000008
		// 17715.61000000001
		double num = 1.1;
		for (int i = 0; i < 5; i++) {
			num *= 1.1;
		}
		System.out.println( 10000 * num);
		
	}
	
	@Test
	public void testZhouYi(){
		// 来源：http://www.kuaisee.com/article/676/3.shtml
		// 准备49颗棋子，分为2组为：天和地，再从天和地中的任意一组拿出1颗棋子作为人，这样天地人都有人
		List<Integer> gua = getGua();
		String guaTextAndSymbol = getGuaTextAndSymbol(gua);
		System.out.println(guaTextAndSymbol);
	}
	
	/**
	 * 获取挂 六爻为一卦
	 * @return
	 */
	public List<Integer> getGua(){
		List<Integer> yaoList = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			yaoList.add(getYao());
		}
		// 把爻倒过来，因为没得到一爻都放在之前爻的上面的
		Collections.reverse(yaoList);
		return yaoList;
	}

	/**
	 * 获取爻 三变为一爻
	 * @return
	 */
	public int getYao(){
		int sum = 49;
		// 三变为一爻
		for (int j = 0; j < 3; j++) {
			// 获取变数
			sum -= getBianShu(sum);
		}
		return sum / 4;
	}
	
	public int getBianShu(int sum){
		// 天
		int tian = randomHalf(sum);
		// 地
		int di = sum - tian;
		// 人
		int ren = 1;
		di -= 1;
		
		// 天和地都除以4，各自的余数+人
		int tianBian = ( tian % 4 ) > 0 ? ( tian % 4 ) : 4;
		int diBian = ( di % 4 ) > 0 ? ( di % 4 ) : 4;
		int bianShu = tianBian + diBian + ren;

		return bianShu;
	}
	
	static Map<Integer, String> guaTextMapping = new HashMap<Integer, String>();
	static Map<Integer, String> guaSymbolMapping = new HashMap<Integer, String>();
	static{
		// 6是老阴，8是少阴，7是少阳，9是老阳
		guaTextMapping.put(6, "老阴");
		guaTextMapping.put(7, "少阳");
		guaTextMapping.put(8, "少阴");
		guaTextMapping.put(9, "老阳");

		guaSymbolMapping.put(6, "--");
		guaSymbolMapping.put(7, "—");
		guaSymbolMapping.put(8, "--");
		guaSymbolMapping.put(9, "—");
	}
	
	public String getGuaText(List<Integer> gua){
		StringBuilder sb = new StringBuilder();
		for (Integer yao : gua) {
			sb.append( guaTextMapping.get(yao) ).append("\n");
		}
		return sb.toString();
	}

	public String getGuaSymbol(List<Integer> gua){
		StringBuilder sb = new StringBuilder();
		for (Integer yao : gua) {
			sb.append( guaSymbolMapping.get(yao) ).append("\n");
		}
		return sb.toString();
	}
	
	public String getGuaTextAndSymbol(List<Integer> gua){
		StringBuilder sb = new StringBuilder();
		for (Integer yao : gua) {
			sb.append( guaTextMapping.get(yao) ).append("\t").append( guaSymbolMapping.get(yao) ).append("\n");
		}
		return sb.toString();
	}
	
	
	/**
	 * 随机一半
	 * @param val
	 * @return
	 */
	public int randomHalf(double val){
		return (int) Math.round( val / ( Math.random() + 1.5 ) );
	}
	
}
