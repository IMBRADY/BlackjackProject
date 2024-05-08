public class Player {
    private String playerName;
    private int playerMoney;
    //private List<Card> hand;
    
    //Constructor to initialize the players name and  money 
    public Player() {
        this.playerName = "";
        this.playerMoney = 500;
    }

    public String getplayerName() {
        return playerName;
    }

    public int getplayerMoney() {
        return playerMoney;
    }   

    public void setplayerName(String newName) {
        this.playerName = newName;
    }

    public void setplayerMoney(int newMoney) {
        this.playerMoney = newMoney;
    }

}

// accessor/mutator methods for name, money, (maybe) hand
