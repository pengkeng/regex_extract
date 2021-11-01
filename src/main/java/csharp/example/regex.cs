using System;
using System.Diagnostics;
using System.Text.RegularExpressions;
using System.IO;

public class Example
{
 public string pattern1 = @"^[0-9A-Z]([-.\w]*[0-9A-Z])*$" +"123";
 public string pattern2 = @"^[0-9A-Z]([-.\w]*[0-9A-Z])*$" +"123" + "234";
 public string pattern3 = pattern1 +"123" + "234";
 public string pattern4 = pattern1 +pattern2;
 public string pattern5 = pattern4;
 public string pattern6 = "123456";
 public string pattern7 ;



   public static void Main()
   {
        Regex.IsMatch(partNumber, pattern3);
                   Console.WriteLine("{0} {1} a valid part number.",
                                     partNumber,
                                     Regex.IsMatch(partNumber, pattern3,RegexOptions.IgnoreCase)));
    pattern7 = pattern1 +"123" + "234";
      string pattern = @"pattern^[0-9A-Z]([-.\w]*[0-9A-Z])*$";
      string rep = " ";
      rep = @"4512\w";

      Match m = Regex.Match("input", pattern, RegexOptions.IgnoreCase);

      Regex.Match("noinput", pattern, RegexOptions.IgnoreCase);

      string pattern2 = @"\b(\w+((\r?\n)|,?\s))*\w+[.?:;!]";

      Regex int10 = new Regex(pattern2, RegexOptions.Singleline);

      Regex comp10 = new Regex(pattern2,RegexOptions.Singleline | RegexOptions.Compiled);

      Regex compAll = new Regex(pattern2,RegexOptions.Singleline | RegexOptions.Compiled);

      RegexCompilationInfo SentencePattern = new RegexCompilationInfo(
         @"\b(\w+((\r?\n)|,?\s))*\w+[.?:;!]",
         RegexOptions.Multiline,
         "SentencePattern",
         "Utilities.RegularExpressions",
         true);

      string str =   new RegexCompilationInfo(
                  @"test\b(\w+((\r?\n)|,?\s))*\w+[.?:;!]",
                  RegexOptions.Multiline,
                  "SentencePattern",
                  "Utilities.RegularExpressions",
                  true);

   }

      public static void isMatch()
      {

  RegexOptions options = RegexOptions.IgnoreCase;
 string[] partNumbers= { "1298-673-4192", "A08Z-931-468a",
                              "_A90-123-129X", "12345-KKA-1230",
                              "0919-2893-1256" };
     string pattern3 = @"pattern3^[a-zA-Z0-9]\d{2}[a-zA-Z0-9](-\d{3}){2}[A-Za-z0-9]$";
     Regex.IsMatch(partNumber, pattern3);
                Console.WriteLine("{0} {1} a valid part number.",
                                  partNumber,
                                  Regex.IsMatch(partNumber, pattern3));
      string pattern4 = @"pattern4^[A-Z0-9]\d{2}[A-Z0-9](-\d{3}){2}[A-Z0-9]$";
         Console.WriteLine("{0} {1} a valid part number.",
                           partNumber,
                           Regex.IsMatch(partNumber, pattern4, RegexOptions.IgnoreCase)
                                         ? "is" : "is not",newRegex(pattern,options));

         string[] partNumbers= { "1298-673-4192", "A08Z-931-468a",
                                 "_A90-123-129X", "12345-KKA-1230",
                                 "0919-2893-1256" };
         string pattern5 = @"pattern5^[A-Z0-9]\d{2}[A-Z0-9](-\d{3}){2}[A-Z0-9]$";
            try {
               Console.WriteLine("{0} {1} a valid part number.",
                                 partNumber,
                                 Regex.IsMatch(partNumber, pattern5, RegexOptions.IgnoreCase|RegexOptions.Compiled)
                                               ? "is" : "is not", TimeSpan.FromMilliseconds(500));
            }
            catch (RegexMatchTimeoutException e) {
               Console.WriteLine("Timeout after {0} seconds matching {1}.",
                                 e.MatchTimeout, e.Input);
            }
      }


   public static void match()
   {

      string text = "One car red car blue car";
         string pat = @"(\w+)\s+(car)";

         // Instantiate the regular expression object.
         Regex r = new Regex(pat, RegexOptions.IgnoreCase);

         // Match the regular expression pattern against a text string.
         Match m = r.Match(text);

      string input = "ablaze beagle choral dozen elementary fanatic " +
                     "glaze hunger inept jazz kitchen lemon minus " +
                     "night optical pizza quiz restoration stamina " +
                     "train unrest vertical whiz xray yellow zealous";
      string pattern1 = @"\b\w*z+\w*\b";
      Match m1 = Regex.Match(input, pattern1);

        string pattern2 = @"\ba\w*\b";
         string input = "An extraordinary day dawns with each new day.";
         Match m2 = Regex.Match(input, pattern2, RegexOptions.IgnoreCase);
   }


   public static void regex()
      {
         string pattern = @"\b[at]\w+";
         string text = "The threaded application ate up the thread pool as it executed.";
         MatchCollection matches;
         Regex defaultRegex = new Regex(pattern);
       RegexOptions options = RegexOptions.IgnoreCase | RegexOptions.Compiled;
        Regex optionRegex = new Regex(pattern, options);
        Regex optionRegex2 = new Regex(@"\b[at]\w+", RegexOptions.IgnoreCase | RegexOptions.Compiled);
        Regex optionRegex3 = new Regex(@"\b[at]\w+", RegexOptions.IgnoreCase);
        string pattern2 = @"(a+)+$";    // DO NOT REUSE THIS PATTERN.
        Regex rgx = new Regex(pattern2, RegexOptions.IgnoreCase, TimeSpan.FromSeconds(1));
        Stopwatch sw = null;
      }



}
