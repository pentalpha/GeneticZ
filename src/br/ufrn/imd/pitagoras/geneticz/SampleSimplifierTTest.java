package br.ufrn.imd.pitagoras.geneticz;

import org.apache.commons.math3.stat.inference.TTest;

/**
 * Simplificador de amostras que usa o teste estatistico "T de Student".
 * 
 * @author pitagoras
 *
 */
public class SampleSimplifierTTest extends SampleSimplifier {

	/**
	 * 
	 */
	public SampleSimplifierTTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean testWithPValue(double[] caseArray, double[] controlArray,
			double pValue) {
		// TODO Auto-generated method stub
		return (new TTest().tTest(caseArray, controlArray) <= pValue);
	}
	
	@Override
	protected double test(double[] caseArray, double[] controlArray) {
		// TODO Auto-generated method stub
		TTest temp = new TTest();
		return temp.tTest(caseArray, controlArray);
	}

}
