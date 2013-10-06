package edu.cmu.deiis.annotators;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;

import java.io.StringReader;
import java.util.Iterator;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Token;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import edu.stanford.nlp.process.Tokenizer;

/** annotate tokens using Stanford NLP lab. The periods at the end of each sentece is excluded
 * @generated */
public class TokenAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    String text = aJCas.getDocumentText();

    TokenizerFactory<Word> factory = PTBTokenizerFactory.newTokenizerFactory();
    // the thing that can generate tokens

    /*
     * Tokenizer<Word> tokenizer = factory.getTokenizer (new StringReader (text));
     * while(tokenizer.hasNext()) { Token annotation = new Token(aJCas); Word temp =
     * tokenizer.next(); annotation.setBegin(temp.beginPosition());
     * annotation.setEnd(temp.endPosition()); annotation.addToIndexes(); }
     */

    FSIndex<?> questionIndex = aJCas.getAnnotationIndex(Question.type);
    Iterator<?> questionIter = questionIndex.iterator();
    while (questionIter.hasNext()) {// extract tokens from question
      Question question = (Question) questionIter.next();
      Tokenizer<Word> tokenizer = factory.getTokenizer(new StringReader(text.substring(
              question.getBegin(), question.getEnd() - 2)));// -2 because i don't want the "?" in
                                                            // the end
      while (tokenizer.hasNext()) {
        Token annotation = new Token(aJCas);
        Word temp = tokenizer.next();
        annotation.setBegin(temp.beginPosition() + question.getBegin());
        annotation.setEnd(temp.endPosition() + question.getBegin());
        annotation.setCasProcessorId("edu.cmu.dells.annotators.TokenAnnotator");
        annotation.setConfidence(1);
        annotation.addToIndexes();
      }
    }

    FSIndex<?> answerIndex = aJCas.getAnnotationIndex(Answer.type);
    Iterator<?> answerIter = answerIndex.iterator();
    while (answerIter.hasNext()) {// extract tokens from answers
      Answer answer = (Answer) answerIter.next();
      Tokenizer<Word> tokenizer = factory.getTokenizer(new StringReader(text.substring(
              answer.getBegin(), answer.getEnd() - 2)));// -2 because i don't want the "." in the
                                                        // end
      while (tokenizer.hasNext()) {
        Token annotation = new Token(aJCas);
        Word temp = tokenizer.next();
        annotation.setBegin(temp.beginPosition() + answer.getBegin());
        annotation.setEnd(temp.endPosition() + answer.getBegin());
        annotation.setCasProcessorId("edu.cmu.dells.annotators.TokenAnnotator");
        annotation.setConfidence(1);
        annotation.addToIndexes();
      }
    }
  }// end of process
}
