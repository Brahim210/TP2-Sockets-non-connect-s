package Exercice2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;
public class ClientVoitureUDP {
public static void main(String argv[]) {
    int port = 0;
    String host = "";
    Scanner keyb = new Scanner(System.in);
    try {
        // Création d'un objet Voiture
        voiture maVoiture = new voiture("Kia", "Picanto");

        // Conversion de l'objet Voiture en tableau de bytes

            //Création d'un flux de sortie en mémoire
            ByteArrayOutputStream fluxS = new ByteArrayOutputStream();
            
            //Création d'un nouvel objet: Prend le flux de sortie en mémoire "fluxS"
            ObjectOutputStream obj = new ObjectOutputStream(fluxS);

            //Ecriture de l'objet "maVoiture" dans le flux de sortie: c.à.d l'objet "maVoiture" 
            //est converti en une séquence de bytes et écrit dans le flux de sortie "fluxS"
            obj.writeObject(maVoiture);

            //Convertion des données sérialisées dans le flux de sortie "fluxS" en un tableau de bytes.
            // données à envoyer : objet "voiture"
            byte[] data = fluxS.toByteArray();

        // on récupère les paramètres : nom de la machine serveur et
        // numéro de port
        System.out.println("Adress du serveur : ");
        host = keyb.next();
        System.out.println("port d'écoute du serveur : ");
        port = keyb.nextInt();
        InetAddress adr;
        DatagramPacket packet;
        DatagramSocket socket;
        // adr contient l'@IP de la partie serveur
        adr = InetAddress.getByName(host);
        // création du paquet avec les données et en précisant l'adresse
        // du serveur (@IP et port sur lequel il écoute)
        packet = new DatagramPacket(data, data.length, adr, port);
        // création d'une socket, sans la lier à un port particulier
        socket = new DatagramSocket();
        // envoi du paquet via la socket
        socket.send(packet);

        
        // création d'un tableau vide pour la réception
        byte[] reponse = new byte[1024];
        packet.setData(reponse);
        packet.setLength(reponse.length);
        // attente paquet envoyé sur la socket du client
        socket.receive(packet);
        // récupération et affichage de la donnée contenue dans le paquet
         // Récupération des données du paquet
            byte[] dataR = packet.getData();

            // Conversion des données en objet Voiture
            ByteArrayInputStream bais = new ByteArrayInputStream(dataR);
            ObjectInputStream ois = new ObjectInputStream(bais);
            voiture voitureRecue = (voiture) ois.readObject();
        System.out.println(" Volume du carburant devient : " + voitureRecue.getCarburant());
    } catch (Exception e) {
    System.err.println("Erreur : " + e);
}
}
}