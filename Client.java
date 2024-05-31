// A Java program for a Client
import java.io.*;
import java.net.*;
import java.util.Random;


public class Client {
	// initialize socket and input output streams
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;

	// constructor to put ip address and port
	public Client(String address, int port)
	{
		
		// establish a connection
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");

			try {
				in = new DataInputStream(
						new BufferedInputStream(socket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// takes input from terminal
			input = new DataInputStream(System.in);


			// sends output to the socket
			out = new DataOutputStream(
				socket.getOutputStream());

			// takes input from the client socket
			
		}
		catch (UnknownHostException u) {
			System.out.println(u);
			return;
		}
		catch (IOException i) {
			System.out.println(i);
			return;
		}

		// string to read message from input
		String line = "";
		int mismatch = 0; // If max 3 mismatch start index  from a random number
		// keep reading until "Over" is input
		while (!line.equals("Over")) {
			try {
				// Make message digest.

				// Read file to get the seed value, multiplier and incrementor.
				ReadFileData getData = new ReadFileData();
				int values[] = getData.getData();
				int seed = values[0];
				int mod = values[1];
				int mul = values[2];
				int inc = values[3];
				int totalNumbers = 1000;
				int key_mod = totalNumbers;

				// Get key array based on random number.
				GenerateNumbers generateNumbers = new GenerateNumbers();
				int keys_[] = new int[1000];
				int keys[] = generateNumbers.lcm(seed, mod, mul, inc, keys_, totalNumbers);


				// Read which key is to be used.
				ReadFileData readData = new ReadFileData();
				int index = readData.getKeyIndex("key_index_cli.txt");

				// Generate the hash
				int current_key = keys[index % key_mod];

				if(mismatch > 3){
					Random rand = new Random();
					current_key = rand.nextInt(1000);
					// send key to server
					out.writeUTF("NEW_IDX");
					out.writeUTF(""+current_key);

				}

				String message = "" + current_key;
				GenerateHash hash = new GenerateHash();
				String myHash = hash.getDigest(message);

				out.writeUTF(myHash);
				System.out.println("Message Digest:" + myHash);

				Thread.sleep(1000);
				String x = in.readUTF();

				if(x.equals("Auth_Done")){
					System.out.println("Auth done!");
					
					// Update file 
					ReadFileData updateFile = new ReadFileData();
					updateFile.updateKeyIndex(index, "key_index_cli.txt");

					System.exit(0);  //Auth Done
				}


				
				mismatch++;
			}
			catch (IOException i) {
				System.out.println(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		// close the connection
		try {
			input.close();
			out.close();
			socket.close();
		}
		catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String args[])
	{
		Client client = new Client("127.0.0.1", 5000);
	}
}
