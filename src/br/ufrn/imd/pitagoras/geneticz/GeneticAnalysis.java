package br.ufrn.imd.pitagoras.geneticz;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.imageio.ImageIO;

import br.ufrn.imd.pitagoras.geneticz.TestFactory.TestType;
import br.ufrn.imd.pitagoras.geneticz.dataStructures.AVLTreeDouble;
import br.ufrn.imd.pitagoras.geneticz.dataStructures.ClusterNode;

import com.thoughtworks.xstream.*;
/**
 * Classe que engloba toda uma analise genetica.
 * 
 * @author pitagoras
 */
public class GeneticAnalysis {
	
	/*public enum AnalysisState{
		RECEM_CRIADO,
		ASSINATURA_GENICA,
		CLUSTERING,
		TREE_MADE
	}*/
	
	//private AnalysisState analysisState;
	private String folder;
	private String analysisName;
	private AVLTreeDouble[] signature;
	private DistanceMatrix distanceMatrix;
	private ClusterNode cluster;
	private Dendogram dendogram;
	private TestType test;
	private double pValue;
	
	/**
	 * Construtor para iniciar uma nova assinatura gênica do zero.
	 * 
	 * @param newSignature Conjunto de amostras gênicas da analise.
	 * @param test Tipo de teste estatistico utilizado para obter a assinatura.
	 * @param pValue Valor comparativo para selecionar os genes mais significativos.
	 */
	public GeneticAnalysis(AVLTreeDouble[] newSignature, TestType test, double pValue){
		this.test = test;
		this.pValue = pValue;
		signature = newSignature;
		distanceMatrix = new DistanceMatrix(signature);
		
		distanceMatrix.clustering();
		
		this.cluster = distanceMatrix.getClusters()[0];
		dendogram = new Dendogram(distanceMatrix.getClusters()[0], 34, 50);
		
	}
	
	/**
	 * Construtor que carrega uma analise gênica a partir de um arquivo.
	 * 
	 * @param analysisFile Arquivo de analise gênica previamente salvo.
	 */
	public GeneticAnalysis(File analysisFile){
		XStream xmlStream = new XStream();
		GeneticAnalysis tempAnalysis = 
				(GeneticAnalysis) xmlStream.fromXML(analysisFile);
		this.setFolder(tempAnalysis.getFolder());
		this.setName(tempAnalysis.getName());
		this.setSignature(tempAnalysis.getSignature());
		this.setMatrix(tempAnalysis.getMatrix());
		this.setDendogram(tempAnalysis.getDendogram());
		this.setTest(tempAnalysis.getTestType());
		this.setPValue(tempAnalysis.getPValue());
	}
	
	/**
	 * Salva os dados da analise num arquivo.
	 */
	public void saveData(){
		BufferedWriter bw = null;
		try{
			String analysisFile = "";
			//analysisFile += analysisState.toString() + "\n";
			//analysisFile += folder + "\n";
			//analysisFile += analysisName;
			
			XStream xmlStream = new XStream();
			analysisFile = xmlStream.toXML(this);
			
			File file = new File(folder + analysisName + ".gAnalysis");
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
			
			bw.write(analysisFile);
			
			BufferedImage bi = dendogram.getImage();
			File outputfile = new File(folder + analysisName +".png");
			ImageIO.write(bi, "png", outputfile);
		}catch (Exception e){
			
		}finally{
			try{
				bw.close();
			}catch (Exception e){
				
			}
		}
	}
	
	/**
	 * Aplica a técnica de validação cruzada para testar a consistencia das amostras.
	 * @return Descrição do teste realizado.
	 */
	public String crossValidation(){
		String testResult= "";
		ClusterNode[] rightGroup = ((ClusterNode)cluster.getRightSon()).leafSet();
		ClusterNode[] leftGroup = ((ClusterNode)cluster.getLeftSon()).leafSet();
		
		testResult += "Right group (" + rightGroup.length + "): ";
		for(int i = 0; i < rightGroup.length; i++){
			testResult += rightGroup[i].toString() + ", ";
		}
		
		testResult += "\nLeft group (" + leftGroup.length + "): ";
		for(int i = 0; i < leftGroup.length; i++){
			testResult += leftGroup[i].toString() + ", ";
		}
		
		for(int i = 0; i < signature.length; i++){
			AVLTreeDouble[] testSignature = new AVLTreeDouble[signature.length-1];
			int x = 0;
			for(int j = 0; j < i; j++){
				testSignature[x] = signature[j];
				x++;
			}
			
			for(int j = i+1; j < signature.length; j++){
				testSignature[x] = signature[j];
				x++;
			}
			System.out.println("Testing without: " + i);
			
			DistanceMatrix testDistanceMatrix = new DistanceMatrix(testSignature);
			
			testDistanceMatrix.clustering();
			
			ClusterNode testCluster = testDistanceMatrix.getClusters()[0];
			
			ClusterNode[] testRightGroup = ((ClusterNode)testCluster.getRightSon()).leafSet();
			ClusterNode[] testLeftGroup = ((ClusterNode)testCluster.getLeftSon()).leafSet();
			
			for(ClusterNode testNode : testRightGroup){
				for(ClusterNode leftGroupNode : leftGroup){
					if(testNode.getName().equals(leftGroupNode.getName())){
						testResult += "\nTesting without " + SampleSimplifier.groupsData.getSampleName(i) + ": ";
						testResult += "\nThe sample " + testNode.getName() + " of the left group was finded in the right group.";
						testResult += "\nTHE SAMPLES DO NOT HAVE PASSED ON THE CROSS-VALIDATION!";
						return testResult;
					}
				}
			}
			
			for(ClusterNode testNode : testLeftGroup){
				for(ClusterNode rightGroupNode : rightGroup){
					if(testNode.getName().equals(rightGroupNode.getName())){
						testResult += "\nTesting without " + SampleSimplifier.groupsData.getSampleName(i) + ": ";
						testResult += "\nThe sample " + testNode.getName() + " of the right group was finded in the left group.";
						testResult += "\nTHE SAMPLES DO NOT HAVE PASSED ON THE CROSS-VALIDATION!";
						return testResult;
					}
				}
			}
		}
		
		testResult += "\nCROSS-VALIDATION SUCCESSFULL!";
		
		return testResult;
	}

	
	public Dendogram getDendogram(){
		return dendogram;
	}
	
	public void setDendogram(Dendogram dendogram){
		this.dendogram = dendogram;
	}
	
	public ClusterNode getCluster(){
		return cluster;
	}
	
	public void setCluster(ClusterNode cluster){
		this.cluster = cluster;
	}
	
	public DistanceMatrix getMatrix(){
		return distanceMatrix;
	}
	
	public void setMatrix(DistanceMatrix distanceMatrix){
		this.distanceMatrix = distanceMatrix;
	}
	
	public AVLTreeDouble[] getSignature(){
		return signature;
	}
	
	public void setSignature(AVLTreeDouble[] signature){
		this.signature = signature;
	}
	
	public String getFolder(){
		return folder;
	}
	
	public void setFolder(String folder){
		this.folder = folder;
	}
	
	public String getName(){
		return analysisName;
	}
	
	public void setName(String name){
		this.analysisName = name;
	}
	
	public void setTest(TestType type){
		this.test = type;
	}
	public TestType getTestType(){
		return test;
	}
	public void setPValue(double pValue){
		this.pValue = pValue;
	}
	
	public double getPValue(){
		return pValue;
	}
}