

/* First created by JCasGen Mon Sep 30 23:42:29 CST 2013 */
package org.cleartk.timeml.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.cleartk.score.type.ScoredAnnotation;


/** 
 * Updated by JCasGen Wed Oct 02 03:56:33 CST 2013
 * XML source: C:/Users/Eltshan/git/hw3-shenwu/hw3-shenwu-ziy-proto/src/main/resources/hw2-shenwu-aae.xml
 * @generated */
public class Anchor extends ScoredAnnotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Anchor.class);
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
  protected Anchor() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Anchor(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Anchor(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Anchor(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (Anchor_Type.featOkTst && ((Anchor_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "org.cleartk.timeml.type.Anchor");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Anchor_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (Anchor_Type.featOkTst && ((Anchor_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "org.cleartk.timeml.type.Anchor");
    jcasType.ll_cas.ll_setStringValue(addr, ((Anchor_Type)jcasType).casFeatCode_id, v);}    
  }

    