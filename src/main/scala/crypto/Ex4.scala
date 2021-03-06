//4. Detect single-character XOR
//
//One of the 60-character strings at:
//https://gist.github.com/3132713
//has been encrypted by single-character XOR. Find it. (Your code from #3 should help.)

package crypto.ex4

import scala.io.Source
import crypto.utils._

object Ex4 {
  def main(args: Array[String]) = {
    val source = Source.fromFile("data/ex4.txt")
    val lines = source.getLines().toSeq.map(Utils.hexToBin(_))
    val result = this.findEncrypted(lines)
    println("Key: " + result._1.toChar)
    println("Message: " + result._2.map(_.toChar).mkString)
    println("String: " + Utils.binToHex(result._3))
    source.close()
  }

  def findEncrypted(lines: Seq[Array[Byte]]) =
    lines
      .map(findBestScore(_))
      .maxBy((x: (Byte, Array[Byte], Array[Byte])) => Utils.scoreByVowelFrequency(x._2))

  def findBestScore(bytes: Array[Byte]) =
    (0 to 255)
      .map((b: Int) => (b.toByte, Utils.xorSingle(bytes, b.toByte), bytes))
      .maxBy((x: (Byte, Array[Byte], Array[Byte])) => Utils.scoreByVowelFrequency(x._2))
}
