import java.util.Scanner;

public class Receiver {

    public static void main(String[] args) {
        int userInput = 0;
        String data;
        Scanner kb = new Scanner(System.in);
        System.out.println("Which framing method would you like to use to deframe?");
        System.out.println("1. Byte Count\n2. Byte Stuffing\n3. Bit Stuffing");

        try {
            userInput = kb.nextInt();

            switch (userInput) {
                case 1:
                    data = askForInput();
                    byteCountReceive(data);
                    break;

                case 2:
                    data = askForInput();
                    byteStuffingReceive(data);
                    break;

                case 3:
                    String bitData = askForBitInput();
                    bitStuffingReceive(bitData);
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

    private static void byteCountReceive(String input) {
        String output = input.substring(1,input.length());
        if (Character.getNumericValue(input.charAt(0)) != input.length()){
            System.out.println("THE RECEIVED FRAME HAS AN ERROR");
        } else {
            System.out.println(output);
        }
        System.exit(0);
    }

    private static void byteStuffingReceive(String input) {
        String output = String.valueOf(input.charAt(0));
        for (int i = 1; i<input.length()-1; i++){
            if (input.charAt(i) == 'E'){
                i++;
            }
            output = output + input.charAt(i);
        }
        output = output + String.valueOf(input.charAt(input.length()-1));
        System.out.println(output);
        System.exit(0);
    }

    private static void bitStuffingReceive(String input){
        String output = input.substring(0,8);
        int oneCount = 0;
        for (int j = 8; j < input.length()-8; j++){
            if (input.charAt(j) == '1'){
                oneCount++;
            } else {
                oneCount = 0;
            }
            if (oneCount == 5){
                output = output + input.charAt(j);
                oneCount = 0;
                j++;
            } else {
                output = output + input.charAt(j);
            }
        }
        output = output + input.substring(input.length()-8, input.length());
        System.out.println(output);
        System.exit(0);
    }

    public static String askForInput(){
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter the data you wou ld like to be deframed");
        String dataInput = kb.nextLine();
        if (dataInput.length() > 10){
            System.out.println("FRAME MUST BE LESS THAN 10 BYTES");
            dataInput = askForInput();
        }
        return dataInput;
    }

    private static String askForBitInput() {
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter the data you would like to be deframed");
        String bitDataInput = kb.nextLine();
        return bitDataInput;
    }
}