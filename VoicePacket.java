
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VoicePacket implements Serializable{

	private final int sequence ;
	private final byte [] tempBuffer;
	public static int SIZE = 0;


	public VoicePacket(int sequence,byte [] tempBuffer ){

		this.sequence = sequence ;
		this.tempBuffer = tempBuffer ;
				
	}


	public static byte[] serialize(VoicePacket obj) throws IOException {

        try(ByteArrayOutputStream byteOutPutStream = new ByteArrayOutputStream()){
            try( ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutPutStream) ){
                objectOutputStream.writeObject(obj);
            }
            return byteOutPutStream.toByteArray();
        }

    }
    
    
    //to deserialize the byte stream
    public static VoicePacket deserialize(byte[] bytes) throws IOException, ClassNotFoundException {

        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)){
            try( ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream) ){
                return (VoicePacket)objectInputStream .readObject();
            }
        }

    }


    public int getSequenceNumber(){

    	return this.sequence ;
    }

    public byte[] getData(){

    	return this.tempBuffer ;
    }

    public static int getSize(){

    	try{

    		if (SIZE == 0) {
    			SIZE = VoicePacket.serialize(new VoicePacket(1	,new byte[ReciptionAndPlay.PACKETSIZE])).length;    			
    		}    		

    	}catch (Exception e) {

    		System.out.println(e);
    		
    	}
    	return SIZE ;

    	

    }
    

	// public static final int SIZE = (Integer.SIZE + ReciptionAndPlay.PACKETSIZE * Byte.SIZE)/8 ; //get size for buffer

	// final ByteBuffer buf;
	// final int sequence ;	

	// //constructor for serialize 	
	// public VoicePacket(int sequence,byte [] tempBuffer ){

	// 	buf = ByteBuffer.allocate(SIZE);		 //allocate space for temp buffer and set packet headers
	// 	buf.putInt( sequence );
	// 	buf.put( tempBuffer );
	// 	this.sequence = sequence ;
	// 	//System.out.println("original   "+this.buf.array().length);
				
	// }

	// //constructor fordesirialize
	// public VoicePacket(byte [] data) throws IOException {
	//   //	System.out.println("recieved   "+data.length);

	// 	buf = ByteBuffer.wrap(data);	
	// 	sequence =	buf.getInt(0) ;
		
	// }


	// @Override
	// public String toString() {

	//  	return "sequence number:" + buf.getInt(0) ;

	// }


	// byte[] getData() {  //get the real voice data

	// 	byte [] temp = new byte[ReciptionAndPlay.PACKETSIZE];
	// 	// System.out.println("buff array length  "+this.buf.array().length);
	// 	// System.out.println("Reciption PACKETSIZE "+ReciptionAndPlay.PACKETSIZE);
	// 	// System.out.println("allocate size "+SIZE);

	// 	for (int i = 4; i < this.buf.array().length; i++)
 //     		temp[i-4] = buf.array()[i];
		
	// 	return temp;

	// }


	// byte[] getVoicePacket() {

	// 	return buf.array();  // get formated array

	// }


	// int getSequenceNumber() {

	// 	return buf.getInt(0);

	// }	

	// /**
	//  * A quick unit test for the class. 
	//  */
	// public static void main(String[] args) throws IOException	{

	// 	byte[] bytes = new byte[100];
 //    Arrays.fill( bytes, (byte) 1 );

 //    VoicePacket p1 = new VoicePacket(1,bytes);       
 //    assert Arrays.equals(bytes , new VoicePacket(p1.getVoicePacket()).getData() );
               		
	// }
	
}