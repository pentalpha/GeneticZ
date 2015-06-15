package br.ufrn.imd.pitagoras.geneticz.windows;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;

/**
 * Dialogo que exibe uma tabela de dados.
 * @author pitagoras
 *
 */
public class TableDialog extends JDialog {

	private static final long serialVersionUID = 5163191275986779008L;
	private JTable table;

	/**
	 * Cria o dialogo de tabela.
	 * @param data Matrix com os dados a serem exibidos.
	 * @param columns Nomes das colunas da tabela.
	 * @param title Titulo da tabela.
	 * @param x Posição X.
	 * @param y Posição Y.
	 */
	public TableDialog(Object[][] data, String[] columns, String title, int x, int y) {
		//setModal(true);
		setTitle(title);
		setBounds(x, y, 640, 400);
		
		
		
		/*for(int i = 0; i < tableData.length; i++){
			for(int j = 0; j < tableData[i].length; j++){
				tableData[i][j] = 0;
			}
		}
		
		tableData[1][2] = 1;
		*/
		
		
		table = new JTable(data, columns);
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		for(int i = 0; i < columns.length; i++){
			table.getColumnModel().getColumn(i).setPreferredWidth(75);
		}
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		//table.
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

	}

}
