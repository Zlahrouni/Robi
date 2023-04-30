package clnt_ex4;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ClientRobi2 est une classe qui implémente une interface utilisateur graphique
 * pour communiquer avec un robot Robi à distance. Cette classe permet de
 * charger et envoyer du code, de l'exécuter, d'afficher des résultats et des
 * graphiques, et de se connecter / déconnecter du robot.
 * 
 * @author Hanane Erraji
 * @author Ziad Lahrouni
 * @author Youenn Robitzer
 * @author Gwendal Le Tareau
 *
 */
public class ClientRobi2 extends JFrame {

	/**
	 * serialVersionUID est un identificateur unique de la classe ClientRobi2 pour
	 * la sérialisation.
	 */
	private static final long serialVersionUID = 1L;

	// Composants graphiques
	private JFrame frame;
	private JTextArea codeField;
	private JTextArea resultArea;
	private JRadioButton blockRadioButton;
	private JRadioButton stepRadioButton;
	private JLabel imageLabel;
	private JPanel graph = null;
	private JMenuBar menubar = null;
	private JPanel panelPrincipal = null;
	private JTextArea snodeLog = null;
	private JTextArea envLog = null;

	// Réseau
	private Socket socket = null;
	private OutputStream os = null;

	/**
	 * Constructeur de la classe ClientRobi2, qui initialise la fenêtre d'interface
	 * utilisateur graphique et ajoute des éléments d'interface utilisateur.
	 */
	public ClientRobi2() {
		// Initialisation de la fenêtre principale
		frame = new JFrame();
		frame.setTitle("Robi Client 2 - Exercice 4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
 
		// Création du panel principal
		panelPrincipal = new JPanel(new BorderLayout());

		// Panel nord
		JPanel northPanel = new JPanel(new GridLayout(2, 1));
		northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Panel pour le code
		JPanel codePanel = new JPanel(new BorderLayout());
		codeField = new JTextArea();
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new LoadActionListener());
		JScrollPane scrollPane = new JScrollPane(codeField);
		scrollPane.setPreferredSize(
				new Dimension(codeField.getPreferredSize().width, codeField.getPreferredSize().height + 100));
		codePanel.add(scrollPane, BorderLayout.CENTER);
		codePanel.add(loadButton, BorderLayout.EAST);

		// adding the menu
		menubar = new JMenuBar();
		JMenu auth = new JMenu("Authentification");
		JMenuItem connexion = new JMenuItem("Connexion");
		connexion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginDialog();
			}
		});
		auth.add(connexion);
		JMenuItem deconnexion = new JMenuItem("Deconnexion");
		deconnexion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sendData("quit", "");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					System.out.println("Try by");
					if (socket != null && !socket.isClosed() && socket.isConnected()) {
						if (os != null) {
							os.close();
							System.out.println("os close");
						}

						System.out.println("sock close");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		auth.add(deconnexion);
		menubar.add(auth);
		frame.setJMenuBar(menubar);

		// Panel pour le choix du mode
		JPanel modePanel = new JPanel(new GridLayout(1, 2));
		ButtonGroup modeButtonGroup = new ButtonGroup();
		blockRadioButton = new JRadioButton("Block");
		stepRadioButton = new JRadioButton("Step");
		modeButtonGroup.add(blockRadioButton);
		modeButtonGroup.add(stepRadioButton);
		blockRadioButton.setSelected(true);
		modePanel.add(blockRadioButton);
		modePanel.add(stepRadioButton);

		// Ajout des panels au panel nord
		northPanel.add(codePanel);
		northPanel.add(modePanel);

		// Panel pour les résultats
		resultArea = new JTextArea();
		resultArea.setEditable(false);
		JScrollPane scrollPane1 = new JScrollPane(resultArea);
		scrollPane1.setPreferredSize(
				new Dimension(codeField.getPreferredSize().width + 400, codeField.getPreferredSize().height + 300));
		JPanel resultPanel = new JPanel(new BorderLayout());
		resultPanel.setBorder(BorderFactory.createTitledBorder("Client Log"));
		resultPanel.add(scrollPane1, BorderLayout.CENTER);

		// Panel pour l'image
		imageLabel = new JLabel();
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane imageScrollPane = new JScrollPane(imageLabel);

		// Boutons pour envoyer le code et l'exécuter
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendActionListener());
		frame.getRootPane().setDefaultButton(sendButton);

		JButton executeButton = new JButton("Execute");
		executeButton.addActionListener(new ExecuteActionListener());
		JPanel southPanel = new JPanel(new GridLayout(1, 2));
		southPanel.add(sendButton);
		southPanel.add(executeButton);

		// Graphique
		graph = new JPanel();
		JPanel centerPanel = new JPanel(new GridLayout());
		centerPanel.add(resultPanel);
		centerPanel.add(graph);

		// Logs
		JPanel westLogsPanel = new JPanel(new GridLayout(2, 1));
		snodeLog = new JTextArea();
		snodeLog.setEditable(false);
		envLog = new JTextArea();
		envLog.setEditable(false);
		JScrollPane snodeLogPanel = new JScrollPane(snodeLog);
		JScrollPane envLogPanel = new JScrollPane(envLog);
		snodeLogPanel.setPreferredSize(
				new Dimension(codeField.getPreferredSize().width + 300, codeField.getPreferredSize().height + 200));
		snodeLogPanel.setBorder(BorderFactory.createTitledBorder("SNode Log"));
		envLogPanel.setPreferredSize(
				new Dimension(codeField.getPreferredSize().width + 300, codeField.getPreferredSize().height + 200));
		envLogPanel.setBorder(BorderFactory.createTitledBorder("Environment Log"));
		westLogsPanel.add(envLogPanel);
		westLogsPanel.add(snodeLogPanel);

		// Ajout des éléments au panel principal
		panelPrincipal.add(northPanel, BorderLayout.NORTH);
		panelPrincipal.add(centerPanel, BorderLayout.CENTER);
		panelPrincipal.add(imageScrollPane, BorderLayout.EAST);
		panelPrincipal.add(southPanel, BorderLayout.SOUTH);
		// panelPrincipal.add(westLogsPanel, BorderLayout.WEST);
		frame.add(panelPrincipal, BorderLayout.CENTER);
		frame.add(westLogsPanel, BorderLayout.WEST);
		frame.repaint();

		// Fermeture de la fenêtre
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {

					if (socket != null && !socket.isClosed() && socket.isConnected()) {

						if (os != null) {
							sendData("quit", "");
							os.close();
						}
						socket.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

		// Affichage de la fenêtre
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Cette classe est un écouteur d'événements pour le bouton "Load".
	 * 
	 */
	private class LoadActionListener implements ActionListener {
		/**
		 * Cette méthode est appelée lorsque l'utilisateur clique sur le bouton "Load"
		 * Elle permet à l'utilisateur de charger un fichier de code dans l'interface
		 * graphique.
		 * 
		 * @param e l'événement qui a déclenché l'action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showOpenDialog(ClientRobi2.this) == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line).append("\n");
					}
					reader.close();
					codeField.setText(sb.toString());
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(ClientRobi2.this, "Error loading file");
				}
			}
		}
	}

	/**
	 * ActionListener pour le bouton d'envoi de code au serveur.
	 * 
	 */
	private class SendActionListener implements ActionListener {
		/**
		 * Cette méthode est appelée lorsque l'utilisateur clique sur le bouton "Send".
		 * Elle envoie le script du champ de texte "codeField" au serveur. Si la
		 * connexion au serveur est établie, elle envoie les données au format "prog"
		 * via la méthode "sendData". Sinon, elle affiche un message d'erreur dans le
		 * champ "resultArea".
		 * 
		 * @param e l'événement qui a déclenché l'action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			checkConnexion();
			if (socket != null && socket.isConnected()) {
				String code = codeField.getText();
				if (!code.isBlank() && !code.isEmpty()) {
					sendData("prog", code);
					codeField.setText("");
				}
				

			} else {
				resultArea.append("You're not connected, set your user uthentication \n");
			}
		}
	}

	/**
	 * ActionListener pour le bouton d'exécution du code sur le serveur
	 */
	private class ExecuteActionListener implements ActionListener {
		/**
		 * Cette méthode est appelée lorsque l'utilisateur clique sur le bouton
		 * "Execute". Elle envoie une commande au serveur pour lui demander d'exécuter
		 * le code qui a été envoyé précédemment. Le mode d'exécution est déterminé en
		 * fonction de l'état du bouton radio "blockRadioButton". Si la connexion au
		 * serveur est établie, elle envoie les données via la méthode "sendData". Puis
		 * elle lit la réponse du serveur via la méthode "readData". En cas d'erreur
		 * lors de la lecture, elle affiche la stack trace de l'exception.
		 * 
		 * @param e l'événement qui a déclenché l'action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// String code = codeField.getText();
			String mode = blockRadioButton.isSelected() ? "block" : "step";
			sendData(mode, "");
			try {
				System.out.println("Read");
				readData();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Vérifie l'état de la connexion au serveur
	 */
	public void checkConnexion() {
		if (socket == null || socket.isClosed() || !socket.isConnected()) {
			loginDialog();
		}
	}

	/**
	 * Lit les données renvoyées par le serveur
	 * 
	 * @throws IOException si une erreur survient lors de la lecture des données
	 */
	public void readData() throws IOException {
		// Récupération de l'objet InputStream associé à la connexion au serveur
		InputStream is = socket.getInputStream();
		// Création d'un objet ByteArrayOutputStream pour stocker les données lues
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// Création d'un buffer pour stocker les données lues
		byte[] buffer = new byte[8192];
		int len;
		// Tant qu'il reste des données à lire
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
			// Si on a lu toutes les données, on sort de la boucle
			if (is.available() == 0) {
				break;
			}
		}
		byte[] datas = baos.toByteArray();
		
		// Création d'un objet ObjectMapper pour convertir les données JSON en objet
		// DataSC
		ObjectMapper mapper2 = new ObjectMapper();
		// Conversion des données JSON en objet DataSC
		DataSC data2 = mapper2.readValue(datas, DataSC.class);

		
		if (data2.getCmd().equals("response")) {
			resultArea.append("Response JSON from Server :\n");
			resultArea.append(baos.toString());
			String res = "Executed :\n";
			String ins = "";
			// Organise les instructions reçues pour avoir un affichage lisible.
			for (int i = 0; i < data2.getInstructionSN().size(); i++) {
				ins = data2.getInstructionSN().get(i);
				ins = ins.replace("[", "").replace("]", "");
				String[] parts = ins.split(",");
				for (String s : parts) {
					res += " " + s + "\n";
				}

			}
			snodeLog.append(res + "\n");
			envLog.setText(data2.getEnv().toString());

			// transformation du dataSC en une image
//			byte[] imageBytes = Base64.getDecoder().decode(data2.getImg());
//			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
//			File outputFile = new File("output.png");
//			ImageIO.write(image, "png", outputFile);
//			afficherImage(data2.getImg(), graph);
			graph.removeAll();
			
			for( Graph g : data2.getGraph()) {
				System.out.println("Drawing : "+g.getCmd());
				System.out.println(g.toString());
				g.draw(graph);

			}
		} else {
			resultArea.append(data2.getMessage() + "\n");
		}
		

	}

	/**
	 * Affiche une boîte de dialogue de connexion avec un serveur distant.
	 * La méthode crée un JDialog avec deux champs de texte pour le nom d'hôte et le numéro de port,
	 * et des boutons "OK" et "Annuler". Si l'utilisateur clique sur "OK",
	 * la méthode crée un Socket pour se connecter au serveur à l'aide des informations fournies,
	 * et stocke le Socket et le OutputStream dans des variables globales.
	 * Si la connexion échoue, la méthode affiche un message d'erreur dans un JTextArea.
	 */
	public void loginDialog() {
		JDialog loginDialog = new JDialog(this, "Login", ModalityType.MODELESS);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		JTextField hostField = new JTextField();
		JTextField portField = new JTextField();

		// JTextField portField = new JTextField();
		panel.add(new JLabel("Host:"));
		panel.add(hostField);
		panel.add(new JLabel("Port:"));
		panel.add(portField);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String host = hostField.getText();
				String port = new String(portField.getText());
				try {
					socket = new Socket(host, Integer.parseInt(port));
				} catch (NumberFormatException e1) {
					resultArea.append("Le port doit etre un nombre (1024 à 65535)  \n");
				} catch (UnknownHostException e1) {
					resultArea.append("Hote invalid \n");
				} catch (IOException e1) {
					resultArea.append("Refuse to connect \n");
				}

				loginDialog.dispose();
				if (socket != null && !socket.isClosed() && socket.isConnected()) {
					resultArea.append("Vous etes bien connecter \n");
					try {
						os = socket.getOutputStream();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					resultArea.append("Authentification Invalid veuillez ressayer \n");
				}
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginDialog.dispose();
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		loginDialog.add(panel, BorderLayout.CENTER);
		loginDialog.add(buttonPanel, BorderLayout.SOUTH);
		loginDialog.pack();
		loginDialog.setLocationRelativeTo(this);
		loginDialog.setVisible(true);
	}

	/**
	 * Envoie une commande et un code au serveur sous forme d'objet JSON.
	 * La méthode crée un objet DataCS, ajoute les paramètres de la commande et du code,
	 * puis le transforme en JSON et l'envoie au serveur via le OutputStream stocké dans une variable globale.
	 * La méthode affiche le JSON envoyé dans un JTextArea.
	 * Si une erreur se produit lors de l'envoi, la méthode affiche un message d'erreur.
	 * 
	 * @param cmd la commande à envoyer
	 * @param code le code à envoyer
	 */
	private void sendData(String cmd, String code) {
		try {
			// Creation de l'objet a envoyer au serveur
			DataCS data = new DataCS();
			data.setCmd(cmd);
			data.setParam(code);
			// envoie du l'objet au server
			StringWriter sw = new StringWriter();
			JsonGenerator generator = new JsonFactory().createGenerator(sw);
			ObjectMapper mapper = new ObjectMapper();
			generator.setCodec(mapper);
			generator.writeObject(data);
			generator.close();

			String json = sw.toString();
			os.write((json).getBytes());
			os.flush();
			if (cmd.equals("prog")) {
				resultArea.append("Le JSON envoyer au client : \n");
				resultArea.append("    " + json + "\n");
			}

			///////////////////////////////////
			// Lecture de la reponde du serveur

		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(ClientRobi2.this, "Error sending code to server");
		}

	}

	/**
	 * Affiche une image à partir de sa représentation en Base64.
	 * 
	 * @param base64ImageString la chaîne de caractères contenant la représentation en Base64 de l'image
	 * @param jPanel le JPanel sur lequel afficher l'image
	 */
	public void afficherImage(String base64ImageString, JPanel jPanel) {
		try {
			byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);
			ImageIcon imageIcon = new ImageIcon(imageBytes);
			JLabel imageLabel = new JLabel(imageIcon);

			// Effacer tous les composants précédents
			jPanel.removeAll();

			// Ajouter la nouvelle image
			jPanel.add(imageLabel);
			jPanel.revalidate();
			jPanel.repaint();
		} catch (Exception e) {
			System.out.println("Erreur lors de l'affichage de l'image : " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		System.out.println("Robi client");
		SwingUtilities.invokeLater(ClientRobi2::new);
	}

}
