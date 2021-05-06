package ija21;

public class PathObject extends Coordinates{
    public PathObject(Coordinates cord,Controller c) {
        super((int)cord.getX(),(int)cord.getY());
        this.setOnMouseClicked(mouseEvent -> {
            System.out.println("PATH: "+(int)cord.getX()+" y: "+(int)cord.getY());
            c.jsonParser.addShelf(cord);
            c.root.getChildren().add(c.jsonParser.getAllShelfs().getOrDefault(cord, new Shelf(cord)));
            c.findPath.updatePaths(c.jsonParser.getAllShelfs());
        });
    }
}
