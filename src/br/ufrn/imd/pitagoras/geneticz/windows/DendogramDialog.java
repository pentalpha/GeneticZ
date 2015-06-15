package br.ufrn.imd.pitagoras.geneticz.windows;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import javax.swing.JLabel;

import br.ufrn.imd.pitagoras.geneticz.Dendogram;
import java.awt.Toolkit;

/**
 * Exibe um dialogo com a imagem de um dendograma na tela.
 * @author pitagoras
 *
 */
@SuppressWarnings("serial")
public class DendogramDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Construtor para a classe DendogramDialog
	 * @param dendogram Dendograma à ser exibido.
	 */
	public DendogramDialog(Dendogram dendogram) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DendogramDialog.class.getResource("/com/alee/managers/notification/icons/types/database.png")));
		BufferedImage tree = dendogram.getImage();
		this.setTitle("Dendogram");
		setResizable(true);
		setModal(false);
		setBounds(100, 100, tree.getWidth()+30, tree.getHeight()+50);
		getContentPane().setLayout(null);
		
		JScrollBar scrollBar = new JScrollBar();
		getContentPane().add(scrollBar);
		
		JLabel label = new JLabel("");
		
		label.setBounds(0, 0, tree.getWidth(), tree.getHeight());
		label.setIcon(new ImageIcon(tree));
		getContentPane().add(label);
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
	}
}
