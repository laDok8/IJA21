package sample;

public class Trolley extends Storeable{
    private int id,capacity,items_in;

    /**
     * @param id_in - identifikacia vozika
     */
    public Trolley(int id_in, Cordinates cord){
        super(cord.x, cord.y);
        this.id = id_in;
        this.capacity = 5;
        this.items_in = 0;
    }

    /**
     * Vrati ID vozika
     * @return ID vozika
     */
    public int getId(){
        return id;
    }

    public int getItemInCount(){
        return stored.size();
    }

    public void moveX(int direction) {
        //if (Math.abs(direction) == 1){
        if (direction == 1){
            this.x += direction;
        } else if (direction == -1){
            this.x -= direction;
        }
    }
    public void moveY(int direction) {
        //if (Math.abs(direction) == 1){
        if (direction == 1){
            this.y += direction;
        } else if (direction == -1){
            this.y -= direction;
        }
    }



}
