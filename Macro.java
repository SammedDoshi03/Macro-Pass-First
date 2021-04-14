import java.util.*;
import java.io.*;

public class Macro{
    
    
    static  class MDT{
        int mdtLineNo;
        String mdtCode;
        MDT(int mdtLineNo, String mdtCode){
            this.mdtLineNo =mdtLineNo;
            this.mdtCode = mdtCode;
        }
    } 

    static class MNT{
        int mntLineNo;
        String mntCode;
        MNT(int mntLineNo, String mntCode){
            this.mntLineNo =mntLineNo;
            this.mntCode = mntCode;
        }
    } 

    static class ALA{
        int alaNo;
        String alaArg;
        ALA(int alaNo, String alaArg){
            this.alaNo =alaNo;
            this.alaArg = alaArg;
        }
    } 

    static class PASS1{

        protected MDT[] mdt = new MDT[50];
        protected MNT[] mnt = new MNT[50];
        protected ALA[] ala = new ALA[50];
        protected int mdtp = 0, mntp = 0, alap = 0;
    
       void pass(){
        String s = new String();
        String mdtline;
        try {
            int linNo = 1;
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
            PrintWriter output = new PrintWriter(new FileOutputStream("output_pass1.txt"), true);
            while((s = input.readLine()) != null) {
                if(s.equalsIgnoreCase("MACRO")) {
                    processMacroDefinition(input);
                    if(!(s.equalsIgnoreCase("MEND"))){
                        output.write(s);
                        output.println();
                        // output.newLine();
                        // output.write('\n');
                    }
                } else {
                    output.write(s);
                    output.println();
                }
            }
            display();
            output.close();   
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e);
        }
       }

       void display(){
        System.out.println("MNT is: ");
        for(int i= 0;i<mntp;i++)
            System.out.println(mnt[i].mntLineNo+"\t"+ mnt[i].mntCode);

        System.out.println("ALA is: ");
        for(int i= 0;i<alap;i++)
            System.out.println(ala[i].alaNo+"\t"+ ala[i].alaArg);

        System.out.println("MDT is: ");
        for(int i= 0;i<mdtp;i++)
            System.out.println(mdt[i].mdtLineNo+"\t"+ mdt[i].mdtCode);
       }

       int search(String args, int len){
            for(int i= 0;i<len;i++){
                if((ala[i].alaArg).equals(args)){
                    return i;
                }
            }
            return 0;  
       }

       void processMacroDefinition(BufferedReader input) throws Exception{
            int flag = 0;
            String mdtline = " ";
            String s = input.readLine();
           while(!(s.equalsIgnoreCase("MEND"))){
                s = input.readLine();
                String lineArr[] = s.split(" ");
                for(int i = 0; i<lineArr.length;i++){
                    if(flag==0 && !(lineArr[i].contains("&"))){
                        mnt[mntp] = new MNT(mdtp,lineArr[i]);
                        mntp++;
                        flag = 1;
                    }
                    if(lineArr[i].contains("&")){
                        String lineAlaArr[] = lineArr[i].split(",");
                        for(int j=0; j< lineAlaArr.length;j++){
                            if(lineAlaArr[j].contains("&")){
                                int findindex = search(lineAlaArr[j],alap);
                                if(findindex==0){
                                    ala[alap] = new ALA(alap,lineAlaArr[j]);
                                    s = s.replaceAll(lineAlaArr[j],"#"+alap);
                                    alap++;
                                }
                                else{
                                    s = s.replaceAll(lineAlaArr[j],"#"+findindex);
                                }
                            }
                        }
                    }
                }
                mdt[mdtp] = new MDT(mdtp,s);
                mdtp++;
           }
       }

    }


    public static void main(String[] args) {

        PASS1 passExample = new PASS1();
        passExample.pass();
        
      /*  int choice;
        System.out.println("Macro Implimentation\n 1. Pass 1 \t 2. Pass 2\n Enter you Choice ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        switch (br) {
            case 1:
                pass();
                break;
            case 2:
                pass2();
                break;
            default:
            System.out.println("Invalid input");
        }
        */
    }

   


    
    
}