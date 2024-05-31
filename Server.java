// A Java program for a Server
import java.net.*;
import java.io.*;

public class Server
{
	//initialize socket and input stream
	private Socket		 socket = null;
	private ServerSocket server = null;
	private DataInputStream in	 = null;
	private DataOutputStream out = null;

	private static  String myHash = "";
	private static int index = 0;

	// constructor with port
	public Server(int port)
	{
		// starts server and waits for a connection
		try
		{
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");

			// takes input from the client socket
			in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));

					// sends output to the socket
			out = new DataOutputStream(
				socket.getOutputStream());
				

			String line = "";

			// reads message from client until "Over" is sent
			while (!line.equals("Over"))
			{
				try
				{
					line = in.readUTF();

					// Check for the message digest 
					if(line.equals(myHash)){
						System.out.println("From Client: " + line);
						System.out.println("Mydigest: " + myHash);
						System.out.println("Client Authentication done.");

						out.writeUTF("Auth_Done");


						// Update file 
						ReadFileData updateFile = new ReadFileData();
						updateFile.updateKeyIndex(index, "key_index.txt");



					}

					// check for mismatch request 
					if(line.equals("NEW_IDX")){
						line = in.readUTF(); // read key 
						int index = Integer.parseInt(line);

						// Update file with new key 
						ReadFileData updateFile = new ReadFileData();
						updateFile.updateKeyIndex(index, "key_index.txt");

						System.exit(0);  //update file and close 

					}
					


					Thread.sleep(1000);
					System.exit(0);  //Auth Done


				}
				catch(IOException i)
				{
					//System.out.println(i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Closing connection");

			// close connection
			socket.close();
			in.close();
		}
		catch(IOException i)
		{
			//System.out.println(i);
		}
	}

	public static void main(String args[])
	{

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
		 index = readData.getKeyIndex("key_index.txt");

		// Generate the hash
		int current_key = keys[index % key_mod];
		String message = "" + current_key;
		GenerateHash hash = new GenerateHash();
		myHash = hash.getDigest(message);
		System.out.println("My Digest: " + myHash);
		// Make server socket and connect to client.

		Server server = new Server(5000);
		
		
	}
}
