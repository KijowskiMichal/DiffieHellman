package MainPackage;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.InputMismatchException;

public class Main
{
    private static int serverKlient = 0;
    private static int aesDes = 0;
    private static int port = 0;
    private static int acceptReject = 0;
    private static String adres = "";
    private static String path = "";
    private static String file = "";
    private static String encryptedfile = "";
    private static Windows windows = new Windows();
    public static void main(String[]args)
    {
        windows.initialize();
        new Thread(new Runnable() {
            @Override
            public void run() {
                windows.firstScreen();
                while (Main.getServerKlient()==0)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (Main.getServerKlient()==1)
                {
                    windows.secondScreen();
                    while (Main.getAesDes()==0)
                    {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    windows.fourthScreen();
                    while (Main.getPort()==0)
                    {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator.getInstance("DH");
                        algorithmParameterGenerator.init(1024, new SecureRandom());
                        AlgorithmParameters algorithmParameters = algorithmParameterGenerator.generateParameters();
                        String tmp = algorithmParameters.toString();
                        int i0 = tmp.indexOf("p:");
                        int i1 = tmp.indexOf("g:");
                        String tmp0 = tmp.substring(i0+2,i1-1);
                        String tmp1 = tmp.substring(i1+2);
                        tmp0 = tmp0.replace(" ", "").replace("\n", "");
                        tmp1 = tmp1.replace(" ", "").replace("\n", "");
                        BigInteger p = new BigInteger(tmp0, 16);
                        BigInteger g = new BigInteger(tmp1, 16);
                        windows.thirdScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\n");
                        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
                        DHParameterSpec dhSpec = new DHParameterSpec(p, g, 1024);
                        keyGen.initialize(dhSpec);
                        KeyPair keypair = keyGen.generateKeyPair();
                        PrivateKey privateKey = keypair.getPrivate();
                        PublicKey publicKey = keypair.getPublic();
                        windows.thirdScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n");
                        byte[] publicKeyBytes = publicKey.getEncoded();
                        windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n", "Uruchamianie serwera...");
                        ServerSocket welcomeSocket = new ServerSocket(Main.getPort());
                        windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n", "Serwer oczekuje ma połączenie na porcie "+Main.getPort());
                        Socket connectionSocket = welcomeSocket.accept();
                        windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n", "Połączono z "+connectionSocket.getRemoteSocketAddress());
                        DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
                        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                        byte[] tmpBytes = ("|"+getAesDes()+"|"+p+"|"+g+"|"+ (new String(Base64.getEncoder().encode(publicKey.getEncoded()))) +"|").getBytes();
                        outToClient.write(tmpBytes);
                        windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nWysłano do klienta paczkę (protokol, p. g, yA)\n", "Oczekiwanie na yB...");
                        while (inFromClient.available()==0)
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        tmpBytes = new byte[inFromClient.available()];
                        inFromClient.read(tmpBytes, 0, inFromClient.available());
                        String tmpString = (new String(tmpBytes));
                        String[]tmpStringA = tmpString.split("\\|");
                        publicKeyBytes = Base64.getDecoder().decode(tmpStringA[1]);
                        windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nWysłano do klienta paczkę (protokol, p. g, yA)\n\nOdebrano yB: "+Base64.getEncoder().encodeToString(publicKeyBytes)+"\n", "Generowanie klucza...");
                        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
                        KeyFactory keyFact = KeyFactory.getInstance("DH");
                        PublicKey publicKey2 = keyFact.generatePublic(x509KeySpec);
                        KeyAgreement ka = KeyAgreement.getInstance("DH");
                        ka.init(privateKey);
                        ka.doPhase(publicKey2, true);
                        byte[]secret = ka.generateSecret();
                        SecretKey secretKey = new SecretKeySpec(secret, 0, (Main.getAesDes()==1) ? 32 : 8, (Main.getAesDes()==1) ? "AES" : "DES");
                        windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nWysłano do klienta paczkę (protokol, p. g, yA)\n\nOdebrano yB: "+Base64.getEncoder().encodeToString(publicKeyBytes)+"\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n", "Oczekiwanie na jakiś plik...");
                        while (inFromClient.available()==0)
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        tmpBytes = new byte[inFromClient.available()];
                        inFromClient.read(tmpBytes, 0, inFromClient.available());
                        tmpString = (new String(tmpBytes));
                        tmpStringA = tmpString.split("\\|");
                        windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nWysłano do klienta paczkę (protokol, p. g, yA)\n\nOdebrano yB: "+Base64.getEncoder().encodeToString(publicKeyBytes)+"\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n\nOtrzymano zaszyfrowaną nazwę pliku: "+tmpStringA[1]+"\n", "Deszyfrowanie nazwy pliku...");
                        if (Main.getAesDes()==1)
                        {
                            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(Arrays.copyOfRange(publicKey.getEncoded(), 14, 30)));
                            Main.setFile(new String(cipher.doFinal(Base64.getDecoder().decode(tmpStringA[1]))));
                        }
                        else
                        {
                            Cipher cipher = Cipher.getInstance("DES");
                            cipher.init(Cipher.DECRYPT_MODE, secretKey);
                            Main.setFile(new String(cipher.doFinal(Base64.getDecoder().decode(tmpStringA[1]))));
                        }
                        windows.eighthScreen();
                        while (Main.getAcceptReject()==0)
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (Main.getAcceptReject()==1)
                        {
                            windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nWysłano do klienta paczkę (protokol, p. g, yA)\n\nOdebrano yB: "+Base64.getEncoder().encodeToString(publicKeyBytes)+"\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n\nOtrzymano zaszyfrowaną nazwę pliku: "+tmpStringA[1]+"\n\nOdszyfrowano nazwę pliku: "+Main.getFile()+"\n", "Odbieranie pliku "+Main.getFile()+"...");
                            outToClient.write(("|yes|").getBytes());
                            FileOutputStream out = new FileOutputStream("encrypted_"+Main.getFile());
                            byte[] bytes = new byte[8192];
                            while (inFromClient.available()==0)
                            {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            int count;
                            while ((count = inFromClient.read(bytes)) > 0) {
                                out.write(bytes, 0, count);
                            }
                            windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nWysłano do klienta paczkę (protokol, p. g, yA)\n\nOdebrano yB: "+Base64.getEncoder().encodeToString(publicKeyBytes)+"\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n\nOtrzymano zaszyfrowaną nazwę pliku: "+tmpStringA[1]+"\n\nOdszyfrowano nazwę pliku: "+Main.getFile()+"\n\nOdebrano plik: "+Main.getFile()+"\n", "Deszyfrowanie pliku "+Main.getFile()+"...");
                            if (Main.getAesDes()==1)
                            {
                                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                                cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(Arrays.copyOfRange(publicKey.getEncoded(), 14, 30)));
                                FileInputStream is = new FileInputStream("encrypted_"+Main.getFile());
                                FileOutputStream os = new FileOutputStream(Main.getFile());
                                CipherOutputStream cos = new CipherOutputStream(os, cipher);
                                bytes = new byte[64];
                                int numBytes;
                                while ((numBytes = is.read(bytes)) != -1) {
                                    cos.write(bytes, 0, numBytes);
                                }
                                os.flush();
                                os.close();
                                cos.close();
                                is.close();
                            }
                            else
                            {
                                Cipher cipher = Cipher.getInstance("DES");
                                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                                FileInputStream is = new FileInputStream("encrypted_"+Main.getFile());
                                FileOutputStream os = new FileOutputStream(Main.getFile());
                                CipherOutputStream cos = new CipherOutputStream(os, cipher);
                                bytes = new byte[64];
                                int numBytes;
                                while ((numBytes = is.read(bytes)) != -1) {
                                    cos.write(bytes, 0, numBytes);
                                }
                                os.flush();
                                os.close();
                                cos.close();
                                is.close();
                            }
                            windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nWysłano do klienta paczkę (protokol, p. g, yA)\n\nOdebrano yB: "+Base64.getEncoder().encodeToString(publicKeyBytes)+"\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n\nOtrzymano zaszyfrowaną nazwę pliku: "+tmpStringA[1]+"\n\nOdszyfrowano nazwę pliku: "+Main.getFile()+"\n\nOdebrano plik: "+Main.getFile()+"\n\nZdeszyfrowano plik: "+Main.getFile()+"\n", "Zapisano plik "+Main.getFile()+"<br />Zakończono działanie programu.");
                        }
                        else
                        {
                            outToClient.write(("|no|").getBytes());
                            windows.fivethScreen("Wygenerowano p: "+p+"\nWygenerowano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nWysłano do klienta paczkę (protokol, p. g, yA)\n\nOdebrano yB: "+Base64.getEncoder().encodeToString(publicKeyBytes)+"\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n\nOtrzymano zaszyfrowaną nazwę pliku: "+tmpStringA[1]+"\n\nOdszyfrowano nazwę pliku: "+Main.getFile()+"\n", "Zakończono działanie programu. <br /> Możesz zamknąć okno.");
                        }
                        inFromClient.close();
                        outToClient.close();
                        connectionSocket.close();
                        welcomeSocket.close();
                    } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException | InvalidKeyException | IOException | NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    try {
                        windows.sixthScreen();
                        while (Main.getPort()==0)
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        windows.fivethScreen("", "Próba podłączenia się pod "+Main.getAdres()+":"+Main.getPort());
                        Socket connectionSocket = new Socket(Main.getAdres(), Main.getPort());
                        DataInputStream inFromServer = new DataInputStream(connectionSocket.getInputStream());
                        DataOutputStream outToServer = new DataOutputStream(connectionSocket.getOutputStream());
                        windows.fivethScreen("", "Połączono z "+Main.getAdres()+":"+Main.getPort()+"<br />Oczekiwanie na (protokol, p, g, yA)...");
                        while (inFromServer.available()==0)
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        byte[]tmpBytes = new byte[inFromServer.available()];
                        inFromServer.read(tmpBytes, 0, inFromServer.available());
                        String tmpString = (new String(tmpBytes));
                        String[]tmpStringA = tmpString.split("\\|");
                        Main.setAesDes(Integer.valueOf(tmpStringA[1]));
                        BigInteger p = new BigInteger(tmpStringA[2], 10);
                        BigInteger g = new BigInteger(tmpStringA[3], 10);
                        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
                        DHParameterSpec dhSpec = new DHParameterSpec(p, g, 1024);
                        keyGen.initialize(dhSpec);
                        KeyPair keypair = keyGen.generateKeyPair();
                        PrivateKey privateKey = keypair.getPrivate();
                        PublicKey publicKey = keypair.getPublic();
                        windows.fivethScreen("Odebrano p: "+p+"\nOdebrano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nOdebrano yA: "+tmpStringA[4]+"\n", "Wysyłam yB...");
                        tmpBytes = ("|"+ (new String(Base64.getEncoder().encode(publicKey.getEncoded()))) +"|").getBytes();
                        outToServer.write(tmpBytes);
                        windows.fivethScreen("Odebrano p: "+p+"\nOdebrano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nOdebrano yA: "+tmpStringA[4]+"\n\nWysłano yB\n", "Generowanie klucza...");
                        byte[]publicKeyBytes = Base64.getDecoder().decode(tmpStringA[4]);
                        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
                        KeyFactory keyFact = KeyFactory.getInstance("DH");
                        PublicKey publicKey2 = keyFact.generatePublic(x509KeySpec);
                        KeyAgreement ka = KeyAgreement.getInstance("DH");
                        ka.init(privateKey);
                        ka.doPhase(publicKey2, true);
                        byte[]secret = ka.generateSecret();
                        SecretKey secretKey = new SecretKeySpec(secret, 0, (Main.getAesDes()==1) ? 32 : 8, (Main.getAesDes()==1) ? "AES" : "DES");
                        windows.seventhScreen();
                        while (Main.getFile().equals(""))
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        windows.fivethScreen("Odebrano p: "+p+"\nOdebrano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nOdebrano yA: "+tmpStringA[4]+"\n\nWysłano yB\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n", "Szyfrowanie pliku...");
                        if (Main.getAesDes()==1)
                        {
                            FileInputStream fis = new FileInputStream(Main.getPath());
                            FileOutputStream fos = new FileOutputStream("encrypted_"+Main.getFile());
                            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(Arrays.copyOfRange(publicKey2.getEncoded(), 14, 30)));
                            Main.setEncryptedfile(Base64.getEncoder().encodeToString(cipher.doFinal(Main.getFile().getBytes())));
                            CipherInputStream cis = new CipherInputStream(fis, cipher);
                            byte[] bytes = new byte[64];
                            int numBytes;
                            while ((numBytes = cis.read(bytes)) != -1) {
                                fos.write(bytes, 0, numBytes);
                            }
                            fos.flush();
                            fos.close();
                            cis.close();
                            fis.close();
                        }
                        else
                        {
                            FileInputStream fis = new FileInputStream(Main.getPath());
                            FileOutputStream fos = new FileOutputStream("encrypted_"+Main.getFile());
                            Cipher cipher = Cipher.getInstance("DES");
                            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                            Main.setEncryptedfile(Base64.getEncoder().encodeToString(cipher.doFinal(Main.getFile().getBytes())));
                            CipherInputStream cis = new CipherInputStream(fis, cipher);
                            byte[] bytes = new byte[64];
                            int numBytes;
                            while ((numBytes = cis.read(bytes)) != -1) {
                                fos.write(bytes, 0, numBytes);
                            }
                            fos.flush();
                            fos.close();
                            cis.close();
                            fis.close();
                        }
                        windows.fivethScreen("Odebrano p: "+p+"\nOdebrano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nOdebrano yA: "+tmpStringA[4]+"\n\nWysłano yB\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n\nZaszyfrowano plik kluczem: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\nUżyto IV: "+(Main.getAesDes()==1 ? Base64.getEncoder().encodeToString(Arrays.copyOfRange(publicKey2.getEncoded(), 14, 30)) : "Nie dotyczy")+"\n", "Wysyłanie pliku...");
                        tmpBytes = ("|"+ Main.getEncryptedfile() +"|").getBytes();
                        outToServer.write(tmpBytes);
                        windows.fivethScreen("Odebrano p: "+p+"\nOdebrano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nOdebrano yA: "+tmpStringA[4]+"\n\nWysłano yB\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n\nZaszyfrowano plik kluczem: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\nUżyto IV: "+(Main.getAesDes()==1 ? Base64.getEncoder().encodeToString(Arrays.copyOfRange(publicKey2.getEncoded(), 14, 30)) : "Nie dotyczy")+"\n\nWysłąno zapytanie odnośnie pliku: "+Main.getFile()+"\n", "Czekanie na akceptacje pliku...");
                        while (inFromServer.available()==0)
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        byte[]tmpBytes2 = new byte[inFromServer.available()];
                        inFromServer.read(tmpBytes2, 0, inFromServer.available());
                        String tmpString2 = (new String(tmpBytes2));
                        String[]tmpStringA2 = tmpString2.split("\\|");
                        if (tmpStringA2[1].equals("yes"))
                        {
                            windows.fivethScreen("Odebrano p: "+p+"\nOdebrano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nOdebrano yA: "+tmpStringA[4]+"\n\nWysłano yB\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n\nZaszyfrowano plik kluczem: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\nUżyto IV: "+(Main.getAesDes()==1 ? Base64.getEncoder().encodeToString(Arrays.copyOfRange(publicKey2.getEncoded(), 14, 30)) : "Nie dotyczy")+"\n\nWysłąno zapytanie odnośnie pliku: "+Main.getFile()+"\n\nZaakceptowano plik: "+Main.getFile()+"\n", "Wysyłanie pliku.");
                            File file = new File("encrypted_"+Main.getFile());
                            byte[] bytes = new byte[8192];
                            InputStream in = new FileInputStream(file);
                            int count;
                            while ((count = in.read(bytes)) > 0) {
                                outToServer.write(bytes, 0, count);
                            }
                            in.close();
                            windows.fivethScreen("Odebrano p: "+p+"\nOdebrano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nOdebrano yA: "+tmpStringA[4]+"\n\nWysłano yB\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n\nZaszyfrowano plik kluczem: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\nUżyto IV: "+(Main.getAesDes()==1 ? Base64.getEncoder().encodeToString(Arrays.copyOfRange(publicKey2.getEncoded(), 14, 30)) : "Nie dotyczy")+"\n\nWysłąno zapytanie odnośnie pliku: "+Main.getFile()+"\n\nZaakceptowano plik: "+Main.getFile()+"\n", "Wysłano plik.<br />Możesz zamknąć to okno.");
                        }
                        else
                        {
                            windows.fivethScreen("Odebrano p: "+p+"\nOdebrano g: "+g+"\nWygenerowano klucz prywatny: "+Base64.getEncoder().encodeToString(privateKey.getEncoded())+"\nWygenerowano klucz publiczny: "+Base64.getEncoder().encodeToString(publicKey.getEncoded())+"\n\nOdebrano yA: "+tmpStringA[4]+"\n\nWysłano yB\n\nWygenerowano S: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\n\nZaszyfrowano plik kluczem: "+Base64.getEncoder().encodeToString(secretKey.getEncoded())+"\nUżyto IV: "+(Main.getAesDes()==1 ? Base64.getEncoder().encodeToString(Arrays.copyOfRange(publicKey2.getEncoded(), 14, 30)) : "Nie dotyczy")+"\n\nWysłąno zapytanie odnośnie pliku: "+Main.getFile()+"\n", "Odrzucenie przesyłu pliku.<br />Możesz zamknąć to okno.");
                        }
                        inFromServer.close();
                        outToServer.close();
                        connectionSocket.close();
                    } catch (IOException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static int getServerKlient() {
        return serverKlient;
    }

    public static void setServerKlient(int serverKlient) {
        Main.serverKlient = serverKlient;
    }

    public static int getAesDes() {
        return aesDes;
    }

    public static void setAesDes(int aesDes) {
        Main.aesDes = aesDes;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Main.port = port;
    }

    public static String getAdres() {
        return adres;
    }

    public static void setAdres(String adres) {
        Main.adres = adres;
    }

    public static String getFile() {
        return file;
    }

    public static void setFile(String file) {
        Main.file = file;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        Main.path = path;
    }

    public static String getEncryptedfile() {
        return encryptedfile;
    }

    public static void setEncryptedfile(String encryptedfile) {
        Main.encryptedfile = encryptedfile;
    }

    public static int getAcceptReject() {
        return acceptReject;
    }

    public static void setAcceptReject(int acceptReject) {
        Main.acceptReject = acceptReject;
    }
}
