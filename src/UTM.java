import java.util.Scanner;

public class UTM {

    private String tapeMachine;
    private String tapeContent;
    private String tapeState;
    private int headerContent, headerMachine, headerState;

    public void setTapeMachine(String tapeMachine) {
        this.tapeMachine = tapeMachine;
    }

    public void setTapeContent(String tapeContent) {
        this.tapeContent = tapeContent;
    }

    public void setTapeState(String tapeState) {
        this.tapeState = tapeState;
    }

    public void setContentHeader() {
        headerContent = 0;
    }

    public String getTapeContent() {
        return tapeContent;
    }

    public void start() {
        headerMachine = 0;
        headerContent = 0;
        while (tapeContent.charAt(headerContent) == 'B')
            headerContent ++;
        headerState = 0;
        while (tapeMachine.charAt(headerMachine) != '$')
            headerMachine ++;
        headerMachine ++;

        Scanner sc = new Scanner(tapeMachine);
        sc.useDelimiter("\\$");
        String firstPart = sc.next();
        Scanner sc3 = new Scanner(firstPart);
        sc3.useDelimiter("#");
        sc3.next();
        String finalsPart = sc3.next();
        String transitions = sc.next();
        Scanner sc2 = new Scanner(transitions);
        sc2.useDelimiter("0");

        System.out.println("state tape: " + tapeState);
        System.out.println(tapeContent);
        for (int i = 0; i < headerContent; i++)
            System.out.print(" ");
        System.out.println("^");


        while(true) {
            String tempState = sc2.next() + "0";
            if(tempState.equals("B0")) {
                System.out.println("halt!");
                Scanner sc4 = new Scanner(finalsPart);
                sc4.useDelimiter("0");
                while (sc4.hasNext()) {
                    String cur = sc4.next() + "0";
                    if(cur.equals(tapeState)) {
                        System.out.println("Accepted.");
                        return;
                    }
                }
                System.out.println("Rejected.");
                return;
            }
            String tempContent = sc2.next();
            String tempReplaceContent = sc2.next();
            String tempDirection = sc2.next();
            String tempNextState = sc2.next() + "0";

//            System.out.println("S " + tempState);
//            System.out.println("C "+tempContent);
//            System.out.println("R "+tempReplaceContent);
//            System.out.println("D "+tempDirection);
//            System.out.println("N "+tempNextState);

            if(tempState.equals(tapeState)
                    && tapeContent.charAt(headerContent) == tapeMachine.charAt(tempContent.length()-1)) {

                tapeContent = tapeContent.substring(0, headerContent) +
                        tapeMachine.charAt(tempReplaceContent.length()-1) +
                        tapeContent.substring(headerContent + 1);
                switch (tempDirection) {
                    case "1": // R
                        headerContent ++;
                        break;
                    case "11": // L
                        headerContent --;
                        break;
                }

                tapeState = tempNextState;
                sc2 = new Scanner(transitions);
                sc2.useDelimiter("0");

                System.out.println("state tape: " + tapeState);
                System.out.println(tapeContent);
                for (int i = 0; i < headerContent; i++)
                    System.out.print(" ");
                System.out.println("^");
            }

        }

    }
}
