//4. Detect single-character XOR
//
//One of the 60-character strings at:
//https://gist.github.com/3132713
//has been encrypted by single-character XOR. Find it. (Your code from #3 should help.)

import org.scalatest._
import scala.io.Source
import crypto.ex4.Ex4
import crypto.utils.Utils

class Ex4Spec extends FunSuite with DiagrammedAssertions {
  val fileName = "data/ex4.txt"

  test("finds the correct enrypted string") {
    val expectedEncryptedString = "7b5a4215415d544115415d5015455447414c155c46155f4058455c5b523f"
    val source = Source.fromFile(fileName)
    val lines = source.getLines().toSeq.map(Utils.hexToBin(_))
    val result = Ex4.findEncrypted(lines)
    assert(Utils.binToHex(result._3) == expectedEncryptedString)
  }
}
