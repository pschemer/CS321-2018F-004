
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 * @author Kevin
 */

public class Map{   
        private final LinkedList<Room> map;

        public Map(String worldFile) {
                map = new LinkedList<>();
                try {
                        File mapFile = new File(worldFile);	//Use the world file to create the game world
                        Scanner mapIn = new Scanner(mapFile).useDelimiter(",|\\n|\\r\\n");	//defines the key elements to brak the information

                        int numRooms, numExits;

                        String title, description, room_type;	//Data types used to build the rooms
                        String message;
                        int id, link;

                        Direction exitId;

                        Room newRoom;
                        Exit newExit;

                        numRooms = Integer.parseInt(mapIn.nextLine());	//Read the first line that indicates the number of rooms
                        numExits = 4;	//Stores the 4 directions than can be exits


                        for(int i = 0; i < numRooms; i++) {

                                mapIn.useDelimiter(",|\\n|\\r\\n"); 	//Using the delimiters, parse the data in the file.
                                id = Integer.parseInt(mapIn.next());	//Parse the Id of the next room
                                room_type = mapIn.next();	//Pare if the room s indoor or outdoor
                                title = mapIn.next();	//Read the title of the room
                                mapIn.useDelimiter("\\S|\\s");	
                                mapIn.next();
                                mapIn.useDelimiter("\\n|\\r\\n");
                                description = mapIn.next();

                                //                System.out.println("Adding Room " + id + " with Title " + title + ": " + description);


                                if(id == 1){
                                        LinkedList<String> quests = new LinkedList<>(Arrays.asList("quest1", "quest2", "quest3"));
                                        newRoom = new Room(id, room_type, title, description, new LinkedList<>(Arrays.asList(
                                                            new NPC("Slartibartfast", 1, quests))));	//if this is room 1 put the quest NPC here
                                }
                                else {
                                        newRoom = new Room(id, room_type, title, description);
                                }

                                for(int j = 0; j < numExits; j++) {

                                        mapIn.useDelimiter(",|\\n|\\r\\n");
                                        exitId = Direction.valueOf(mapIn.next());	//Parse the directions of travel from the file
                                        link = Integer.parseInt(mapIn.next());
                                        mapIn.useDelimiter("\\S|\\s");
                                        mapIn.next();
                                        mapIn.useDelimiter("\\n|\\r\\n");
                                        message = mapIn.next();

                                        //                    System.out.println("... Adding Exit " + exitId + " to " + link + ": " + message);
                                        newRoom.addExit(exitId, link, message);
                                }                

                                map.add(newRoom);	//add the room to the lnked list of rooms
                        }
                        mapIn.close();	//when done reading close the file
                } catch (IOException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

        public Room findRoom(int roomId) {
                for(Room room : this.map) {
                        if(room.getId() == roomId) {
                                return room;
                        }
                }
                return null;
        }

    public Room randomRoom() {
        Random rand = new Random();
        return map.get(rand.nextInt(map.size()));
    }
    
    /**
     * @author Group 4: King
     * Checks that room the player is contains a shop
     * @param r The room in question
     * @return true if it's a shoppable room, false otherwise
     */
    public boolean isShoppable(Room r) {
    	if (r.getId() == 1) {	// Need to improve this if more shops are added
    		return true;
    	}
    	return false;
    }
}
