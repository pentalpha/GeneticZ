package br.ufrn.imd.pitagoras.geneticz;

import java.io.File;
import java.util.Scanner;

import br.ufrn.imd.pitagoras.geneticz.dataStructures.AVLTreeDouble;
import br.ufrn.imd.pitagoras.geneticz.dataStructures.HeapMaxDouble;
import br.ufrn.imd.pitagoras.geneticz.dataStructures.HeapMinDouble;
/**
 * Classe que é capaz de converter uma assinatura gênica "bruta" numa assinatura menor
 * que contém apenas os genes relevantes.
 * 
 * @author pitagoras
 */
public abstract class SampleSimplifier {
	
	/**
	 * Dados relativos aos grupos das amostras geneticas.
	 * Apenas disponivel após o método "getSignature" ser chamado pelo menos uma vez.
	 */
	public static SampleGroupsData groupsData = null;
	
	/**
	 * Retorna a assinatura gênica processada.
	 * 
	 * @param samplesFile Arquivo com as amostras geneticas.
	 * @param groupsFile Arquivo com a descrição dos grupos dessas amostras geneticas.
	 * @param pValue Valor estatistico para determinar quais genes são relevantes
	 * @param maxGenes Numero maximo de genes na amostra.
	 * @return Lista de amostras geneticas.
	 */
	public AVLTreeDouble[] getSignature(File samplesFile, File groupsFile, double pValue, int maxGenes){
		
		groupsData = new SampleGroupsData(groupsFile);
		
		AVLTreeDouble[] signature = new AVLTreeDouble[groupsData.getNumberOfSamples()];
		for(int i = 0; i < signature.length; i++){
			signature[i] = new AVLTreeDouble();
		}
		
		int numberOfGenes = 0;
		
		try{
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(samplesFile);
			while(scanner.hasNextLine()){
				numberOfGenes++;
				scanner.nextLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Numero de genes: " + numberOfGenes);
		
		/*System.out.println("Casos: " + groupsData.getNumberOfCaseSamples());
		System.out.println("Controle: " + groupsData.getNumberOfControlSamples());
		for(int i = 0; i < groupsData.getNumberOfSamples(); i++){
			System.out.println(i+1);
			System.out.println(groupsData.getGroupOfX(i));
		}*/
		
		//populateSignature(signature, groupsData, samplesFile, testOption, pValue);
		
		try{
			Scanner scanner = new Scanner(samplesFile);
			System.out.println("maxGenes lido: " + maxGenes);
			HeapMinDouble<Double[]> heap = new HeapMinDouble<Double[]>(numberOfGenes);
			int inseridos = 0;
			while(scanner.hasNextLine()){
				String[] words = scanner.nextLine().split("\t");
				double[] caseArray = new double[groupsData.getNumberOfCaseSamples()];
				double[] controlArray = new double[groupsData.getNumberOfControlSamples()];
				
				int nextEmptyCase = 0, nextEmptyControl = 0;
						
				for(int i = 1; i < words.length; i++){
					String type = groupsData.getGroupOfX(i-1);
					if(type.equals("caso")){
						caseArray[nextEmptyCase] = Double.parseDouble(words[i]);
						nextEmptyCase++;
					}else if(type.equals("controle")){
						controlArray[nextEmptyControl] = Double.parseDouble(words[i]);
						nextEmptyControl++;
					}
				}
				
				//boolean isSignificant= testWithPValue(caseArray, controlArray, pValue);
				double pValueResult = test(caseArray, controlArray);
				
				if(pValueResult <= pValue){
					System.out.println(pValueResult);
					Double[] genes = new Double[words.length-1];
					
					
					for(int i = 1; i < words.length; i++){
						genes[i-1] = Double.parseDouble(words[i]);
					}
					
					heap.insert(pValueResult, genes);
					inseridos++;
					
					
					/*for(int i = 0; i < signature.length; i++){
						
						Double data = Double.parseDouble(words[i+1]);
						
						signature[i].insert(key, data);
						
					}
					for(int i = 1; i < signature.length; i++){
						
						if(!signature[0].toString().equals(signature[i].toString())){
							System.out.println("Matriz 0 diferente de " + i);
						}
						
					}*/
				}
			}
			System.out.println("Inseridos: " + inseridos);
			int removidos = 0, inserts = 0;
			for(int x = 0; x < maxGenes && heap.getSize() > 0; x++){
				Double[] max = heap.getMin();
				
				//if(node.getKey() >= pValue){
				for(int i = 0; i < signature.length; i++){
					int numberOfElements = AVLTreeDouble.numberOfElements(signature[i].getRoot());
					signature[i].insert(max[i]);
					if(numberOfElements != (AVLTreeDouble.numberOfElements(signature[i].getRoot()) -1)){
						System.out.println("antes da inserção:" + numberOfElements +
											" após a inserção " + (AVLTreeDouble.numberOfElements(signature[i].getRoot())));
					}
					inserts++;
				}
				heap.removeMax();
				removidos++;
				//}
			}
			System.out.println("Removidos: " + removidos + ", Inserts = " + inserts + ", Inserts per Sample: " + (inserts / signature.length));
			System.out.println(heap.getSize());
			for(AVLTreeDouble sample : signature){
				System.out.println(AVLTreeDouble.numberOfElements(sample.getRoot()));
			}
			scanner.close();
			if(removidos <= 0){
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		//signature[0].print();
		
		return signature;
	}
	
	/**
	 * Determina se um gene é relevante ou não.
	 * @param caseArray Valores de expressão do gene nos casos.
	 * @param controlArray Valores de expressão do gene nos casos de controle.
	 * @param pValue Valor estatistico comparativo.
	 * @return Se o gene é relevante ou não.
	 */
	protected abstract boolean testWithPValue(double[] caseArray, double[] controlArray, double pValue);
	
	/**
	 * Calcula o pValue resultante do teste entre as amostras de caso e de controle
	 * @param caseArray Valores de expressão do gene nos casos.
	 * @param controlArray Valores de expressão do gene nos casos de controle.
	 * @return pValue
	 */
	protected abstract double test(double[] caseArray, double[] controlArray);

}
