package com.example.fitcheck.data.repository

import android.util.Patterns
import com.example.fitcheck.data.dao.UserDao
import com.example.fitcheck.data.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

sealed class AuthResult {
    data class Success(val userId: Long) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthRepository(private val userDao: UserDao) {

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
        private const val ITERATIONS = 10000
        private const val KEY_LENGTH = 256
        private const val SALT_BYTE_SIZE = 16
    }

    private fun hashPassword(password: String, saltBytes: ByteArray? = null): Pair<String, String> {
        val salt = saltBytes ?: ByteArray(SALT_BYTE_SIZE).apply { SecureRandom().nextBytes(this) }
        val algorithm = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            "PBKDF2WithHmacSHA256"
        } else {
            "PBKDF2WithHmacSHA1"
        }
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance(algorithm)
        val hash = factory.generateSecret(spec).encoded
        
        val saltBase64 = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(salt)
        } else {
            android.util.Base64.encodeToString(salt, android.util.Base64.NO_WRAP)
        }
        val hashBase64 = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(hash)
        } else {
            android.util.Base64.encodeToString(hash, android.util.Base64.NO_WRAP)
        }
        return Pair(saltBase64, hashBase64)
    }

    private fun verifyPassword(password: String, saltBase64: String, expectedHashBase64: String): Boolean {
        try {
            if (saltBase64.isEmpty()) {
                // Fallback for legacy SHA-256 hashes if any
                val legacyHash = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
                    .joinToString("") { "%02x".format(it) }
                return MessageDigest.isEqual(legacyHash.toByteArray(), expectedHashBase64.toByteArray())
            }

            val salt = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Base64.getDecoder().decode(saltBase64)
            } else {
                android.util.Base64.decode(saltBase64, android.util.Base64.NO_WRAP)
            }
            val (_, actualHashBase64) = hashPassword(password, salt)
            
            val expectedBytes = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Base64.getDecoder().decode(expectedHashBase64)
            } else {
                android.util.Base64.decode(expectedHashBase64, android.util.Base64.NO_WRAP)
            }
            val actualBytes = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Base64.getDecoder().decode(actualHashBase64)
            } else {
                android.util.Base64.decode(actualHashBase64, android.util.Base64.NO_WRAP)
            }
            return MessageDigest.isEqual(expectedBytes, actualBytes)
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun register(name: String, email: String, password: String): AuthResult =
        withContext(Dispatchers.IO) {
            try {
                val trimmedName = name.trim()
                val trimmedEmail = email.trim().lowercase()

                if (trimmedName.isEmpty()) {
                    return@withContext AuthResult.Error("Họ tên không được để trống")
                }
                if (trimmedEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
                    return@withContext AuthResult.Error("Email không đúng định dạng")
                }
                if (password.length < MIN_PASSWORD_LENGTH) {
                    return@withContext AuthResult.Error("Mật khẩu phải có ít nhất $MIN_PASSWORD_LENGTH ký tự")
                }

                if (userDao.findByEmail(trimmedEmail) != null) {
                    return@withContext AuthResult.Error("Email đã tồn tại")
                }

                val (salt, hash) = hashPassword(password)
                val id = userDao.insert(
                    UserEntity(
                        fullName = trimmedName,
                        email = trimmedEmail,
                        passwordHash = hash,
                        passwordSalt = salt
                    )
                )
                AuthResult.Success(id)
            } catch (e: Exception) {
                AuthResult.Error("Lỗi hệ thống: ${e.localizedMessage ?: "Không thể đăng ký"}")
            }
        }

    suspend fun login(email: String, password: String): AuthResult =
        withContext(Dispatchers.IO) {
            try {
                val trimmedEmail = email.trim().lowercase()
                if (trimmedEmail.isEmpty() || password.isEmpty()) {
                    return@withContext AuthResult.Error("Vui lòng nhập đầy đủ email và mật khẩu")
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
                    return@withContext AuthResult.Error("Email không đúng định dạng")
                }

                val user = userDao.findByEmail(trimmedEmail)
                if (user == null || !verifyPassword(password, user.passwordSalt, user.passwordHash)) {
                    return@withContext AuthResult.Error("Sai email hoặc mật khẩu")
                }
                AuthResult.Success(user.id)
            } catch (e: Exception) {
                AuthResult.Error("Lỗi hệ thống: ${e.localizedMessage ?: "Không thể đăng nhập"}")
            }
        }

    fun observeUser(id: Long) = userDao.observeById(id)

    suspend fun updateInfo(id: Long, name: String, phone: String?, avatar: String?) =
        withContext(Dispatchers.IO) { userDao.updateInfo(id, name, phone, avatar) }
}
