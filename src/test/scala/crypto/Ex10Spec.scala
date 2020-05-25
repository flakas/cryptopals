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

import org.scalatest._
import org.scalatest.Matchers._
import crypto.ex10.Ex10
import crypto.algorithms.aes.AES
import crypto.utils._

class Ex10Spec extends FunSuite with DiagrammedAssertions {
  val fileName = "data/ex10.txt"

  test("Can encrypt AES128CBC") {
    val originalBytes = "Don't mind me, I am just testing".getBytes()
    val key = "YELLOW SUBMARINE".getBytes()
    val iv = Array.fill(32)(0.toByte)
    val encryptedBytes = AES.encryptAES128CBC(originalBytes, key, iv)
    val decryptedBytes = AES.decryptAES128CBC(encryptedBytes, key, iv)
    originalBytes should be(decryptedBytes)
  }

  test("Can decrypt AES128CBC") {
    val targetBytes = "I'm back and I'm ringin' the bell".getBytes()
    val source = scala.io.Source.fromFile(fileName)
    val lines = source.getLines().toArray
    val bytes = Utils.b64Decode(lines.head)
    val initializationVector = Array.fill(32)(0.toByte)
    val decryptedBytes = AES.decryptAES128CBC(bytes, "YELLOW SUBMARINE".getBytes(), initializationVector)
    decryptedBytes.slice(0, targetBytes.length) should equal(targetBytes)
  }
}
