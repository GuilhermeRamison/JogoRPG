package jogorpg.world_of_zuul;

import items.Item;
import items.Perma;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;
import personas.Folk;
import personas.Hero;
import personas.Merchant;
import personas.Villain;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Game 
{
    private Parser parser;
    public static Room currentRoom;
    private Room back;
    private HashMap<String, Room> rooms;
    public static Hero player;
    private String playerName; 
    private HashMap<String, Villain> mobs;
    private HashMap<String, Folk> folks;
    private HashMap<String, Item> loots;
    private boolean finished;
    private Scanner reader;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        rooms = new HashMap<>();
        mobs = new HashMap<>();
        loots = new HashMap<>();
        folks = new HashMap<>();
        parser = new Parser();
        finished = false;
        createRooms();
        createPersonas();
        createItems();
        makeWorld();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        File arq = new File("Rooms.txt");
        int i = 2,j;
       
        try {
            FileReader fileReader = new FileReader(arq);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while(!"EXITS:".equals(line = bufferedReader.readLine())) {
                String[] tokens = line.split(Pattern.quote(";"));
                Room sala = new Room(tokens[0]);
                rooms.put(tokens[1], sala);
            }
            while((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(Pattern.quote(";"));
                for(j = 0;j < Integer.parseInt(tokens[1]);j++) {
                    rooms.get(tokens[0]).setExit(tokens[i], rooms.get(tokens[i+1]));
                    i += 2;
                }
                i = 2;
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e){
            System.out.println("Erro na leitura do arquivo 'Rooms.txt'");
        }
        currentRoom = rooms.get("Entrada");  // start game sala1
    }
    
    private void createPersonas() {
        File arq = new File("Personas.txt");
        Villain[] villains = null;
        try {
            FileReader fileReader = new FileReader(arq);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while(!"FOLKS:".equals(line = bufferedReader.readLine())) {
                String[] tokens = line.split(Pattern.quote(";"));
                Villain v = new Villain(tokens[0], 
                                        Integer.parseInt(tokens[1]), 
                                        Integer.parseInt(tokens[2]), 
                                        Integer.parseInt(tokens[3]), 
                                        Integer.parseInt(tokens[4]));
                mobs.put(tokens[0], v);
            }
            while(!"MERCHANTS:".equals(line = bufferedReader.readLine())) {
                String[] tokens = line.split(Pattern.quote(";"));
                Folk f = new Folk(tokens[0], 
                                        Integer.parseInt(tokens[1]),
                                        tokens[2],
                                        tokens[3]);
                folks.put(tokens[0], f);
            }
            while((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(Pattern.quote(";"));
                Merchant m = new Merchant(tokens[0], 
                                        Integer.parseInt(tokens[1]),
                                        Integer.parseInt(tokens[2]),
                                        tokens[3],
                                        tokens[4]);
                folks.put(tokens[0], m);;
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e){
            System.out.println("Erro na leitura do arquivo 'Personas.txt'");
        }
        
        /*Villain Teste1 = new Villain("Teste1", 100, 10, 10, 2);
        Villain Teste2 = new Villain("Teste2", 100, 10, 10, 2);
        mobs.put("Teste1", Teste1);
        mobs.put("Teste2", Teste2);*/
    }
    private void createItems() {
        File arq = new File("Items.txt");
        
        try {
            FileReader fileReader = new FileReader(arq);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while(!"PERMAS:".equals(line = bufferedReader.readLine())) {
                String[] tokens = line.split(Pattern.quote(";"));
                Item i = new Item(tokens[0], 
                                    tokens[1], 
                                    tokens[2], 
                                    Integer.parseInt(tokens[3]), 
                                    Integer.parseInt(tokens[4]),
                                    Integer.parseInt(tokens[5]),
                                    Integer.parseInt(tokens[6]));
                loots.put(tokens[0], i);
            }
            while((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(Pattern.quote(";"));
                Item i = new Perma(tokens[0], 
                                    tokens[1], 
                                    tokens[2], 
                                    Integer.parseInt(tokens[3]), 
                                    Integer.parseInt(tokens[4]),
                                    Integer.parseInt(tokens[5]),
                                    Integer.parseInt(tokens[6]),
                                    Integer.parseInt(tokens[7]),
                                    Integer.parseInt(tokens[8]),
                                    Integer.parseInt(tokens[9]),
                                    Integer.parseInt(tokens[10]),
                                    Integer.parseInt(tokens[11]));
                loots.put(tokens[0], i);
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e){
            System.out.println("Erro na leitura do arquivo 'Items.txt'");
        }
    }
    private void makeWorld() {
        File arq = new File("World.txt");
        HashMap inventoryM = new HashMap<String, Item>();
        HashMap inventoryA = new HashMap<String, Item>();
       
        try {
            FileReader fileReader = new FileReader(arq);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while(!(line = bufferedReader.readLine()).equals("FOLKS:")) {
                String[] tokens = line.split(Pattern.quote(";"));
                rooms.get(tokens[0]).setVillainInTheRoom(mobs.get(tokens[1]));
            }
            while(!(line = bufferedReader.readLine()).equals("MERCHANTS:")) {
                String[] tokens = line.split(Pattern.quote(";"));
                rooms.get(tokens[0]).setFolkInTheRoom(folks.get(tokens[1]));
            }
            while(!(line = bufferedReader.readLine()).equals("ITEMS:")) {
                String[] tokens = line.split(Pattern.quote(";"));
                rooms.get(tokens[0]).setFolkInTheRoom(folks.get(tokens[1]));
            }
            while(!(line = bufferedReader.readLine()).equals("invARMOR:")) {
                String[] tokens = line.split(Pattern.quote(";"));
                rooms.get(tokens[0]).setItemInTheRoom(loots.get(tokens[1]));
            }
            while(!(line = bufferedReader.readLine()).equals("invMERC:")) {
                String[] tokens = line.split(Pattern.quote(";"));
                inventoryA.put(tokens[0], loots.get(tokens[1]));
            }
            while((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(Pattern.quote(";"));
                inventoryM.put(tokens[0], loots.get(tokens[1]));
            }
            rooms.get("Armoaria").getFolkInTheRoom("Fergus", 1).setInventory(inventoryA);
            rooms.get("Mercado").getFolkInTheRoom("Gaunter", 1).setInventory(inventoryM);
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e){
            System.out.println("Erro na leitura do arquivo 'World.txt'");
        }
    }
    public String getPlayerName() {
        return playerName;
    }
    public static Hero getPlayer() {
        return player;
    }
    public Room getCurrentRoom() {
        return currentRoom;
    }
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() {   
        reader = new Scanner(System.in);
        System.out.println("Digite o nome do seu personagem: \n");
        playerName = reader.nextLine();
        player = new Hero(playerName, 200, 75, 1000, 100);
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
              
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Obrigado por jogar! Até mais.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("Você foi contratado pelo Barão Herold para acabar com os Kobolds da região.");
        System.out.println("Fale com o Barão ao norte do vilarejo para mais informações.");
        System.out.println("Digite '" + CommandWord.HELP + "' se você precisar de ajuda.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("Não sei o que você quer dizer...");
            return false;
        }
        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            wantToQuit = goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } 
        else if(commandWord == CommandWord.ATTACK) {
            System.out.println("Você não pode atacar agora!");
        }
        else if(commandWord == CommandWord.HEAL) {
            heal(command);
        }
        else if(commandWord == CommandWord.TALK) {
            talk(command);
        }
        else if(commandWord == CommandWord.PICK) {
            pick(command);
        }
        else if(commandWord == CommandWord.DROP) {
            drop(command);
        }
        else if(commandWord == CommandWord.EQUIP) {
            equip(command);
        }
        else if(commandWord == CommandWord.UNEQUIP) {
            unequip(command);
        }
        else if(commandWord == CommandWord.SELL) {
            sell(command);
        }
        else if(commandWord == CommandWord.BUY) {
            buy(command);
        }
        else if(commandWord == CommandWord.REPAIR) {
            repair(command);
        }
        else if(commandWord == CommandWord.INVENTORY) {
            showInventory(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Você chegou em um vilarejo que frequentemente é atacado por Kobolds");
        System.out.println("Sua missão principal é descobrir o que atrai os Kobolds");
        System.out.println();
        System.out.println("Seus comandos são:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private boolean goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Ir aonde?");
            return false;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Esse caminho não existe!");
        }
        else {
            back = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            if(currentRoom.haveBattle()) { // Put the enemys in the battle
                Battle fight = currentRoom.putRoomInBattle(back);
                finished = fight.battleSimulator();
                currentRoom.printItemsInTheRoom();
                return finished;
            }
        }
        return false;
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    private void heal(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Curar com o que?");
            return;
        }
        String itemName = command.getSecondWord();
        Item healer = player.getItemInTheInventory(itemName);
        if(healer == null) {
            System.out.println("Item não encontrado!");
            return;
        } 
        player.incHp(healer);
    }
    private void talk(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Falar com quem?");
            return;
        }
        String farmerName = command.getSecondWord();
        Folk person = currentRoom.getFolkInTheRoom(farmerName);
        if (person == null) {
            System.out.println("Pessoa não encontrada!");
        }
        else {
            person.chat();
        }
    }
    private void pick(Command command) {
        if(currentRoom.haveBattle()) {
            System.out.println("Você não pode pegar itens agora!");
            return;
        }
        if(!command.hasSecondWord()) {
            System.out.println("Pegar o que?");
            return;
        }
        String itemName = command.getSecondWord();
        Item loot = currentRoom.getItemInTheRoom(itemName);
        if(loot == null) {
            System.out.println("Item não encontrado!");
        } 
        else {
            if(player.pickItem(loot))
                currentRoom.removeItem(loot);
        }
    }
    private void drop(Command command) {
        if(currentRoom.haveBattle()) {
            System.out.println("Você não pode largar itens agora!");
            return;
        }
        if(!command.hasSecondWord()) {
            System.out.println("Largar o que?");
            return;
        }
        String itemName = command.getSecondWord();
        Item loot = player.getItemInTheInventory(itemName);
        if(loot == null) {
            System.out.println("Item não encontrado!");
        }
        else {
            player.dropItem(loot);
            currentRoom.setItemInTheRoom(loot);
        }
    }
    private void equip(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Equipar o que?");
            return;
        }
        String itemName = command.getSecondWord();
        Item item = player.getItemInTheInventory(itemName);
        if(item == null) {
            System.out.println("Você não possui esse item!");
        }
        else if(item.getType().equals("heal")) {
            System.out.println("Você não pode equipar um healer!");
        }else {
            player.equipItem((Perma) item);
        }
    }
    private void unequip(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Desequipar o que?");
            return;
        }
        String itemName = command.getSecondWord();
        Perma item = (Perma) player.getEquippedItems().get(itemName);
        if(item == null) {
            System.out.println("Você não possui esse item!");
        }else {
            player.unequipItem(item);
        }
    }
    private void sell(Command command) {
        if(currentRoom.equals(rooms.get("Armoaria")) || currentRoom.equals(rooms.get("Mercado"))) {
            if(!command.hasSecondWord()) {
                System.out.println("Vender o que?");
                return;
            }
            String itemName = command.getSecondWord();
            Item item = player.getItemInTheInventory(itemName);
            if(item == null) {
                System.out.println("Item não encontrado!");
            }
            else {
                if(currentRoom.equals(rooms.get("Armoaria"))) {
                    currentRoom.getFolkInTheRoom("Fergus", 1).buyItem(item);
                } else {
                    currentRoom.getFolkInTheRoom("Gaunter", 1).buyItem(item);
                }
            }
        } else {
            System.out.println("Você precisa estar na sala de um mercador!");
        }
    }
    private void buy(Command command) {
        if(currentRoom.equals(rooms.get("Armoaria")) || currentRoom.equals(rooms.get("Mercado"))) {
            if(!command.hasSecondWord()) {
                System.out.println("Comprar o que?");
                return;
            }
            Item item = null;
            String itemName = command.getSecondWord();
            if(currentRoom.equals(rooms.get("Armoaria"))) {
                item = currentRoom.getFolkInTheRoom("Fergus", 1).getInventory().get(itemName);
            } else {
                item = currentRoom.getFolkInTheRoom("Gaunter", 1).getInventory().get(itemName);
            }
            if(item == null) {
                System.out.println("Item não encontrado!");
            }
            else {
                if(currentRoom.equals(rooms.get("Armoaria"))) {
                    currentRoom.getFolkInTheRoom("Fergus", 1).sellItem(item);
                } else {
                    currentRoom.getFolkInTheRoom("Gaunter", 1).sellItem(item);
                }
            }
        } else {
            System.out.println("Você precisa estar no mercado de um mercador!");
        }
    }
    private void repair(Command command) {
        if(currentRoom.equals(rooms.get("Armoaria"))) {
            if(!command.hasSecondWord()) {
                System.out.println("Reparar o que?");
                return;
            }
            String itemName = command.getSecondWord();
            Item item = player.getInventory().get(itemName);
            if(item == null) {
                System.out.println("Item não encontrado!");
            }
            else {
                if(currentRoom.equals(rooms.get("Armoaria"))) {
                    currentRoom.getFolkInTheRoom("Fergus", 1).repairItem((Perma) item);
                } else {
                    System.out.println("Você precisa estar perto de um armeiro!");
                }
            }
        } else {
            System.out.println("Você precisa estar na armoaria!");
        }
    }
    private void showInventory(Command command) {
        player.printStatus();
        System.out.println(player.printIventory());
    }
}
