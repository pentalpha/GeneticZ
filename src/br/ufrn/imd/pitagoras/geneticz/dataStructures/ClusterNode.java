package br.ufrn.imd.pitagoras.geneticz.dataStructures;

import java.util.LinkedList;

/**
 * Agrupamento de amostras genicas.
 * @author pitagoras
 *
 */
public class ClusterNode extends BinNodeDouble{

	private String name;
	private AVLTreeDouble genes;
	
	/**
	 * Construtor para o ClusterNode
	 * @param id Chave do cluster.
	 * @param name Nome do cluster.
	 * @param tree Amostra genica armazenada neste cluster.
	 */
	public ClusterNode(int id, String name, AVLTreeDouble tree) {
		super(id, 0.0);
		this.name = name;
		genes = tree;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Une dois clusters, criando um cluster que é pai dos dois.
	 * @param b Cluster para ser unido com este cluster.
	 * @param difference Diferença entre os dois clusters, para ser armazenada.
	 * @return Pai dos dois clusters já unidos.
	 */
	public ClusterNode union(ClusterNode b, double difference){
		AVLTreeDouble genes = new AVLTreeDouble();
		ClusterNode a = (ClusterNode) this.getRoot();
		b = (ClusterNode) b.getRoot();
		genes.setRoot(averageGenes(a.getGenes().getRoot(), b.getGenes().getRoot()));
		ClusterNode father = new ClusterNode(0, "(" + a.getName() + ")(" + b.getName() + ")", genes);
		father.setData(difference);
		father.setLeftSon(a);
		a.setFather(father);
		father.setRightSon(b);
		b.setFather(father);
		father.setFather(null);
		/*this.setFather(father);
		b.setFather(father);*/
		
		return father;
	}
	
	public AVLTreeDouble getGenes(){
		return genes;
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * Cria uma amostra gênica que é a media de duas outras amostras.
	 * As duas amostras devem ser identicas.
	 * @param a Amostra A
	 * @param b Amostra B
	 * @return Média entre os genes da amostra A e da B.
	 */
	public static BinNodeDouble averageGenes(BinNodeDouble a, BinNodeDouble b){
		int key = a.getKey();
		
		double averageValue = (a.getData() + b.getData()) / 2;
		
		/*if(b != null && a != null){
			averageValue = (a.getData() + b.getData()) / 2;
			key = a.getKey();
		}else if(b != null){
			averageValue = b.getData();
			key = b.getKey();
		}else{
			averageValue = a.getData();
			key = a.getKey();
		}*/
		BinNodeDouble node = new BinNodeDouble(key, averageValue);
		if(a.getRightSon() != null){
			node.setRightSon(ClusterNode.averageGenes(a.getRightSon(), b.getRightSon()));
		}else{
			node.setRightSon(null);
		}
		
		if(a.getLeftSon() != null){
			node.setLeftSon(ClusterNode.averageGenes(a.getLeftSon(), b.getLeftSon()));
		}else{
			node.setLeftSon(null);
		}
		return node;
	}
	
	/**
	 * Calcula a diferença entre este cluster e outro.
	 * @param b Segundo cluster a partir do qual a diferença será calculada.
	 * @return Diferença entre este cluster e o cluster "b".
	 */
	public Double difference (ClusterNode b){
		if(b == this){
    		return 0.0;
    	}else{
    		double sum = 0.0;
    		double term;
    		for(BinTreeIteratorDouble i = this.genes.getIterator(), j = b.getGenes().getIterator();
    				i.hasActual() && j.hasActual(); i.iterate(), j.iterate()){
                term = i.get() - j.get();
                
                
    			
                term = term * term;
                sum += term;
            }
    		double square = Math.sqrt(sum);
    		return square;
    	}
	}
	
	/**
	 * @return Vetor com todas as folhas a partir desde Cluster.
	 */
	public ClusterNode[] leafSet(){
		LinkedList<ClusterNode> list = ClusterNode.leafList(this);
		System.out.println("folhas: " + list.size());
		ClusterNode[] leafs = new ClusterNode[list.size()];
		int x = 0;
		for(ClusterNode cluster : list){
			leafs[x] = cluster;
			x++;
		}
		return leafs;
	}
	
	private static LinkedList<ClusterNode> leafList(ClusterNode node){
		LinkedList<ClusterNode> list = new LinkedList<ClusterNode>();
		if(node != null){
			if(node.isLeaf()){
				list.add(node);
			}else{
				if(node.getRightSon() != null){
					list.addAll(ClusterNode.leafList((ClusterNode)node.getRightSon()));
				}
				if(node.getLeftSon() != null){
					list.addAll(ClusterNode.leafList((ClusterNode)node.getLeftSon()));
				}
			}
		}
		return list;
	}
	
	public String toString(){
		return this.getName();
	}
}
