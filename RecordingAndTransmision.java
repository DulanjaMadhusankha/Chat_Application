import java.net.* ;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;


public class RecordingAndTransmision extends InitResources implements Runnable{

	private final int port = 45000 ;
	private static int count = 0;

	private InetAddress host = null;
	private DatagramSocket socket = null ;
	private ByteArrayOutputStream byteArrayOutputStream = null;
	private byte tempBuffer[] = new byte[ReciptionAndPlay.PACKETSIZE];
	private boolean stopCapture = false;



    private void captureAndSend() {

        this.byteArrayOutputStream  = new ByteArrayOutputStream();
        this.stopCapture  = false;

        try {

          int readCount;

          while (!this.stopCapture ) {

            readCount = getTargetDataLine().read(this.tempBuffer, 0, this.tempBuffer.length);  //capture sound into tempBuffer

            if (readCount > 0) {

              this.byteArrayOutputStream.write(this.tempBuffer, 0, readCount);

              if (count == 1000000000) {

              	count = 0 ;  

              }
                  
                VoicePacket p1 = new VoicePacket(count++ , this.tempBuffer);   //serialize tempbuffer in to the voice packet object  

	       	  //  DatagramPacket packet  = new DatagramPacket(p1.getVoicePacket(), VoicePacket.SIZE , this.host , this.port); //create data packet for send
	       	    //System.out.println(p1.getVoicePacket().length+"jdkgghvjggh djgj vdg jghj");      
	        		// Send the packet

                DatagramPacket packet  = new DatagramPacket(VoicePacket.serialize(p1), VoicePacket.getSize() , this.host , this.port); //create data packet for send
	        	this.socket.send( packet ) ;

            }

          }

          this.byteArrayOutputStream.close();

        } catch (IOException e) {

            System.out.println(e);
            System.exit(0);
        }
    }


    public void run(){

    	try{

	    	this.socket = new DatagramSocket();  //create soket for transmit data
	    	this.captureAndSend();

    	 }catch( Exception e ){

	        System.out.println( e ) ;

	    } finally{

	       this.socket.close() ;
	      
	    }
	} 
   
	//constructor 
  	public  RecordingAndTransmision(InetAddress host){

		this.host = host;
		super.captureAudio();

	}
	
   
  public static void main( String args[] ){

	  // Check the whether the arguments are given
	  if( args.length != 1 ){

	    System.out.println( "usage:  < host/client IP > " ) ;
	    return ;      

	  }

	  try{

	  	//create thread for record and transmision
		  Thread rat = new Thread(new RecordingAndTransmision(InetAddress.getByName( args[0] )) );
		  rat.start();

		  //create thread for reciption and play
		  Thread rap = new Thread (new ReciptionAndPlay(InetAddress.getByName( args[0] ) ) );
		  rap.start();

		}catch(Exception e){

			System.out.println(e);

		}

  }

}

