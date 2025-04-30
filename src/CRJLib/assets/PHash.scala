/*
 * Copyright (c) 2025 CJ Remillard
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package CRJLib.assets

import java.security.{NoSuchAlgorithmException, SecureRandom}
import java.security.spec.{InvalidKeySpecException, KeySpec}
import java.util.Base64
import java.util.regex.Pattern
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import scala.util.matching.Regex

/**
 * PHash class provides secure password hashing functionality using PBKDF2.
 * This class implements a secure password hashing scheme that is resistant
 * to brute-force attacks and rainbow table attacks.
 * 
 * Features:
 * - PBKDF2 with HMAC-SHA1
 * - Configurable cost factor
 * - Secure random salt generation
 * - Password verification
 * - Base64 encoding for storage
 * 
 * @author CJ Remillard
 * @version 1.0
 */
class PHash(private val cost: Int = PHash.DEFAULT_COST) {

  import PHash._

  require(cost >= 0 && cost <= 30, s"cost: $cost")

  private val random = new SecureRandom()

  /**
   * Hash a password string.
   * 
   * @param password Password to hash
   * @return Hashed password string
   */
  def hash(password: String): String = hash(password.toCharArray)

  /**
   * Hash a password character array.
   * This is the preferred method as it allows for secure password handling.
   * 
   * @param password Password as character array
   * @return Hashed password string in format: $31$cost$hash
   */
  def hash(password: Array[Char]): String = {
    val salt = new Array[Byte](SIZE / 8)
    random.nextBytes(salt)

    val dk = pbkdf2(password, salt, iterations(cost))
    val hash = salt ++ dk

    val encoder = Base64.getUrlEncoder.withoutPadding()
    s"$ID$cost$${encoder.encodeToString(hash)}"
  }

  /**
   * Verify a password against a hash.
   * 
   * @param password Password to verify
   * @param token Hash to verify against
   * @return true if password matches, false otherwise
   */
  def auth(password: Array[Char], token: String): Boolean = {
    val matcher = layout.pattern.matcher(token)
    if (!matcher.matches()) throw new IllegalArgumentException("Invalid token format")

    val iterations = PHash.iterations(matcher.group(1).toInt)
    val hash = Base64.getUrlDecoder.decode(matcher.group(2))

    val salt = hash.slice(0, SIZE / 8)
    val check = pbkdf2(password, salt, iterations)

    hash.slice(SIZE / 8, hash.length).sameElements(check)
  }

  /**
   * Verify a password string against a hash.
   * 
   * @param password Password to verify
   * @param token Hash to verify against
   * @return true if password matches, false otherwise
   */
  def auth(password: String, token: String): Boolean = auth(password.toCharArray, token)

  private def pbkdf2(password: Array[Char], salt: Array[Byte], iterations: Int): Array[Byte] = {
    val spec: KeySpec = new PBEKeySpec(password, salt, iterations, SIZE)
    try {
      val factory = SecretKeyFactory.getInstance(ALGORITHM)
      factory.generateSecret(spec).getEncoded
    } catch {
      case ex: NoSuchAlgorithmException =>
        throw new IllegalStateException(s"Missing algorithm: $ALGORITHM", ex)
      case ex: InvalidKeySpecException =>
        throw new IllegalStateException("Invalid SecretKeyFactory", ex)
    }
  }
}

object PHash {
  val ID: String = "$31$"
  val DEFAULT_COST: Int = 16
  private val ALGORITHM: String = "PBKDF2WithHmacSHA1"
  private val SIZE: Int = 128
  private val layout: Regex = raw"\$31\$(\d\d?)\$(.{43})".r

  def iterations(cost: Int): Int = 1 << cost
}