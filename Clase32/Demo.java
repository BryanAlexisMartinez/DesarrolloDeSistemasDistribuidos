import java.math.BigInteger;

class Demo implements java.io.Serializable {
  public BigInteger[] numbers;

  public Demo(BigInteger... numbers) {
    this.numbers = numbers;
  }
}

