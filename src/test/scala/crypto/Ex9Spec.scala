//9. Implement PKCS#7 padding
//Pad any block to a specific block length, by appending the number of
//bytes of padding to the end of the block. For instance,
//"YELLOW SUBMARINE"
//padded to 20 bytes would be:
//"YELLOW SUBMARINE\x04\x04\x04\x04"
//The particulars of this algorithm are easy to find online.

import org.scalatest._
import crypto.ex9.Ex9
import crypto.utils.Utils

class Ex9Spec extends FunSuite with DiagrammedAssertions {
  val fileName = "data/ex9.txt"

  test("Can pkcs7 pad to block length") {
    val source = "YELLOW SUBMARINE"
    var length = 20
    val target = "59454c4c4f57205355424d4152494e4504040404"
    assert(Utils.binToHex(Ex9.pkcs7PadBlocks(source.getBytes(), length)) == target)
  }
}
