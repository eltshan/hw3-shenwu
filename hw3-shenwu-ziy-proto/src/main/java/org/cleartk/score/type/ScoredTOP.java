

/* First created by JCasGen Mon Sep 30 23:42:29 CST 2013 */
package org.cleartk.score.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.TOP;


/** 
 * Updated by JCasGen Wed Oct 02 03:56:33 CST 2013
 * XML source: C:/Users/Eltshan/git/hw3-shenwu/hw3-shenwu-ziy-proto/src/main/resources/hw2-shenwu-aae.xml
 * @generated */
public class ScoredTOP extends TOP {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ScoredTOP.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected ScoredTOP() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public ScoredTOP(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public ScoredTOP(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: score

  /** getter for score - gets 
   * @generated */
  public double getScore() {
    if (ScoredTOP_Type.featOkTst && ((ScoredTOP_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "org.cleartk.score.type.ScoredTOP");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((ScoredTOP_Type)jcasType).casFeatCode_score);}
    
  /** setter for score - sets  
   * @generated */
  public void setScore(double v) {
    if (ScoredTOP_Type.featOkTst && ((ScoredTOP_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "org.cleartk.score.type.ScoredTOP");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((ScoredTOP_Type)jcasType).casFeatCode_score, v);}    
  }

    