package team3MorseCode;

import java.util.*;
import java.io.*;

public class team3MorseCode {
	static final int COUNT = 10;
	static Scanner console = new Scanner(System.in);
	public static void main(String[] args) {
		//looks for file in the eclipse project src folder
			File myFile = new File("src/Morse_Code.txt");
			
			TreeNode head = new TreeNode(' ');
			//puts each line inside of the text file into the project's methods
			//and prints their outputs in a user-friendly way
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(myFile));
				String lineStr = reader.readLine();
				while (lineStr != null) {
					treeBuilder(lineStr, head);
					lineStr = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			print2D(head);
			
			String option = "f";
			while (option.charAt(0) != 'q') {
				System.out.println("MENU");
				System.out.println("e - Encode message");
				System.out.println("d - Decode message");
		        System.out.println("q - Quit");
		        System.out.println("");
		        System.out.println("Choose an option: ");
				option = console.nextLine();
				
				//e
                if (option.charAt(0) == 'e') {
                    System.out.println("Enter message to encode:");
                    String toEncode = console.nextLine();
                    StringBuilder encodedStr = new StringBuilder();
                    for (int i = 0; i < toEncode.length(); i++) {
                        if(toEncode.charAt(i) == ' ') {encodedStr.append(' ');}
                        else{
                            //append morse code for given character, function returns morse code with 1 appended
                            encodedStr.append(encode(toEncode.charAt(i), "", head).replace("1", ""));
                        }
                    }
                    System.out.println("Message Encoded into Morse Code: " + encodedStr.toString());
                }
				//d - Decode option
				if (option.charAt(0) == 'd') {
					System.out.println("Enter message to decode: ");
					//Full string of morse code - separated by spaces (append space in case user doesn't).
					String fullStrtoDecode = console.nextLine()+' ';
					StringBuilder strToDecode = new StringBuilder();
					String answer = "";
					//Iterate over morse code
					for (int i = 0; i < fullStrtoDecode.length(); i++) {
						if(strToDecode.length() == 0) { strToDecode = new StringBuilder(); }
						if (fullStrtoDecode.charAt(i) != ' ') {
							strToDecode.append(fullStrtoDecode.charAt(i));
						}
						else {
							//Append decoded morse code to full answer and reset strToDecode for next loop
							answer += decode(strToDecode.toString()+' ', 0, head);
							strToDecode.delete(0, strToDecode.length()-1);
							strToDecode.setLength(0);
						}
					}
					//Print answer
					System.out.println("The Morse Code Message was Decoded: " + answer);
				}
				
				System.out.println();
			}//end while loop
			
			System.out.println("Have a nice day, PROGRAM TERMINATED");			
	}//end main

	 // Converts lower-case plain-text (with no spaces) to Morse Code form.
    public static String encode(char letter, String encodedStr, TreeNode root) {
        //If desired letter is found in node, append 1 for identification through recursion
        if(root.val == letter) {
            return encodedStr+" 1";
        }else {
            
            //Propagate through left and right subtrees
            String leftStr = null, rightStr = null;
            if(root.left != null) {//Out of bounds check
                leftStr = (root.left.val != ' ') ? encode(letter, encodedStr+".", root.left) : null;
                if(leftStr.contains("1")) {
                    return leftStr;
                }
            }
            
            if(root.right != null) {
                rightStr = (root.right.val != ' ') ? encode(letter, encodedStr+"-", root.right) : null;
                if(rightStr.contains("1")) {
                    return rightStr;
                }
            }
            
            return "";
        }
    }
	//Decode function taking position of char (.) and (-)
	public static char decode(String toDecode, int pos, TreeNode root) {
		if(toDecode.charAt(pos) == '.') {
			pos+=1;
			return decode(toDecode, pos, root.left);
		}else if(toDecode.charAt(pos) == '-') {
			pos+=1;
			return decode(toDecode, pos, root.right);
		}
		return root.val;
	}

	
	//Wrapper method
	public static void treeBuilder(String line, TreeNode head) {
		treeBuilder(line, 0, head);
	}//end treeBuilder
	 
	public static TreeNode treeBuilder(String line, int pos, TreeNode root) {	
		pos++;
		boolean finished = false;
		if (pos == line.length()) {
			finished = true;
			if (root == null) {
				TreeNode newNode = new TreeNode(line.charAt(0));
				root = newNode;
			}
			else {
				root.val = line.charAt(0);
			}
			
		}
		if (finished == false) {
			if (line.charAt(pos) == '.') {
				if (root == null) {
					TreeNode newNode = new TreeNode('0');
					root = newNode;
					root.left = treeBuilder(line, pos, root.left);
				}
				else {
					root.left = treeBuilder(line, pos, root.left);
				}
			}//end if
			
			if (line.charAt(pos) == '-') {
				if (root == null) {
					TreeNode newNode = new TreeNode('0');
					root = newNode;
					root.right = treeBuilder(line, pos, root.right);
				}
				else {
					root.right = treeBuilder(line, pos, root.right);
				}
			}//end if
		}
		return root;
	}//end treeBuilder
	
	
	static void print2DUtil(TreeNode root, int space)
	{
	    // Base case
	    if (root == null)
	        return;
	 
	    // Increase distance between levels
	    space += COUNT;
	 
	    // Process right child first
	    print2DUtil(root.right, space);
	 
	    // Print current node after space
	    // count
	    System.out.print("\n");
	    for (int i = COUNT; i < space; i++)
	        System.out.print(" ");
	    System.out.print(root.val + "\n");
	 
	    // Process left child
	    print2DUtil(root.left, space);
	}
	 
	// Wrapper over print2DUtil()
	static void print2D(TreeNode root)
	{
	    // Pass initial space count as 0
	    print2DUtil(root, 0);
	}
}//end class