/*
  Class PathNode
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */
package sample;

import java.util.*;

class PathNode implements Comparable<PathNode> {
    Coordinates self;
    PathNode parent;
    //distance from finish
    Double h = Double.MAX_VALUE;
    //distance from start
    Double g = Double.MAX_VALUE;

    List<PathNode> neighbours = new ArrayList<>();

    public PathNode(Coordinates self){
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