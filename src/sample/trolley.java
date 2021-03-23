package sample;

public class trolley {
    int id;
    int capacity;
    int items_in;

    /**
     * @param id_in - identifikacia vozika
     */
    public trolley(int id_in){
        this.id = id_in;
        this.capacity = 0;
        this.items_in = 0;
    }

    /**
     * Vrati ID vozika
     * @return ID vozika
     */
    public int getId(){
        return this.id;
    }

    /**
     * Nastavi parameter kapacita pre dany vozik
     * @param capacity - kapacita daneho vozika
     */
    public void set_capacity(int capacity){
        this.capacity = capacity;
    }

    /**
     * Nastavi parameter pocet poloziek na voziku pre dany vozik
     * @param items_in_count - pocet poloziek na voziku
     */
    public void set_items_count(int items_in_count){
        this.items_in = items_in_count;
    }



}
