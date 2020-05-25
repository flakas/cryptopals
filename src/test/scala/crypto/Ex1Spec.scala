//1. Convert hex to base64 and back. The string:
//49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d

//should produce:
//SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t

//Now use this code everywhere for the rest of the exercises. Here's a simple
//rule of thumb: Always operate on raw bytes, never on encoded strings. Only use
//hex and base64 for pretty-printing.

import org.scalatest._
import org.scalatest.Matchers._
import crypto.ex1.Ex1
import crypto.utils.Utils

class Ex1Spec extends FunSuite with DiagrammedAssertions {
  val sample_hex = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
  val sample_b64 = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"

  test("Encodes hex to base64") {
    val target = sample_b64
    val b64 = Ex1.encodeHexToBase64(sample_hex)
    b64 should equal(sample_b64)
  }

  test("Decodes base64 to binary") {
    val got_bytes = Ex1.decodeBase64ToHex(sample_b64)
    val got_hex = Utils.binToHex(got_bytes)
    got_hex should equal(sample_hex)
  }
}
