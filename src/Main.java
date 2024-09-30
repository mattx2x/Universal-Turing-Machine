import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static String createOnes(int n) {
        String s = "";
        for (int i = 0; i <= n; i++)
            s += "1";
        return s;
    }
    public static void main(String[] args) throws FileNotFoundException {

        String[] machineSpecifics = new String[4];
        machineSpecifics[3] = "";
        String path = "";
        System.out.println("select your operation type -->  s:sum    m:mul");
        Scanner sc = new Scanner(System.in);
        boolean opSelected = false;
        while (!opSelected) {
            String op = sc.next();
            switch (op.toLowerCase()) {
                case "s":
                    path = "src/SummationMachine.txt";
                    opSelected = true;
                    break;
                case "m":
                    path = "src/MultiplicationMachine.txt";
                    opSelected = true;
                    break;
                default:
                    System.out.println("error: not found operation, try again.");
                    break;
            }
        }
        sc = new Scanner(new File(path));
        String str, machineOnTape = "";
        while (sc.hasNext()) {
            str = sc.nextLine();
            String[] sArr = str.split("[:{}() ]");
            for (int i = 1; i < sArr.length; i++) {
                if(!Objects.equals(sArr[i], "")) {
                    switch (sArr[0]) {
                        case "alphabet":
                            machineSpecifics[0] = sArr[i].replace(",", "");
                            break;
                        case "start_state":
                            machineSpecifics[1] = sArr[i];
                            break;
                        case "final_states":
                            machineSpecifics[2] = sArr[i];
                            break;
                        case "actions":
                            machineSpecifics[3] += sArr[i];
                            break;
                    }
                }

            }
        }

        Scanner sc3 = new Scanner(machineSpecifics[3]);
        sc3.useDelimiter(",");
        int i = 0;
        while (sc3.hasNext()) {
            String next = sc3.next();
            if(i%5 == 0 || i%5 == 4) //states
                machineOnTape += createOnes(Integer.parseInt(next.replace("q", ""))) + "0";
            else if (i%5 == 1 || i%5 == 2) //alphabet
                machineOnTape += createOnes(machineSpecifics[0].indexOf(next)) + "0";
            else if (i%5 == 3) { //direction : L, R, S
                switch (next) {
                    case "R":
                        machineOnTape += "10";
                        break;
                    case "L":
                        machineOnTape += "110";
                        break;
                    case "S":
                        machineOnTape += "1110";
                        break;
                }
            }

            i++;
        }

        String startUnary = createOnes(Integer.parseInt(machineSpecifics[1].replace("q", ""))) + "0";
        String finalsUnary = "";
        Scanner sc2 = new Scanner(machineSpecifics[2]);
        sc2.useDelimiter(",");
        while (sc2.hasNext())
            finalsUnary += createOnes(Integer.parseInt(sc2.next().replace("q", ""))) + "0";

        machineOnTape = machineSpecifics[0] + "#" + finalsUnary + "$" + machineOnTape + "B";

        //****************************************************************

        System.out.println(machineOnTape);
        sc = new Scanner(System.in);
        String cont = "y";
        while (cont.equals("y")) {
            System.out.println("enter your operands in the form of 1...110111..1  :");
            String input = sc.next();
            String someBlank = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
            UTM utm = new UTM();
            utm.setTapeMachine(machineOnTape);
            utm.setTapeState(startUnary);
            utm.setTapeContent(someBlank + input + someBlank);
            utm.start();

            System.out.println("you entered : " + input);
            int x = 0;
            String tapeContent = utm.getTapeContent();
            for (int j = 0; j < tapeContent.length(); j++)
                if(tapeContent.charAt(j) == '1')
                    x++;
            System.out.println("number of ones : " + x);

            System.out.println("enter Y/y if you want another operation or enter something else if you don't:" );
            cont = sc.next();
        }
    }
}