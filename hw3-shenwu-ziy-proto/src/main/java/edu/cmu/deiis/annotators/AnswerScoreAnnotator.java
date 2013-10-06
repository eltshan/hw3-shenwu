package edu.cmu.deiis.annotators;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.cleartk.ne.type.NamedEntity;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.Question;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;

/** annotation of answers, specifically with the given input, the answers are the sentences started with 'A'.
 * @generated */

public class AnswerScoreAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    String text = aJCas.getDocumentText();

    TokenizerFactory<Word> factory = PTBTokenizerFactory.newTokenizerFactory();
    // the thing that can generate tokens

    FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);// question Iterator
    Iterator questionIter = questionIndex.iterator();

    Question question = (Question) questionIter.next();

    FSIndex answerIndex = aJCas.getAnnotationIndex(Answer.type);// answer Iterator
    Iterator answerIter = answerIndex.iterator();

    FSIndex answerNgramIndex = aJCas.getAnnotationIndex(NGram.type);// NGram Iterator
    Iterator asnwerNgramIter = answerNgramIndex.iterator();
    // answerNgramIndex.
    Set<String> set = new HashSet<String>();// because we will need visit each element in question
                                            // many times, I chose hashTable to do it
    FSIndex QuestionNgramIndex = aJCas.getAnnotationIndex(NGram.type);
    Iterator QuestionNgramIter = QuestionNgramIndex.iterator();
    NGram questionNGram = (NGram) QuestionNgramIter.next();
    int ll = 0;
    while (questionNGram.getEnd() < question.getEnd()) {
      ll++;
      String tmp = text.substring(questionNGram.getBegin(), questionNGram.getEnd());
      set.add(tmp);
      questionNGram = (NGram) QuestionNgramIter.next();
    }

    Answer answer;
    while (answerIter.hasNext()) {
      double match = 0;
      double total = 0;
      answer = (Answer) answerIter.next();

      while (true)// each ngram in current answer
      {
        NGram answerNgram = (NGram) asnwerNgramIter.next();
        if (answerNgram.getBegin() >= answer.getBegin() && answerNgram.getBegin() < answer.getEnd()) {
          total++;
          //total += answerNgram.getElements().size();
          // FSIndex QuestionNgramIndex = aJCas.getAnnotationIndex(NGram.type);
          // Iterator QuestionNgramIter = QuestionNgramIndex.iterator();
          String tmp = text.substring(answerNgram.getBegin(), answerNgram.getEnd());
          if (set.contains(tmp))
            //match += answerNgram.getElements().size(); // consider weight
            match++; //didn't consider weight

        }
        if (answerNgram.getEnd() == answer.getEnd() - 2 && answerNgram.getElements().size() == 1)
          // when find the last NGram in the answer, break
          break;
      }// end of each ngram in answer

      AnswerScore currentAnswerScore = new AnswerScore(aJCas);
      currentAnswerScore.setBegin(answer.getBegin());
      currentAnswerScore.setEnd(answer.getEnd());
      currentAnswerScore.setAnswer(answer);
      currentAnswerScore.setScore(match / total);

      currentAnswerScore.setConfidence(1);
      currentAnswerScore.setCasProcessorId("edu.cmu.deiis.annotators.AnswerScoreAnnotator");
      currentAnswerScore.addToIndexes();
      
     
    }// end of each answer
    //aJCas.gete
    FSIndex<?> stanfordIndex = aJCas.getAnnotationIndex(org.cleartk.token.type.Token.type);
    Iterator<?> stanfordEntityIter = stanfordIndex.iterator();
    
    while(stanfordEntityIter.hasNext())
    {
      org.cleartk.token.type.Token token =(org.cleartk.token.type.Token) stanfordEntityIter.next();
      if(token.getPos().equals("NNP"));
        System.out.println("namedEntity: " + token.toString());
    }
    
    
    
  }// end of process

}
