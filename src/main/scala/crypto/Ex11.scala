// 11. Write an oracle function and use it to detect ECB.
//
// Now that you have ECB and CBC working:
//
// Write a function to generate a random AES key; that's just 16 random
// bytes.
//
// Write a function that encrypts data under an unknown key --- that is,
// a function that generates a random key and encrypts under it.
//
// The function should look like:
//
// encryption_oracle(your-input)
//  => [MEANINGLESS JIBBER JABBER]
//
// Under the hood, have the function APPEND 5-10 bytes (count chosen
// randomly) BEFORE the plaintext and 5-10 bytes AFTER the plaintext.
//
// Now, have the function choose to encrypt under ECB 1/2 the time, and
// under CBC the other half (just use random IVs each time for CBC). Use
// rand(2) to decide which to use.
//
// Now detect the block cipher mode the function is using each time.

package crypto.ex11

import scala.util.Random
import crypto.utils._
import crypto.algorithms.AES

object Ex11 {
  def main(args: Array[String]) = {
    val plaintext = "A"*128 // Need to have enough repeating blocks to differentiate between ECB and CBC
    val cipher = encryptionOracle(plaintext.getBytes())
    val guess = AES.isECB(cipher._2)
    assert((cipher._1 == "ECB" && guess) || (cipher._1 == "CBC" && !guess))
  }

  def encryptionOracle(data: Array[Byte]) : (String, Array[Byte]) = {
    val paddedData = Oracle.randomPadPlaintext(data, (5 to 11), (5 to 11))
    if (Random.nextBoolean()) {
      ("ECB", encryptECB(paddedData))
    } else {
      ("CBC", encryptCBC(paddedData))
    }
  }
  def encryptECB(data: Array[Byte]) = Oracle.encryptAES128ECB(data)
  def encryptCBC(data: Array[Byte]) = Oracle.encryptAES128CBC(data)

  def predictMode(data: Array[Byte]) : String = if(AES.isECB(data)) "ECB" else "CBC"
}
