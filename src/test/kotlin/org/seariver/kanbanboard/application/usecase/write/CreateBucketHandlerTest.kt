package org.seariver.kanbanboard.application.usecase.write

import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.seariver.kanbanboard.application.domain.Bucket
import org.seariver.kanbanboard.application.output.BucketRepository
import java.util.*

class CreateBucketHandlerTest {

    @Test
    fun `GIVEN valid data MUST create a Bucket`() {

        // GIVEN
        val bucketId = UUID.randomUUID()
        val position = 1.5
        val name = "TODO"
        val command = CreateBucketCommand(bucketId, position, name)
        val repository = mock<BucketRepository>()

        // WHEN
        val handler = CreateBucketHandler(repository)
        handler.handle(command)

        // THEN
        argumentCaptor<Bucket>().apply {
            verify(repository).create(capture())
            assertThat(bucketId).isEqualTo(firstValue.bucketId)
            assertThat(position).isEqualTo(firstValue.position)
            assertThat(name).isEqualTo(firstValue.name)
        }
    }
}
