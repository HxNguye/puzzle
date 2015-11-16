package server;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class PuzzleObject {
	String fileName;
	BufferedInputStream bis = null;
    OutputStream os = null;
    Socket sock = null;
	
	public PuzzleObject(String file, BufferedInputStream bis, OutputStream os, Socket sock){
		fileName = file;
		this.os = os;
		this.bis = bis;
		this.sock = sock;
	}
	public void getPuzzle() throws IOException{
		 File myFile = new File (fileName);
         byte [] mybytearray  = new byte [(int)myFile.length()];
         bis.read(mybytearray,0,mybytearray.length);
         os = sock.getOutputStream();
         System.out.println("Sending " + fileName + "(" + mybytearray.length + " bytes)");
         os.write(mybytearray,0,mybytearray.length);
         os.flush();
         System.out.println("Done.");
	}
}
