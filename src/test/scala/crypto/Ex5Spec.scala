//5. Repeating-key XOR Cipher
//
//Write the code to encrypt the string:
//
//Burning 'em, if you ain't quick and nimble
//I go crazy when I hear a cymbal
//
//Under the key "ICE", using repeating-key XOR. It should come out to:
//0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f
//Encrypt a bunch of stuff using your repeating-key XOR function. Get a
//feel for it.

import org.scalatest._
import org.scalatest.Matchers._
import crypto.ex5.Ex5

class Ex5Spec extends FunSuite with DiagrammedAssertions {
  test("Can XOR encrypt a string with a repeating key") {
    val source = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal"
    val key = "ICE"
    val target = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"
    Ex5.encrypt(source, key) should equal(target)
  }
}
