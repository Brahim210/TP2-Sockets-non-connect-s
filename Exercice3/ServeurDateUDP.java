package Exercice3;

import java.net.*;
import java.util.Date;

public class ServeurDateUDP {
    public static void main(String[] args) {
        try {
            // Création d'une socket UDP écoutant sur le port 1250
            DatagramSocket socket = new DatagramSocket(1250);

            // Boucle infinie pour écouter les datagrammes entrants
            while (true) {
                // Création d'un buffer pour stocker les données reçues
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Attente de la réception d'un datagramme
                socket.receive(packet);

                // Récupération de l'adresse et du port de l'émetteur
                InetAddress adresseClient = packet.getAddress();
                int portClient = packet.getPort();

                // Création d'une chaîne contenant la date et l'heure courante
                String dateHeure = new Date().toString();
                byte[] dateHeureBytes = dateHeure.getBytes();

                // Création d'un nouveau datagramme contenant la date et l'heure
                DatagramPacket packetReponse = new DatagramPacket(dateHeureBytes, dateHeureBytes.length, adresseClient, portClient);

                // Envoi du datagramme contenant la date et l'heure à l'émetteur
                socket.send(packetReponse);

                // Affichage de l'heure de la réponse côté serveur (facultatif)
                System.out.println("Réponse envoyée à " + adresseClient + ":" + portClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
