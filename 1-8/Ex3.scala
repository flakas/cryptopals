// 3. Single-character XOR Cipher
//
// The hex encoded string:
//
// 1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736
//
// ... has been XOR'd against a single character. Find the key, decrypt
// the message.
//
// Write code to do this for you. How? Devise some method for "scoring" a
// piece of English plaintext. (Character frequency is a good metric.)
// Evaluate each output and choose the one with the best score.
//
// Tune your algorithm until this works.

package crypto.ex3

import crypto.utils._

object Ex3 {
  def main(args: Array[String]) = {
    val s = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"
    val bytes = Utils.hexToBin(s)
    val result = solveSingleCharacterXor(bytes)
    println("Key: " + result._1.toChar)
    println("Message: " + result._2.map(_.toChar).mkString)
  }

  def solveSingleCharacterXor(bytes: Array[Byte]) =
    (0 to 255).map((b: Int) => (b.toByte, Utils.xorSingle(bytes, b.toByte))).maxBy((x: (Byte, Array[Byte])) => Utils.score(x._2))
}
