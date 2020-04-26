import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.simple.*;
import org.json.simple.parser.*;
public class Server {
    private static Socket clientSocket; //Ñîêåò êëèåíòà
    private static ServerSocket server; //Ñîêåò ñåðâåðà
    private static BufferedReader in; //×òîáû ïðèíèìàòü è îòïðàâëÿòü ñîîáùåíèÿ áóôåðû
    private static BufferedWriter out;
    public static  String NomberVar;
    public static  String Repos;

    //ÔÓÍÊÖÈß ñîçäàíèÿ ÄÆÑÎÍ ÎÒÂÅÒÀ
    public static String Otvet(String grade,String comment) {//Ïîëó÷àåì ðåç.ðàáîòû ñèëåíèóìà
        JSONObject jsonObject = new JSONObject();//Ñîçäàåì íîâûé îáüåêò
        jsonObject.put("mark", grade);//Âñå ëè óñïåøíî
        jsonObject.put("comment", comment);//Åñëè íåò, òî ãäå îøèáêè
        System.out.println(jsonObject.toJSONString());
        return jsonObject.toString();//îáüåêò ÄÆÑÎÍ â ñòðîêó è îòïðàâëÿåì
    }
    public static void OsnovaDecoda(String word) throws ParseException
    {
        System.out.println(word);
        JSONObject Cilka = (JSONObject)JSONValue.parseWithException(word);

        NomberVar=Cilka.get("option").toString();
        System.out.println(NomberVar);

        Repos=Cilka.get("body").toString();
        System.out.println(Repos);
    }
    public static void main(String[] args) throws ParseException {
        try {
            try  {
                server = new ServerSocket(4003); //Ñåðâåð íà ïîðòå 4003
                System.out.println("Ñåðâåð çàïóùåí!");
                clientSocket = server.accept(); //Ñåðâåð ãîòîâ ïðèíèìàòü ñèãíàë
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   //Ýòî òóïî îáåðòêè áóôåðîâ
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String word = in.readLine(); //Ýòî ìû ïîëó÷àåì ñòðîêó çàêîäèðîâàííóþ
                    System.out.println(word);
                    JSONObject Cilka = (JSONObject)JSONValue.parseWithException(word);

                    NomberVar=Cilka.get("option").toString();
                    System.out.println(NomberVar);

                    Repos=Cilka.get("body").toString();
                    System.out.println(Repos);

                    out.write("Ïðèâåò, ýòî Ñåðâåð! Îòâåò: " + Otvet("1","NO") + "\n");
                    out.flush();

                } finally {
                    System.out.println("Âñå óäà÷íî");
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Ñåðâåð çàêðûò!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}