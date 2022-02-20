


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.*;




public class KnightsTour7{

	
	private static JFrame frame; 
	private static JPanel panel;
	private static JLabel chessBoard;
	private static Image board;
	private static Image knight;
	private static JLabel piece;
	private static Scanner scan;
    private static int[][] boardPos = {
		   {0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0},
		   {0,0,0,0,0,0,0,0}
    };
    private static int prevY;
    private static int prevX;
    private static Image red;
    private static ArrayList<int[][]> saves = new ArrayList<int[][]>();
	private static int[] movesY;
	private static int[] movesX;
	private static int startPos;
	private static int knightY;
	private static int knightX;
	private static int[] possibleWhys;
	private static int[] possibleExs;
	private static boolean sameMoveSize=true;
    private static int best = 0;
   
   private static int startLoc;
   
   private static int[] pathY = {2, 3, 4, 5, 6, 7, 5, 6, 7, 5, 6, 7, 6,
    7, 5, 6, 7, 5, 6, 7, 5, 6, 7, 6, 7, 5, 4, 3, 4, 2, 0, 1, 2, 4, 3,
     4, 5, 3, 2, 0, 1, 2, 3, 1, 0, 2, 1, 0, 1, 0, 2, 4, 3, 4, 3, 1, 0,
      1, 0, 2, 4, 3, 1, 0}; //path from 2 (used brute force to get)
   private static int[] pathX = {5, 7, 5, 7, 5, 7, 6, 4, 6, 5, 7, 5, 3, 1,
    2, 0, 2, 3, 1, 3, 4, 6, 4, 2, 0, 1, 3, 5, 7, 6, 7, 5, 7, 6, 4, 2, 0,
     1, 3, 2, 0, 2, 0, 1, 3, 4, 6, 4, 2, 0, 1, 0, 2, 4, 6, 7, 5, 3, 1,
      0, 1, 3, 4, 6};
   
   private static int[] newPathY;
   private static int[] newPathX;


	
	public static void main(String[] args) {
      
		frame = new JFrame();
      
     
      
		panel = new JPanel();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(null);
		
		try {
			board = ImageIO.read(new File("chessBoard.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			knight = ImageIO.read(new File("Knight.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		chessBoard = new JLabel(new ImageIcon(board));
		chessBoard.setBounds(0,0,600,600);
		
		panel.add(chessBoard);
		
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle("Knight's Tour");
		frame.pack();
		frame.setSize(600,600);
		frame.setVisible(true);
		
		scan = new Scanner(System.in);
	    
		System.out.println("Where do you want the knight to start?");
		System.out.println("Enter a number from 1 to 64 :");
		startPos = scan.nextInt();
        System.out.println(startPos);
        getMoves();
        doMoves(0);
        
	}
   
   public static void refresh() {
       frame.add(panel);
	   frame.invalidate();
	   frame.revalidate();
	   frame.repaint();
   }
   
   public static int coordsToPos(int y, int x) {
	   int pos = y*8+(x+1);
	   return(pos);
   }
   
   public static void placeKnight(int pos) {
	   
	   int y;
	   int x;
    
	   if (pos<=8) {
		   y=0;
	   }
	   else if (pos<=16) {
		   y=1;
	   }
	   else if (pos<=24) {
		   y=2;
	   }
	   else if (pos<=32) {
		   y=3;
	   }
	   else if (pos<=40) {
		   y=4;
	   }
	   else if (pos<=48) {
		   y=5;
	   }
	   else if (pos<=56) {
		   y=6;
	   }
	   else {
		   y=7;
	   }
	   x=(pos-1)-(y*8);
	  
	   
       //System.out.println(pos);
       //System.out.println(y);
       //System.out.println(x);
	   piece = new JLabel(new ImageIcon(knight));
	   piece.setBounds(x*600/8,y*600/8,75,75);
	   panel.add(piece);
       panel.add(chessBoard);
       boardPos[y][x]=2;
       prevY=y;
       prevX=x;
   }
   
   
   
   public static void removeKnight() {
	   try {
		 red = ImageIO.read(new File("red.jpg"));
	   } 
	   catch (IOException e) {
		e.printStackTrace();
	   }
	   JLabel usedSquare = new JLabel(new ImageIcon(red));
	   usedSquare.setBounds(prevX*600/8,prevY*600/8,75,75);
       panel.add(usedSquare);
	   boardPos[prevY][prevX]=1;
	   panel.remove(piece);
	   
   }
   
   public static boolean end() {
	   boolean finished = true; 
	   for (int i = 0; i < boardPos.length; i++) {
		   for (int a = 0; a < boardPos[i].length; a++) {
			   if (boardPos[i][a]==0) {
				   finished = false;
			   }
		   }
	   }
	   return(finished);
   }
   
   public static void getMoves() {
	   
	   int y;
	   int x;
    
	   if (startPos<=8) {
		   y=0;
	   }
	   else if (startPos<=16) {
		   y=1;
	   }
	   else if (startPos<=24) {
		   y=2;
	   }
	   else if (startPos<=32) {
		   y=3;
	   }
	   else if (startPos<=40) {
		   y=4;
	   }
	   else if (startPos<=48) {
		   y=5;
	   }
	   else if (startPos<=56) {
		   y=6;
	   }
	   else {
		   y=7;
	   }
	   x=(startPos-1)-(y*8);
	   
	   for (int i=0; i<pathX.length; i++) {
		   if (y==pathY[i] && x==pathX[i]) {
			   startLoc = i;
		   }
	   }
	   
	   if (!(startLoc==0)) {
		   int[] secondPartX = Arrays.copyOfRange(pathX, 0,startLoc);
		   int[] secondPartY = Arrays.copyOfRange(pathY,0,startLoc);
		   int[] firstPartX = Arrays.copyOfRange(pathX, startLoc, pathX.length);
		   int[] firstPartY = Arrays.copyOfRange(pathY, startLoc, pathY.length);
		   movesX = new int[firstPartX.length + secondPartX.length];  
		   System.arraycopy(firstPartX, 0, movesX, 0, firstPartX.length);  
		   System.arraycopy(secondPartX, 0, movesX, firstPartX.length, secondPartX.length);  
		   //System.out.println(Arrays.toString(movesX));
		   movesY = new int[firstPartY.length + secondPartY.length];  
		   System.arraycopy(firstPartY, 0, movesY, 0, firstPartY.length);  
		   System.arraycopy(secondPartY, 0, movesY, firstPartY.length, secondPartY.length); 
         //System.out.println(Arrays.toString(movesY));
	   }
	   else {
		    movesX=pathX;
		    movesY=pathY;
	   }
	   
   }
   
  
   
   public static void doMoves(int index) {
	   if (index==0) {
		   placeKnight(startPos);
         System.out.println(Integer.toString(startPos));
		   try {
			   TimeUnit.SECONDS.sleep(1);
		   } 
		   catch (InterruptedException e) {
			   e.printStackTrace();
		   }
		   removeKnight();
				   
	   }
	   else if (index==pathX.length) {
         
		   return;
	   }
		   
	   int place = coordsToPos(movesY[index],movesX[index]);
	   placeKnight(place);
       refresh();
       System.out.println(Integer.toString(place));
       try {
		  TimeUnit.SECONDS.sleep(1);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		removeKnight();
		doMoves(index+1);   
		return;
	   }
   }
   
 
   




