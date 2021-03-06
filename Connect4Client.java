import mayflower.*;
import mayflower.net.*;
import java.util.Scanner;
import java.awt.Color;
public class Connect4Client extends Client
{
    private Connect4Stage stage;
    private Connect4 game;
    private Player piece ;
    String ip;
    buttonConnect but;
    public Connect4Client(buttonConnect a) 
    {
        but = a;
        if(a.getIP()!=null){
            ip = a.getIP();
        }
        else{
            ip = null;
        }
        //String ip = "localhost";
        //if(a.getPress()!=true){
            //ip = "10.11.22.140";
        //}
        //String ip = 10.11.22.140
        //System.out.print("Port > ");
        //int port = in.nextInt();
        int port = 1234;
        if(ip!=null){
            System.out.println();
            System.out.println("Connecting...");
            try{
                connect(ip, port);
            }
            catch(Exception e){
                System.out.println("Couldn't Connect. Try Again.");
                a.setEnter(false);
            }
        }
    }
    /*
     *  Messages:
     *      youare [piece]
     *      addpiece [row] [col]
     *      winner disconnect
     *      error [message...]
     */
    public void process(String message)
    {
        //output the message recieved from the server. This is useful for debugging
        System.out.println("Message from server: " + message);
        
        //split message into an array of Strings separated by space characters
        //  "addpiece 1 2" would become the array ["addpiece", "1", "2"]
        String[] parts = message.split(" ");
        
        //use the first value in the array as the "command"
        //use if statements to determine which command to process
        if("youare".equals(parts[0]))
        {
            //TODO:
            //parts[1] contains "X" or "O", this is your piece
            //1. Create a TicTacToePiece variable to store your piece
            //TicTacToePiece p = (parts[1].equals("X")) ? TicTacToePiece.X: TicTacToePiece.O;
            //2. Create a new TicTacToe game and store it in the game instance variable
           
            //3. Create a new TicTacToeStage and pass it this client, the game, and your piece
            
              //4. create a new Mayflower object with the stage you just created. This will open the GUI and start the game
            if(parts[1].equals("X"))
            {
              piece = Player.A;
             
            }
            if(parts[1].equals("O"))
            {
              piece = Player.B;
            }
            game = new Connect4();
            stage = new Connect4Stage(this, game, piece);
            new Mayflower("Rock Scissors Paper",800, 600, stage);
        }
        else if("addpiece".equals(parts[0]))
        {
            //TODO:
            //parts[1] contains the row
            //parts[2] contains the column
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);
            //1. add a piece to the game at the specified (row, col)
            //2. tell the game it is the next player's turn
            //3. tell the stage to update the piece at (row, col) to the correct type (X or O) depending on which piece was just added to (row, col)
            int wha = game.addPiece(row, col);
            game.nextPlayer();
            //this
            stage.updatePiece(game.getPiece(wha,col), wha, col);
            
        }
        else if("error".equals(parts[0]))
        {
            //Output the error message sent from the server
            System.out.println(message);
        }
        else if("winner".equals(parts[0]))
        {
            //Congratulations, you win becuase you opponent quit!
            System.out.println("Opponent disconnected. You win!");
        }
        
    }
    
    public void onDisconnect(String message)
    {
        System.out.println("Disconnected from server.");
    }
    
    public void onConnect()
    {
        but.setCon(true);
        System.out.println("Connected!");
    }
    
    
}