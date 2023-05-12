import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        // variables
        int playerTokens = 100, playerBet, doubleDown;
        String cardName, choice = "", keepPlaying = "", bet;

        //player object
        Player player = new Player();

        //dealer object
        Player dealer = new Player();

        // input
        Scanner input = new Scanner(System.in);

        // show rules
        rules();

        System.out.println("Press enter to continue.");
        input.nextLine();

        //loop unless player enters no
        while(!keepPlaying.equalsIgnoreCase("quit")) {
            // reset bet
            playerBet = 0;
            doubleDown = 1;

            System.out.println("\nCurrent tokens: " + playerTokens);
            // get bet
            // loop until valid int has been input
            while(playerBet <= 0 || playerBet > playerTokens){
                System.out.println("\nPlease enter a valid bet above 0: ");
                bet = input.next();
                // try catch used to check for valid int
                try {
                    playerBet = Integer.parseInt(bet);
                } catch(NumberFormatException ne) {
                    System.out.println("Please enter a whole number!");
                }
            }

            // deal in players first 2 cards
            player.setCard1(deal());
            // show card name
            cardName = convertCard(player.getCard1());
            System.out.println("\n" + cardName + "\nValue: " + player.getCard1());

            player.setCard2(deal());
            cardName = convertCard(player.getCard2());
            System.out.println("\n" + cardName + "\nValue: " + player.getCard2());

            //check for blackjack
            if ((player.getCard1() == 1 && player.getCard2() == 10) || (player.getCard2() == 1 && player.getCard1()== 10)) {
                System.out.println("Player blackjack!");
                player.setPoints(21);
            } else {
                player.setPoints(player.getCard1() + player.getCard2());
            }

            System.out.println("\nCurrent points: " + player.getPoints());

            System.out.println("Press enter to continue.");
            input.nextLine();

            //deal dealers first card
            System.out.println("\nDealing dealer's cards.");

            dealer.setCard1(deal());
            cardName = convertCard(dealer.getCard1());
            System.out.println("\n" + cardName + "\nValue: " + dealer.getCard1());

            dealer.setPoints(dealer.getCard1());

            System.out.println("Press enter to continue.");
            input.nextLine();

            //reset player choice
            choice = "";

            // get players choice
            while ((player.getPoints() < 21) && !choice.equals("stand") && !choice.equals("double")) {
                choice = playerChoice();

                // hit if player chose hit
                if (!choice.equalsIgnoreCase("stand")) {
                    player.setCard2(deal());
                    cardName = convertCard(player.getCard2());
                    System.out.println("\n" + cardName + "\nValue: " + player.getCard2());

                    player.addPoints(player.getCard2());

                    System.out.println("\nCurrent points: " + player.getPoints());
                }

                // double down
                if(choice.equalsIgnoreCase("double")){
                    doubleDown = 2;
                }
            }

            // bust
            if (player.getPoints() > 21) {
                System.out.println("player bust!");
            }

            System.out.println("Press enter to continue.");
            input.nextLine();

            // dealers second card
            System.out.println("\nDealing dealer's cards.");

            dealer.setCard2(deal());
            cardName = convertCard(dealer.getCard2());
            System.out.println("\n" + cardName + "\nValue: " + dealer.getCard2());

            //check for blackjack
            if ((dealer.getCard1() == 1 && dealer.getCard2() == 10) || (dealer.getCard2() == 1 && dealer.getCard1() == 10)) {
                System.out.println("Dealer blackjack!");
                dealer.setPoints(21);
            } else {
                dealer.addPoints(dealer.getCard2());
            }

            System.out.println("\nCurrent points: " + dealer.getPoints());

            //deal dealer until they get 17+ and player is not bust
            while (dealer.getPoints() < 17 && player.getPoints() < 22) {
                dealer.setCard2(deal());
                cardName = convertCard(dealer.getCard2());
                System.out.println("\n" + cardName + "\nValue: " + dealer.getCard2());

                dealer.addPoints(dealer.getCard2());
                System.out.println("\nCurrent points: " + dealer.getPoints());
            }

            // check for dealer bust
            if (dealer.getPoints() > 21) {
                System.out.println("Dealer bust!");
            }

            System.out.println("Press enter to continue.");
            input.nextLine();

            //multiply bet by double down
            playerBet *= doubleDown;

            // check for win or lose
            if(player.getPoints() >= 22){
                System.out.println("\nYou lose!");
                playerTokens -= playerBet;
            }else if(dealer.getPoints() >= 22){
                System.out.println("\nYou win!");
                playerTokens += playerBet;
            } else if(player.getPoints() < dealer.getPoints()) {
                System.out.println("\nYou lose!");
                playerTokens -= playerBet;
            } else if(player.getPoints() > dealer.getPoints()) {
                System.out.println("\nYou win!");
                playerTokens += playerBet;
            }else{
                System.out.println("\nPush!");
            }

            // check if player is out of tokens
            if(playerTokens <= 0){
                System.out.println("You are out of tokens. The game will now quit.");
                keepPlaying = "quit";
            }else{
                System.out.println("Enter 'quit' to stop playing or hit enter to keep playing: ");
                keepPlaying = input.nextLine();
            }

        }
    }

    // read rules
    public static void rules(){
        // show basic rules
        System.out.println("Rules:" +
                "\nBlackjack hands are scored by their point total. " +
                "\nThe hand with the highest total wins as long as it doesn't exceed 21." +
                "\nA hand exceeds 21, the player is bust." +
                "\nAn ace is worth 1 point and all face cards are worth 10 points." +
                "\nBlackjack is an ace and a 10 or face card. This is worth 21 points.\n");
    }

    // deal cards
    public static int deal(){
        //list of cards in suit
        int[] cards = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

        //random num inclusive
        //ThreadLocalRandom.current().nextInt(min, max + 1);
        int randomCard = ThreadLocalRandom.current().nextInt(0, 12 + 1);

        randomCard = cards[randomCard];

        return randomCard;
    }

    // get card name
    public static String convertCard(int card){
        //variables
        String suit, number, cardName;

        // check if card is ace
        if (card == 1)
        {
            number = "Ace";
        } else if (card == 10) {
            // get random 10 value card
            int randomCard = ThreadLocalRandom.current().nextInt(0, 3 + 1);

            if (randomCard == 0){
                number = "10";
            }else if(randomCard == 1){
                number = "Jack";
            }else if(randomCard == 2){
                number = "Queen";
            }else{
                number = "King";
            }
        } else {
            // convert to string
            number = String.valueOf(card);
        }

        number += " of ";

        // choose suit
        int randomSuit = ThreadLocalRandom.current().nextInt(0, 3 + 1);

        if (randomSuit == 0){
            suit = "Hearts";
        }else if (randomSuit == 1){
            suit = "Diamonds";
        }else if (randomSuit == 2){
            suit = "Spades";
        }else{
            suit = "Clubs";
        }

        cardName = number + suit;

        return cardName;
    }

    // ask player to hit or stand
    public static String playerChoice(){
        //variables
        String choice = "";

        //input
        Scanner input = new Scanner(System.in);

        //ask player to hit or stand
        //loop until valid option
        while(!choice.equalsIgnoreCase("hit") && !choice.equalsIgnoreCase("stand") && !choice.equalsIgnoreCase("double")) {
            System.out.println("\nEnter 'hit' to hit, 'stand' to stand, or 'double' to double down: ");
            choice = input.next();
        }

        return choice;
    }
}