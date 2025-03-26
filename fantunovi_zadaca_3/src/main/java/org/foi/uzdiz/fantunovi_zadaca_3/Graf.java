package org.foi.uzdiz.fantunovi_zadaca_3;

import java.util.*;

import org.foi.uzdiz.builderstanica.Stanica;
import org.foi.uzdiz.compositevoznired.Etape;
import org.foi.uzdiz.compositevoznired.LeafStanica;
import org.foi.uzdiz.compositevoznired.Vlak;
import org.foi.uzdiz.factoryucitavanjedatoteka.ZsDatoteka;

public class Graf {
    private Map<String, Node> nodes;
    private List<Edge> edges;

    public Graf() {
        nodes = new HashMap<>();
        edges = new ArrayList<>();
    }

    public void addNode(String oznakaStanice, String type) {
        nodes.putIfAbsent(oznakaStanice, new Node(oznakaStanice, type));
    }

    public void addEdge(String from, String to, double udaljenost) {
        Node nodeFrom = nodes.get(from);
        Node nodeTo = nodes.get(to);
        if (nodeFrom != null && nodeTo != null) {
            boolean postojiEdge = nodeFrom.getEdges().stream()
                .anyMatch(e -> e.getTo().equals(nodeTo) && e.getUdaljenost() == udaljenost);

            if (!postojiEdge) {
                Edge edge1 = new Edge(nodeFrom, nodeTo, udaljenost);
                nodeFrom.addEdge(edge1);

                Edge edge2 = new Edge(nodeTo, nodeFrom, udaljenost);
                nodeTo.addEdge(edge2);

                edges.add(edge1);
                edges.add(edge2);
            }
        }
    }


    public Node getNode(String oznakaStanice) {
        return nodes.get(oznakaStanice);
    }

    public void printajGraf() {
        for (Node node : nodes.values()) {
            System.out.println("Stanica: " + node.getOznakaStanice());
            for (Edge edge : node.getEdges()) {
                System.out.println("  - Povezana s: " + edge.getTo().getOznakaStanice() + ", Udaljenost: " + edge.getUdaljenost());
            }
        }
    }

    public void izgradnjaGrafaIzEtapa(List<Etape> etape, ZsDatoteka zsDatoteka) {
        for (Etape etapa : etape) {
            List<LeafStanica> staniceNaEtapi = etapa.getStaniceNaEtapi(etapa.getOznakaPruge(), zsDatoteka);

            for (int i = 0; i < staniceNaEtapi.size() - 1; i++) {
                LeafStanica startStanica = staniceNaEtapi.get(i);
                LeafStanica endStanica = staniceNaEtapi.get(i + 1);
                double udaljenost = etapa.izracunajUdaljenost(startStanica, endStanica, etapa.getOznakaPruge(), zsDatoteka);

                addNode(startStanica.getNaziv(), "Stanica");
                addNode(endStanica.getNaziv(), "Stanica");
                addEdge(startStanica.getNaziv(), endStanica.getNaziv(), udaljenost);
            }

            List<String> prijelazi = etapa.dohvatiPrijelaze();
            for (int i = 0; i < prijelazi.size() - 1; i++) {
                String startPrijelaz = prijelazi.get(i);
                String endPrijelaz = prijelazi.get(i + 1);

                addNode(startPrijelaz, "Stanica");
                addNode(endPrijelaz, "Stanica");
                addEdge(startPrijelaz, endPrijelaz, 0);
            }
        }

        for (int i = 0; i < etape.size(); i++) {
            for (int j = i + 1; j < etape.size(); j++) {
                Etape etapa1 = etape.get(i);
                Etape etapa2 = etape.get(j);

                List<String> zajednickeStanice = etapa1.dohvatiZajednickeStanice(etapa2, zsDatoteka);

                for (String zajednickaStanica : zajednickeStanice) {
                    addNode(zajednickaStanica, "Stanica");
                    addEdge(etapa1.getOznakaPruge(), etapa2.getOznakaPruge(), 0);
                }
            }
        }
    }
    
    public List<String> bfs(String start, String odrediste) {
        Set<String> posjeceneStanice = new HashSet<>();
        Queue<List<String>> queue = new LinkedList<>();
        Map<String, Double> udaljenostOdPocetka = new HashMap<>();
        List<String> inicijalniPut = new ArrayList<>();
        inicijalniPut.add(start);
        queue.add(inicijalniPut);
        udaljenostOdPocetka.put(start, 0.0);

        while (!queue.isEmpty()) {
            List<String> put = queue.poll();
            String trenutnaStanica = put.get(put.size() - 1);

            if (trenutnaStanica.equals(odrediste)) {
                printajDetaljeRute(put, udaljenostOdPocetka);
                return put;
            }

            if (posjeceneStanice.contains(trenutnaStanica)) {
                continue;
            }
            posjeceneStanice.add(trenutnaStanica);

            Node currentNode = getNode(trenutnaStanica);
            if (currentNode != null) {
                for (Edge edge : currentNode.getEdges()) {
                    String susjed = edge.getTo().getOznakaStanice();
                    if (!posjeceneStanice.contains(susjed)) {
                        List<String> noviPut = new ArrayList<>(put);
                        noviPut.add(susjed);

                        double novaUdaljenost = udaljenostOdPocetka.get(trenutnaStanica) + edge.getUdaljenost();
                        udaljenostOdPocetka.put(susjed, novaUdaljenost);
                        queue.add(noviPut);
                    }
                }
            }
        }

        System.out.println("Nema puta između " + start + " i " + odrediste);
        return Collections.emptyList();
    }

    private void printajDetaljeRute(List<String> put, Map<String, Double> udaljenostOdPocetka) {
        System.out.printf("\n%-20s %-15s %s%n", "Naziv stanice", "Vrsta stanice", "Kilometara od početne stanice");
        System.out.println("-----------------------------------------------------------");

        for (String nazivStanice : put) {
            Node node = getNode(nazivStanice);
            String vrstaStanice = node.getTip();
            double udaljenostOdPocetne = udaljenostOdPocetka.get(nazivStanice);

            System.out.printf("%-20s %-15s %.1f km%n", nazivStanice, vrstaStanice, udaljenostOdPocetne);
        }
    }
    
    public Edge getEdge(String from, String to) {
        for (Edge edge : edges) {
            if (edge.getFrom().getOznakaStanice().equals(from) &&
                edge.getTo().getOznakaStanice().equals(to)) {
                return edge;
            }
        }
        return null;
    }


    public static class Node {
        private String oznakaStanice;
        private String tip;
        private List<Edge> edges;
        private double udaljenostOdPocetka;

        public Node(String oznakaStanice, String type) {
            this.oznakaStanice = oznakaStanice;
            this.tip = type;
            this.edges = new ArrayList<>();
            this.udaljenostOdPocetka = Double.POSITIVE_INFINITY;
        }

        public void addEdge(Edge edge) {
            edges.add(edge);
        }

        public String getOznakaStanice() {
            return oznakaStanice;
        }

        public String getTip() {
            return tip;
        }

        public List<Edge> getEdges() {
            return edges;
        }

        public double getUdaljenostOdPocetka() {
            return udaljenostOdPocetka;
        }

        public void setUdaljenostOdPocetka(double distance) {
            this.udaljenostOdPocetka = distance;
        }
    }


    public static class Edge {
        private Node from;
        private Node to;
        private double udaljenost;

        public Edge(Node from, Node to, double udaljenost) {
            this.from = from;
            this.to = to;
            this.udaljenost = udaljenost;
        }

        public Node getFrom() {
            return from;
        }

        public Node getTo() {
            return to;
        }

        public double getUdaljenost() {
            return udaljenost;
        }
    }
}
