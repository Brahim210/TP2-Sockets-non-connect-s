package Exercice2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*; 
import java.util.Scanner; 
public class ServeurVoitureUDP { 
    public static void main(String argv[]) { 
        int port = 0; Scanner keyb = new Scanner(System.in); 
        try { 
            // on récupère le paramètre : port d'écoute 
            System.out.println("port d'écoute : "); 
            port = keyb.nextInt(); 
            DatagramPacket packet; 
            
            // création d'une socket liée au port précisé en paramètre 
            DatagramSocket socket = new DatagramSocket(port); 

            // tableau de 1024 octets qui contiendra les données reçues 
            byte[] data = new byte[1024]; 

            // création d'un paquet en utilisant le tableau d'octets 
            packet = new DatagramPacket(data, data.length); 

            // attente de la réception d'un paquet. Le paquet reçu est placé 
            // dans packet et ses données dans data. 
            socket.receive(packet); 

            // Récupération des données du paquet
            byte[] dataR = packet.getData();

            // Conversion des données en objet Voiture
                //Création d'un nouvel objet à partir du tableau de bytes "dataR" contenant les données reçues du client
            ByteArrayInputStream bais = new ByteArrayInputStream(dataR);

                //Création d'un nouvel objet ObjectInputStream à partir du "bais": lire des objets sérialisés à partir d'un flux d'entrée
            ObjectInputStream ois = new ObjectInputStream(bais);

                //Lire de l'objet sérialisé à partir du flux d'entrée "ois" et le désérialise en un objet de type "voiture".
            voiture voitureRecue = (voiture) ois.readObject();

            System.out.println(" ca vient de : " + packet.getAddress() + ":" + packet.getPort()); 

            // Fixation de la quantité de carburant
            voitureRecue.setCarburant(50);
            

            // on met une nouvelle donnée dans le paquet 
            // (qui contient donc le couple @IP/port de la socket coté client) 

            // Conversion de l'objet Voiture en tableau de bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(voitureRecue);
            byte[] reponse = baos.toByteArray();

            packet.setData(reponse); 
            packet.setLength(reponse.length); 
            // on envoie le paquet au client 
            socket.send(packet); 
        } catch (Exception e) 
        { System.err.println("Erreur : " + e); 
    } 
} 
}