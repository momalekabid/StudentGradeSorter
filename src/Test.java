import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.*;

public class Test
{

  private static FileInputStream inFile;
  private static InputStreamReader inReader;
  private static BufferedReader reader;
  private static int loBound = 0, hiBound = 1; // for merge sort

  private static List<Student> classroom = new ArrayList<Student>(); // ArrayList
                                                                     // to store
                                                                     // the
                                                                     // classroom.

  public static void main(String args[]) throws IOException
  {
    int whichSort;
    Scanner scan = new Scanner(System.in);
    System.out.println("Would you like to sort your scores with quick sort (respond with 0) or merge sort?(respond with any other number)");
    if (scan.nextInt() == 0)
    {
      whichSort = 0;
    }
    else
    {
      whichSort = 1;
    }
    initFile();
    getData();
    System.out.print(classroom); // output of the complete class.
    System.out.println("After sorting....\n \n");
    if (whichSort == 0)
    {
      quickSort();
    }
    else
    {
     mergeSort();
    }
    System.out.print(classroom); // output after sorting.
    inFile.close();

  }

  // preparing the file for input

  public static void initFile() throws IOException

  {
    inFile = new FileInputStream("/answers.txt");
    inReader = new InputStreamReader(inFile);
    reader = new BufferedReader(inReader);

  }

  // Separate the id from the answers and store the answers in an array.

  public static void getData() throws IOException
  {
    String line = reader.readLine(); // Seed

    String[] answerkey = new String[10]; // Store the answer key from the first
                                         // line of the txt file.

    for (int i = 0; i < answerkey.length; i++)
    { // take that line and place each answer in an array.

      answerkey[i] = line.substring(i, i + 1);
    }

    line = reader.readLine(); // read the following line of the txt file.

    while (line != null) // Read and create a student for each line.
    {
      String[] answers = new String[10];
      StringTokenizer strTkn = new StringTokenizer(line);
      String id = strTkn.nextToken();
      String answerline = strTkn.nextToken();

      for (int i = 0; i < answers.length; i++)
      {

        answers[i] = answerline.substring(i, i + 1);

      }

      Student stu = new Student(id, answers);

      stu.grade(answerkey, answers);

      classroom.add(stu);

      line = reader.readLine(); // updating what is being read

    }

  }

  // In this method you should sort the classroom in ascending order depending
  // on the score obtained on the quiz.

  // merge sort runner method
  public static void mergeSort()
  {
    loBound = 0;
    hiBound = 1;
    if (classroom.size() > 1)
    {
      int partitionSize = 1;
      int arraySize = classroom.size();

      if (classroom.size() % 2 != 0) // if the size is odd make it even so loop
                                     // doesn't terminate early without sorting
                                     // all values
        arraySize++;

      while (arraySize / 2 >= partitionSize)
      {
        while (true)
        {
          ArrayList<Student> left = new ArrayList<Student>();
          ArrayList<Student> right = new ArrayList<Student>();
          // Variables which will be sent to 'update' method as parameters.
          // Tells method which indexes to update (in-between low and high)
          int loInd = loBound, hiInd;
          for (int i = loBound; i < hiBound; i++)
          {
            left.add(classroom.get(i));
          }
          calcBounds(partitionSize);
          for (int j = loBound; j < hiBound; j++)
          {
            right.add(classroom.get(j));
          }
          hiInd = hiBound;
          if (right.size() != partitionSize)
          {
            update(left, right, loInd, hiInd);
            break;
          }
          update(left, right, loInd, hiInd);
          calcBounds(partitionSize);
        }
        partitionSize *= 2;
        loBound = 0;
        hiBound = partitionSize;
      }
    }
  }

  // calculates the indexes from which I will extract from the original array
  // (mergesort)
  public static void calcBounds(int partitionSize)
  {
    if (classroom.size() - hiBound > partitionSize)
    {
      loBound = hiBound;
      hiBound = loBound + partitionSize;
    }
    else
    {
      loBound = hiBound;
      hiBound = classroom.size();
    }
  }

  // updates original ArrayList after sorting 'left' and 'right', FOR MERGESORT
  public static void update(ArrayList<Student> left, ArrayList<Student> right,
    int loInd, int hiInd)
  {
    // sort
    ArrayList<Student> result = new ArrayList<Student>();
    int lIndex = 0, rIndex = 0, resIndex = 0;
    while (lIndex < left.size() || rIndex < right.size())
    {
      if (lIndex < left.size() && rIndex < right.size())
      {
        if (left.get(lIndex).score <= right.get(rIndex).score)
        {
          result.add(resIndex, left.get(lIndex));
          resIndex++;
          lIndex++;
        }
        else
        {
          result.add(resIndex, right.get(rIndex));
          resIndex++;
          rIndex++;
        }
      }
      else if (lIndex < left.size())
      {
        result.add(resIndex, left.get(lIndex));
        resIndex++;
        lIndex++;
      }
      else if (rIndex < right.size())
      {
        result.add(resIndex, right.get(rIndex));
        resIndex++;
        rIndex++;
      }
    }
    // update original ArrayList using result ArrayList
    int count = 0;
    for (int i = loInd; i < hiInd; i++)
    {
      classroom.set(i, result.get(count));
      count++;
    }
  }

  public static void quickSort() //uses stack method to copy buffer for iterative quick sort
  {
    Stack stack = new Stack();
    stack.push(0);
    stack.push(classroom.size());

    while (!stack.isEmpty())
    {
      int end = (int) stack.pop();
      int start = (int) stack.pop(); //manipulating indexes using a stack (last in- first out!)
      if (!(end - start < 2))
      {
        int p = start + ((end - start) / 2);
        p = partition(p, start, end);

        stack.push(p + 1);
        stack.push(end);

        stack.push(start);
        stack.push(p);
      }

    }
  }

  /*
   * method to partition the list, and comparing
   * scores to rearrange them through swap as per QUICKSORT algorithm.
   */
  private static int partition(int position, int start, int end)
  {
    int l = start;
    int h = end - 1;
    int piv = classroom.get(position).score;
    Collections.swap(classroom, position, end-1);
    while (l < h)
    {
      if (classroom.get(l).score < piv)
      {
        l++;
      }
      else if (classroom.get(h).score >= piv)
      {
        h--;
      }
      else
      {
        Collections.swap(classroom, l, h);
      }
    }
    int partitionIndex = h;
    if (classroom.get(h).score < piv)
    {
      partitionIndex++;
    }
    Collections.swap(classroom, end-1, partitionIndex);
    return partitionIndex;
  }

}
