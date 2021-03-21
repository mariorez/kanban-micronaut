package org.seariver.kanbanboard.application.usecase.write

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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
        verify(repository).create(any<Bucket>())
    }
}
