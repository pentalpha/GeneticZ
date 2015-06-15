package br.ufrn.imd.pitagoras.geneticz;

import java.io.File;
import java.util.Scanner;

/**
 * Armazena informações quanto aos grupos de amostras lidos.
 * @author pitagoras
 *
 */
public class SampleGroupsData{
	int numberOfCaseSamples;
	int numberOfControlSamples;
	private String[] sampleNames;
	private String[] groups;
	
	/**
	 * Construtor para a classe SampleGroupsData
	 * @param groupsFile Arquivo que lista os grupos de amostras.
	 */
	@SuppressWarnings("resource")
	public SampleGroupsData(File groupsFile){
		Scanner scanner;
		try{
			scanner = new Scanner(groupsFile);
			int lines = 0;
			numberOfCaseSamples = numberOfControlSamples = 0;
			while(scanner.hasNextLine()){
				scanner.nextLine();
				lines++;
			}
			
			sampleNames = new String[lines];
			groups = new String[lines];

			scanner = new Scanner(groupsFile);
			scanner.useDelimiter("\n ");
			
			String line;
			String[] words;
			while(scanner.hasNextLine()){
				line = scanner.nextLine();
				words = line.split(" ");
				int sampleNumber = Integer.parseInt(words[0].replace("CT", "").replace("c", ""));
				if (words[1].equals("caso")){
					numberOfCaseSamples++;
				}else if(words[1].equals("controle")){
					numberOfControlSamples++;
				}
				sampleNames[sampleNumber-1] = words[0];
				groups[sampleNumber-1] = words[1];
			}
			scanner.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int getNumberOfCaseSamples(){
		return numberOfCaseSamples;
	}
	
	public int getNumberOfControlSamples(){
		return numberOfControlSamples;
	}
	
	public int getNumberOfSamples(){
		return groups.length;
	}
	
	/**
	 * Obter o nome de uma respectiva amostra
	 * @param i Indice da amostra na assinatura gênica.
	 * @return Nome descrito no arquivo de grupos.
	 */
	public String getSampleName(int i){
		return sampleNames[i];
	}
	
	/**
	 * Determina o grupo de uma determinada amostra
	 * 
	 * @param x Indice da amostra.
	 * @return Grupo ao qual ela pertence.
	 */
	public String getGroupOfX(int x){
		try{
			return groups[x];
		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Index " + x + " not found. The array goes from 0 to " + (groups.length-1));
			e.printStackTrace();
			return "";
		}
		
	}
}