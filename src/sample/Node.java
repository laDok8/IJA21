package sample;

import java.util.*;

class Node implements Comparable<Node> {
    Cordinates parent, self;
    //distance from finish
    Double f = Double.MAX_VALUE;
    //distance from start
    Double g = Double.MAX_VALUE;
    //g+h
    Double h;

    public Double getF() {
        return f;
    }

    public void setF(Double f) {
        this.f = f;
    }

    public Double getG() {
        return g;
    }

    public void setG(Double g) {
        this.g = g;
    }

    public Node(Double h, Cordinates parent, Cordinates self) {
        this.h = h;
        this.parent = parent;
        this.self = self;
    }

    @Override
    public int compareTo(Node x) {
        return (int) (this.h - x.h);
    }


    public static void aStar(Cordinates start, Cordinates end, Map<Cordinates, Shelf> shelfs) {
        Queue<Node> open = new PriorityQueue<>();
        Queue<Node> closed = new PriorityQueue<>();
        Node startN = new Node(0.0, null, start);
        open.add(startN);
        int CONSTANT = 40;


        while (!open.isEmpty()) {
            Node next = open.poll();
            if (next.self == end) {
                System.out.println("WIN");
                return;
            }

            //GET NEIGHBOURS
            for (int x = next.self.x - 1; x <= next.self.x + 1; x++) {
                for (int y = next.self.y - 1; y <= next.self.y + 1; y++) {
                    //out of bounds
                    if (x < 0 || x > CONSTANT || y < 0 || y > CONSTANT)
                        continue;
                    //wall
                    if (shelfs.containsKey(new Cordinates(x, y)))
                        continue;
                    //already discovered - check if better path
                    /*if(closed.contains(next) || open.contains(next)){
                        next.
                    }*/

                }
            }


        }


    }
}