import java.util.Scanner;
import java.lang.Math;

class IPFragmentationCalculator{
  public static void main(String[] args){
    Scanner myInput = new Scanner(System.in);
    int datasize;
    int mtu;
    int i;
    
    System.out.print("Enter DATA SIZE: ");
    while (!myInput.hasNextInt()) 
    {
        myInput.nextLine();
        System.out.print("That's not a number! Try again: ");
    }
    datasize = myInput.nextInt();
    System.out.print("Enter MTU: ");
    while (!myInput.hasNextInt()) 
    {
        myInput.nextLine();
        System.out.print("That's not a number! Try again: ");
    }
    mtu = myInput.nextInt();

    int n = (int) Math.ceil((double)(datasize - 20)/(mtu - 20));    
    
    if (datasize >= mtu){
      System.out.println("\n" + n + " fragments were created:");
      System.out.println("----------------------------------------------");
      System.out.printf("%2s %20s %10s %10s", "#", "TOTAL LENGTH", "FLAG", "OFFSET");
      System.out.println();
      System.out.println("----------------------------------------------");
      for(i=1;i<=n-1;i++){
          
        int length = mtu;
        int flags =   1; 
        int offset = (int) Math.ceil((double)((mtu - 20)*(i-1))/8);
                
        
        System.out.format("%2d %20d %10d %10d", i, length, flags, offset);
        System.out.println();  
        System.out.println("----------------------------------------------");
      }     
      int final_length = datasize - (mtu - 20)*(n-1);
      int final_flags = 0;
      int final_offset = ((mtu - 20)*(n-1))/8;
      
      System.out.format("%2d %20d %10d %10d", i, final_length, final_flags, final_offset);
      System.out.println();
      System.out.println("----------------------------------------------");
      }else{        
        System.out.println("\nSince MTU > DATA SIZE, the packet moves on to the next encapsulation phase without fragmentation:");
        System.out.println("----------------------------------------------");
        System.out.printf("%2s %20s %10s %10s", "#", "TOTAL LENGTH", "FLAG", "OFFSET");
        System.out.println();
        System.out.println("----------------------------------------------");   
        System.out.format("%2d %20d %10d %10d", 1, datasize, 0, 0);
        System.out.println();  
        System.out.println("----------------------------------------------");  
      }
    myInput.close();
  }        
} 