/*
 * MIT License
 *
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

package CRJLib

import java.awt.Desktop
import java.io.{Console, File, IOException}
import java.net.{URI, URISyntaxException}
import java.security.SecureRandom
import java.util.Scanner
import CRJLib.assets.{Jcrypt, PHash}

/**
 * Std class provides utility methods for console I/O, random number generation,
 * and file/URL handling. This version links only to PHash and Jcrypt.
 * 
 * @author CJ Remillard
 * @version 1.0
 */
class Std {

  // FIELDS //
  private val scanner = new Scanner(System.in)
  private val random = new scala.util.Random()
  private val secureRandom = new SecureRandom()

  // Linked assets
  val jcrypt = new Jcrypt()
  val phash = new PHash()

  // CONSOLE IO //
  /**
   * Prompt the user with a string and capture input.
   * 
   * @param s String to prompt to the console
   * @return String captured from input
   */
  def cin(s: String): String = {
    cout(s)
    scanner.next()
  }

  /**
   * Print an object to the console.
   * 
   * @param o Object to print
   */
  def cout(o: Any): Unit = print(o)

  /**
   * Print an object to the console with a newline.
   * 
   * @param o Object to print
   */
  def coutln(o: Any): Unit = println(o)

  /**
   * Log an object to the console (alias for coutln).
   * 
   * @param o Object to log
   */
  def log(o: Any): Unit = coutln(o)

  // RANDOMS //
  /**
   * Generate a pseudo-random integer within a range.
   * 
   * @param min Minimum integer in pseudo-random bounds
   * @param max Maximum integer in pseudo-random bounds
   * @return Pseudo-random integer
   */
  def prand(min: Int, max: Int): Int = {
    if (max - min > 0) random.nextInt(max - min) + min else min
  }

  /**
   * Generate a secure-random integer within a range.
   * 
   * @param min Minimum integer in secure-random bounds
   * @param max Maximum integer in secure-random bounds
   * @return Secure-random integer
   */
  def srand(min: Int, max: Int): Int = {
    if (max - min > 0) secureRandom.nextInt(max - min) + min else min
  }

  /**
   * Get the instance of SecureRandom.
   * 
   * @return SecureRandom instance
   */
  def getSrand: SecureRandom = secureRandom

  // URL LINKS //
  /**
   * Open a URL in the default browser.
   * 
   * @param urlLink URL link to open
   * @return true if successful, false otherwise
   */
  def openUrl(urlLink: String): Boolean = {
    try {
      val uri = new URI(urlLink)
      if (Desktop.isDesktopSupported && Desktop.getDesktop.isSupported(Desktop.Action.BROWSE)) {
        Desktop.getDesktop.browse(uri)
        true
      } else {
        false
      }
    } catch {
      case _: URISyntaxException | _: IOException =>
        false
    }
  }

  // OPEN FILE //
  /**
   * Open a file in the default application.
   * 
   * @param file File to open
   * @return true if successful, false otherwise
   */
  def openFile(file: File): Boolean = {
    try {
      if (Desktop.isDesktopSupported && Desktop.getDesktop.isSupported(Desktop.Action.OPEN)) {
        Desktop.getDesktop.open(file)
        true
      } else {
        false
      }
    } catch {
      case _: IOException =>
        false
    }
  }

  /**
   * Securely capture input from the console (e.g., for passwords).
   * 
   * @param prompt Prompt to display
   * @return Captured input as a string
   */
  def seccin(prompt: String): String = {
    val console: Console = System.console()
    if (console != null) {
      val passwordChars = console.readPassword(prompt)
      new String(passwordChars)
    } else {
      log("Failed to use Console.readPassword()!")
      ""
    }
  }
}