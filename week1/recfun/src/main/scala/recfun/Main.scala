package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      def pascalIter(iC: Int, iR: Int): Int = {
        if (iR == 0 || iC == 0 || iC == iR) 1
        else pascalIter(iC - 1, iR - 1) + pascalIter(iC, iR - 1)
      }
      pascalIter(c, r)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {

      def newCount(chr: Char, count: Int): Int = {
        chr match {
          case '(' => count + 1
          case ')' => count - 1
          case _ => count
        }
      }

      def balanceIter(iChrs: List[Char], count: Int): Boolean = {
        (iChrs.isEmpty, count) match {
          case (_, x) if x < 0 => false
          case (true, 0) => true
          case (true, _) => false
          case _ => balanceIter(iChrs.tail, newCount(iChrs.head, count))
        }
      }

      balanceIter(chars, 0)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {

      def countIter(money: Int, coins: List[Int]): Int = {
        if (money == 0 || coins.isEmpty) 0
        else
          money - coins.head match {
            case 0 =>  1 + countIter(money, coins.tail)
            case x if x < 0 => countIter(money, coins.tail)
            case _ => countIter(money - coins.head, coins) + countIter(money, coins.tail)
          }
      }

      countIter(money, coins.sorted.reverse)
    }
  }
