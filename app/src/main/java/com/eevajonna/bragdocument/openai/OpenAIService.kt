package com.eevajonna.bragdocument.openai

import android.content.Context
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
import com.eevajonna.bragdocument.R
import com.eevajonna.bragdocument.utils.Language
import kotlin.time.Duration.Companion.seconds

class OpenAIService {
    private val config = OpenAIConfig(
        token = BuildConfig.OPENAI_API_KEY,
        retry = RetryStrategy(3),
        timeout = Timeout(socket = 120.seconds),
    )

    private val openAI = OpenAI(config)

    suspend fun getPerformanceReview(context: Context, bragItemTexts: List<String>, language: Language): String? {
        val sentences = when (bragItemTexts.count()) {
            in 3..5 -> 3
            in 5..10 -> 4
            else -> 5
        }
        val promptResId = if (language == Language.EN) R.string.prompt else R.string.prompt_fi
        val prompt = context.getString(promptResId, sentences, bragItemTexts.joinToString("\n"))

        val systemPromptResId = if (language == Language.EN) R.string.system_prompt else R.string.system_prompt_fi

        try {
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId(MODEL),
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = context.getString(systemPromptResId),
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
            return context.getString(R.string.error_text, e.message)
        }
    }
}

const val MODEL = "gpt-3.5-turbo-1106"
