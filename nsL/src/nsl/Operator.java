/*
 * Operator.java
 */

package nsl;

/**
 * Matches and describes a mathematical or logical operator.
 * @author Stuart
 */
public class Operator
{
  private String operator;
  private OperatorType type;

  /**
   * Class constructor specifying the operator and type.
   * @param operator the operator
   * @param type the type
   */
  public Operator(String operator, OperatorType type)
  {
    this.operator = operator;
    this.type = type;
  }

  /**
   * Class constructor specifying the script tokenizer.
   * @param tokenizer the script tokenizer
   */
  public Operator(Tokenizer tokenizer) throws NslException
  {
    if (tokenizer.match('+'))
    {
      this.operator = "+";
      this.type = OperatorType.Mathematical;
    }
    else if (tokenizer.match('-'))
    {
      this.operator = "-";
      this.type = OperatorType.Mathematical;
    }
    else if (tokenizer.match('*'))
    {
      this.operator = "*";
      this.type = OperatorType.Mathematical;
    }
    else if (tokenizer.match('/'))
    {
      this.operator = "/";
      this.type = OperatorType.Mathematical;
    }
    else if (tokenizer.match('='))
    {
      if (tokenizer.match('='))
      {
        this.operator = "==";
        this.type = OperatorType.Boolean;
      }
      else if (!tokenizer.tokenIsChar())
      {
        this.operator = "=";
        this.type = OperatorType.Assignment;
      }
      else
        throw new NslExpectedException("\"=\" or an expression");
    }
    else if (tokenizer.match('<'))
    {
      if (tokenizer.match('='))
      {
        this.operator = "<=";
        this.type = OperatorType.Boolean;
      }
      else if (!tokenizer.tokenIsChar())
      {
        this.operator = "<";
        this.type = OperatorType.Boolean;
      }
      else
        throw new NslExpectedException("\"=\" or an expression");
    }
    else if (tokenizer.match('>'))
    {
      if (tokenizer.match('='))
      {
        this.operator = ">=";
        this.type = OperatorType.Boolean;
      }
      else if (!tokenizer.tokenIsChar())
      {
        this.operator = ">";
        this.type = OperatorType.Boolean;
      }
      else
        throw new NslExpectedException("\"=\" or an expression");
    }
    else
      throw new NslExpectedException("an operator");
  }

  /**
   * Gets the operator
   * @return the operator
   */
  public String getOperator()
  {
    return this.operator;
  }

  /**
   * Gets the type of operator.
   * @return the type of operator
   */
  public OperatorType getType()
  {
    return this.type;
  }

  /**
   * Gets a string representation of the current object.
   * @return a string representation of the current object
   */
  @Override
  public String toString()
  {
    return this.operator;
  }
}
