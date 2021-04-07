package sample;

import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class FindPath {


    Map<Cordinates, PathNode> paths = new Hashtable<>();

     int maxX, maxY;

     public FindPath(int maxX, int maxY){
         this.maxX = maxX;
         this.maxY = maxY;
     }


    public void updatePaths( Map<Cordinates, Shelf> shelfs){

        //make new paths ( opposite of shelfs)
        paths.clear();
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                Cordinates position = new Cordinates(x,y);
                //is shelf
                if(shelfs.containsKey(position))
                    continue;
                paths.put(position,new PathNode(position));
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
                    if (x1 < 0 || x1 > maxX || y1 < 0 || y1 > maxY)
                        continue;
                    //wall or self
                    if (shelfs.containsKey(seek) || (x1 == x && y1 == y))
                        continue;
                    entry.getValue().neighbours.add(paths.get(seek));
                }
            }
        }


    }

    public PathNode aStar(Cordinates start, Cordinates end) {
        Queue<PathNode> open = new PriorityQueue<>();
        Queue<PathNode> closed = new PriorityQueue<>();
        PathNode startN = paths.get(start);
        open.add(startN);
        startN.g = .0;


        while (!open.isEmpty()) {
            PathNode next = open.peek();
            if (next == paths.get(end) || next.self.getDistance(end) == 1) {
                return next;
            }

            for(var node : next.neighbours){
                node.h = end.getDistance(node.self);

                if(!open.contains(node) && !closed.contains(node)){
                    node.update(next, next.g + next.self.getDistance(node.self));
                    open.add(node);
                } else if(next.g + next.self.getDistance(node.self) < node.g) {
                    node.update(next, next.g + next.self.getDistance(node.self));
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
