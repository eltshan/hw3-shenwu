package edu.cmu.deiis.annotators;

import java.util.Scanner;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;

/** annotate question or answers based on the start char of each sentence.
 * @generated */
public class TestElementAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    String text = aJCas.getDocumentText();

    Scanner scanner = new Scanner(text);
    int begin = 0;
    int end = 1;
    while (scanner.hasNextLine()) {
      String sentence = scanner.nextLine(); // extract each line

      end = begin + sentence.length() + 1;
      if (sentence.charAt(0) == 'Q') { // is question
        Question annotation = new Question(aJCas);
        annotation.setBegin(begin + 2); // begin, exclude the "Q "
        annotation.setEnd(end);
        annotation.setConfidence(1); // confidence = 1
        annotation.setCasProcessorId("edu.cmu.dells.annotators.TestElementAnnotator");// set casID
        annotation.addToIndexes();
      } else if (sentence.charAt(0) == 'A') { // is answer
        Answer annotation = new Answer(aJCas);
        annotation.setBegin(begin + 4); // exclude "A 1 "
        annotation.setEnd(end);
        annotation.setConfidence(1); // confidence = 1
        annotation.setCasProcessorId("edu.cmu.dells.annotators.TestElementAnnotator");// set casID
        if (sentence.charAt(2) == '1')
          annotation.setIsCorrect(true);
        else if (sentence.charAt(2) == '0')
          annotation.setIsCorrect(false);
        annotation.addToIndexes();
      }
      begin = end + 1;

    }

  }// process

}
