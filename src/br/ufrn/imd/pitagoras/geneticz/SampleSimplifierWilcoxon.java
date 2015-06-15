package br.ufrn.imd.pitagoras.geneticz;

import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;

/**
 * Simplificador de amostras que utiliza o teste estatistico "Wilcoxon"
 * 
 * @author pitagoras
 *
 */
public class SampleSimplifierWilcoxon extends SampleSimplifier {

	/**
	 * 
	 */
	public SampleSimplifierWilcoxon() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean testWithPValue(double[] caseArray, double[] controlArray,
			double pValue) {
		return (new WilcoxonSignedRankTest().wilcoxonSignedRankTest(caseArray, controlArray, false) <= pValue);
	}
	
	@Override
	protected double test(double[] caseArray, double[] controlArray) {
		// TODO Auto-generated method stub
		WilcoxonSignedRankTest temp = new WilcoxonSignedRankTest();
		return temp.wilcoxonSignedRankTest(caseArray, controlArray, false);
	}

}
