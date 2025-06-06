package com.projetlong.gh02.map;

import com.projetlong.gh02.handlers.RenderHandler;
import com.projetlong.gh02.sprite.Sprite;
import com.projetlong.gh02.sprite.SpriteSheet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tiles {


    /** The array of tiles from the spritesheet. */
    private ArrayList<Tile> tileArray = new ArrayList<>();

    /** Creates an instance of tiles from a file
     * following the convention "tileName-xPos-yPos"
     * and the spriteSheet from where the tiles come
     * from.
     * @param tilesFile The text file containing the info of the tiles
     * @param spriteSheet0 The spritesheet where the tiles from the layer 0 (background) come from
     * @param spriteSheet1 The spritesheet where the tiles from the layer 1 (obstacle) come from
     * @param spriteSheet2 The spritesheet where the tiles from the layer 2 (decoration) come from
     */
    public Tiles(File tilesFile, SpriteSheet spriteSheet0, SpriteSheet spriteSheet1, SpriteSheet spriteSheet2) {

        try (Scanner scanner = new Scanner(tilesFile)) {
            while(scanner.hasNextLine()) {
                String[] tileString = scanner.nextLine().split("-");
                String tileName = tileString[0];
                int tileXPos = Integer.parseInt(tileString[1]);
                int tileYPos = Integer.parseInt(tileString[2]);
                int layer = Integer.parseInt(tileString[3]);
                
                /* A déplacer dans le map editor. */
                SpriteSheet selectedSheet;
                selectedSheet = switch (layer) {
                    case 1 -> spriteSheet1;
                    case 2 -> spriteSheet2;
                    default -> spriteSheet0;
                };
                Tile tile = new Tile(tileName, selectedSheet.getSprite(tileXPos, tileYPos));
                tileArray.add(tile);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file at location " + tilesFile.getAbsolutePath());
        } catch (NumberFormatException e) {
            System.out.println("Tiles.txt does not follow the convention 'tileName-xPos-yPos-layer");
        }
    }


    /** Loads the tile's sprite into the graphics buffer.
     * @param tileID The tile's unique ID
     * @param renderHandler The render handler used in the game frame
     * @param xPos The x-position where to render the tile
     * @param yPos The y-position where to render the tile
     * @param scale The desired scaling ratio
    */
    public void load(int tileID, RenderHandler renderHandler, int xPos, int yPos, int scale) {
        if (tileArray.size() <= tileID || tileID < 0) {
            System.out.println("The tile with ID : " + tileID + " is not within the tile array.");
            return;
        }
        renderHandler.loadSprite(tileArray.get(tileID).getSprite(), xPos, yPos, scale);
    }


    /** Returns the number of tiles in the
     * tile sheet.
     * @return The number of tiles
     */
    public int getNumberOfTiles() {
        return this.tileArray.size();
    }

    class Tile {

        /** The name of the tile. */
        private final String name;
        /** The sprite of the tile. */
        private final Sprite sprite;

        /** Creates a tile from its name and its sprite.
         * @param tileName The name of the tile
         * @param sprite The sprite of the tile
         */
        public Tile(String tileName, Sprite sprite) {
            this.name = tileName;
            this.sprite = sprite;
        }


        /** Returns the name of the Tile.
         * @return The name of the tile
         */
        public String getName() {
            return this.name;
        }



        /** Returns the sprite of the Tile.
         * @return The sprite of the tile
         */
        public Sprite getSprite() {
            return this.sprite;
        }
    }

}
