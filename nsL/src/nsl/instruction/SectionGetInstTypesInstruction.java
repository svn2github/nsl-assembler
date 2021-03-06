/*
 * SectionGetInstTypesInstruction.java
 */

package nsl.instruction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import nsl.*;
import nsl.expression.*;

/**
 * @author Stuart
 */
public class SectionGetInstTypesInstruction extends AssembleExpression
{
  public static final String name = "SectionGetInstTypes";
  private final Expression index;

  /**
   * Class constructor.
   * @param returns the number of values to return
   */
  public SectionGetInstTypesInstruction(int returns)
  {
    if (PageExInfo.in())
      throw new NslContextException(EnumSet.of(NslContext.Section, NslContext.Function, NslContext.Global), name);
    if (returns != 1)
      throw new NslReturnValueException(name, 1);

    ArrayList<Expression> paramsList = Expression.matchList();
    if (paramsList.size() != 1)
      throw new NslArgumentException(name, 1);

    this.index = paramsList.get(0);
  }

  /**
   * Assembles the source code.
   * @throws IOException
   */
  @Override
  public void assemble() throws IOException
  {
    throw new UnsupportedOperationException("Not supported.");
  }

  /**
   * Assembles the source code.
   * @param var the variable to assign the value to
   */
  @Override
  public void assemble(Register var) throws IOException
  {
    Expression varOrIndex = AssembleExpression.getRegisterOrExpression(this.index);
    ScriptParser.writeLine(name + " " + var + " " + varOrIndex);
    varOrIndex.setInUse(false);
  }
}
