package br.ufrn.imd.pitagoras.geneticz;

/**
 * Fabrica para simplificadores de amostras.
 * @author pitagoras
 */
public abstract class TestFactory {
	
	/**
	 * Testes estatisticos disponiveis.
	 * @author pitagoras
	 */
	public enum TestType{
		WILCOXON,
		T_STUDENT
	}
	
	/**
	 * Instancia um um simplificador de amostras de acordo com o tipo de teste definido.
	 * 
	 * @param test Tipo de teste estatistico.
	 */
	public static SampleSimplifier create(TestType test){
		if(test == TestType.WILCOXON){
			return new SampleSimplifierWilcoxon();
		}else if(test == TestType.T_STUDENT){
			return new SampleSimplifierTTest();
		}else{
			return null;
		}
	}

}
