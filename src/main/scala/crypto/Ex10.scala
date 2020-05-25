//10. Implement CBC Mode
//In CBC mode, each ciphertext block is added to the next plaintext
//block before the next call to the cipher core.
//
//The first plaintext block, which has no associated previous ciphertext
//block, is added to a "fake 0th ciphertext block" called the IV.
//
//Implement CBC mode by hand by taking the ECB function you just wrote,
//making it encrypt instead of decrypt (verify this by decrypting
//whatever you encrypt to test), and using your XOR function from
//previous exercise.
//
//DO NOT CHEAT AND USE OPENSSL TO DO CBC MODE, EVEN TO VERIFY YOUR
//RESULTS. What's the point of even doing this stuff if you aren't going
//to learn from it?
//
//The buffer at:
//https://gist.github.com/3132976
//is intelligible (somewhat) when CBC decrypted against "YELLOW
//SUBMARINE" with an IV of all ASCII 0 (\x00\x00\x00 &c)

package crypto.ex10

import crypto.utils._
import crypto.ex9._
import crypto.algorithms.aes.AES

object Ex10 {
  def main(args: Array[String]) = {
    val source = scala.io.Source.fromFile("data/ex10.txt")
    val lines = source.getLines().toArray
    val bytes = Utils.b64Decode(lines.head)
    val initializationVector = Array.fill(32)(0.toByte)
    val result = AES.decryptAES128CBC(bytes, "YELLOW SUBMARINE".getBytes(), initializationVector)
    println(result.map(_.toChar).mkString)
  }
}
