package BBgame;

import java.awt.*;
import java.util.Arrays;


public class Map
{
    int [][] blocks ;
    public int blockHeight;
    public int blockWidth;
    Color brickColor = new Color(109, 133, 122);
    Color backColor = new Color(189, 226, 210);

    public Map(int rows , int cols)
    {
        blocks = new int [rows][cols];

        for (int[] block : blocks) {
            Arrays.fill(block, 1);
        }

        blockHeight = 200/rows;
        blockWidth = 680/cols;
    }

    public void draw(Graphics2D G){
        for(int i = 0 ; i < blocks.length ; i++)
        {
            for (int j = 0 ; j < blocks[i].length ; j++)
            {
                if(blocks[i][j] > 0)
                {
                    int brickX = j*blockWidth+55; // +55 to start 55px from left
                    int brickY = i*blockHeight+60; // +60 to start 60px from top
                    G.setColor(brickColor);
                    G.fillRect( brickX,brickY, blockWidth, blockHeight);
                    G.setStroke(new BasicStroke(5)); // we need Graphics2D for this
                    G.setColor(backColor);
                    G.drawRect(brickX, brickY,blockWidth,blockHeight);

                }
            }
        }
    }
    public void BrickValue(int value , int i , int j )
    {
        blocks[i][j] = value;
    }

}
