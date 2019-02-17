//1. Convert hex to base64 and back. The string:
//49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d

//should produce:
//SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t

//Now use this code everywhere for the rest of the exercises. Here's a simple
//rule of thumb: Always operate on raw bytes, never on encoded strings. Only use
//hex and base64 for pretty-printing.

package crypto.ex1

import crypto.utils._

object Ex1 {
  def main(args: Array[String]) {
    val initial = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val target = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
    val bytes = Utils.hexToBin(initial)
    val b64 = Utils.b64Encode(bytes)
    val b64_decoded = Utils.b64Decode(b64)
    println(target)
    println(b64)
  }

}
