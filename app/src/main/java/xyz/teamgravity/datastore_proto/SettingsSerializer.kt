package xyz.teamgravity.datastore_proto

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object SettingsSerializer : Serializer<SettingsModel> {

    override val defaultValue: SettingsModel
        get() = SettingsModel()

    override suspend fun readFrom(input: InputStream): SettingsModel {
        return try {
            Json.decodeFromString(
                deserializer = SettingsModel.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: SettingsModel, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = SettingsModel.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}