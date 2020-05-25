// 2. Fixed XOR
//
// Write a function that takes two equal-length buffers and produces
// their XOR sum.
//
// The string:
//
// 1c0111001f010100061a024b53535009181c
//
// ... after hex decoding, when xor'd against:
//
// 686974207468652062756c6c277320657965
//
// ... should produce:
//
// 746865206b696420646f6e277420706c6179

import org.scalatest._
import org.scalatest.Matchers._
import crypto.ex2.Ex2
import crypto.utils.Utils

class Ex2Spec extends FunSuite with DiagrammedAssertions {
  val hexA = "1c0111001f010100061a024b53535009181c"
  val hexB = "686974207468652062756c6c277320657965"
  val xorSummedHex = "746865206b696420646f6e277420706c6179"

  test("xor sums byte arrays") {
    val gotSum = Utils.binToHex(Ex2.xorBytes(
      Utils.hexToBin(hexA),
      Utils.hexToBin(hexB),
    ))
    gotSum should equal(xorSummedHex)
  }
}
