package sample;

import java.util.*;

class PathNode implements Comparable<PathNode> {
    Cordinates self;
    PathNode parent;
    //distance from finish
    Double h = Double.MAX_VALUE;
    //distance from start
    Double g = Double.MAX_VALUE;

    List<PathNode> neighbours = new ArrayList<>();

    public PathNode(Cordinates self){
        this.self = self;
    }

    public void update(PathNode parent, double g){
        this.g = g;
        this.parent = parent;
    }
    public Double heuristic(){
        return g+h;
    }

    @Override
    public int compareTo(PathNode x) {
        return (int) (this.heuristic() - x.heuristic());
    }
}