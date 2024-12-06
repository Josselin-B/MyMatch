package composant;

public class Participant {
    String name;
    int id;

    public Participant(String name, int id) {
        this.name=name;
        this.id=id;
    }







    public String toString(){
        return name+" "+id;
    }
}
