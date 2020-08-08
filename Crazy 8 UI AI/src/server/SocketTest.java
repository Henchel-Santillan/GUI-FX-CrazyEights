package server;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.net.Socket;
import java.net.UnknownHostException;

import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class SocketTest {

	public static void echo(String hostName, int portNumber) {
		try (
			
			//Establish connection between socket and server
			Socket echo = new Socket(hostName, portNumber);
				
			//Open writers and readers on the socket
			PrintWriter echoOut = new PrintWriter(echo.getOutputStream(), true);	
			BufferedReader echoIn = new BufferedReader(new InputStreamReader(echo.getInputStream()));
			BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
				
		) {
			
			//Reads input one line at a time from the standard input stream
			//and pushes it to the server connected to the socket
			String input;
			
			while ((input = systemIn.readLine()) != null) {
				echoOut.println(input);
				
				//Waits for the server to echo back; when the reader returns, input is printed to standard output
				System.out.println("System echo: " + echoIn.readLine());
			}
		
		} catch (UnknownHostException e) {
			System.err.println("Unkown Host.\n");
			Logger.getLogger(SocketTest.class.getName()).log(Level.SEVERE, null, e);
			System.exit(1);
			
		} catch (IOException e) {
			System.err.println("Failed to retrieve input and echo output for the connection.\n");
			Logger.getLogger(SocketTest.class.getName()).log(Level.SEVERE, null, e);
			System.exit(1);
		}
	}
	
	
	public static void main(String[] args) {
		
		if (args.length != 2) {
			System.err.println("Only 2 arguments <host name> <port number> expected.");
			System.exit(1);
		}
		
		SocketTest.echo(args[0], Integer.parseInt(args[1]));
	}

}
