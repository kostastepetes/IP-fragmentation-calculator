import java.util.Scanner;
import java.lang.Math;

class IPFragmentationCalculator {
  public static void main(String[] args) {
    Scanner myInput = new Scanner(System.in);
    int datasize;
    int mtu;
    int i;
    
    System.out.println("Enter Data Size: ");
    datasize = myInput.nextInt();
    System.out.println("Enter MTU Size: ");
    mtu = myInput.nextInt();
    int n = (int) Math.ceil((double)(datasize - 20)/(mtu - 20));
    System.out.println("The Number of Fragments is: " + n);
    
  if (datasize >= mtu){
    for(i=1;i<=n-1;i++){
        
        int length = mtu;
        int flags =   1; 
        int offset = (int) Math.ceil((double)((mtu - 20)*(i-1))/8);
          
    System.out.println("The Total Length of Packet " + i + " is: " + length);
    System.out.println("The MF Flags of Packet " + i + " is: " + flags);
    System.out.println("The Offset of Packet " + i + " is: " + offset);
    }
     
    int final_length = datasize - (mtu - 20)*(n-1);
    int final_flags = 0;
    int final_offset = ((mtu - 20)*(n-1))/8;
    System.out.println("The Total Length of Packet " + n + " is: " + final_length);
    System.out.println("The MF Flag of Packet " + n + " is: " + final_flags);
    System.out.println("The Offset of Packet " + n + " is: " + final_offset);
            
    }else{
        
        System.out.println("Since MTU > Data Size, the Packet moves on to the next encapsulation phase without fragmentation ");
        
    }
    
    }
        
    } 