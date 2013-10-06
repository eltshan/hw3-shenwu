package edu.cmu.deiis.annotators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Token;
/** Evaluate answer score and calculate the precision by assuming the answer with score more than 0.33 is right, and compare to golden answer
 * @generated */
public class Evaluator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    String text = aJCas.getDocumentText();
    java.text.DecimalFormat df = new java.text.DecimalFormat("#.##"); // for print decimal

    FSIndex answerScoreIndex = aJCas.getAnnotationIndex(AnswerScore.type); // import answer score
    Iterator answerScoreIter = answerScoreIndex.iterator();

    FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type); // question
    Iterator questionIter = questionIndex.iterator();
    Question question = (Question) questionIter.next();

    ArrayList<AnswerScore> arraylist = new ArrayList<AnswerScore>(); //Arraylist for answer score
    Comparator<AnswerScore> comparator = new Comparator<AnswerScore>() { //comparator for sort arraylist based on score
      public int compare(AnswerScore S1, AnswerScore S2) {
        if (S1.getScore() != S2.getScore()) {
          return (int) (S2.getScore() * 100 - S1.getScore() * 100);
        } else
          return 0;
      }

    };
    while (answerScoreIter.hasNext()) {
      AnswerScore answerScore = (AnswerScore) answerScoreIter.next();
      arraylist.add(answerScore);
    }
    Collections.sort(arraylist, comparator); // sort arraylist based on score
    // display(arraylist);
    System.out.print("Question: " + text.substring(question.getBegin(), question.getEnd()));
    int at = 0;
    double right = 0;
    double wrong = 0;
    double precision = 0;
    for (int i = 0; i < arraylist.size(); i++) {
      String str = null;
      if (arraylist.get(i).getAnswer().getIsCorrect() == true)
        str = "+ ";
      else
        str = "- ";
      if (arraylist.get(i).getScore() >= 0.33) {
        at++;
        if (str == "+ ")
          right++;
        else
          wrong++;
      } else {
        if (str == "+ ")
          wrong++;
        else
          right++;

      }

      System.out.print(str
              + df.format(arraylist.get(i).getScore())
              + " "
              + text.substring(arraylist.get(i).getAnswer().getBegin(), arraylist.get(i)
                      .getAnswer().getEnd()));

    }
    precision = right / (right + wrong);
    System.out.print("Presion at " + at + " " + ":" + " " + precision); // print precision
  }

}
