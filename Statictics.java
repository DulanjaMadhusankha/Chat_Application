public class Statictics  {
	

	private static int lostCount = 0;
	private static int unorderedCount = 0;
	private static int currentPacket = 0;

	public static void setLostCount(int amount){
		lostCount = amount;
	}
	public static void setUnOrderdCount(int amount){
		unorderedCount = amount;
	}
	public static void addLostCount(int amount){
		lostCount += amount;
	}
	public static void addUnOrderdCount(int amount){
		unorderedCount += amount;
	}

	public static int getLostCount(){
		return lostCount;
	}
	public static int getUnOrderdCount(){
		return unorderedCount;
	}
 
	public static String stats(){
		return "WITHIN PREVIOUS MINIUTE:\t LOST PACKETS: "+(lostCount)+"\t UNORDERED RECIEVINGS: "+unorderedCount+"\n" ;

	}
 	
 	public static boolean handlePacket(int sequence){

 		if (sequence >= currentPacket) {
 			if(sequence > currentPacket )
 			    addLostCount(sequence - currentPacket-1) ;
 			currentPacket = sequence ;
 			return true; 			
 		}
 		else if (currentPacket > 999999900 & sequence < 1000) {
 			addLostCount( sequence+(1000000000-currentPacket) );
 			currentPacket = sequence;
 			return true;
 			
 		}
 		else {
 			addUnOrderdCount(1);

 		}

 		return false ;
 	} 
}
