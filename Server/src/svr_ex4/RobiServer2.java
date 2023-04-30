package svr_ex4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import exercice4serv.Execute;
import stree.parser.SParser;
import stree.parser.SPrinter;

public class RobiServer2 {
	/**
	 * Sokcet du serveur
	 */
	private ServerSocket serverSocket;
	/**
	 * Socket de communication entre serveur et client
	 */
	private Socket clientSocket;

	InputStream is;

	ByteArrayOutputStream baos;

	int port;

	Execute exe;

	public RobiServer2() {
		this.serverSocket = null;
		this.clientSocket = null;
		this.is = null;
		this.baos = null;
		this.port = 8888;
		this.exe = new Execute();
	}

	public ServerSocket getServerSocket() {
		return this.serverSocket;
	}
 
	public void start() {
		try {
			this.serverSocket = new ServerSocket(port);
			System.out.println("Le serveur 2 est démarré sur le port " + port);
			// Boucle d'attente de connexions clientes
			while (true) {
				// Attente de connexion
				clientSocket = serverSocket.accept();

				handleConnexion(clientSocket);

			}
		} catch (IOException e) {
			System.out.println("start");
			e.printStackTrace();
		}

	}

	private void writeData(DataSC datasc) {
		try {
			StringWriter ss = new StringWriter();
			JsonGenerator generator;
			generator = new JsonFactory().createGenerator(ss);
			ObjectMapper mapper_reponse = new ObjectMapper();
			mapper_reponse.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			// mapper_reponse.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			generator.setCodec(mapper_reponse);
			generator.writeObject(datasc);
			generator.close();
			String json = ss.toString();
			System.out.println("------- JSON Sent to the client --------");
			System.out.println(json);
			System.out.println("------- END JSON Sent to the client --------");
			if (clientSocket.isClosed()) {
				System.out.println("Le client s'est déconnecté");
			} else {
				// l'envoie de l'objet json
				OutputStream os = clientSocket.getOutputStream();
				os.write((json).getBytes());
				os.flush();
			}
		} catch (IOException e) {
			System.out.println("Write");
			e.printStackTrace();
		}

	}

	private DataCS readData() throws IOException {
		this.is = this.clientSocket.getInputStream();
		this.baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int len = 0;

		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
			if (is.available() == 0) {
				break;
			}
		}

		byte[] datas = baos.toByteArray();
		ObjectMapper mapper = new ObjectMapper();
		DataCS datacs = mapper.readValue(datas, DataCS.class);

		return datacs;
	}

	void handleConnexion(Socket clientSocket) throws IOException {
		while (this.clientSocket != null && !this.clientSocket.isClosed() && this.clientSocket.isConnected()) {
			// lecture de l'objet depuit le client
			DataCS datacs = readData();
			if (datacs.getCmd().equals("quit")) {
				clientSocket.close();
				break;
			}

			// DEBUG
			System.out.println("------- Data Recu du client -----");
			System.out.println("Commande : " + datacs.getCmd());
			System.out.println("Paramètre : " + datacs.getParam());
			System.out.println("------- Fin Data Recu du client -----");
			String script = datacs.getParam();
			if (datacs.getCmd().equals("block") || datacs.getCmd().equals("step")) {
				DataSC datasc = new DataSC();
				List<String> instructions = new ArrayList<String>();
				System.out.println("size sn : " + Execute.getInstructionSN().size());
				if (Execute.instructionSN.isEmpty()) {
					datasc.setCmd("error");
					datasc.setMessage("There is no instruction to execute");
				} else {
					if (datacs.getCmd().equals("block")) {
						System.out.println("Exec in block mode");
						for (int i = 0; i < Execute.instructionSN.size(); i++) {
							SPrinter printer = new SPrinter();
							Execute.instructionSN.get(i).accept(printer);
							instructions.add(printer.result().toString());
						}
					} else if (datacs.getCmd().equals("step")) {
						System.out.println("Exec in step mode");
						if (!Execute.instructionSN.isEmpty()) {
							SPrinter printer = new SPrinter();
							Execute.instructionSN.get(0).accept(printer);
							instructions.add(printer.result().toString());
						}
						
					}
					datasc.setInstructionSN(instructions);
					exe.scriptParser(datacs.getCmd());
					datasc.setCmd("response");
					exe.getEnv();
					datasc.setEnv(exe.env);
					EnvironmentSimple evs = new EnvironmentSimple();
					//for(e)
					//datasc.setImg(exe.getImage());
					this.exe.getGraphes();
					datasc.setGraph(this.exe.graphes);
				}
				
				
				
				
				
				
				// datasc.setNode(ex4.getListSNode());

				writeData(datasc);
			} else if (datacs.getCmd().equals("prog")) {
				exe.addScript(datacs.getParam());
			}

		}
	}

}
