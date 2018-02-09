import java.util.Scanner;

public class Sender {

    public static void main(String[] args) {
        int userInput = 0;
        String data;
        Scanner kb = new Scanner(System.in);
        System.out.println("Which framing method would you like to use to frame?");
        System.out.println("1. Byte Count\n2. Byte Stuffing\n3. Bit Stuffing");

        try {
            userInput = kb.nextInt();
            switch (userInput) {
                case 1:
                    data = askForInput();
                    byteCountSend(data);
                    break;

                case 2:
                    data = askForInput();
                    byteStuffingSend(data);
                    break;

                case 3:
                    String bitData = askForBitInput();
                    bitStuffingSend(bitData);
                    break;

                default:
                    System.out.println("ERROR: PLEASE TRY AGAIN");
                    main(args);
            }
        } catch (Exception e){
            System.out.println("INVALID INPUT PLEASE TRY AGAIN");
            main(args);
        }
    }

    private static void byteCountSend(String input) {
        String length = String.valueOf(input.length() + 1);
        System.out.println(length + input);
    }

    private static void byteStuffingSend(String input) {
        String output = String.valueOf(input.charAt(0));
        for (int i=1; i<input.length()-1; i++){
            if (input.charAt(i) == 'F'){
                output = output + "EF";
            } else if (input.charAt(i) == 'E') {
                output = output + "EE";
            } else{
                output = output + input.charAt(i);
            }
        }
        output = output + input.charAt(input.length()-1);
        System.out.println(output);
    }

    private static void bitStuffingSend(String input) {
        int oneCount = 0;
        String output = input.substring(0,8);
        for (int j = 8; j < input.length()-8; j++){
            output = output + input.charAt(j);
            if (input.charAt(j) == '1'){
                oneCount++;
            } else {
                oneCount = 0;
            }
            if (oneCount == 5){
                output = output + 0;
                oneCount = 0;
            }
        }
        output = output + input.substring(input.length()-8, input.length());
        System.out.println(output);
    }

    public static String askForInput(){
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter the data you wou ld like to be framed");
        String dataInput = kb.nextLine();
        if (dataInput.length() > 8){
            System.out.println("FRAME MUST BE LESS THAN 9 BYTES");
            dataInput = askForInput();
        }
        return dataInput;
    }

    private static String askForBitInput() {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter the data you would like to be framed");
        String bitDataInput = kb.nextLine();
        return bitDataInput;
    }
}
