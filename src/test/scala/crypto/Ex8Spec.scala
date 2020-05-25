//At the following URL are a bunch of hex-encoded ciphertexts:
//https://gist.github.com/3132928
//One of them is ECB encrypted. Detect it.
//Remember that the problem with ECB is that it is stateless and
//deterministic; the same 16 byte plaintext block will always produce
//the same 16 byte ciphertext.

import org.scalatest._
import org.scalatest.Matchers._
import crypto.ex8.Ex8

class Ex8Spec extends FunSuite with DiagrammedAssertions {
  val fileName = "data/ex8.txt"

  test("Can detect ECB encrypted ciphertext") {
    val source = scala.io.Source.fromFile("data/ex8.txt")
    val lines = source.getLines().toArray
    val encryptedLine = lines.reverse.maxBy(line => Ex8.findMostRepeatingBlockFrequency(line))
    val target = "d880619740a8a19b7840a8a31c810a3d08649af70dc06f4fd5d2d69c744cd283e2dd052f6b641dbf9d11b0348542bb5708649af70dc06f4fd5d2d69c744cd2839475c9dfdbc1d46597949d9c7e82bf5a08649af70dc06f4fd5d2d69c744cd28397a93eab8d6aecd566489154789a6b0308649af70dc06f4fd5d2d69c744cd283d403180c98c8f6db1f2a3f9c4040deb0ab51b29933f2c123c58386b06fba186a"
    encryptedLine should equal(target)
  }
}
