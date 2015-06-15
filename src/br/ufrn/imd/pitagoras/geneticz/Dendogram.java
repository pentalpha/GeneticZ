package br.ufrn.imd.pitagoras.geneticz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import br.ufrn.imd.pitagoras.geneticz.dataStructures.BinNodeDouble;
import br.ufrn.imd.pitagoras.geneticz.dataStructures.ClusterNode;

/**Classe que representa um dendograma, 
 * contendo tanto o conjunto de clusters como a imagem renderizada deles.
 * 
 * @author Pitágoras Alves
 *
 */
public class Dendogram {
	private BufferedImage image;
	private ClusterNode cluster;
	//private BinNodeDouble[] leafs;
	//private int widthPerLeaf, 
	private int heightPerLevel;
	static private Color lineColor = new Color(0x2020D0);
	static private Color backgroundColor = new Color(230, 230, 255);
	static private Stroke line = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	static private Stroke smallLine = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	//static private Color boxColor = new Color(230, 230, 255);
	
	/**Construtor para a classe Dendograma
	 * @param cluster O Cluster a partir do qual será criado e desenhado o dendograma;
	 * @param widthPerLeaf Quantidade de pixels de largura que cada folha do dendograma terá na tela.
	 * @param heightPerLevel Quantidade de pixels de altura que cada nivel do dendograma terá.
	 */
	public Dendogram(ClusterNode cluster, int widthPerLeaf, int heightPerLevel){
		this.cluster = cluster;
		int leafsCount = ClusterNode.subLeafs(cluster);
		System.out.println(leafsCount);
		
		//this.widthPerLeaf = widthPerLeaf;
		this.heightPerLevel = heightPerLevel;
		int width = heightPerLevel*2 + leafsCount*widthPerLeaf;
		int height = (cluster.getHeight() * heightPerLevel) + 100;
		int imageType = java.awt.image.IndexColorModel.OPAQUE;
		image = new BufferedImage(width, height, imageType);
		drawTreeOnImage();
	}
	
	/**
	 * Renderiza o dendograma.
	 */
	private void drawTreeOnImage(){
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
		drawClusterOnImage(cluster, g2d, 10, image.getWidth()-10, 
				20, image.getHeight()-20);
	}
	
	/**
	 * Função que recursivamente desenha cada cluster numa imagem.
	 * 
	 */
	private void drawClusterOnImage(BinNodeDouble cluster, Graphics2D g2d, float startX, float endX,
			float y, float maxY){
		
		float widthPerLeaf = (endX-startX) / ClusterNode.subLeafs(cluster);
		float x = (startX + endX) / 2;
		float endY = y + heightPerLevel;
		if(cluster.isLeaf()){
			endY = maxY;
		}
		
		g2d.setColor(lineColor);
		g2d.setStroke(line);
		g2d.drawLine((int)x, (int)y, (int)x, (int)endY);
		
		if(cluster.isLeaf()){
			g2d.setColor(backgroundColor);
			g2d.fillRect((int)x-11, (int)endY, 25, 15);
			g2d.setStroke(smallLine);
			g2d.setColor(lineColor);
			g2d.drawRect((int)x-11, (int)endY, 25, 15);
			g2d.drawString(((ClusterNode)cluster).getName(), x-10, endY+12);
		}
		
		if(cluster.getRightSon() != null && cluster.getLeftSon() != null){
			float leftStartX = startX;
			float leftEndX = startX + ClusterNode.subLeafs(cluster.getLeftSon())*widthPerLeaf;
			if(leftEndX == startX){
				leftEndX += widthPerLeaf;
			}
			float rightStartX = leftEndX;
			float rightEndX = leftEndX + ClusterNode.subLeafs(cluster.getLeftSon())*widthPerLeaf;
			rightEndX = endX;
			
			g2d.setColor(lineColor);
			g2d.setStroke(line);
			g2d.drawLine((int) (leftStartX + leftEndX) / 2, (int)endY, (int) (rightStartX + rightEndX) / 2, (int) endY);
			
			drawClusterOnImage(cluster.getLeftSon(), g2d, leftStartX, leftEndX, endY, maxY);
			drawClusterOnImage(cluster.getRightSon(), g2d, rightStartX, rightEndX, endY, maxY);
		}else if(cluster.getLeftSon() != null){
			drawClusterOnImage(cluster.getLeftSon(), g2d, startX, endX, endY, maxY);
		}else if(cluster.getRightSon() != null){
			drawClusterOnImage(cluster.getRightSon(), g2d, startX, endX, endY, maxY);
		}
		//System.out.println(cluster.level());
	}
	
	
	/**
	 * Retorna a imagem do dendograma, pronta para ser exibida ou salva.
	 * @return Imagem do dendograma.
	 */
	public BufferedImage getImage(){
		return image;
	}

}
