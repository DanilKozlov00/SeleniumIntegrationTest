import java.io.*;

import java.net.ServerSocket;

import java.net.Socket;

import org.json.simple.*;

import org.json.simple.parser.*;


public class Server {

    private static Socket clientSocket; //Сокет клиента

    private static ServerSocket server; //Сокет сервера

    private static BufferedReader in; //Чтобы принимать и отправлять сообщения буферы

    private static BufferedWriter out;

    public static String TypeMessage;

    public static String NomberVar;

    public static String Repos;

    public static String NomberLab;

    public static boolean conec=false;

//ФУНКЦИЯ создания ДЖСОН ОТВЕТА

    public static String Otvet(String grade,String comment) {//Получаем рез.работы силениума

        JSONObject jsonObject = new JSONObject();//Создаем новый обьект

        jsonObject.put("messageType", "2");//Все ли успешно

        jsonObject.put("grade", grade);

        jsonObject.put("comment", comment);//Если нет, то где ошибки

        System.out.println(jsonObject.toJSONString());

        return jsonObject.toString();//обьект ДЖСОН в строку и отправляем

    }

    public static String ErrorServer(IOException a) {//метод с ошибкой на сервере

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("messageType", "4");

        jsonObject.put("errorMessage", a);

        System.out.println(jsonObject.toJSONString());

        return jsonObject.toString();

    }

    public static String ErrorArg(String nameKey,String rejectCodeName) {//метод с ошибкой на сервере

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("messageType", "3");

        jsonObject.put("key",nameKey);

        jsonObject.put("rejectCode",rejectCodeName);

        System.out.println(jsonObject.toJSONString()+"OSHIBKA");

        return jsonObject.toString();

    }

    public static void OsnovaDecoda(String word) throws ParseException, IOException

    {

        System.out.println(word);

        JSONObject Cilka = (JSONObject)JSONValue.parseWithException(word);

        TypeMessage=Cilka.get("messageType").toString();

        System.out.println(TypeMessage);

        byte x1=Byte.parseByte(TypeMessage);

        if(x1!=1) {

            out.write(ErrorArg("messageType","2"));

            out.flush();

            conec=true;

        }

        NomberLab=Cilka.get("lab").toString();

        System.out.println(NomberLab);

        byte x2=Byte.parseByte(NomberLab);

        if(x2!=3) {

            out.write(ErrorArg("lab","2"));

            out.flush();

            conec=true;

        }

        NomberVar=Cilka.get("variant").toString();

        System.out.println(NomberVar);

        byte x3=Byte.parseByte(NomberVar);

        if(x3<0||x3>15) {

            out.write(ErrorArg("variant","3"));

            out.flush();

            conec=true;

        }

        Repos=Cilka.get("link").toString();

        System.out.println(Repos);

        String s1=Repos.substring(0,19);

        String s2="https://github.com/";

        if(!s1.equals(s2)) {

            out.write(ErrorArg("link","1"));

            out.flush();

            conec=true;

        }

    }

    public static void main(String[] args) throws ParseException, IOException {

        try {

            try {

                server = new ServerSocket(4003); //Сервер на порте 4003

                System.out.println("Сервер запущен!");

                clientSocket = server.accept(); //Сервер готов принимать сигнал

                try {

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Это тупо обертки буферов

                    out = new BufferedWriter(new

                            OutputStreamWriter(clientSocket.getOutputStream()));

                    String word = in.readLine(); //Это мы получаем строку закодированную

                    System.out.println(word+"This prisho ot kletnta");

                    JSONObject Cilka = (JSONObject)JSONValue.parseWithException(word);

                    TypeMessage=Cilka.get("messageType").toString();

                    System.out.println(TypeMessage);

                    byte x1=Byte.parseByte(TypeMessage);

                    if(x1!=1) {

                        out.write(ErrorArg("messageType","2"));

                        out.flush();

                        conec=true;

                    }

                    NomberLab=Cilka.get("lab").toString();

                    System.out.println(NomberLab);

                    byte x2=Byte.parseByte(NomberLab);

                    if(x2!=3) {

                        out.write(ErrorArg("lab","2"));

                        out.flush();

                        conec=true;

                    }

                    NomberVar=Cilka.get("variant").toString();

                    System.out.println(NomberVar);

                    byte x3=Byte.parseByte(NomberVar);

                    if(x3<0||x3>15) {

                        out.write(ErrorArg("variant","3"));

                        out.flush();

                        conec=true;

                    }

                    Repos=Cilka.get("link").toString();

                    System.out.println(Repos);

                    String s1=Repos.substring(0,19);

                    String s2="https://github.com/";

                    if(!s1.equals(s2)) {

                        out.write(ErrorArg("link","1"));

                        out.flush();

                        conec=true;

                    }

                    if(conec==false) {

                        out.write("Привет, это Сервер! Ответ: " + Otvet("1","NO") + "\n");

                        out.flush();

                    }

                } finally {

                    System.out.println("Все удачно");

                    clientSocket.close();

                    in.close();

                    out.close();

                }

            } finally {

                System.out.println("Сервер закрыт!");

                server.close();

            }

        } catch (IOException e) {

            out.write(ErrorServer(e));//здесь формируется json ошибки и отправляется пользователю

            out.flush();

            clientSocket.close();

            in.close();

            out.close();

        }

    }}