public class Player {
    //states
    int points, card1, card2;

    //constructor
    public Player(){

    }

    //behaviour
    public void setPoints(int value){
        this.points = value;
    }

    public int getPoints(){
        return this.points;
    }

    public void addPoints(int value){
        this.points += value;
    }

    public void setCard1(int card){
        this.card1 = card;
    }

    public int getCard1(){
        return this.card1;
    }

    public void setCard2(int card){
        this.card2 = card;
    }

    public int getCard2(){
        return this.card2;
    }
}
