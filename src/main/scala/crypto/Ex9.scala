//9. Implement PKCS#7 padding
//Pad any block to a specific block length, by appending the number of
//bytes of padding to the end of the block. For instance,
//"YELLOW SUBMARINE"
//padded to 20 bytes would be:
//"YELLOW SUBMARINE\x04\x04\x04\x04"
//The particulars of this algorithm are easy to find online.

package crypto.ex9

import crypto.utils._

object Ex9 {
  def main(args: Array[String]) = {
    val bytes = "YELLOW SUBMARINE".getBytes()
    val paddedBytes = Utils.pkcs7PadBlock(bytes, 20)
    println(Utils.binToHex(paddedBytes))
  }

}
