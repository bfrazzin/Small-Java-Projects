package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *  * @author Blake Frazzini
 *  * course CS1450
 *  * section M/W
 *  * date 11/03/2021
 *  * assignment 8
 *
 * This program works with array lists, queues, a stack, and iterators. It
 * translates a secret message that has been split into 3 parts by
 * translating the parts using information from 3 different files.
 * It translates one part of the secret message as characters,
 * it translates another part of the secret message as ascii codes
 * and finally, it uses a key with 0’s and 1’s, one key for each character in
 * original message.
 */
public class FrazziniBlakeAssignment8
{
    // Holds the main part of the functional code
    public static void main(String[] args) throws IOException
    {
        // Create arraylists for message 1, 2 and key
        ArrayList<Character> message1 = new ArrayList<>();
        ArrayList<Integer> message2 = new ArrayList<>();
        ArrayList<Integer> key = new ArrayList<>();

        // Create strings for file names
        final String ARRAY_MESSAGE_1 = "arrayMessage1.txt";
        final String ARRAY_MESSAGE_2 = "arrayMessage2.txt";
        final String ARRAY_KEY = "arrayKey.txt";

        // Create files and pass in Strings holding file names
        final File MESSAGE1 = new File(ARRAY_MESSAGE_1);
        final File MESSAGE2 = new File(ARRAY_MESSAGE_2);
        final File KEY = new File(ARRAY_KEY);

        // Create files and set them to txt file name
        // (I only did this differently than the ones above to experiment with
        // files and strings)
        final File QUEUE_MESSAGE1 = new File("queueMessage1.txt");
        final File QUEUE_MESSAGE2 = new File("queueMessage2.txt");
        final File QUEUE_KEY = new File("queueKey.txt");

        // Create scanners for messages and key (arrays)
        Scanner message1Input = new Scanner(MESSAGE1);
        Scanner message2Input = new Scanner(MESSAGE2);
        Scanner keyInput = new Scanner(KEY);

        // Create queues for message 1 2 and key
        Queue<Character> queueMessage1 = new LinkedList<>();
        Queue<Integer> queueMessage2 = new LinkedList<>();
        Queue<Integer> queueKey = new LinkedList<>();

        // Create scanners for message 1 2 and key queues
        Scanner queue1Input = new Scanner(QUEUE_MESSAGE1);
        Scanner queue2Input = new Scanner(QUEUE_MESSAGE2);
        Scanner queueKeyInput = new Scanner(QUEUE_KEY);

        // Create an instance of the secret translator class for decoding
        // message 1 and 2
        SecretTranslator translator = new SecretTranslator();
        SecretTranslator translator2 = new SecretTranslator();

        // Read and place each character in message into an ArrayList of
        // characters
        String str = message1Input.nextLine();
        for (int i = 0; i < str.length(); i++)
        {
            message1.add(str.charAt(i));
        }

        // Read and place each integer in message into an ArrayList of integers
        while(message2Input.hasNextInt())
        {
            message2.add(message2Input.nextInt());
        }

        // Read and place each integers (i.e. 0  or 1) into an ArrayList of integers
        while(keyInput.hasNextInt())
        {
            key.add(keyInput.nextInt());
        }

        // Create 3 iterators - one for each ArrayList
        Iterator<Character> msg1Iterator = message1.iterator();
        Iterator<Integer> msg2Iterator = message2.iterator();
        Iterator<Integer> keyIterator = key.iterator();

        // Call secret translator’s decode method sending in 3 iterators to decode the message
        String decodedMessage1 = translator.decode(msg1Iterator, msg2Iterator,
            keyIterator);

        // print decoded message 1
        System.out.println(decodedMessage1);

        // Read and place each character in message into a Queue
        str = queue1Input.nextLine();
        for (int i = 0; i < str.length(); i++)
        {
            queueMessage1.offer(str.charAt(i));
        }

        // Read and place each integer in message into a Queue of integers
        while(queue2Input.hasNextInt())
        {
            queueMessage2.offer(queue2Input.nextInt());
        }

        // Read and place each integer (i.e. 0  or 1) into a Queue of integers
        while(queueKeyInput.hasNextInt())
        {
            queueKey.offer(queueKeyInput.nextInt());
        }

        // Create iterators for message 1 2 and key Queues
        msg1Iterator = queueMessage1.iterator();
        msg2Iterator = queueMessage2.iterator();
        keyIterator = queueKey.iterator();

        // decode message 2
        String decodedMessage2 = translator2.decode(msg1Iterator, msg2Iterator,
                keyIterator);

        // print decoded message 2
        System.out.println(decodedMessage2);

    }
}

/**
 * Handles the translation function
 */
class SecretTranslator
{
    // Used during translation of the secret message
    private final Stack stack;

    // default constructor for SecretTranslator class
    // allocates memory for stack
    public SecretTranslator()
    {
        stack = new Stack();
    }

    // handles what order for the combination of the 2 messages
    public String decode (Iterator<Character> msg1Iterator,
                          Iterator<Integer> msg2Iterator,
                          Iterator<Integer>keyIterator)
    {
        // create StringBuilder to be appended to for the result of the
        // final message
        StringBuilder messageDecode = new StringBuilder();

        // iterate through the values in the keyIterator and determine if
        // value from msg1Iterator or msg2Iterator will be obtained
        while(keyIterator.hasNext())
        {
            // represents the character reading from keyIterator
            int num = keyIterator.next();

            // value from msg1Iterator obtained
            if (num == 0)
            {
                // get next character from msg1Iterator
                if (msg1Iterator.hasNext())
                {
                    // push onto the stack
                    stack.push(msg1Iterator.next());
                }

            }

            // value from msg2Iterator obtained
            else
            {
                // get next integer from msg2Iterator,
                if(msg2Iterator.hasNext())
                {
                    // represents the character reading from keyIterator
                    // for pushing onto the stack
                    int nextInt = msg2Iterator.next();

                    // convert to a character and push onto the stack
                    stack.push((char)nextInt);
                }

            }
        }

        // append to messageDecode for as long as the size of the stack
        while(!stack.isEmpty())
        {
            // append to the messageDecode variable the value popped off
            // of the stack
            messageDecode.append(stack.pop());
        }

        // the finally decoded message is returned
        return messageDecode.toString();
    }
}


/**
 * Creates a stack using an arrayList
 * Used to handle the final step in unscrambling the secret message
 */
class Stack
{
    // Use an ArrayList to serve as the storage container for the characters
    private final ArrayList<Character> list;

    // default constructor for Stack class
    // Allocates memory for the arrayList
    public Stack()
    {
        list = new ArrayList<>();
    }

    // Returns a boolean indicating if the stack is empty
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    // Returns the number of elements currently on the stack
    public int getSize()
    {
        return list.size();
    }

    // Adds a character to the top of the stack
    public void push(Character value)
    {
        list.add(value);
    }

    // Removes and returns the character on the top of the stack.
    public Character pop()
    {
        return list.remove(list.size() - 1);
    }
}

