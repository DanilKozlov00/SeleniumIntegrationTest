import java.io.*;
import java.net.Socket;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.Scanner;
public class Client {
    static Scanner scan=new Scanner(System.in);
    public static String Privet(String grade,String comment) {//Ïîëó÷àåì ðåç.ðàáîòû ñèëåíèóìà
        JSONObject jsonObject = new JSONObject();//Ñîçäàåì íîâûé îáüåêò
        jsonObject.put("option",grade);//Âñå ëè óñïåøíî
        jsonObject.put("body",comment);//Åñëè íåò, òî ãäå îøèáêè
        System.out.println(jsonObject.toJSONString());
        return jsonObject.toString();//îáüåêò ÄÆÑÎÍ â ñòðîêó è îòïðàâëÿåì
    }
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;
    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 4003);
                reader = new BufferedReader(new InputStreamReader(System.in));
                // ÷èòàòü ñîîîáùåíèÿ ñ ñåðâåðà
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // ïèñàòü òóäà æå
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                System.out.println("Íàïèøèòå íîìåð âàðèàíòà:");
                String Nomber=scan.nextLine();
                Nomber="1";
                System.out.println("Ââåäèòå ññûëêó íà ðåïîçèòîðèé Git:");
                String Cilka=scan.nextLine();
                Cilka="https://github.com/vvtatyana/Losiash.git";
                String word = Privet(Nomber,Cilka);//reader.readLine();
                out.write(word + "\n"); // îòïðàâëÿåì ñîîáùåíèå íà ñåðâåð
                out.flush();
                String serverWord = in.readLine(); // æä¸ì, ÷òî ñêàæåò ñåðâåð
                System.out.println(serverWord); // ïîëó÷èâ - âûâîäèì íà ýêðàí
            } finally {
                System.out.println("Êëèåíò áûë çàêðûò...");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}