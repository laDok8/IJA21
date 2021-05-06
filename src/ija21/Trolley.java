/*
  Class Trolley represent a trolley in the map.
  @author Ladislav Dokoupil (xdokou14)
  @author Adrián Bobola (xbobol00)
 */

package ija21;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Trolley extends Storeable {
    private final int id;
    private static final int capacity = 5;
    public boolean unload = false;

    public Map.Entry<String, Integer> task;
    public List<Coordinates> pathList;
    public Coordinates location;

    /**
     * Save trolley data into local structures task
     * @param id_in trolley ID
     * @param cord  trolley coordinates (contains X and Y)
     */
    public Trolley(int id_in, Coordinates cord) {
        super(cord, 0,0,1);
        this.id = id_in;
        Label label = new Label();
        label.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));

        List<Line> pathVisual = new ArrayList<>();
        this.setOnMouseEntered(mouseEvent -> {
            StringBuilder str = new StringBuilder("TROLLEY \ncontains:");
            for (Map.Entry<String, Integer> item:this.getStored().entrySet()) {
                str.append("\n").append(item.getKey()).append(": ").append(item.getValue());
            }
            label.setText(str.toString());
            label.relocate((int)this.getX()+blockWidth.SCALE, (int)this.getY()+blockWidth.SCALE);
            GlobalScene.pane.getChildren().add(label);

            //middPoint of codinate
            int mid = blockWidth.SCALE/2;
            //make all lines
            if(pathList == null)
                return;
            for (int i=0;i < pathList.size()-1;i++){
                Line l = new Line(pathList.get(i).getX()+mid,pathList.get(i).getY()+mid,
                        pathList.get(i+1).getX()+mid,pathList.get(i+1).getY()+mid);
                l.setStroke(Color.RED);
                pathVisual.add(l);
            }
            GlobalScene.pane.getChildren().addAll(pathVisual);
        });
        this.setOnMouseExited(mouseEvent -> {
            GlobalScene.pane.getChildren().remove(label);
            GlobalScene.pane.getChildren().removeAll(pathVisual);
            pathVisual.clear();
        });
    }

    /**
     * get trolley ID
     * @return trolley ID
     */
    public int getid() {
        return id;
    }

    /**
     * get amount of items in trolley
     * @return amount of items
     */
    public int getItemInCount() {
        int sum = 0;
        for (int count : this.getStored().values() ) {
            sum+=count;
        }
        return sum;
    }

    public void unloadGoods(){
        for (Map.Entry<String, Integer> item: this.getStored().entrySet()) {
            this.delete_item(item.getKey(),item.getValue());
        }
        this.unload = false;
    }
    public int getRemainCapacity() {
        return capacity-this.getItemInCount();
    }


   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Trolley trolley = (Trolley) o;
        return id == trolley.id && location.equals(trolley.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, location);
    }*/
}
