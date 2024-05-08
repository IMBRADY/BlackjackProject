import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class BlackjackProject {

    public static void main(String[] args)
    {

        //Initialization of all variables

        ArrayList<String> dealerHand = new ArrayList<>();
        ArrayList<String> playerHand = new ArrayList<>();
        ArrayList<String> card = new ArrayList<>();
        Scanner scnr = new Scanner(System.in);
        Player player = new Player();
        boolean isPlay = true;
        int buyInVal;
        String temp;

        // Ask for player name and buy-in

        System.out.println("Welcome to Blackjack!");
        
        System.out.println("Enter your name: ");
        player.setplayerName(scnr.nextLine());

        System.out.print("Welcome " + player.getplayerName() + "!");
        while(isPlay)
        {
            while(true)
            {
                System.out.println();
                System.out.print("Enter buy-in amount: $"); 
                buyInVal = scnr.nextInt();

                if(buyInVal > player.getplayerMoney())
                    System.out.print("Insufficient funds. ");
                else
                {
                    System.out.println("Buy-in successful! Best of luck!");
                    break;
                }
            }
            scnr.nextLine(); // consume new line after nextInt()

            resetTable(dealerHand, playerHand, card);

            // Deal Cards
            
            playerHand.add(drawCard(card));
            dealerHand.add(drawCard(card));
            playerHand.add(drawCard(card));
            dealerHand.add(drawCard(card));

            printTable(dealerHand, playerHand, true);

            // Player turn
        
            System.out.println("Hit or Stay?");
            while(true)
            {
                temp = scnr.nextLine();
                if(temp.equalsIgnoreCase("hit") || temp.equalsIgnoreCase("h"))
                {
                    playerHand.add(drawCard(card));
                    printTable(dealerHand, playerHand, true);
                    if(countCards(playerHand) > 21)
                    {
                        delay(2);
                        System.out.println("Player busts, dealer wins!");
                        player.setplayerMoney(player.getplayerMoney()-buyInVal);
                        break;
                    }
                }
                else if(temp.equalsIgnoreCase("stay") || temp.equalsIgnoreCase("s"))
                {

                    // Dealer turn 

                    printTable(dealerHand, playerHand, false);
                    delay(2);
                    while(countCards(dealerHand) < 17)
                    {
                        dealerHand.add(drawCard(card));
                        printTable(dealerHand, playerHand, false);
                        delay(2);
                    }

                    // Decide Winner 

                    if(countCards(dealerHand) > 21)
                    {
                        System.out.println("Dealer busts, you win!");
                        player.setplayerMoney(player.getplayerMoney()+buyInVal);
                        break;
                    }
                    else if(countCards(dealerHand) > countCards(playerHand))
                    {
                        System.out.println("Dealer wins, you lose.");
                        player.setplayerMoney(player.getplayerMoney()-buyInVal);
                        break;
                    }
                    else if(countCards(playerHand) > countCards(dealerHand))
                    {
                        System.out.println("Dealer loses, you win!");
                        player.setplayerMoney(player.getplayerMoney()+buyInVal);
                        break;
                    }
                    else if(countCards(playerHand) == countCards(dealerHand))
                    {
                        System.out.println("Push, player ties dealer");
                        break;
                    }
                }
            }
            
            delay(2);

            System.out.println("Would you like to play again? (Yes/No)\nCurrent Balance: "+player.getplayerMoney());
            while(true)
            {
                temp = scnr.nextLine();
                if(temp.equalsIgnoreCase("yes") || temp.equalsIgnoreCase("y"))
                {
                    if(player.getplayerMoney() > 0)
                    {
                        System.out.println("Playing again!");
                        resetTable(dealerHand, playerHand, card);
                        delay(1);
                        break;
                    }
                    else
                    {
                        System.out.println("Looks like you have no money :(");
                        isPlay = false;
                        break;
                    }
                }
                else if(temp.equalsIgnoreCase("no") || temp.equalsIgnoreCase("n"))
                {
                    System.out.println("Thank you for playing. Come back again soon!");
                    isPlay = false;
                    break;
                }
                else
                    System.out.println("Unrecognized answer, please answer yes or no");
            }  
        } 
    }

    // Utility Methods

    public static String convertToCard(String l)
    {
        if(l.equals("1"))
            return "A";
        if(l.equals("11"))
            return "J";
        if(l.equals("12"))
            return "Q";
        if(l.equals("13"))
            return "K";
        return l;
    }
    public static int convertToVal(String l)
    {
        if(l.equals("A"))
            return 11;
        if(l.equals("J") || l.equals("Q") || l.equals("K"))
            return 10;
        else
            return Integer.parseInt(l);
    }
    public static void delay(int s)
    {
        try{
            Thread.sleep(s*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Game Sequence Methods

    public static void resetTable(ArrayList<String> d, ArrayList<String> p, ArrayList<String> c)
    {
        d.clear();
        p.clear();
        c.clear();
        for(int i = 4; i < 56; i++)
            c.add(convertToCard(String.valueOf(i/4)));
    }
    public static String drawCard(ArrayList<String> c)
    {
        Random rand = new Random();
        String temp = c.get(rand.nextInt(c.size()));
        c.remove(temp);
        return temp;
    }
    public static int countCards(ArrayList<String> c)
    {
        int total = 0;
        int numAces = 0;
        for(int i = 0; i < c.size(); i++)
        {
            total += convertToVal(c.get(i));
            if(c.get(i).equals("A"))
                numAces++;
        }
        while(total > 21 && numAces > 0)
        {
            total -= 10;
            numAces--;
        }
        return total;
    }
    public static void printTable(ArrayList<String> d, ArrayList<String> p, boolean hidden)
    {
        if(!hidden)
        {
            System.out.print("Dealer Hand: ");
            for(int i = 0; i < d.size(); i++)
            {
                System.out.print(d.get(i)+" ");
            }
            System.out.print("\nYour Hand: ");
            for(int i = 0; i < p.size(); i++)
            {
                System.out.print(p.get(i)+" ");
            }
            System.out.println();
        }
        else
        {
            System.out.print("Dealer Hand: ");
            for(int i = 0; i < d.size()-1; i++)
            {
                System.out.print(d.get(i)+" ");
            }
            System.out.print("?\nYour Hand: ");
            for(int i = 0; i < p.size(); i++)
            {
                System.out.print(p.get(i)+" ");
            }
            System.out.println();
        }
    }
}
          