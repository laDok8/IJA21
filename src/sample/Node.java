package sample;

import java.util.*;

class Node implements Comparable<Node> {
    Cordinates self;
    Node parent;
    //distance from finish
    Double h = Double.MAX_VALUE;
    //distance from start
    Double g = Double.MAX_VALUE;
    //g+h
    //Double f;
    List<Node> neighbours = new ArrayList<>();

    public Node(Cordinates self){
        this.self = self;
    }

    public void update(Node parent,double g){
        this.g = g;
        this.parent = parent;
    }
    public Double heuristic(){
        return g+h;
    }

    @Override
    public int compareTo(Node x) {
        return (int) (this.heuristic() - x.heuristic());
    }

    static Map<Cordinates,Node> paths = new Hashtable<>();

    static final int CONSTANT = 3;

    public static void updatePaths( Map<Cordinates, Shelf> shelfs){

        //make new paths ( opposite of shelfs)
        paths.clear();
        for (int x = 0; x <= CONSTANT; x++) {
            for (int y = 0; y <= CONSTANT; y++) {
                Cordinates position = new Cordinates(x,y);
                //is shelf
                if(shelfs.containsKey(position))
                    continue;
                paths.put(position,new Node(position));
            }
        }

        //add neighbour
        for(var entry : paths.entrySet()){
            int x = entry.getKey().x;
            int y = entry.getKey().y;

            for(int x1=x-1;x1<=x+1;x1++){
                for(int y1=y-1;y1<=y+1;y1++){
                    Cordinates seek = new Cordinates(x1,y1);
                    //out of bounds
                    if (x1 < 0 || x1 > CONSTANT || y1 < 0 || y1 > CONSTANT)
                        continue;
                    //wall or self
                    if (shelfs.containsKey(seek) || (x1 == x && y1 == y))
                        continue;
                    entry.getValue().neighbours.add(paths.get(seek));
                }
            }
        }


    }


    public static Node aStar(Cordinates start, Cordinates end) {
        Queue<Node> open = new PriorityQueue<>();
        Queue<Node> closed = new PriorityQueue<>();
        Node startN = paths.get(start);
        open.add(startN);
        startN.g = .0;


        while (!open.isEmpty()) {
            Node next = open.peek();
            if (next == paths.get(end) || next.self.getDistance(end) == 1) {
                return next;
            }

            for(var node : next.neighbours){
                node.h = end.getDistance(node.self);

                if(!open.contains(node) && !closed.contains(node)){
                    node.parent = next;
                    node.g = next.g + next.self.getDistance(node.self);
                    open.add(node);
                } else if(next.g + next.self.getDistance(node.self) < node.g) {
                        node.parent = next;
                        node.g = next.g + next.self.getDistance(node.self);
                        if(!closed.contains(node)){
                            closed.remove(node);
                            open.add(node);
                        }
                }
            }
            open.remove(next);
            closed.add(next);
        }
    return null;
    }
}