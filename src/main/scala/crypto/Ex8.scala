//At the following URL are a bunch of hex-encoded ciphertexts:
//https://gist.github.com/3132928
//One of them is ECB encrypted. Detect it.
//Remember that the problem with ECB is that it is stateless and
//deterministic; the same 16 byte plaintext block will always produce
//the same 16 byte ciphertext.

package crypto.ex8

import crypto.utils._

object Ex8 {
  def main(args: Array[String]) = {
    val source = scala.io.Source.fromFile("data/ex8.txt")
    val lines = source.getLines().toArray
    val encryptedLine = lines.reverse.maxBy(line => findMostRepeatingBlockFrequency(line))
    println(encryptedLine)
    source.close()
  }

  def findMostRepeatingBlockFrequency(line: String): Float = {
    Utils.findMostRepeatingBlockFrequency(line.getBytes(), 16)
  }
}
