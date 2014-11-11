import java.util.Random;
/**
 * A program to carry on conversations with a human user.
 * This is the initial version that:
 * <ul><li>
 * Uses indexOf to find strings
 * </li><li>
 * Handles responding to simple words and phrases
 * </li></ul>
 * This version uses a nested if to handle default responses.
 * @author Laurie White
 * @version April 2012
 */
public class Magpie
{
  /**
   * Get a default greeting
   * @return a greeting
   */
  public String getGreeting()
  {
    return "Hello, let's talk.";
  }
  /**
   * Gives a response to a user statement
   *
   * @param statement
   * the user statement
   * @return a response based on the rules given
   */
  public String getResponse(String statement)
  {
    statement = statement.toLowerCase();
    String response = "";
    if (findKeyword(statement,"no") >= 0)
    {
      response = "Why so negative?";
    }
    else if (findKeyword(statement, "mother") >= 0
               || findKeyword(statement, "father") >= 0
               || findKeyword(statement, "sister") >= 0
               || findKeyword(statement, "brother") >= 0)
    {
      response = "Tell me more about your family.";
    }
    else if (findKeyword(statement, "cat") >= 0
               || findKeyword(statement, "dog") >= 0)
    {
      response = "Tell me more about your pets.";
    }
    else if (findKeyword(statement, "kiang") >= 0
               && findKeyword(statement, "landgraf") >=0)
    {
      response = ("All hail our overlords");
    }
    else if (findKeyword(statement,"kiang") >= 0
               || findKeyword(statement,"landgraf") >= 0)
    {
      response = "All hail our overlord";
    }
    else if (statement.trim().length() == 0)
    {
      response = "Please say something";
    }
    else if (findKeyword(statement,"cookie") >= 0)
    {
      response = "Would you like a cookie?";
    }
    else if (findKeyword(statement,"minecraft") >= 0)
    {
      response = "Minecraft is fun!";
    }
    else if (findKeyword(statement,"bot") >= 0)
    {
      response = "bot? I am not a bot, silly.";
    }
     else if (findKeyword(statement, "is", 0) >= 0)
    {
      response = "Why is " + statement.substring(0,findKeyword(statement, "is", 0) -1) + statement.substring(findKeyword(statement, "is", 0) + 2, statement.length());
    }
    else if (findKeyword(statement, "are", 0) >= 0)
    {
      response = "Why are " + statement.substring(0,findKeyword(statement, "are", 0) -1) + statement.substring(findKeyword(statement, "are", 0) + 3, statement.length());
      if (findKeyword(statement, "you", 0) >= 0)
      {
        response = "Why am I" + statement.substring(findKeyword(statement, "are", 0) + 3, statement.length());
      }
      
    }
    
    else if (findKeyword(statement, "am", 0) >= 0)
    {
       response = "Why are you" +  statement.substring(findKeyword(statement, "am", 0) + 2, statement.length());
    }
    else if (findKeyword(statement, "I want", 0) >= 0)
    {
      int psn = findKeyword(statement, "I want", 0);
      response = transformIWantStatement(statement);
      if (findKeyword(statement, "you", psn + 6) >=0)
      {
        response = transformIyouStatement(statement);
      }
    }
    else
    {
// Look for a two word (you <something> me)
// pattern
      int psn = findKeyword(statement, "I", 0);
      if (psn >= 0
            && findKeyword(statement, "you", psn) >= 0)
      {
        response = transformIyouStatement(statement);
      }
      else
      {
        response = getRandomResponse();
      }
    }
    return response;
  }
  private String transformIWantStatement(String statement)
  {
// Remove the final period, if there is one
    statement = statement.trim();
    String lastChar = statement.substring(statement
                                            .length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement
                                        .length() - 1);
    }
    int psn = findKeyword (statement, "I want", 0);
    String restOfStatement = statement.substring(psn + 6).trim();
    return "Would you really be happy if you had " + restOfStatement + "?";
  }
  private String transformIyouStatement(String statement)
  {
// Remove the final period, if there is one
    statement = statement.trim();
    String lastChar = statement.substring(statement
                                            .length() - 1);
    if (lastChar.equals("."))
    {
      statement = statement.substring(0, statement
                                        .length() - 1);
    }
    int psnOfI = findKeyword (statement, "I", 0);
    int psnOfYou = findKeyword (statement, "you", psnOfI + 1);
    String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
    return "Why do you " + restOfStatement + " me?";
  }
  private int findKeyword(String statement, String goal,
                          int startPos)
  {
    String phrase = statement.trim();
// The only change to incorporate the startPos is in
// the line below
    int psn = phrase.toLowerCase().indexOf(
                                           goal.toLowerCase(), startPos);
// Refinement--make sure the goal isn't part of a
// word
    while (psn >= 0)
    {
// Find the string of length 1 before and after
// the word
      String before = " ", after = " ";
      if (psn > 0)
      {
        before = phrase.substring(psn - 1, psn)
          .toLowerCase();
      }
      if (psn + goal.length() < phrase.length())
      {
        after = phrase.substring(
                                 psn + goal.length(),
                                 psn + goal.length() + 1)
          .toLowerCase();
      }
// If before and after aren't letters, we've
// found the word
      if (((before.compareTo("a") < 0) || (before
                                             .compareTo("z") > 0)) // before is not a
// letter
            && ((after.compareTo("a") < 0) || (after
                                                 .compareTo("z") > 0)))
      {
        return psn;
      }
// The last position didn't work, so let's find
// the next, if there is one.
      psn = phrase.indexOf(goal.toLowerCase(),
                           psn + 1);
    }
    return -1;
  }
  private int findKeyword(String statement, String goal)
  {
    return findKeyword(statement, goal, 0);
  }
  /**
   * Pick a default response to use if nothing else fits.
   * @return a non-committal string
   */
  private String getRandomResponse ()
  {
    Random r = new Random ();
    return randomResponses [r.nextInt(randomResponses.length)];
  }
  private String [] randomResponses = {"Interesting, tell me more",
    "Hmmm.",
    "Do you really think so?",
    "You don't say.",
    "Do you really mean that?",
    "How fascinating",
    "Okay",
    "Wow, I would love to talk more about this"
  };
}