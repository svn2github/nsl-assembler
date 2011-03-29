/*
 * EqualityExpression.java
 */

package nsl.expression;

import java.io.IOException;
import nsl.*;

/**
 * Describes an equality expression.
 * @author Stuart
 */
public class EqualityExpression extends ConditionalExpression
{
  /**
   * Class constructor
   */
  protected EqualityExpression()
  {
    super(null, null, null);
  }

  /**
   * Class constructor specifying the left and right operands and the operator.
   * @param leftOperand the left operand
   * @param operator the operator
   * @param rightOperand the right operand
   */
  public EqualityExpression(Expression leftOperand, String operator, Expression rightOperand)
  {
    super(leftOperand, new Operator(operator, OperatorType.Mathematical), rightOperand);
  }

  /**
   * Assembles the source code for comparing the left and right operands.
   * @param leftOperand the left operand
   * @param operator the comparison operator
   * @param rightOperand the right operand
   * @param gotoA the first go-to
   * @param gotoB the second go-to
   * @throws IOException
   */
  private static void assembleCmp(String leftOperand, String operator, String rightOperand, Label gotoA, Label gotoB) throws IOException
  {
    if (operator.equals("=="))
      ScriptParser.writeLine(String.format("StrCmp %s %s %s %s", leftOperand, rightOperand, gotoA, gotoB));
    else if (operator.equals("!="))
      ScriptParser.writeLine(String.format("StrCmp %s %s %s %s", leftOperand, rightOperand, gotoB, gotoA));
    else if (operator.equals("<="))
      ScriptParser.writeLine(String.format("IntCmp %s %s %s %s %s", leftOperand, rightOperand, gotoA, gotoA, gotoB));
    else if (operator.equals(">="))
      ScriptParser.writeLine(String.format("IntCmp %s %s %s %s %s", leftOperand, rightOperand, gotoA, gotoB, gotoA));
    else if (operator.equals("<"))
      ScriptParser.writeLine(String.format("IntCmp %s %s %s %s %s", leftOperand, rightOperand, gotoB, gotoA, gotoB));
    else if (operator.equals(">"))
      ScriptParser.writeLine(String.format("IntCmp %s %s %s %s %s", leftOperand, rightOperand, gotoB, gotoB, gotoA));
    else
      throw new NslException("Unknown operator " + operator);
  }

  /**
   * Assembles the source code.
   */
  @Override
  public void assemble(Register var) throws IOException
  {
    throw new UnsupportedOperationException("Not supported.");
  }

  /**
   * Assembles the source code.
   * @param gotoA the first go-to
   * @param gotoB the second go-to
   */
  public void assemble(Label gotoA, Label gotoB) throws IOException
  {
    if (this.leftOperand instanceof AssembleExpression && this.rightOperand instanceof AssembleExpression)
    {
      Register varLeft = RegisterList.getCurrent().getNext();
      Register varRight = RegisterList.getCurrent().getNext();
      ((AssembleExpression)this.leftOperand).assemble(varLeft);
      ((AssembleExpression)this.rightOperand).assemble(varRight);
      assembleCmp(varLeft.toString(), this.operator.getOperator(), varRight.toString(), gotoA, gotoB);
      varRight.setInUse(false);
      varLeft.setInUse(false);
    }
    else if (this.leftOperand instanceof AssembleExpression)
    {
      Register varLeft = RegisterList.getCurrent().getNext();
      ((AssembleExpression)this.leftOperand).assemble(varLeft);
      assembleCmp(varLeft.toString(), this.operator.getOperator(), this.rightOperand.toString(), gotoA, gotoB);
      varLeft.setInUse(false);
    }
    else if (this.rightOperand instanceof AssembleExpression)
    {
      Register varRight = RegisterList.getCurrent().getNext();
      ((AssembleExpression)this.rightOperand).assemble(varRight);
      assembleCmp(this.leftOperand.toString(), this.operator.getOperator(), varRight.toString(), gotoA, gotoB);
      varRight.setInUse(false);
    }
    else
    {
      assembleCmp(this.leftOperand.toString(), this.operator.getOperator(), this.rightOperand.toString(), gotoA, gotoB);
    }
  }
}
