package edu.cmu.deiis.annotators;

import java.io.StringReader;
import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Token;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;

/** annotate N-gram feature by combining every one, every two, and every three tokens in each sentences.
 * @generated */
public class NGramAnnotator extends JCasAnnotator_ImplBase {

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

    FSIndex<?> tokenIndex = aJCas.getAnnotationIndex(Token.type);
    Iterator<?> tokenIter = tokenIndex.iterator();
    // while (questionIter.hasNext()) {// extract tokens from question but with only question, no
    // need to hasNext
    FSIndex<?> questionIndex = aJCas.getAnnotationIndex(Question.type);
    Iterator<?> questionIter = questionIndex.iterator();

    Question question = (Question) questionIter.next();

    // Token token = (Token) tokenIter.next();

    int lastStart = -1;
    int lastLastStart = -1;
    FSArray elementOne = new FSArray(aJCas, 1); // element array for uniGram
    FSArray elementTwo = new FSArray(aJCas, 2);// for biGram
    FSArray elementThree = new FSArray(aJCas, 3);// for TriGram
    Token lastToken = null;
    Token lastLastToken = null;
    while (tokenIter.hasNext()) {// I know that I write the token code twice, but it make the
                                 // process more clear
      Token token = (Token) tokenIter.next();// iteratorte each token

      NGram ngram = new NGram(aJCas);
      ngram.setBegin(token.getBegin());
      ngram.setEnd(token.getEnd());
      //ngram.setNum(1);

      elementOne.set(0, token);

      ngram.setElements(elementOne);
      ngram.setConfidence(1);
      ngram.setCasProcessorId("edu.cmu.deiis.annotators.NGramAnnotator");
      ngram.addToIndexes(); // each token is a uniGram

      if (lastStart >= 0) { // lastStart >= 0 means current token has a previous token, so combine
                            // them to a Bigram
        ngram = new NGram(aJCas);
        ngram.setBegin(lastStart);
        ngram.setEnd(token.getEnd());
        //ngram.setNum(2);
        elementTwo.set(0, lastToken);
        elementTwo.set(1, token);

        ngram.setElements(elementTwo);
        ngram.setConfidence(1);
        ngram.setCasProcessorId("edu.cmu.deiis.annotators.NGramAnnotator");
        ngram.addToIndexes();
      }

      if (lastLastStart >= 0) { // the same as last
        ngram = new NGram(aJCas);
        ngram.setBegin(lastLastStart);
        ngram.setEnd(token.getEnd());
        //ngram.setNum(3);

        elementThree.set(0, lastLastToken);
        elementThree.set(1, lastToken);
        elementThree.set(2, token);

        ngram.setElements(elementThree);
        ngram.setConfidence(1);
        ngram.setCasProcessorId("edu.cmu.deiis.annotators.NGramAnnotator");
        ngram.addToIndexes();

      }
      lastLastStart = lastStart;
      lastStart = token.getBegin();
      lastToken = token;
      lastLastToken = lastToken;

      if (token.getEnd() == question.getEnd() - 2)
        break;
    }

    FSIndex<?> answerIndex = aJCas.getAnnotationIndex(Answer.type);
    Iterator<?> answerIter = answerIndex.iterator();

    while (answerIter.hasNext()) { // iterator of answer
      lastStart = -1;
      lastLastStart = -1;
      Answer answer = (Answer) answerIter.next();
      while (tokenIter.hasNext()) {
        Token token = (Token) tokenIter.next();
        NGram ngram;

        if (lastStart >= 0) {
          ngram = new NGram(aJCas);
          ngram.setBegin(lastStart);
          ngram.setEnd(token.getEnd());
          //ngram.setNum(2);
          elementTwo.set(0, lastToken);
          elementTwo.set(1, token);
          ngram.setElements(elementTwo);

          ngram.setConfidence(1);
          ngram.setCasProcessorId("edu.cmu.deiis.annotators.NGramAnnotator");
          ngram.addToIndexes();
        }

        if (lastLastStart >= 0) {
          ngram = new NGram(aJCas);
          ngram.setBegin(lastLastStart);
          ngram.setEnd(token.getEnd());
          //ngram.setNum(3);
          elementThree.set(0, lastLastToken);
          elementThree.set(1, lastToken);
          elementThree.set(2, token);
          ngram.setElements(elementThree);

          ngram.setConfidence(1);
          ngram.setCasProcessorId("edu.cmu.deiis.annotators.NGramAnnotator");
          ngram.addToIndexes();
        }

        ngram = new NGram(aJCas);
        ngram.setBegin(token.getBegin());
        ngram.setEnd(token.getEnd());
        //ngram.setNum(1);
        elementOne.set(0, token);

        ngram.setElements(elementOne);
        ngram.setConfidence(1);
        ngram.setCasProcessorId("edu.cmu.deiis.annotators.NGramAnnotator");
        ngram.addToIndexes();
        lastLastStart = lastStart;
        lastStart = token.getBegin();
        lastToken = token;
        lastLastToken = lastToken;
        if (token.getEnd() == answer.getEnd() - 2)
          break;
      }

    }

  }// end of process

}
