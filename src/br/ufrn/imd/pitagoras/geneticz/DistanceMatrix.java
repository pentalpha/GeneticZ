package br.ufrn.imd.pitagoras.geneticz;

import br.ufrn.imd.pitagoras.geneticz.dataStructures.AVLTreeDouble;
import br.ufrn.imd.pitagoras.geneticz.dataStructures.ClusterNode;
import br.ufrn.imd.pitagoras.geneticz.windows.TableDialog;
/**Matrix de distancias utilizada para agrupar uma 
 * assinatura em um conjunto de clusters.
 * 
 * @author pitagoras
 */
public class DistanceMatrix {
	private Double[][] matrix;
	private ClusterNode[] clusters;
	private int space;
	
	/**
	 * Construtor da classe DistanceMatrix
	 * 
	 * @param signature Assinatura genica a partir da qual será gerado o cluster final.
	 */
	public DistanceMatrix(AVLTreeDouble[] signature) {
		space = signature.length;
		clusters = new ClusterNode[space];
		matrix = new Double[space][space];
		//generateInitialMatrix(signature);
		for(int i = 0; i < space; i++){
			clusters[i] = new ClusterNode(i, SampleSimplifier.groupsData.getSampleName(i), signature[i]);
		}
		generateDifferenceMatrix();
	}
	
	public int size(){
		return space;
	}
	
	
	/**
	 * Agrupa e processa os clusters obtidos a partir 
	 * da assinatura genica até obter um cluster final.
	 */
	public void clustering(){
		//ClusterNode[] unions = new ClusterNode[space];
		//int unionsMade = 0;
		while(space > 1){
			for(int y = 0; y < space; y++){
				if(clusters[y] == (ClusterNode)clusters[y].getRoot()){
					Integer smallerDifference = null;
					for(int x = 0; x < space; x++){
						if(!(x == y)){
							if(smallerDifference == null){
								smallerDifference = x;
							}else if(matrix[x][y] < matrix[smallerDifference][y]){
								smallerDifference = x;
							}
						}
					}
					if((smallerDifference != null  && clusters[smallerDifference] != clusters[y]) 
						&& (clusters[smallerDifference] == (ClusterNode)clusters[smallerDifference].getRoot())){
						ClusterNode united = clusters[y].union((ClusterNode)clusters[smallerDifference].getRoot(),
											matrix[smallerDifference][y]);
						united.setHeight();
					}
				}
			}
			
			for(int i = 0; i < space; i++){
				clusters[i] = (ClusterNode)clusters[i].getRoot();
			}
			
			for(int i = 0; i < space; i++){
				if(clusters[i] != null){
					Integer j = null;
					for(int z = i+1; z < space; z++){
						if(i != z){
							if(clusters[z] == clusters[i]){
								j = z;
								break;
							}
						}
					}
					if(j!=null){
						//clusters[Math.max(j, i)] = null;
						for(int z = j+1; z < space; z++){
							clusters[z-1] = clusters[z];
						}
						clusters[space-1] = null;
					}
				}
			}
			/*for(int i = 0; i < space; i++){
				if(clusters[i] == null){
					
				}
			}*/
			for(int i = 0; i < space; i++){
				if(clusters[i] == null){
					space = i;
					break;
				}
			}
			this.generateDifferenceMatrix();
		}
	}
	
	/*public void iterate(){
		while(space > 1){
			Point2D element = getSmallerDifference();
			ClusterNode clusterC = clusters[element.x].union(clusters[element.y],
															matrix[element.x][element.y]);
			matrix[element.x][element.y] = null;
			ClusterNode.setHeight(clusterC);
			if(element.x != space-1){
				clusters[element.x] = clusters[space-1];
			}
			space--;
			clusters[element.y] = clusterC;
			generateDifferenceMatrix();
			for(int i = 0; i < clusters.length; i++){
				clusters[i] = (ClusterNode)(clusters[i].getRoot());
			}
			
		}
	}
	
	public void iterate2(){
		int dialogPositionDelta = 35;
		while(space > 1){
			ClusterNode[] aux = new ClusterNode[space];
			showDistanceMatrix(dialogPositionDelta, dialogPositionDelta);
			dialogPositionDelta += 40;
			while(true){
				Point2D element = this.getSmallerDifference();
				if(element != null){
					if(clusters[element.x] != null && clusters[element.y] != null){
						
						ClusterNode united = clusters[element.x].union(clusters[element.y],
								matrix[element.x][element.y]);
						ClusterNode.setHeight(united);
						aux[element.y] = united;
						clusters[element.y] = null;
						clusters[element.x] = null;
						matrix[element.x][element.y] = null;
						matrix[element.y][element.x] = null;
						
					}else{
						break;
					}
				}else{
					break;
				}
				
			}
			
			for(int i = 0; i < space; i++){
				if(aux[i] != null){
					clusters[i] = aux[i];
				}
			}
			for(int i = 0; i < space-1; i++){
				if(clusters[i] == null){
					int auxInt = i;
					for(int j = i+1; j < space; j++){
						if(clusters[j] != null){
							auxInt = j;
						}
					}
					clusters[i] = clusters[auxInt];
					clusters[auxInt] = null;
				}
			}
			
			while(clusters[space-1] == null){
				space--;
				if(space == 0){
					break;
				}
			}
			
			generateDifferenceMatrix();
		}
	}*/
	
	/*public void iterate3(){
		while(space > 1){
			ClusterNode[] aux = new ClusterNode[space];
			while(true){
				Point2D element = this.getSmallerDifference();
				if(clusters[element.x] != null && clusters[element.y] != null){
					ClusterNode united = clusters[element.x].union(clusters[element.y],
							matrix[element.x][element.y]);
					ClusterNode.setHeight(united);
					aux[element.y] = united;
					clusters[element.y] = null;
					clusters[element.x] = null;
					matrix[element.x][element.y] = null;
					matrix[element.y][element.x] = null;
				}else{
					break;
				}
			}
			
			for(int i = 0; i < space; i++){
				if(aux[i] != null){
					clusters[i] = aux[i];
				}
			}
			for(int i = 0; i < space-1; i++){
				if(clusters[i] == null){
					int auxInt = i;
					for(int j = i+1; j < space; j++){
						if(clusters[j] != null){
							auxInt = j;
						}
					}
					clusters[i] = clusters[auxInt];
					clusters[auxInt] = null;
				}
			}
			
			while(clusters[space-1] == null){
				space--;
			}
			
			generateDifferenceMatrix();
		}
	}
	
	public void iterate4(){
			Point2D element;
			do{
				element = getSmallerDifference();
				if(element != null){
					if(clusters[element.x] != clusters[element.y]){
						ClusterNode united = clusters[element.x].union(clusters[element.y],
								matrix[element.x][element.y]);
						ClusterNode.setHeight(united);
						//clusters[element.x] = clusters[element.y] = united;
						matrix[element.x][element.y] = null;
						matrix[element.y][element.x] = null;
					}
				}
				for(int i = 0; i < space; i++){
					//System.out.println(", " + clusters[i].getName)
					clusters[i] = (ClusterNode)clusters[i].getRoot();
				}
				
			}while(element != null);
	}*/
	
	/*public boolean iterate(){
		if(space <= 1){
			return false;
		}else{
			//Double[] a, b/*, c*/;
			//a = new Double[space];
			//b = new Double[space];
			//c = new Double[space];
			//Point2D element = getSmallerDifference();
			//System.out.println("union de " + element.x + ": " + clusters[element.x].getName() + 
			//						" e "+ element.y + ": " + clusters[element.y].getName() +
			//						" - " + Double.toHexString(matrix[element.x][element.y]));
			/*for(int i = 0; i < space; i++){
				a[i] = matrix[element.x][i];
				b[i] = matrix[element.y][i];
				//c[i] = (a[i] + b[i]) / 2;
			}*/
			
			//ClusterNode clusterC = clusters[element.x].union(clusters[element.y],
			//												matrix[element.x][element.y]);
			/*if((AVLTreeDouble.numberOfElements(clusterC.getGenes().getRoot()) != AVLTreeDouble.numberOfElements(clusters[element.x].getGenes().getRoot())) ||
			(AVLTreeDouble.numberOfElements(clusterC.getGenes().getRoot()) != AVLTreeDouble.numberOfElements(clusters[element.y].getGenes().getRoot()))){
				System.out.println("Elements in C(" + clusterC.getName() + "): " 
									+ AVLTreeDouble.numberOfElements(clusterC.getGenes().getRoot()));
				System.out.println("Elements in A(" + clusters[element.x].getName() + "): " 
						+ AVLTreeDouble.numberOfElements(clusters[element.x].getGenes().getRoot()));
				System.out.println("Elements in B(" + clusters[element.y].getName() + "): " 
						+ AVLTreeDouble.numberOfElements(clusters[element.y].getGenes().getRoot()));
			}
			//ClusterNode.setHeight(clusterB);
			//ClusterNode.setHeight(clusterC);*/
			//ClusterNode.setHeight(clusterC);
			
			
			//if(element.x != space-1){
				//copyIndex(space-1, element.x);
			//	clusters[element.x] = clusters[space-1];
			//}
			
			//space--;
			
			/*clusters[element.y] = clusters[space];
			System.out.println("Copia " + (space) + "(" + clusters[space].getName() + ") para " 
					+ element.y + "(" + clusters[element.y].getName() + ")");
			clusters[element.x] = clusters[space-1];
			System.out.println("Copia " + (space-1) + "(" + clusters[space-1].getName() + ") para "
					+ element.x + "(" + clusters[element.x].getName() + ")");*/
			
			//c[element.x] = c[space];
			//c[element.x] = c[space-1];
			
			//clusters[element.y] = clusterC;
			
			//generateDifferenceMatrix();
			/*for(int i = 0; i < space; i++){
				matrix[element.y][i] = clusterC.getGenes().difference(clusters[i].getGenes());
				matrix[i][element.y] = clusterC.getGenes().difference(clusters[i].getGenes());
			}*/
			
			
			//System.out.println("Copia clusterC(" + clusterC.getName() + ") para "
				//	+ (space-1) + "(" + clusters[space-1].getName() + ")");
			
			//for(int i = 0; i < space; i++){
				//matrix[i][i] = 0.0;
			//}
			
			//for(int i = 0; i < clusters.length; i++){
			//	clusters[i] = (ClusterNode)(clusters[i].getRoot());
				//}
			
			//System.out.println(ClusterNode.subLeafs(clusterC));
			
			//return true;
			//}
			//}*/
	/**
	 * Exibe um dialogo com uma uma tabela que descreve o estado atual da matrix de distancias.		
	 * @param x Posição x em relação a janela principal.
	 * @param y Posição y em relação a janela principal.
	 */
    public void showDistanceMatrix(int x, int y){
		String[] columns = new String[size()];
		for(int i = 0; i < columns.length; i++){
			columns[i] = getClusters()[i].getName();
		}
		String[][] table = new String[space][space];
		for(int i = 0; i < space; i++){
			for(int j = 0; j < space; j++){
				table[i][j] = Double.toString(matrix[i][j]);
			}
		
		}
		TableDialog dialog = new TableDialog(table, columns, "Matriz de Distancias", x, y);
		dialog.setVisible(true);
	}
	
	/**
	 * @return Se o processo de agrupamento já acabou e resta apenas um cluster.
	 */
	public boolean clusterEnded(){
		for(int i = 0; i < space; i++){
			//System.out.println(", " + clusters[i].getName)
			if(clusters[i].getRoot() != clusters[1].getRoot()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * A partir dos clusters atuais, este metodo atualiza a matriz de distancias.
	 */
	private void generateDifferenceMatrix(){
		for(int i = 0; i < space; i++){
			clusters[i].setHeight();
			for(int j = 0; j < space; j++){
				Double difference = clusters[i].difference(clusters[j]);
				matrix[i][j] = difference;
			}
		}
		
	}
	
	public ClusterNode[] getClusters(){
		return clusters;
	}
	
	public Double[][] getMatrix(){
		return matrix;
	}
	
	/**
	 * Procura pela menor diferença entre dois clusters na matriz de distancias.
	 * 
	 * @return Cluster X e Cluster Y da diferença em questão. Retorna null caso nenhuma diferença seja encontrada.
	 * 
	 */
	@SuppressWarnings("unused")
	private Point2D getSmallerDifference(){
		Integer smallerX = null, smallerY = null;
		for(int x = 0; x < space; x++){
			for(int y = 0; y < x; y++){
				if(matrix[x][y] != null){
					if(smallerX == null && smallerY == null){
						smallerX = x;
						smallerY = y;
					}else{
						if(matrix[x][y] < matrix[smallerX][smallerY]){
							smallerX = x;
							smallerY = y;
						}
					}
				}
			}
		}
		if(smallerX == null){
			return null;
		}else{
			return new Point2D(smallerX, smallerY);
		}
	}
	
	/*private void copyIndex(int x, int y){
		
		for (int i = 0; i < space; i++){
			matrix[y][i] = matrix[x][i];
			matrix[i][y] = matrix[i][x];
		}
		clusters[y] = clusters[x];
	}*/
	
	

}