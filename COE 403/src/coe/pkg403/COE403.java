/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe.pkg403;

/**
 *
 * @author SAMSUNG
 */
public class COE403 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String OP="",a="",b="",c="",d="",f="",x="",Imm="";
        String Rd="",Rs="",Rt="",Func="";
        String ff,aa,bb,dd,ImmImm;//AI=all_instructions OI=one_instruction
        int i;
        String instructions[] = new String[2];
        instructions[0]= "add r20 = r2 , r4 ";
        instructions[1]= "cor r5 = r7 , 3 ";
//        instructions[2]= "mul r5 = r9 , r30 ";
//        instructions[3]= "geu r25 = r13 , r30 ";
//        instructions[4]= "shr r3 = r2 , r30 ";
        
        
        for(i=0;i<instructions.length;i++){
            System.out.println("instruction # "+i);
            String instArray[]=instructions[i].split(" ");
            if(instArray[5].contains("r")){//R-FORMAT
                System.out.println("R-FORMAT");
            System.out.println(instArray[0]+" "+instArray[1]+
                    " "+instArray[3]+" "+instArray[5]);
            f=getFunc(instArray[0]);
            d=getRegister(instArray[1]);
            a=getRegister(instArray[3]);
            b=getRegister(instArray[5]);
            System.out.println("Instruction Number");
            System.out.println(f+" "+d+" "+a+" "+b);
            
            ff=toBinary(f);
            dd=toBinary(d);
            aa=toBinary(a);
            bb=toBinary(b);
            System.out.println("Instruction in binary befor extending ");
            System.out.println(ff+" "+dd+" "+aa+" "+bb);
            
            f=ExtRegister_4(ff);
            d=ExtRegister_5(dd);
            a=ExtRegister_5(aa);
            b=ExtRegister_5(bb);
            System.out.println("Instruction in binary after extending ");
            System.out.println(f+" "+d+" "+a+" "+b);
        }else  if(!instArray[5].contains("r")){//I-FORMAT
            System.out.println("I-FORMAT");
            System.out.println(instArray[0]+" "+instArray[1]+
                    " "+instArray[3]+" "+instArray[5]);
            f=getFunc(instArray[0]);// function
            b=getRegister(instArray[1]);//Rb
            a=getRegister(instArray[3]);//Rs
            Imm=instArray[5];//Imm
            System.out.println("Instruction Number");
            System.out.println(f+" "+b+" "+a+" "+Imm);
            
            ff=toBinary(f);
            bb=toBinary(b);
            aa=toBinary(a);
            ImmImm=toBinary(Imm);
            System.out.println("Instruction in binary befor extending ");
            System.out.println(ff+" "+bb+" "+aa+" "+ImmImm);
            
            f=ExtRegister_4(ff);
            b=ExtRegister_5(bb);
            a=ExtRegister_5(aa);
            Imm=ExtRegister_12(ImmImm);
            System.out.println("Instruction in binary after extending ");
            System.out.println(f+" "+b+" "+a+" "+Imm);
        }
         System.out.println();   
    }
    }
    public static String getFunc(String Func){
        String f="";
        if(Func.equals("add")|Func.equals("add")){
            f="0";
        }else if(Func.equals("nadd")|Func.equals("shr")){
            f="1";
        }else if(Func.equals("and")|Func.equals("sar")){
            f="2";
        }else if(Func.equals("cand")|Func.equals("ror")){
            f="3";
        }else if(Func.equals("or")){
            f="4";
        }else if(Func.equals("cor")){
            f="5";
        }else if(Func.equals("xor")){
            f="6";
        }else if(Func.equals("xnor")|Func.equals("set")){
            f="7";
        }else if(Func.equals("eq")|Func.equals("mul")){
            f="8";
        }else if(Func.equals("ne")){
            f="9";
        }else if(Func.equals("lt")){
            f="10";
        }else if(Func.equals("ge")){
            f="11";
        }else if(Func.equals("ltu")|Func.equals("div")){
            f="12";
        }else if(Func.equals("geu")|Func.equals("mod")){
            f="13";
        }else if(Func.equals("min")|Func.equals("divu")){
            f="14";
        }else if(Func.equals("max")|Func.equals("modu")){
            f="15";
        } 
        return f;
    }
    public static String getRegister(String operand){
        String d="";
        if(operand.equals("r0")){
            d="0";
        }else if(operand.equals("r1")){
            d="1";
        }else if(operand.equals("r2")){
            d="2";
        }else if(operand.equals("r3")){
            d="3";
        }else if(operand.equals("r4")){
            d="4";
        }else if(operand.equals("r5")){
            d="5";
        }else if(operand.equals("r6")){
            d="6";
        }else if(operand.equals("r7")){
            d="7";
        }else if(operand.equals("r8")){
            d="8";
        }else if(operand.equals("r9")){
            d="9";
        }else if(operand.equals("r10")){
            d="10";
        }else if(operand.equals("r11")){
            d="11";
        }else if(operand.equals("r12")){
            d="12";
        }else if(operand.equals("r13")){
            d="13";
        }else if(operand.equals("r14")){
            d="14";
        }else if(operand.equals("r15")){
            d="15";
        }else if(operand.equals("r16")){
            d="16";
        }else if(operand.equals("r17")){
            d="17";
        }else if(operand.equals("r18")){
            d="18";
        }else if(operand.equals("r19")){
            d="19";
        }else if(operand.equals("r20")){
            d="20";
        }else if(operand.equals("r21")){
            d="21";
        }else if(operand.equals("r22")){
            d="22";
        }else if(operand.equals("r23")){
            d="23";
        }else if(operand.equals("r24")){
            d="24";
        }else if(operand.equals("r25")){
            d="25";
        }else if(operand.equals("r26")){
            d="26";
        }else if(operand.equals("r27")){
            d="27";
        }else if(operand.equals("r28")){
            d="28";
        }else if(operand.equals("r29")){
            d="29";
        }else if(operand.equals("r30")){
            d="30";
        }else if(operand.equals("r31")){
            d="31";
        }
        return d;
    }
    private static String toBinary(String operand) {
         int dd=Integer.parseInt(operand);
         String a=Integer.toBinaryString(dd);
         return a;
    }
    public static String ExtRegister_4(String operand){
        String d="";
    if(operand.length()==1){
        d="000"+operand;
    }else if(operand.length()==2){
        d="00"+operand;
    }else if(operand.length()==3){
        d="0"+operand;
    }else {
        d=operand;
    }
    return d;
    }
    public static String ExtRegister_5(String operand){
        String d="";
    if(operand.length()==1){
        d="0000"+operand;
    }else if(operand.length()==2){
        d="000"+operand;
    }else if(operand.length()==3){
        d="00"+operand;
    }else if(operand.length()==4){
        d="0"+operand;
    }else {
        d=operand;
    }
    return d;
    }
    public static String ExtRegister_12(String operand){
        String d="";
    if(operand.length()==1){
        d="00000000000"+operand;
    }else if(operand.length()==2){
        d="0000000000"+operand;
    }else if(operand.length()==3){
        d="000000000"+operand;
    }else if(operand.length()==4){
        d="00000000"+operand;
    }else if(operand.length()==5){
        d="0000000"+operand;
    }else if(operand.length()==6){
        d="000000"+operand;
    }else if(operand.length()==7){
        d="00000"+operand;
    }else if(operand.length()==8){
        d="0000"+operand;
    }else if(operand.length()==9){
        d="000"+operand;
    }else if(operand.length()==10){
        d="00"+operand;
    }else if(operand.length()==11){
        d="0"+operand;
    }else {
        d=operand;
    }
    return d;
    }
}
