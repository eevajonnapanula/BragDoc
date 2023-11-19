package com.eevajonna.bragdocument.openai

import android.util.Log
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.aallam.openai.client.RetryStrategy
import com.eevajonna.bragdocument.BuildConfig
import kotlin.time.Duration.Companion.seconds

class OpenAIService {
    private val config = OpenAIConfig(
        token = BuildConfig.OPENAI_API_KEY,
        retry = RetryStrategy(3),
        timeout = Timeout(socket = 120.seconds),
    )

    private val openAI = OpenAI(config)

    suspend fun getPerformanceReview(bragItemTexts: List<String>): String? {
        val prompt = "Could you summarize the following items for a self reflection for performance review about two or three sentences long? ${bragItemTexts.joinToString("\n")}"
        try {
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = "You are a helpful assistant helping with self reflections for performance reviews.",
                    ),
                    ChatMessage(
                        role = ChatRole.User,
                        content = prompt,
                    ),
                ),
            )
            val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)

            return completion.choices.last().message.content
        } catch (e: Exception) {
            Log.e("OpenAIService", "Error: $e")
            throw e
        }
    }
}
