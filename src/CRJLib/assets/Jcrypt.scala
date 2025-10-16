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

import java.io._
import java.security.spec.KeySpec
import javax.crypto.{Cipher, CipherOutputStream, SecretKey, SecretKeyFactory}
import javax.crypto.spec.{PBEKeySpec, SecretKeySpec}

/**
 * Jcrypt class provides file encryption and decryption functionality using AES encryption.
 * This class implements secure file encryption with password-based key derivation
 * and supports both encryption and decryption of files.
 * 
 * Features:
 * - AES encryption/decryption
 * - Password-based key derivation (PBKDF2)
 * - File encryption/decryption
 * - Secure key generation
 * - Automatic file cleanup
 * 
 * @author CJ Remillard
 * @version 1.0
 */
class Jcrypt {

  // FIELDS //
  //
  /** The encryption algorithm used (AES) */
  val ALGO: String = "AES"

  /**
   * Generate a SecretKey from a password string using PBKDF2.
   * This method creates a cryptographically secure key suitable for AES encryption.
   * 
   * @param password Password to use for key generation
   * @return AES-compatible SecretKey
   * @throws Exception If key generation fails
   * 
   * Note: Uses a fixed salt for demonstration. In production, use a unique salt per file.
   */
  def genKey(password: String): SecretKey = {
    val salt = "RandomSalt123".getBytes // Use a consistent salt (for production, consider storing salt with encrypted files)
    val spec: KeySpec = new PBEKeySpec(password.toCharArray, salt, 65536, 256)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
    val keyBytes = factory.generateSecret(spec).getEncoded
    new SecretKeySpec(keyBytes, ALGO) // Convert to AES key
  }

  /**
   * Encrypt a file using AES encryption.
   * The original file is deleted after successful encryption.
   * 
   * @param inputPath Path to file to encrypt
   * @param sk Secret key for encryption
   * @param exten Extension for encrypted file
   * @throws Exception If encryption fails
   * 
   * Example:
   * encFile("secret.txt", key, ".enc") creates "secret.txt.enc"
   */
  def encFile(inputPath: String, sk: SecretKey, exten: String): Unit = {
    val in = new File(inputPath)
    if (!in.exists()) {
      throw new FileNotFoundException(s"Input file not found: ${in.getAbsolutePath}")
    }

    val encFile = new File(in.getParent, in.getName + exten)
    procFile(Cipher.ENCRYPT_MODE, in, encFile, sk)
    in.delete()
  }

  /**
   * Decrypt a file using AES decryption.
   * The encrypted file is deleted after successful decryption.
   * 
   * @param encPath Path to encrypted file to decrypt
   * @param sk Secret key for decryption
   * @param exten Extension of encrypted file
   * @throws Exception If decryption fails
   * 
   * Example:
   * decFile("secret.txt.enc", key, ".enc") restores "secret.txt"
   */
  def decFile(encPath: String, sk: SecretKey, exten: String): Unit = {
    val encFile = new File(encPath)
    if (!encFile.exists()) {
      throw new FileNotFoundException(s"Encrypted file not found: ${encFile.getAbsolutePath}")
    }

    if (!encFile.getName.endsWith(exten)) {
      throw new IllegalArgumentException(s"File does not have $exten extension: ${encFile.getName}")
    }

    val org = encFile.getName.replace(exten, "")
    val decFile = new File(encFile.getParent, org)
    procFile(Cipher.DECRYPT_MODE, encFile, decFile, sk)
    encFile.delete()
  }

  /**
   * Process a file for encryption or decryption.
   * This internal method handles the actual encryption/decryption process.
   * 
   * @param cipherMode Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
   * @param inFile Input file
   * @param outFile Output file
   * @param sk Secret key
   * @throws Exception If process fails
   */
  private def procFile(cipherMode: Int, inFile: File, outFile: File, sk: SecretKey): Unit = {
    val cipher = Cipher.getInstance(ALGO)
    cipher.init(cipherMode, sk)

    val fis = new FileInputStream(inFile)
    val fos = new FileOutputStream(outFile)
    val cos = new CipherOutputStream(fos, cipher)

    try {
      val buf = new Array[Byte](4096)
      Iterator
        .continually(fis.read(buf))
        .takeWhile(_ != -1)
        .foreach(bRead => cos.write(buf, 0, bRead))
    } finally {
      fis.close()
      cos.close()
    }
  }
}