package shark

import java.io.File
import okio.Buffer
import okio.BufferedSource
import okio.Source
import okio.buffer
import okio.source

/**
 * A [DualSourceProvider] that invokes [throwIfCanceled] before every read, allowing
 * cancellation of IO based work built on top by throwing an exception.
 */
class ThrowingCancelableFileSourceProvider(
  private val file: File,
  private val throwIfCanceled: Runnable
) : DualSourceProvider {

  override fun openStreamingSource(): BufferedSource {
    val realSource = file.inputStream().source()
    return object : Source by realSource {
      override fun read(
        sink: Buffer,
        byteCount: Long
      ): Long {
        throwIfCanceled.run()
        return realSource.read(sink, byteCount)
      }
    }.buffer()
  }

  override fun openRandomAccessSource(): RandomAccessSource {
    val channel = file.inputStream().channel
    return object : RandomAccessSource {
      override fun read(
        sink: Buffer,
        position: Long,
        byteCount: Long
      ): Long {
        throwIfCanceled.run()
        return channel.transferTo(position, byteCount, sink)
      }

      override fun close() {
        try {
          channel.close()
        } catch (ignored: Throwable) {
          SharkLog.d(ignored) { "Failed to close file, ignoring" }
        }
      }
    }
  }
}
